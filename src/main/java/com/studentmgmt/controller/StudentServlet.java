package com.studentmgmt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import com.studentmgmt.dao.StudentDAO;
import com.studentmgmt.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private StudentDAO studentDAO;
    private static final int PAGE_SIZE = 10;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
			action = "list";
		}

        switch (action) {
            case "add":
                showAddForm(req, resp);
                break;
            case "edit":
                showEditForm(req, resp);
                break;
            case "delete":
                deleteStudent(req, resp);
                break;
            case "export":
                exportCsv(req, resp);
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
            case "insert":
                insertStudent(req, resp);
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
            String search = req.getParameter("search");
            String course = req.getParameter("course");
            String status = req.getParameter("status");

            int page = 1;
            String pageStr = req.getParameter("page");
            if (pageStr != null) {
                try { page = Integer.parseInt(pageStr); } catch (NumberFormatException e) { page = 1; }
            }
            if (page < 1) {
				page = 1;
			}

            int totalStudents = studentDAO.countStudents(search, course, status);
            int totalPages = (int) Math.ceil((double) totalStudents / PAGE_SIZE);
            if (page > totalPages && totalPages > 0) {
				page = totalPages;
			}

            List<Student> students = studentDAO.getStudents(page, PAGE_SIZE, search, course, status);
            List<String> courses = studentDAO.getAllCourses();

            req.setAttribute("students", students);
            req.setAttribute("courses", courses);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("totalStudents", totalStudents);
            req.getRequestDispatcher("/views/list.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error listing students", e);
        }
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<String> courses = studentDAO.getAllCourses();
            req.setAttribute("courses", courses);
        } catch (SQLException e) {
            req.setAttribute("courses", List.of());
        }
        req.getRequestDispatcher("/views/add.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Student student = studentDAO.getStudentById(id);
            List<String> courses = studentDAO.getAllCourses();
            req.setAttribute("student", student);
            req.setAttribute("courses", courses);
            req.getRequestDispatcher("/views/edit.jsp").forward(req, resp);
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error showing edit form", e);
        }
    }

    private void insertStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            Student student = extractStudent(req);
            studentDAO.addStudent(student);
            resp.sendRedirect(req.getContextPath() + "/students?action=list");
        } catch (SQLException e) {
            throw new ServletException("Error adding student", e);
        }
    }

    private void updateStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Student student = extractStudent(req);
            student.setId(id);
            studentDAO.updateStudent(student);
            resp.sendRedirect(req.getContextPath() + "/students?action=list");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error updating student", e);
        }
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            studentDAO.deleteStudent(id);
            resp.sendRedirect(req.getContextPath() + "/students?action=list");
        } catch (SQLException | NumberFormatException e) {
            throw new ServletException("Error deleting student", e);
        }
    }

    private void exportCsv(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=students.csv");

        String search = req.getParameter("search");
        String course = req.getParameter("course");
        String status = req.getParameter("status");

        try {
            List<Student> students = studentDAO.getAllStudentsForExport(search, course, status);
            PrintWriter out = resp.getWriter();
            out.println("ID,Student Code,First Name,Last Name,Email,Phone,Gender,Course,Enrollment Year,Status");
            for (Student s : students) {
                out.printf("%d,%s,%s,%s,%s,%s,%s,%s,%d,%s%n",
                        s.getId(), csvEscape(s.getStudentCode()), csvEscape(s.getFirstName()),
                        csvEscape(s.getLastName()), csvEscape(s.getEmail()), csvEscape(s.getPhone()),
                        csvEscape(s.getGender()), csvEscape(s.getCourse()), s.getEnrollmentYear(),
                        csvEscape(s.getStatus()));
            }
            out.flush();
        } catch (SQLException e) {
            throw new IOException("Error exporting students", e);
        }
    }

    private String csvEscape(String value) {
        if (value == null) {
			return "";
		}
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    private Student extractStudent(HttpServletRequest req) {
        String studentCode = req.getParameter("studentCode");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String dobStr = req.getParameter("dateOfBirth");
        String gender = req.getParameter("gender");
        String address = req.getParameter("address");
        String course = req.getParameter("course");
        int enrollmentYear = Integer.parseInt(req.getParameter("enrollmentYear"));
        String status = req.getParameter("status");

        Student student = new Student();
        student.setStudentCode(studentCode);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPhone(phone);
        student.setDateOfBirth(Date.valueOf(dobStr));
        student.setGender(gender);
        student.setAddress(address);
        student.setCourse(course);
        student.setEnrollmentYear(enrollmentYear);
        student.setStatus(status);
        return student;
    }
}
