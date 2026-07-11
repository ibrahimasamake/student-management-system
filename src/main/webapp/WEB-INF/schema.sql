CREATE DATABASE IF NOT EXISTS student_management_db
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_unicode_ci;

USE student_management_db;

DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS departments;

CREATE TABLE departments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roll_number VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    gender VARCHAR(10),
    date_of_birth DATE,
    department_id BIGINT,
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_student_department FOREIGN KEY (department_id) REFERENCES departments(id)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO departments (name, description) VALUES
('Computer Science', 'Department of Computer Science and Engineering'),
('Mathematics', 'Department of Mathematics and Statistics'),
('Physics', 'Department of Physics and Astronomy'),
('Engineering', 'Department of Engineering and Technology'),
('Business Administration', 'Department of Business and Management');

INSERT INTO students (roll_number, first_name, last_name, email, phone, gender, date_of_birth, department_id, address) VALUES
('STU001', 'John', 'Doe', 'john.doe@example.com', '1234567890', 'Male', '2000-05-15', 1, '123 Main St, City'),
('STU002', 'Jane', 'Smith', 'jane.smith@example.com', '0987654321', 'Female', '2001-08-22', 2, '456 Oak Ave, Town'),
('STU003', 'Bob', 'Johnson', 'bob.johnson@example.com', '5551234567', 'Male', '1999-12-10', 3, '789 Pine Rd, Village'),
('STU004', 'Alice', 'Williams', 'alice.williams@example.com', '4449876543', 'Female', '2002-03-30', 1, '321 Elm St, City'),
('STU005', 'Charlie', 'Brown', 'charlie.brown@example.com', '7775553333', 'Male', '2000-07-18', 4, '654 Maple Dr, Suburb');
