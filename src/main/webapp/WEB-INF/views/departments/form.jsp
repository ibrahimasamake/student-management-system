<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${department == null ? 'Add' : 'Edit'} Department - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../../includes/header.jsp" />

    <div class="container">
        <h2 class="page-title">${department == null ? 'Add New' : 'Edit'} Department</h2>

        <form action="${pageContext.request.contextPath}/departments" method="post" class="student-form">
            <input type="hidden" name="action" value="${department == null ? 'create' : 'update'}">
            <c:if test="${department != null}">
                <input type="hidden" name="id" value="${department.id}">
            </c:if>

            <c:if test="${not empty errors}">
                <div class="alert alert-error">
                    <ul>
                        <c:forEach var="err" items="${errors}">
                            <li>${err.value}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <div class="form-grid">
                <div class="form-group form-group-full">
                    <label for="name">Department Name <span class="required">*</span></label>
                    <input type="text" id="name" name="name" value="${department.name}" required
                           class="${not empty errors and errors.containsKey('name') ? 'error' : ''}">
                    <c:if test="${not empty errors and not empty errors['name']}">
                        <span class="field-error">${errors['name']}</span>
                    </c:if>
                </div>

                <div class="form-group form-group-full">
                    <label for="description">Description</label>
                    <textarea id="description" name="description" rows="4">${department.description}</textarea>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${department == null ? 'Save Department' : 'Update Department'}</button>
                <a href="${pageContext.request.contextPath}/departments" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
