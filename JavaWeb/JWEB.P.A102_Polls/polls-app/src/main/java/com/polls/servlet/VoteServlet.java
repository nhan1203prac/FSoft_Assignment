package com.polls.servlet;

import com.polls.dao.AnswerDAO;
import com.polls.dao.PollDAO;
import com.polls.dao.UserDAO;
import com.polls.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    private PollDAO pollDAO = new PollDAO();
    private UserDAO userDAO = new UserDAO();
    private AnswerDAO answerDAO = new AnswerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Home/Vote page: show all active polls
        List<Poll> polls = pollDAO.findAllWithDetails();
        req.setAttribute("polls", polls);
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        // Must be logged in to vote
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.setStatus(403);
            out.print("{\"success\":false,\"message\":\"Please login before voting\"}");
            return;
        }

        User loggedUser = (User) session.getAttribute("loggedUser");

        // Get voter user (record IP)
        String ipAddress = req.getRemoteAddr();
        User voter = userDAO.saveOrGet(ipAddress);

        // Server-side validation: process answer IDs submitted
        String[] answerIds = req.getParameterValues("answerIds[]");
        if (answerIds == null || answerIds.length == 0) {
            resp.setStatus(400);
            out.print("{\"success\":false,\"message\":\"Please select at least one answer\"}");
            return;
        }

        try {
            for (String idStr : answerIds) {
                Long answerId = Long.parseLong(idStr.trim());
                Answer answer = answerDAO.findById(answerId);
                if (answer != null) {
                    answerDAO.saveVote(answer, voter);
                }
            }
            out.print("{\"success\":true,\"message\":\"Thank you for voting!\"}");
        } catch (Exception e) {
            resp.setStatus(500);
            out.print("{\"success\":false,\"message\":\"Error saving vote: " + e.getMessage() + "\"}");
        }
    }
}
