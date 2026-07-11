package com.sms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sms.config.DatabaseConfig;
import com.sms.model.Department;

public class DepartmentDAO {

    public List<Department> getAllDepartments() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments ORDER BY name";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                departments.add(mapDepartment(rs));
            }
        }
        return departments;
    }

    public Department getDepartmentById(Long id) throws SQLException {
        String sql = "SELECT * FROM departments WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapDepartment(rs);
                }
            }
        }
        return null;
    }

    public void addDepartment(Department department) throws SQLException {
        String sql = "INSERT INTO departments (name, description) VALUES (?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department.getName());
            ps.setString(2, department.getDescription());
            ps.executeUpdate();
        }
    }

    public void updateDepartment(Department department) throws SQLException {
        String sql = "UPDATE departments SET name = ?, description = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department.getName());
            ps.setString(2, department.getDescription());
            ps.setLong(3, department.getId());
            ps.executeUpdate();
        }
    }

    public void deleteDepartment(Long id) throws SQLException {
        String sql = "DELETE FROM departments WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    public int getDepartmentCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM departments";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public boolean nameExists(String name, Long excludeId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM departments WHERE name = ?";
        if (excludeId != null) {
            sql += " AND id != ?";
        }
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            if (excludeId != null) {
                ps.setLong(2, excludeId);
            }
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    private Department mapDepartment(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setId(rs.getLong("id"));
        d.setName(rs.getString("name"));
        d.setDescription(rs.getString("description"));
        if (rs.getTimestamp("created_at") != null) {
            d.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        if (rs.getTimestamp("updated_at") != null) {
            d.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        return d;
    }
}
