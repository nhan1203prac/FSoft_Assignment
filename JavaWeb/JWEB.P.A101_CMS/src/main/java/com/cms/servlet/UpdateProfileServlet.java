package com.cms.servlet;

import com.cms.dao.MemberDAO;
import com.cms.model.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/edit-profile")
public class UpdateProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Member member = (Member) session.getAttribute("user");
        if (member == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        req.setAttribute("member", member);
        System.out.println("member: " + member);
        req.getRequestDispatcher("edit-profile.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Member member = (Member) session.getAttribute("user");

        if (member == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String phone = req.getParameter("phoneNumber");
        String desc = req.getParameter("desc");

        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setPhone(phone);
        member.setDescription(desc);

        MemberDAO dao = new MemberDAO();
        try {
            boolean isUpdated = dao.updateProfile(member);
            if (isUpdated) {
                session.setAttribute("user", member);
                req.setAttribute("successMessage", "Update profile successfully!");
            } else {
                req.setAttribute("errorMessage", "Update failed. Please try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("errorMessage", "Error: " + e.getMessage());
        }

        req.setAttribute("member", member);
        req.getRequestDispatcher("edit-profile.jsp").forward(req, resp);
    }
}
