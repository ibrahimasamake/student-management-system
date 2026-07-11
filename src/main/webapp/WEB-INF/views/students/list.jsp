<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Students - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../../includes/header.jsp" />

    <div class="container">
        <div class="page-header">
            <h2 class="page-title">Students</h2>
            <a href="${pageContext.request.contextPath}/students?action=new" class="btn btn-primary">+ Add Student</a>
        </div>

        <c:if test="${not empty param.success}">
            <div class="alert alert-success">
                <c:choose>
                    <c:when test="${param.success == 'created'}">Student created successfully!</c:when>
                    <c:when test="${param.success == 'updated'}">Student updated successfully!</c:when>
                    <c:when test="${param.success == 'deleted'}">Student deleted successfully!</c:when>
                    <c:otherwise>Operation completed successfully!</c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <c:if test="${param.error == 'notfound'}">
            <div class="alert alert-error">Student not found.</div>
        </c:if>

        <div class="search-bar">
            <form action="${pageContext.request.contextPath}/students" method="get" class="search-form">
                <input type="hidden" name="action" value="search">
                <input type="text" name="keyword" class="search-input"
                       placeholder="Search by name, roll number, email, or department..."
                       value="${param.keyword}">
                <button type="submit" class="btn btn-search">Search</button>
                <c:if test="${not empty param.keyword}">
                    <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Clear</a>
                </c:if>
            </form>
        </div>

        <c:choose>
            <c:when test="${empty students}">
                <div class="empty-state">
                    <p>No students found.</p>
                    <c:if test="${not empty param.keyword}">
                        <p>Try a different search term.</p>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                        <tr>
                            <th>Roll No</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Department</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${students}">
                            <tr>
                                <td>${s.rollNumber}</td>
                                <td>${s.fullName}</td>
                                <td>${s.email}</td>
                                <td>${s.phone}</td>
                                <td>${s.departmentName}</td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/students?action=view&id=${s.id}" class="btn btn-sm btn-view">View</a>
                                    <a href="${pageContext.request.contextPath}/students?action=edit&id=${s.id}" class="btn btn-sm btn-edit">Edit</a>
                                    <a href="${pageContext.request.contextPath}/students?action=delete&id=${s.id}"
                                       class="btn btn-sm btn-delete"
                                       onclick="return confirm('Delete student ${s.fullName}? This action cannot be undone.')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
