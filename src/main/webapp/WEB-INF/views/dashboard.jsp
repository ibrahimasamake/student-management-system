<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../includes/header.jsp" />

    <div class="container">
        <h2 class="page-title">Dashboard</h2>

        <c:if test="${not empty param.success}">
            <div class="alert alert-success">Operation completed successfully!</div>
        </c:if>

        <div class="stats-grid">
            <div class="stat-card stat-total">
                <div class="stat-number">${totalStudents}</div>
                <div class="stat-label">Total Students</div>
            </div>
            <div class="stat-card stat-departments">
                <div class="stat-number">${totalDepartments}</div>
                <div class="stat-label">Total Departments</div>
            </div>
        </div>

        <div class="dashboard-card">
            <h3>Recent Students</h3>
            <c:choose>
                <c:when test="${empty recentStudents}">
                    <p class="empty-message">No students yet.</p>
                </c:when>
                <c:otherwise>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Roll No</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Department</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${recentStudents}">
                                <tr>
                                    <td>${s.rollNumber}</td>
                                    <td>${s.fullName}</td>
                                    <td>${s.email}</td>
                                    <td>${s.departmentName}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>
