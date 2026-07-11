<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../includes/header.jsp" />

    <div class="container">
        <div class="error-page">
            <h2>Something went wrong</h2>
            <p>An unexpected error occurred. Please try again.</p>
            <p class="error-detail">${error != null ? error : pageContext.exception.message}</p>
            <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">Go to Dashboard</a>
        </div>
    </div>
</body>
</html>
