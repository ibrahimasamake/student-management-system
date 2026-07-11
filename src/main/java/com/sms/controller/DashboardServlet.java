package com.sms.controller;

import java.io.IOException;
import java.sql.SQLException;

import com.sms.service.DepartmentService;
import com.sms.service.StudentService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private StudentService studentService;
    private DepartmentService departmentService;

    @Override
    public void init() {
        studentService = new StudentService();
        departmentService = new DepartmentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int totalStudents = studentService.getStudentCount();
            int totalDepartments = departmentService.getDepartmentCount();
            var recentStudents = studentService.getRecentStudents(5);

            req.setAttribute("totalStudents", totalStudents);
            req.setAttribute("totalDepartments", totalDepartments);
            req.setAttribute("recentStudents", recentStudents);
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading dashboard", e);
        }
    }
}
