<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Details - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../../includes/header.jsp" />

    <div class="container">
        <h2 class="page-title">Student Details</h2>

        <div class="detail-card">
            <div class="detail-header">
                <h3>${student.fullName}</h3>
                <span class="badge">${student.rollNumber}</span>
            </div>

            <div class="detail-grid">
                <div class="detail-item">
                    <span class="detail-label">Roll Number</span>
                    <span class="detail-value">${student.rollNumber}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">First Name</span>
                    <span class="detail-value">${student.firstName}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Last Name</span>
                    <span class="detail-value">${student.lastName}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Email</span>
                    <span class="detail-value">${student.email}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Phone</span>
                    <span class="detail-value">${student.phone != null ? student.phone : '-'}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Gender</span>
                    <span class="detail-value">${student.gender != null ? student.gender : '-'}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Date of Birth</span>
                    <span class="detail-value">${student.dateOfBirth != null ? student.dateOfBirth : '-'}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Department</span>
                    <span class="detail-value">${student.departmentName != null ? student.departmentName : '-'}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Address</span>
                    <span class="detail-value">${student.address != null ? student.address : '-'}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Created At</span>
                    <span class="detail-value">${student.createdAt != null ? student.createdAt : '-'}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">Updated At</span>
                    <span class="detail-value">${student.updatedAt != null ? student.updatedAt : '-'}</span>
                </div>
            </div>

            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/students?action=edit&id=${student.id}" class="btn btn-edit">Edit</a>
                <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Back to List</a>
            </div>
        </div>
    </div>
</body>
</html>
