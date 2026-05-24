package com.cms.servlet;

import com.cms.dao.MemberDAO;
import com.cms.model.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("cEmail")) {
                    req.setAttribute("rememberedEmail", cookie.getValue());
                }
            }
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("rememberMe");

        MemberDAO memberDAO = new MemberDAO();
        Member member = memberDAO.login(email, password);

        if (member != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", member.getId());
            session.setAttribute("user", member);

            if ("on".equals(rememberMe)) {
                Cookie cEmail = new Cookie("cEmail", email);
                cEmail.setMaxAge(60 * 60 * 24);
                resp.addCookie(cEmail);
            }else{
                Cookie cEmail = new Cookie("cEmail", "");
                cEmail.setMaxAge(0);
                resp.addCookie(cEmail);
            }

            resp.sendRedirect("view-content.jsp");
        } else {
            req.setAttribute("errorMessage", "Email hoặc mật khẩu không chính xác!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
