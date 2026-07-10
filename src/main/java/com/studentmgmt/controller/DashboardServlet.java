package com.studentmgmt.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.studentmgmt.dao.StudentDAO;
import com.studentmgmt.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int totalStudents = studentDAO.getTotalStudents();
            int newThisYear = studentDAO.getStudentsEnrolledThisYear();
            Map<String, Integer> statusCounts = studentDAO.getStatusCounts();
            Map<String, Integer> courseCounts = studentDAO.getCourseCounts();
            List<Student> recentStudents = studentDAO.getRecentStudents(5);

            req.setAttribute("totalStudents", totalStudents);
            req.setAttribute("newThisYear", newThisYear);
            req.setAttribute("statusCounts", statusCounts);
            req.setAttribute("courseCounts", courseCounts);
            req.setAttribute("recentStudents", recentStudents);
            req.getRequestDispatcher("/views/dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading dashboard", e);
        }
    }
}
