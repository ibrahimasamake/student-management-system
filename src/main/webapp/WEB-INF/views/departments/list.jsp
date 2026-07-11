<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Departments - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../../includes/header.jsp" />

    <div class="container">
        <div class="page-header">
            <h2 class="page-title">Departments</h2>
            <a href="${pageContext.request.contextPath}/departments?action=new" class="btn btn-primary">+ Add Department</a>
        </div>

        <c:if test="${not empty param.success}">
            <div class="alert alert-success">
                <c:choose>
                    <c:when test="${param.success == 'created'}">Department created successfully!</c:when>
                    <c:when test="${param.success == 'updated'}">Department updated successfully!</c:when>
                    <c:when test="${param.success == 'deleted'}">Department deleted successfully!</c:when>
                    <c:otherwise>Operation completed successfully!</c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <c:if test="${param.error == 'notfound'}">
            <div class="alert alert-error">Department not found.</div>
        </c:if>

        <c:if test="${not empty param.error and param.error != 'notfound'}">
            <div class="alert alert-error">${param.error}</div>
        </c:if>

        <c:choose>
            <c:when test="${empty departments}">
                <div class="empty-state">
                    <p>No departments yet.</p>
                </div>
            </c:when>
            <c:otherwise>
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="d" items="${departments}">
                            <tr>
                                <td>${d.id}</td>
                                <td>${d.name}</td>
                                <td>${d.description != null ? d.description : '-'}</td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/departments?action=edit&id=${d.id}" class="btn btn-sm btn-edit">Edit</a>
                                    <a href="${pageContext.request.contextPath}/departments?action=delete&id=${d.id}"
                                       class="btn btn-sm btn-delete"
                                       onclick="return confirm('Delete department ${d.name}? Students in this department will lose their department assignment.')">Delete</a>
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
