<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${student == null ? 'Add' : 'Edit'} Student - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <jsp:include page="../../includes/header.jsp" />

    <div class="container">
        <h2 class="page-title">${student == null ? 'Add New' : 'Edit'} Student</h2>

        <form action="${pageContext.request.contextPath}/students" method="post" class="student-form">
            <input type="hidden" name="action" value="${student == null ? 'create' : 'update'}">
            <c:if test="${student != null}">
                <input type="hidden" name="id" value="${student.id}">
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
                <div class="form-group">
                    <label for="rollNumber">Roll Number <span class="required">*</span></label>
                    <input type="text" id="rollNumber" name="rollNumber"
                           value="${student.rollNumber}" required
                           class="${not empty errors and errors.containsKey('rollNumber') ? 'error' : ''}">
                    <c:if test="${not empty errors and not empty errors['rollNumber']}">
                        <span class="field-error">${errors['rollNumber']}</span>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="firstName">First Name <span class="required">*</span></label>
                    <input type="text" id="firstName" name="firstName"
                           value="${student.firstName}" required
                           class="${not empty errors and errors.containsKey('firstName') ? 'error' : ''}">
                    <c:if test="${not empty errors and not empty errors['firstName']}">
                        <span class="field-error">${errors['firstName']}</span>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name <span class="required">*</span></label>
                    <input type="text" id="lastName" name="lastName"
                           value="${student.lastName}" required
                           class="${not empty errors and errors.containsKey('lastName') ? 'error' : ''}">
                    <c:if test="${not empty errors and not empty errors['lastName']}">
                        <span class="field-error">${errors['lastName']}</span>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="email">Email <span class="required">*</span></label>
                    <input type="email" id="email" name="email"
                           value="${student.email}" required
                           class="${not empty errors and errors.containsKey('email') ? 'error' : ''}">
                    <c:if test="${not empty errors and not empty errors['email']}">
                        <span class="field-error">${errors['email']}</span>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="tel" id="phone" name="phone" value="${student.phone}">
                </div>

                <div class="form-group">
                    <label for="gender">Gender</label>
                    <select id="gender" name="gender">
                        <option value="">Select Gender</option>
                        <option value="Male" ${student.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${student.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${student.gender == 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth</label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" value="${student.dateOfBirth}">
                </div>

                <div class="form-group">
                    <label for="departmentId">Department <span class="required">*</span></label>
                    <select id="departmentId" name="departmentId" required
                            class="${not empty errors and errors.containsKey('departmentId') ? 'error' : ''}">
                        <option value="">Select Department</option>
                        <c:forEach var="dept" items="${departments}">
                            <option value="${dept.id}" ${student.departmentId == dept.id ? 'selected' : ''}>${dept.name}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty errors and not empty errors['departmentId']}">
                        <span class="field-error">${errors['departmentId']}</span>
                    </c:if>
                </div>

                <div class="form-group form-group-full">
                    <label for="address">Address</label>
                    <textarea id="address" name="address" rows="3">${student.address}</textarea>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${student == null ? 'Save Student' : 'Update Student'}</button>
                <a href="${pageContext.request.contextPath}/students" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>
