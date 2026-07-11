package com.sms.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

import com.sms.model.Student;
import com.sms.service.DepartmentService;
import com.sms.service.StudentService;
import com.sms.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

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
                deleteStudent(req, resp);
                break;
            case "view":
                viewStudent(req, resp);
                break;
            case "search":
                searchStudents(req, resp);
                break;
            default:
                listStudents(req, resp);
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
                createStudent(req, resp);
                break;
            case "update":
                updateStudent(req, resp);
                break;
            default:
                listStudents(req, resp);
                break;
        }
    }

    private void listStudents(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("students", studentService.getAllStudents());
            req.getRequestDispatcher("/WEB-INF/views/students/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error listing students", e);
        }
    }

    private void showNewForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("departments", departmentService.getAllDepartments());
            req.getRequestDispatcher("/WEB-INF/views/students/form.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading form", e);
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Student student = studentService.getStudentById(id);
            if (student == null) {
                resp.sendRedirect(req.getContextPath() + "/students?error=notfound");
                return;
            }
            req.setAttribute("student", student);
            req.setAttribute("departments", departmentService.getAllDepartments());
            req.getRequestDispatcher("/WEB-INF/views/students/form.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error loading edit form", e);
        }
    }

    private void viewStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            Student student = studentService.getStudentById(id);
            if (student == null) {
                resp.sendRedirect(req.getContextPath() + "/students?error=notfound");
                return;
            }
            req.setAttribute("student", student);
            req.getRequestDispatcher("/WEB-INF/views/students/details.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error viewing student", e);
        }
    }

    private void searchStudents(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String keyword = req.getParameter("keyword");
            if (keyword != null && !keyword.trim().isEmpty()) {
                req.setAttribute("students", studentService.searchStudents(keyword));
                req.setAttribute("keyword", keyword);
            } else {
                req.setAttribute("students", studentService.getAllStudents());
            }
            req.getRequestDispatcher("/WEB-INF/views/students/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error searching students", e);
        }
    }

    private void createStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String rollNumber = req.getParameter("rollNumber");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String gender = req.getParameter("gender");
            String dobStr = req.getParameter("dateOfBirth");
            String departmentIdStr = req.getParameter("departmentId");
            String address = req.getParameter("address");

            Map<String, String> errors = ValidationUtil.validateStudent(
                    rollNumber, firstName, lastName, email, departmentIdStr);

            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                req.setAttribute("departments", departmentService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/students/form.jsp").forward(req, resp);
                return;
            }

            Student student = new Student();
            student.setRollNumber(rollNumber.trim());
            student.setFirstName(firstName.trim());
            student.setLastName(lastName.trim());
            student.setEmail(email.trim());
            student.setPhone(phone != null ? phone.trim() : null);
            student.setGender(gender);
            student.setDateOfBirth(dobStr != null && !dobStr.isEmpty() ? LocalDate.parse(dobStr) : null);
            student.setDepartmentId(Long.parseLong(departmentIdStr));
            student.setAddress(address != null ? address.trim() : null);

            Map<String, String> serviceErrors = studentService.addStudent(student);
            if (!serviceErrors.isEmpty()) {
                req.setAttribute("errors", serviceErrors);
                req.setAttribute("departments", departmentService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/students/form.jsp").forward(req, resp);
                return;
            }

            resp.sendRedirect(req.getContextPath() + "/students?success=created");
        } catch (SQLException e) {
            throw new ServletException("Error creating student", e);
        }
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            String rollNumber = req.getParameter("rollNumber");
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String phone = req.getParameter("phone");
            String gender = req.getParameter("gender");
            String dobStr = req.getParameter("dateOfBirth");
            String departmentIdStr = req.getParameter("departmentId");
            String address = req.getParameter("address");

            Map<String, String> errors = ValidationUtil.validateStudent(
                    rollNumber, firstName, lastName, email, departmentIdStr);

            if (!errors.isEmpty()) {
                req.setAttribute("errors", errors);
                Student existing = new Student();
                existing.setId(id);
                existing.setRollNumber(rollNumber);
                existing.setFirstName(firstName);
                existing.setLastName(lastName);
                existing.setEmail(email);
                existing.setPhone(phone);
                existing.setGender(gender);
                existing.setDateOfBirth(dobStr != null && !dobStr.isEmpty() ? LocalDate.parse(dobStr) : null);
                existing.setDepartmentId(departmentIdStr != null ? Long.parseLong(departmentIdStr) : null);
                existing.setAddress(address);
                req.setAttribute("student", existing);
                req.setAttribute("departments", departmentService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/students/form.jsp").forward(req, resp);
                return;
            }

            Student student = new Student();
            student.setId(id);
            student.setRollNumber(rollNumber.trim());
            student.setFirstName(firstName.trim());
            student.setLastName(lastName.trim());
            student.setEmail(email.trim());
            student.setPhone(phone != null ? phone.trim() : null);
            student.setGender(gender);
            student.setDateOfBirth(dobStr != null && !dobStr.isEmpty() ? LocalDate.parse(dobStr) : null);
            student.setDepartmentId(Long.parseLong(departmentIdStr));
            student.setAddress(address != null ? address.trim() : null);

            Map<String, String> serviceErrors = studentService.updateStudent(student);
            if (!serviceErrors.isEmpty()) {
                req.setAttribute("errors", serviceErrors);
                req.setAttribute("student", student);
                req.setAttribute("departments", departmentService.getAllDepartments());
                req.getRequestDispatcher("/WEB-INF/views/students/form.jsp").forward(req, resp);
                return;
            }

            resp.sendRedirect(req.getContextPath() + "/students?success=updated");
        } catch (SQLException e) {
            throw new ServletException("Error updating student", e);
        }
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(req.getParameter("id"));
            studentService.deleteStudent(id);
            resp.sendRedirect(req.getContextPath() + "/students?success=deleted");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error deleting student", e);
        }
    }
}
