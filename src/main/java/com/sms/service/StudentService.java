package com.sms.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;

public class StudentService {

    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAllStudents();
    }

    public Student getStudentById(Long id) throws SQLException {
        return studentDAO.getStudentById(id);
    }

    public List<Student> searchStudents(String keyword) throws SQLException {
        return studentDAO.searchStudents(keyword);
    }

    public Map<String, String> addStudent(Student student) throws SQLException {
        Map<String, String> errors = new HashMap<>();
        checkUniqueness(student, errors, null);
        if (errors.isEmpty()) {
            studentDAO.addStudent(student);
        }
        return errors;
    }

    public Map<String, String> updateStudent(Student student) throws SQLException {
        Map<String, String> errors = new HashMap<>();
        checkUniqueness(student, errors, student.getId());
        if (errors.isEmpty()) {
            studentDAO.updateStudent(student);
        }
        return errors;
    }

    public void deleteStudent(Long id) throws SQLException {
        studentDAO.deleteStudent(id);
    }

    public int getStudentCount() throws SQLException {
        return studentDAO.getStudentCount();
    }

    public List<Student> getRecentStudents(int limit) throws SQLException {
        return studentDAO.getRecentStudents(limit);
    }

    private void checkUniqueness(Student student, Map<String, String> errors, Long excludeId) throws SQLException {
        if (studentDAO.rollNumberExists(student.getRollNumber(), excludeId)) {
            errors.put("rollNumber", "Roll number already exists.");
        }
        if (studentDAO.emailExists(student.getEmail(), excludeId)) {
            errors.put("email", "Email already exists.");
        }
    }
}
