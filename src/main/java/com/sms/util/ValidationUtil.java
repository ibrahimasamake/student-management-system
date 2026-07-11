package com.sms.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static Map<String, String> validateStudent(String rollNumber, String firstName, String lastName,
                                                       String email, String departmentIdStr) {
        Map<String, String> errors = new HashMap<>();

        if (rollNumber == null || rollNumber.trim().isEmpty()) {
            errors.put("rollNumber", "Roll number is required.");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            errors.put("firstName", "First name is required.");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            errors.put("lastName", "Last name is required.");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.put("email", "Email is required.");
        } else if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            errors.put("email", "Please enter a valid email address.");
        }
        if (departmentIdStr == null || departmentIdStr.trim().isEmpty()) {
            errors.put("departmentId", "Department is required.");
        }

        return errors;
    }

    public static Map<String, String> validateDepartment(String name) {
        Map<String, String> errors = new HashMap<>();

        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Department name is required.");
        }

        return errors;
    }
}
