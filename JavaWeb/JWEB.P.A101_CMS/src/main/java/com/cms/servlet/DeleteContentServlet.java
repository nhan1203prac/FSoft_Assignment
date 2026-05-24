package com.cms.servlet;

import com.cms.dao.ContentDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-content")
public class DeleteContentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int authorId = (Integer) req.getSession().getAttribute("userId");

        ContentDAO dao = new ContentDAO();
        try {
            dao.delete(id, authorId);
            resp.getWriter().write("success");
        } catch (Exception e) {
            resp.setStatus(500);
        }
    }
}
