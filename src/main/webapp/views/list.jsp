<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student List - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="header.jspf" %>
    <div class="container">
        <div class="header">
            <h1>Students</h1>
            <span class="header-subtitle">${totalStudents} record(s) found</span>
        </div>

        <div class="toolbar">
            <div class="toolbar-left">
                <a href="${pageContext.request.contextPath}/students?action=add" class="btn btn-primary">+ Add New Student</a>
                <a href="${pageContext.request.contextPath}/students?action=export&search=${param.search}&course=${param.course}&status=${param.status}"
                   class="btn btn-export">Export CSV</a>
            </div>
            <div class="toolbar-right">
                <form action="${pageContext.request.contextPath}/students" method="get" class="filter-form">
                    <input type="hidden" name="action" value="list">
                    <input type="text" name="search" placeholder="Search..."
                           value="${param.search}" class="search-input">
                    <select name="course" class="filter-select">
                        <option value="">All Courses</option>
                        <c:forEach var="c" items="${courses}">
                            <option value="${c}" ${param.course == c ? 'selected' : ''}>${c}</option>
                        </c:forEach>
                    </select>
                    <select name="status" class="filter-select">
                        <option value="">All Statuses</option>
                        <option value="Active" ${param.status == 'Active' ? 'selected' : ''}>Active</option>
                        <option value="Inactive" ${param.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                        <option value="Graduated" ${param.status == 'Graduated' ? 'selected' : ''}>Graduated</option>
                        <option value="Suspended" ${param.status == 'Suspended' ? 'selected' : ''}>Suspended</option>
                    </select>
                    <button type="submit" class="btn btn-search">Filter</button>
                    <c:if test="${not empty param.search or not empty param.course or not empty param.status}">
                        <a href="${pageContext.request.contextPath}/students?action=list" class="btn btn-secondary">Clear</a>
                    </c:if>
                </form>
            </div>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Student Code</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Course</th>
                    <th>Enrollment Year</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty students}">
                        <tr>
                            <td colspan="9" class="empty-message">No students found.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="s" items="${students}">
                            <tr>
                                <td>${s.id}</td>
                                <td>${s.studentCode}</td>
                                <td>${s.fullName}</td>
                                <td>${s.email}</td>
                                <td>${s.phone}</td>
                                <td>${s.course}</td>
                                <td>${s.enrollmentYear}</td>
                                <td>
                                    <span class="status ${s.status.toLowerCase()}">${s.status}</span>
                                </td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/students?action=edit&id=${s.id}" class="btn btn-sm btn-edit">Edit</a>
                                    <a href="${pageContext.request.contextPath}/students?action=delete&id=${s.id}"
                                       class="btn btn-sm btn-delete"
                                       onclick="return confirm('Are you sure you want to delete student ${s.fullName}?')">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>

        <c:if test="${totalPages > 1}">
            <div class="pagination">
                <c:if test="${currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/students?action=list&page=${currentPage - 1}&search=${param.search}&course=${param.course}&status=${param.status}"
                       class="btn btn-sm btn-secondary">&laquo; Prev</a>
                </c:if>
                <span class="page-info">Page ${currentPage} of ${totalPages}</span>
                <c:if test="${currentPage < totalPages}">
                    <a href="${pageContext.request.contextPath}/students?action=list&page=${currentPage + 1}&search=${param.search}&course=${param.course}&status=${param.status}"
                       class="btn btn-sm btn-secondary">Next &raquo;</a>
                </c:if>
            </div>
        </c:if>
    </div>
</body>
</html>
