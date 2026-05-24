package com.polls.servlet;

import com.polls.dao.UserDAO;
import com.polls.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.trim().isEmpty()) {
            resp.setStatus(400);
            out.print("{\"success\":false,\"message\":\"Username is required\"}");
            return;
        }
        if (password == null || password.trim().isEmpty()) {
            resp.setStatus(400);
            out.print("{\"success\":false,\"message\":\"Password is required\"}");
            return;
        }
        if (username.trim().length() < 3) {
            resp.setStatus(400);
            out.print("{\"success\":false,\"message\":\"Username must be at least 3 characters\"}");
            return;
        }

        User user = userDAO.findByUsernameAndPassword(username.trim(), password.trim());
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("loggedUser", user);
            out.print("{\"success\":true,\"username\":\"" + user.getUsername()
                    + "\",\"role\":\"" + user.getRole() + "\"}");
        } else {
            resp.setStatus(401);
            out.print("{\"success\":false,\"message\":\"Wrong username or password\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Logout
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/");
    }
}
