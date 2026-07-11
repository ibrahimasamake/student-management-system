<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<div class="top-nav">
    <div class="nav-inner">
        <a href="${pageContext.request.contextPath}/dashboard" class="nav-brand">Student Manager</a>
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/dashboard"
               class="nav-link ${pageContext.request.servletPath eq '/WEB-INF/views/dashboard.jsp' ? 'active' : ''}">Dashboard</a>
            <a href="${pageContext.request.contextPath}/students"
               class="nav-link ${pageContext.request.servletPath eq '/WEB-INF/views/students/list.jsp' ? 'active' : ''}">Students</a>
            <a href="${pageContext.request.contextPath}/departments"
               class="nav-link ${pageContext.request.servletPath eq '/WEB-INF/views/departments/list.jsp' ? 'active' : ''}">Departments</a>
        </div>
    </div>
</div>
