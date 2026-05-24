package com.polls.filter;

import com.polls.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/polls/create")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedUser") : null;

        if (user != null && "ADMIN".equals(user.getRole())) {
            chain.doFilter(request, response);
        } else {
            // If Ajax request, return JSON error
            String accept = req.getHeader("Accept");
            if (accept != null && accept.contains("application/json")) {
                resp.setContentType("application/json");
                resp.setStatus(403);
                resp.getWriter().print("{\"success\":false,\"message\":\"Admin access required\"}");
            } else {
                resp.sendRedirect(req.getContextPath() + "/vote");
            }
        }
    }

    @Override public void init(FilterConfig filterConfig) {}
    @Override public void destroy() {}
}
