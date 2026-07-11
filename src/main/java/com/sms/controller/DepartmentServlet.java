package com.sms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import com.sms.model.Department;
import com.sms.service.DepartmentService;
import com.sms.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {

    private DepartmentService departmentService;

    @Override
    public void init() {
        departmentService = new DepartmentService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
			action = "list";
		}

        switch (action) {
            case "new":
                showNewForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteDepartment(req, resp);
                break;
            default:
                listDepartments(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
			action = "list";
		}

        switch (action) {
            case "create":
                createDepartment(req, resp);
                break;
            case "update":
                updateDepartment(req, resp);
                break;
            default:
                listDepartments(req, resp);
                break;
        }
    }

    private void listDepartments(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("departments", departmentService.getAllDepartments());
            req.getRequestDispatcher("/WEB-INF/views/departments/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error listing departments", e);
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/departments/form.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Department department = departmentService.getDepartmentById(id);
            if (department == null) {
                resp.sendRedirect(req.getContextPath() + "/departments?error=notfound");
                return;
            }
            req.setAttribute("department", department);
            req.getRequestDispatcher("/WEB-INF/views/departments/form.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error loading edit form", e);
        }
    }

    private void createDepartment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            String description = req.getParameter("description");

            Map<String, String> errors = ValidationUtil.validateDepartment(name);
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                req.getRequestDispatcher("/WEB-INF/views/departments/form.jsp").forward(req, resp);
                return;
            }

            Department department = new Department();
            department.setName(name.trim());
            department.setDescription(description != null ? description.trim() : null);

            Map<String, String> serviceErrors = departmentService.addDepartment(department);
            if (!serviceErrors.isEmpty()) {
                req.setAttribute("errors", serviceErrors);
                req.getRequestDispatcher("/WEB-INF/views/departments/form.jsp").forward(req, resp);
                return;
            }

            resp.sendRedirect(req.getContextPath() + "/departments?success=created");
        } catch (SQLException e) {
            throw new ServletException("Error creating department", e);
        }
    }

    private void updateDepartment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String name = req.getParameter("name");
            String description = req.getParameter("description");

            Map<String, String> errors = ValidationUtil.validateDepartment(name);
            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                Department dept = new Department();
                dept.setId(id);
                dept.setName(name);
                dept.setDescription(description);
                req.setAttribute("department", dept);
                req.getRequestDispatcher("/WEB-INF/views/departments/form.jsp").forward(req, resp);
                return;
            }

            Department department = new Department();
            department.setId(id);
            department.setName(name.trim());
            department.setDescription(description != null ? description.trim() : null);

            Map<String, String> serviceErrors = departmentService.updateDepartment(department);
            if (!serviceErrors.isEmpty()) {
                req.setAttribute("errors", serviceErrors);
                req.setAttribute("department", department);
                req.getRequestDispatcher("/WEB-INF/views/departments/form.jsp").forward(req, resp);
                return;
            }

            resp.sendRedirect(req.getContextPath() + "/departments?success=updated");
        } catch (SQLException e) {
            throw new ServletException("Error updating department", e);
        }
    }

    private void deleteDepartment(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String error = departmentService.deleteDepartment(id);
            if (error != null) {
                resp.sendRedirect(req.getContextPath() + "/departments?error=" + java.net.URLEncoder.encode(error, "UTF-8"));
            } else {
                resp.sendRedirect(req.getContextPath() + "/departments?success=deleted");
            }
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error deleting department", e);
        }
    }
}
