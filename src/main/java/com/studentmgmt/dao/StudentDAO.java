package com.studentmgmt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.studentmgmt.model.Student;

public class StudentDAO {

    public int countStudents(String search, String course, String status) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM students WHERE 1=1");
        List<Object> params = new ArrayList<>();
        appendFilters(sql, params, search, course, status);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
					return rs.getInt(1);
				}
            }
        }
        return 0;
    }

    public List<Student> getStudents(int page, int pageSize, String search, String course, String status)
            throws SQLException {
        List<Student> students = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE 1=1");
        List<Object> params = new ArrayList<>();
        appendFilters(sql, params, search, course, status);
        sql.append(" ORDER BY id DESC LIMIT ? OFFSET ?");
        params.add(pageSize);
        params.add((page - 1) * pageSize);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapStudent(rs));
                }
            }
        }
        return students;
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
					return mapStudent(rs);
				}
            }
        }
        return null;
    }

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (student_code, first_name, last_name, email, phone, "
                + "date_of_birth, gender, address, course, enrollment_year, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getStudentCode());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPhone());
            ps.setDate(6, student.getDateOfBirth());
            ps.setString(7, student.getGender());
            ps.setString(8, student.getAddress());
            ps.setString(9, student.getCourse());
            ps.setInt(10, student.getEnrollmentYear());
            ps.setString(11, student.getStatus());
            ps.executeUpdate();
        }
    }

    public void updateStudent(Student student) throws SQLException {
        String sql = "UPDATE students SET student_code=?, first_name=?, last_name=?, email=?, phone=?, "
                + "date_of_birth=?, gender=?, address=?, course=?, enrollment_year=?, status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getStudentCode());
            ps.setString(2, student.getFirstName());
            ps.setString(3, student.getLastName());
            ps.setString(4, student.getEmail());
            ps.setString(5, student.getPhone());
            ps.setDate(6, student.getDateOfBirth());
            ps.setString(7, student.getGender());
            ps.setString(8, student.getAddress());
            ps.setString(9, student.getCourse());
            ps.setInt(10, student.getEnrollmentYear());
            ps.setString(11, student.getStatus());
            ps.setInt(12, student.getId());
            ps.executeUpdate();
        }
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<String> getAllCourses() throws SQLException {
        List<String> courses = new ArrayList<>();
        String sql = "SELECT DISTINCT course FROM students ORDER BY course";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                courses.add(rs.getString("course"));
            }
        }
        return courses;
    }

    public Map<String, Integer> getStatusCounts() throws SQLException {
        Map<String, Integer> counts = new HashMap<>();
        String sql = "SELECT status, COUNT(*) AS cnt FROM students GROUP BY status";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                counts.put(rs.getString("status"), rs.getInt("cnt"));
            }
        }
        return counts;
    }

    public Map<String, Integer> getCourseCounts() throws SQLException {
        Map<String, Integer> counts = new HashMap<>();
        String sql = "SELECT course, COUNT(*) AS cnt FROM students GROUP BY course ORDER BY cnt DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                counts.put(rs.getString("course"), rs.getInt("cnt"));
            }
        }
        return counts;
    }

    public int getTotalStudents() throws SQLException {
        String sql = "SELECT COUNT(*) FROM students";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
				return rs.getInt(1);
			}
        }
        return 0;
    }

    public int getStudentsEnrolledThisYear() throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE enrollment_year = YEAR(CURDATE())";
        try (Connection conn = DBConnection.getConnection();
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
        String sql = "SELECT * FROM students ORDER BY id DESC LIMIT ?";
        try (Connection conn = DBConnection.getConnection();
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

    public List<Student> getAllStudentsForExport(String search, String course, String status)
            throws SQLException {
        List<Student> students = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM students WHERE 1=1");
        List<Object> params = new ArrayList<>();
        appendFilters(sql, params, search, course, status);
        sql.append(" ORDER BY id DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapStudent(rs));
                }
            }
        }
        return students;
    }

    private void appendFilters(StringBuilder sql, List<Object> params,
                                String search, String course, String status) {
        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND (first_name LIKE ? OR last_name LIKE ? OR student_code LIKE ? OR email LIKE ?)");
            String pattern = "%" + search.trim() + "%";
            params.add(pattern);
            params.add(pattern);
            params.add(pattern);
            params.add(pattern);
        }
        if (course != null && !course.trim().isEmpty()) {
            sql.append(" AND course = ?");
            params.add(course.trim());
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" AND status = ?");
            params.add(status.trim());
        }
    }

    private Student mapStudent(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setStudentCode(rs.getString("student_code"));
        s.setFirstName(rs.getString("first_name"));
        s.setLastName(rs.getString("last_name"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        s.setDateOfBirth(rs.getDate("date_of_birth"));
        s.setGender(rs.getString("gender"));
        s.setAddress(rs.getString("address"));
        s.setCourse(rs.getString("course"));
        s.setEnrollmentYear(rs.getInt("enrollment_year"));
        s.setStatus(rs.getString("status"));
        s.setCreatedAt(rs.getString("created_at"));
        s.setUpdatedAt(rs.getString("updated_at"));
        return s;
    }
}
