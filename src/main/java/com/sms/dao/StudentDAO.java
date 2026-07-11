package com.sms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sms.config.DatabaseConfig;
import com.sms.model.Student;

public class StudentDAO {

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, d.name AS department_name FROM students s " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "ORDER BY s.id DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                students.add(mapStudent(rs));
            }
        }
        return students;
    }

    public Student getStudentById(Long id) throws SQLException {
        String sql = "SELECT s.*, d.name AS department_name FROM students s " +
                     "LEFT JOIN departments d ON s.department_id = d.id WHERE s.id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        }
        return null;
    }

    public List<Student> searchStudents(String keyword) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, d.name AS department_name FROM students s " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "WHERE s.roll_number LIKE ? OR s.first_name LIKE ? OR " +
                     "s.last_name LIKE ? OR s.email LIKE ? OR d.name LIKE ? " +
                     "ORDER BY s.id DESC";
        String pattern = "%" + keyword.trim() + "%";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 1; i <= 5; i++) {
                ps.setString(i, pattern);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapStudent(rs));
                }
            }
        }
        return students;
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (roll_number, first_name, last_name, email, phone, " +
                     "gender, date_of_birth, department_id, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getGender());
            ps.setDate(7, student.getDateOfBirth() != null ? Date.valueOf(student.getDateOfBirth()) : null);
            ps.setLong(8, student.getDepartmentId());
            ps.setString(9, student.getAddress());
            ps.executeUpdate();
        }
    }

    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET roll_number = ?, first_name = ?, last_name = ?, email = ?, " +
                     "phone = ?, gender = ?, date_of_birth = ?, department_id = ?, address = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getRollNumber());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPhone());
            ps.setString(6, student.getGender());
            ps.setDate(7, student.getDateOfBirth() != null ? Date.valueOf(student.getDateOfBirth()) : null);
            ps.setLong(8, student.getDepartmentId());
            ps.setString(9, student.getAddress());
            ps.setLong(10, student.getId());
            ps.executeUpdate();
        }
    }

    public void deleteStudent(Long id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public int getStudentCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM students";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public List<Student> getRecentStudents(int limit) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, d.name AS department_name FROM students s " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "ORDER BY s.id DESC LIMIT ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapStudent(rs));
                }
            }
        }
        return students;
    }

    public boolean rollNumberExists(String rollNumber, Long excludeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE roll_number = ?";
        if (excludeId != null) {
            sql += " AND id != ?";
        }
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, rollNumber);
            if (excludeId != null) {
                ps.setLong(2, excludeId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public boolean emailExists(String email, Long excludeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE email = ?";
        if (excludeId != null) {
            sql += " AND id != ?";
        }
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            if (excludeId != null) {
                ps.setLong(2, excludeId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public List<Student> getStudentsByDepartment(Long departmentId) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT s.*, d.name AS department_name FROM students s " +
                     "LEFT JOIN departments d ON s.department_id = d.id " +
                     "WHERE s.department_id = ? ORDER BY s.id DESC";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapStudent(rs));
                }
            }
        }
        return students;
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getLong("id"));
        s.setRollNumber(rs.getString("roll_number"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        s.setGender(rs.getString("gender"));
        Date dob = rs.getDate("date_of_birth");
        if (dob != null) {
            s.setDateOfBirth(dob.toLocalDate());
        }
        s.setDepartmentId(rs.getLong("department_id"));
        s.setDepartmentName(rs.getString("department_name"));
        s.setAddress(rs.getString("address"));
        if (rs.getTimestamp("created_at") != null) {
            s.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            s.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return s;
    }
}
