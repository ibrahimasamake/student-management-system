package com.sms.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sms.dao.DepartmentDAO;
import com.sms.dao.StudentDAO;
import com.sms.model.Department;

public class DepartmentService {

    private final DepartmentDAO departmentDAO;
    private final StudentDAO studentDAO;

    public DepartmentService() {
        this.departmentDAO = new DepartmentDAO();
        this.studentDAO = new StudentDAO();
    }

    public List<Department> getAllDepartments() throws SQLException {
        return departmentDAO.getAllDepartments();
    }

    public Department getDepartmentById(Long id) throws SQLException {
        return departmentDAO.getDepartmentById(id);
    }

    public Map<String, String> addDepartment(Department department) throws SQLException {
        Map<String, String> errors = new HashMap<>();
        if (departmentDAO.nameExists(department.getName(), null)) {
            errors.put("name", "Department name already exists.");
            return errors;
        }
        departmentDAO.addDepartment(department);
        return errors;
    }

    public Map<String, String> updateDepartment(Department department) throws SQLException {
        Map<String, String> errors = new HashMap<>();
        if (departmentDAO.nameExists(department.getName(), department.getId())) {
            errors.put("name", "Department name already exists.");
            return errors;
        }
        departmentDAO.updateDepartment(department);
        return errors;
    }

    public String deleteDepartment(Long id) throws SQLException {
        int count = studentDAO.getStudentsByDepartment(id).size();
        if (count > 0) {
            return "Cannot delete department with " + count + " student(s) assigned. Reassign students first.";
        }
        departmentDAO.deleteDepartment(id);
        return null;
    }

    public int getDepartmentCount() throws SQLException {
        return departmentDAO.getDepartmentCount();
    }
}
