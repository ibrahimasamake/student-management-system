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
    <%@ include file="header.jspf" %>
    <div class="container">
        <h2 class="page-title">Dashboard</h2>

        <div class="stats-grid">
            <div class="stat-card stat-total">
                <div class="stat-number">${totalStudents}</div>
                <div class="stat-label">Total Students</div>
            </div>
            <div class="stat-card stat-new">
                <div class="stat-number">${newThisYear}</div>
                <div class="stat-label">Enrolled This Year</div>
            </div>
            <div class="stat-card stat-active">
                <div class="stat-number">${statusCounts['Active'] != null ? statusCounts['Active'] : 0}</div>
                <div class="stat-label">Active</div>
            </div>
            <div class="stat-card stat-graduated">
                <div class="stat-number">${statusCounts['Graduated'] != null ? statusCounts['Graduated'] : 0}</div>
                <div class="stat-label">Graduated</div>
            </div>
        </div>

        <div class="dashboard-grid">
            <div class="dashboard-card">
                <h3>Students by Course</h3>
                <c:choose>
                    <c:when test="${empty courseCounts}">
                        <p class="empty-message">No data available.</p>
                    </c:when>
                    <c:otherwise>
                        <table class="table">
                            <thead>
                                <tr><th>Course</th><th>Count</th></tr>
                            </thead>
                            <tbody>
                                <c:forEach var="entry" items="${courseCounts}">
                                    <tr>
                                        <td>${entry.key}</td>
                                        <td>${entry.value}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
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
                                <tr><th>Name</th><th>Code</th><th>Status</th></tr>
                            </thead>
                            <tbody>
                                <c:forEach var="s" items="${recentStudents}">
                                    <tr>
                                        <td>${s.fullName}</td>
                                        <td>${s.studentCode}</td>
                                        <td><span class="status ${s.status.toLowerCase()}">${s.status}</span></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</body>
</html>
