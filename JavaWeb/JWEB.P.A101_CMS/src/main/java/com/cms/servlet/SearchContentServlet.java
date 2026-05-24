package com.cms.servlet;

import com.cms.dao.ContentDAO;
import com.cms.model.Content;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/search-content")
public class SearchContentServlet extends HttpServlet {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("keyword");
        HttpSession session = req.getSession();
        int authorID = (Integer)session.getAttribute("userId");
        System.out.println("author" + authorID);
        if (search == null) {
            search = "";
        }
        ContentDAO dao = new ContentDAO();
        try {
            List<Content> list = dao.search(search);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            mapper.writeValue(resp.getWriter(), list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
