package com.hr.servlet;

import com.hr.dao.PersonalDAO;
import com.hr.model.Personal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


@WebServlet(name = "PersonServlet", urlPatterns = {"/person", "/search"})
public class PersonServlet extends HttpServlet {

    private final PersonalDAO dao = new PersonalDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uri = request.getServletPath();

        if ("/person".equals(uri)) {
            request.getRequestDispatcher("/WEB-INF/views/person.jsp")
                   .forward(request, response);

        } else if ("/search".equals(uri)) {
            String keyword = request.getParameter("firstName");
            List<Personal> results;

            if (keyword != null && !keyword.trim().isEmpty()) {
                results = dao.searchByFirstName(keyword.trim());
                request.setAttribute("keyword", keyword.trim());
            } else {
                results = dao.getAll();
            }

            request.setAttribute("visitors", results);
            request.getRequestDispatcher("/WEB-INF/views/search.jsp")
                   .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String firstName   = request.getParameter("firstName");
        String lastName    = request.getParameter("lastName");
        String gender      = request.getParameter("gender");
        String telephone   = request.getParameter("telephone");
        String email       = request.getParameter("email");
        String region      = request.getParameter("region");
        String[] hobbiesArr = request.getParameterValues("hobbies");
        String description = request.getParameter("description");

        boolean valid = true;
        StringBuilder errors = new StringBuilder();

        String namePattern  = "[a-zA-ZÀ-ỹ\\s]+";
        String phonePattern = "[0-9]+";
        String emailPattern = "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$";

        if (firstName == null || firstName.trim().isEmpty() || firstName.trim().length() > 20
                || !firstName.trim().matches(namePattern)) {
            valid = false;
            errors.append("Invalid First Name. ");
        }
        if (lastName == null || lastName.trim().isEmpty() || lastName.trim().length() > 20
                || !lastName.trim().matches(namePattern)) {
            valid = false;
            errors.append("Invalid Last Name. ");
        }
        if (telephone != null && !telephone.trim().isEmpty()) {
            if (telephone.trim().length() > 11 || !telephone.trim().matches(phonePattern)) {
                valid = false;
                errors.append("Invalid Telephone. ");
            }
        }
        if (email != null && !email.trim().isEmpty()) {
            if (email.trim().length() > 50 || !email.trim().matches(emailPattern)) {
                valid = false;
                errors.append("Invalid Email. ");
            }
        }
        if (region == null || region.trim().isEmpty()) {
            valid = false;
            errors.append("Region is mandatory. ");
        }
        if (description != null && description.trim().length() > 200) {
            valid = false;
            errors.append("Description exceeds 200 characters. ");
        }

        if (!valid) {
            request.setAttribute("errorMessage", errors.toString());
            request.setAttribute("formData", request.getParameterMap());
            request.getRequestDispatcher("/WEB-INF/views/person.jsp").forward(request, response);
            return;
        }

        String hobbies = "";
        if (hobbiesArr != null && hobbiesArr.length > 0) {
            hobbies = String.join(", ", hobbiesArr);
        }

        Personal p = new Personal();
        p.setFirstName(firstName.trim());
        p.setLastName(lastName.trim());
        p.setGender(gender != null ? gender : "Male");
        p.setTelephone(telephone != null ? telephone.trim() : "");
        p.setEmail(email != null ? email.trim() : "");
        p.setRegion(region.trim());
        p.setHobbies(hobbies);
        p.setDescription(description != null ? description.trim() : "");

        boolean success = dao.insert(p);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/search?registered=true");
        } else {
            request.setAttribute("errorMessage", "Database error. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/person.jsp").forward(request, response);
        }
    }
}
