package com.cms.servlet;

import com.cms.dao.MemberDAO;
import com.cms.model.Content;
import com.cms.model.Member;
import com.cms.utils.DBconnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rePassword = req.getParameter("rePassword");

        boolean isValid = true;
        String errorMsg = "";


        if (username.isEmpty()) {
            errorMsg = "Username is required";
            isValid = false;
        } else if (username.length() < 3 || username.length() > 30) {
            errorMsg = "Username must be between 3 and 30 characters";
            isValid = false;
        } else if (email.isEmpty()) {
            errorMsg = "Email is required";
            isValid = false;
        } else if (email.length() < 5) {
            errorMsg = "Email must be at least 5 characters";
            isValid = false;
        } else if (password.isEmpty()) {
            errorMsg = "Password is required";
            isValid = false;
        } else if (password.length() < 8 || password.length() > 30) {
            errorMsg = "Password must be between 8 and 30 characters";
            isValid = false;
        } else if (!password.equals(rePassword)) {
            errorMsg = "Passwords do not match";
            isValid = false;
        }

        if (!isValid) {
            req.setAttribute("errorMessage", errorMsg);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        } else {
            MemberDAO memberDAO = new MemberDAO();

            if (memberDAO.checkExist(username, email)) {
                req.setAttribute("errorMessage", "Username or Email already exists!");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
            }

            Member newMember = new Member();
            newMember.setUsername(username);
            newMember.setEmail(email);
            newMember.setPassword(password);

            boolean success = memberDAO.register(newMember);

            if (success) {
                Member registeredUser = memberDAO.login(email, password);


                if (registeredUser != null) {
                    HttpSession session = req.getSession(true);
                    session.setAttribute("userId", registeredUser.getId());
                    session.setAttribute("user", registeredUser);

                    resp.sendRedirect(req.getContextPath() + "/login");
                }
            } else {
                req.setAttribute("errorMessage", "Registration failed. Please try again.");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
            }
        }


    }

    public List<Content> searchContent(String keyword) {
        List<Content> list = new ArrayList<>();
        String sql = "SELECT * FROM Content WHERE Title LIKE ? OR Brief LIKE ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String searchKey = "%" + keyword + "%";
            ps.setString(1, searchKey);
            ps.setString(2, searchKey);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Content c = new Content();
                c.setId(rs.getInt("id"));
                c.setAuthorID(rs.getInt("AuthorID"));
                c.setTitle(rs.getString("Title"));
                c.setBrief(rs.getString("Brief"));
                c.setContent(rs.getString("Content"));

                c.setSort(rs.getString("Sort"));

                c.setCreateDate(rs.getTimestamp("CreateDate").toLocalDateTime());
                c.setUpdateTime(rs.getTimestamp("UpdateTime").toLocalDateTime());

                list.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
