package com.cms.servlet;

import com.cms.dao.ContentDAO;
import com.cms.model.Content;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/form-content")
public class FormContentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                ContentDAO dao = new ContentDAO();
                Content content = dao.findById(id);
                req.setAttribute("content", content);
                req.setAttribute("formTitle", "Edit Content");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            req.setAttribute("content", new Content());
            req.setAttribute("formTitle", "Add Content");
        }

        req.getRequestDispatcher("form-content.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String title = req.getParameter("title");
        String brief = req.getParameter("brief");
        String contentText = req.getParameter("content");

        String errorMessage = null;

        if (title == null || title.trim().length() < 10 || title.trim().length() > 200) {
            errorMessage = "Title must be between 10 and 200 characters.";
        } else if (brief == null || brief.trim().length() < 30 || brief.trim().length() > 150) {
            errorMessage = "Brief must be between 30 and 150 characters.";
        } else if (contentText == null || contentText.trim().length() < 50 || contentText.trim().length() > 1000) {
            errorMessage = "Content must be between 50 and 1000 characters.";
        }

        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
            req.getRequestDispatcher("form-content.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        Integer authorID = (Integer) session.getAttribute("userId");

        if (authorID == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Content content = new Content();
        content.setAuthorID(authorID);
        content.setTitle(title.trim());
        content.setBrief(brief.trim());
        content.setContent(contentText.trim());

        ContentDAO contentDAO = new ContentDAO();
        boolean success;
        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);
            content.setId(id);
            success = contentDAO.update(content);
        } else {
            success = contentDAO.create(content);
        }

        if (success) {
            resp.sendRedirect("view-content.jsp");
        } else {
            req.setAttribute("errorMessage", "Database error: Failed to add content!");
            req.getRequestDispatcher("form-content.jsp").forward(req, resp);
        }
    }
}
