package com.polls.servlet;

import com.polls.dao.PollDAO;
import com.polls.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/polls/*")
public class PollServlet extends HttpServlet {

    private PollDAO pollDAO = new PollDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String pathInfo = req.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/list")) {
                String status = req.getParameter("status");
                List<Poll> polls = (status != null && !status.isEmpty())
                        ? pollDAO.findByStatus(status.toUpperCase())
                        : pollDAO.findAll();
                req.setAttribute("polls", polls);
                req.setAttribute("currentStatus", status != null ? status.toUpperCase() : "ALL");
                req.getRequestDispatcher("/WEB-INF/views/list.jsp").forward(req, resp);

            } else if (pathInfo.equals("/create")) {
                req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);

            } else if (pathInfo.startsWith("/results/")) {
                Long pollId = Long.parseLong(pathInfo.substring("/results/".length()));
                Poll poll = pollDAO.findByIdWithDetails(pollId);
                req.setAttribute("poll", poll);
                req.getRequestDispatcher("/WEB-INF/views/results.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String pathInfo = req.getPathInfo();

        if ("/create".equals(pathInfo)) {
            handleCreate(req, resp);
        } else if (pathInfo != null && pathInfo.startsWith("/delete/")) {
            handleDelete(req, resp, pathInfo);
        } else if (pathInfo != null && pathInfo.startsWith("/close/")) {
            handleClose(req, resp, pathInfo);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/delete/")) {
            handleDelete(req, resp, pathInfo);
        }
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.setStatus(403);
            out.print("{\"success\":false,\"message\":\"Please login as admin to create polls\"}");
            return;
        }
        User loggedUser = (User) session.getAttribute("loggedUser");
        if (!"ADMIN".equals(loggedUser.getRole())) {
            resp.setStatus(403);
            out.print("{\"success\":false,\"message\":\"Only admin can create polls\"}");
            return;
        }

        String pollName = req.getParameter("pollName");
        if (pollName == null || pollName.trim().length() < 3 || pollName.trim().length() > 255) {
            resp.setStatus(400);
            out.print("{\"success\":false,\"message\":\"Poll name must be between 3 and 255 characters\"}");
            return;
        }

        String[] questionContents = req.getParameterValues("question[]");
        String[] mandatories = req.getParameterValues("mandatory[]");
        String[] multiples = req.getParameterValues("multiple[]");

        if (questionContents == null || questionContents.length == 0) {
            resp.setStatus(400);
            out.print("{\"success\":false,\"message\":\"At least one question is required\"}");
            return;
        }

        Poll poll = new Poll();
        poll.setName(pollName.trim());
        poll.setStatus("ACTIVE");

        Set<PollQuestion> pollQuestions = new LinkedHashSet<>();

        for (int i = 0; i < questionContents.length; i++) {
            String qContent = questionContents[i];

            if (qContent == null || qContent.trim().length() < 3) {
                resp.setStatus(400);
                out.print("{\"success\":false,\"message\":\"Question " + (i+1) + " must be at least 3 characters\"}");
                return;
            }

            Question question = new Question();
            question.setContent(qContent.trim());
            question.setRequired(mandatories != null && i < mandatories.length && "on".equals(mandatories[i]));
            question.setMultiple(multiples != null && i < multiples.length && "on".equals(multiples[i]));

            String[] answers = req.getParameterValues("answers[" + i + "][]");
            if (answers == null || answers.length == 0) {
                resp.setStatus(400);
                out.print("{\"success\":false,\"message\":\"Question " + (i+1) + " must have at least one answer\"}");
                return;
            }

            Set<QuestionAnswer> questionAnswers = new LinkedHashSet<>();
            for (int j = 0; j < answers.length; j++) {
                String aContent = answers[j];
                if (aContent == null || aContent.trim().length() < 1) continue;
                if (aContent.trim().length() < 3 || aContent.trim().length() > 200) {
                    resp.setStatus(400);
                    out.print("{\"success\":false,\"message\":\"Answer must be between 3 and 200 characters\"}");
                    return;
                }
                Answer answer = new Answer(aContent.trim());
                QuestionAnswer qa = new QuestionAnswer(question, answer, j);
                questionAnswers.add(qa);
            }

            if (questionAnswers.isEmpty()) {
                resp.setStatus(400);
                out.print("{\"success\":false,\"message\":\"Question " + (i+1) + " must have at least one valid answer\"}");
                return;
            }

            question.setQuestionAnswers(questionAnswers);
            PollQuestion pq = new PollQuestion(poll, question, i);
            pollQuestions.add(pq);
        }

        poll.setPollQuestions(pollQuestions);
        pollDAO.save(poll);

        out.print("{\"success\":true,\"message\":\"Poll created successfully\",\"pollId\":" + poll.getId() + "}");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp, String pathInfo)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.setStatus(403);
            out.print("{\"success\":false,\"message\":\"Unauthorized\"}");
            return;
        }

        try {
            Long pollId = Long.parseLong(pathInfo.replace("/delete/", ""));
            boolean deleted = pollDAO.delete(pollId);
            if (deleted) {
                out.print("{\"success\":true,\"message\":\"Poll deleted successfully\"}");
            } else {
                resp.setStatus(404);
                out.print("{\"success\":false,\"message\":\"Poll not found\"}");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            out.print("{\"success\":false,\"message\":\"Invalid poll ID\"}");
        }
    }

    private void handleClose(HttpServletRequest req, HttpServletResponse resp, String pathInfo)
            throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            resp.setStatus(403);
            out.print("{\"success\":false,\"message\":\"Unauthorized\"}");
            return;
        }

        try {
            Long pollId = Long.parseLong(pathInfo.replace("/close/", ""));
            pollDAO.updateStatus(pollId, "CLOSED");
            out.print("{\"success\":true,\"message\":\"Poll closed\"}");
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            out.print("{\"success\":false,\"message\":\"Invalid poll ID\"}");
        }
    }
}
