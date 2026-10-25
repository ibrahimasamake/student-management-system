package com.sms.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    // Credentials come from environment variables so no password lives in the code.
    private static final String URL = env("DB_URL",
            "jdbc:mysql://localhost:3306/student_management_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    private static final String USERNAME = env("DB_USERNAME", "root");
    private static final String PASSWORD = env("DB_PASSWORD", "");

    private static String env(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found. Add mysql-connector-j dependency to pom.xml.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
