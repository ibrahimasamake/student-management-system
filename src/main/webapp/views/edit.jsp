<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Student - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="header.jspf" %>
    <div class="container">
        <h2 class="page-title">Edit Student</h2>

        <form action="${pageContext.request.contextPath}/students" method="post" class="student-form" onsubmit="return validateForm()">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${student.id}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="studentCode">Student Code <span class="required">*</span></label>
                    <input type="text" id="studentCode" name="studentCode" value="${student.studentCode}" required>
                </div>

                <div class="form-group">
                    <label for="firstName">First Name <span class="required">*</span></label>
                    <input type="text" id="firstName" name="firstName" value="${student.firstName}" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name <span class="required">*</span></label>
                    <input type="text" id="lastName" name="lastName" value="${student.lastName}" required>
                </div>

                <div class="form-group">
                    <label for="email">Email <span class="required">*</span></label>
                    <input type="email" id="email" name="email" value="${student.email}" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="tel" id="phone" name="phone" value="${student.phone}">
                </div>

                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth <span class="required">*</span></label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" value="${student.dateOfBirth}" required>
                </div>

                <div class="form-group">
                    <label for="gender">Gender <span class="required">*</span></label>
                    <select id="gender" name="gender" required>
                        <option value="Male" ${student.gender == 'Male' ? 'selected' : ''}>Male</option>
                        <option value="Female" ${student.gender == 'Female' ? 'selected' : ''}>Female</option>
                        <option value="Other" ${student.gender == 'Other' ? 'selected' : ''}>Other</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="course">Course <span class="required">*</span></label>
                    <select id="course" name="course" required>
                        <c:forEach var="c" items="${courses}">
                            <option value="${c}" ${student.course == c ? 'selected' : ''}>${c}</option>
                        </c:forEach>
                        <option value="other" ${not courses.contains(student.course) ? 'selected' : ''}>-- Other --</option>
                    </select>
                    <input type="text" id="courseOther" name="courseOther"
                           placeholder="Enter custom course" style="display:none; margin-top:6px;"
                           value="${not courses.contains(student.course) ? student.course : ''}">
                </div>

                <div class="form-group">
                    <label for="enrollmentYear">Enrollment Year <span class="required">*</span></label>
                    <input type="number" id="enrollmentYear" name="enrollmentYear" min="2000" max="2100" value="${student.enrollmentYear}" required>
                </div>

                <div class="form-group">
                    <label for="status">Status <span class="required">*</span></label>
                    <select id="status" name="status" required>
                        <option value="Active" ${student.status == 'Active' ? 'selected' : ''}>Active</option>
                        <option value="Inactive" ${student.status == 'Inactive' ? 'selected' : ''}>Inactive</option>
                        <option value="Graduated" ${student.status == 'Graduated' ? 'selected' : ''}>Graduated</option>
                        <option value="Suspended" ${student.status == 'Suspended' ? 'selected' : ''}>Suspended</option>
                    </select>
                </div>
            </div>

            <div class="form-group form-group-full">
                <label for="address">Address</label>
                <textarea id="address" name="address" rows="3">${student.address}</textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Update Student</button>
                <a href="${pageContext.request.contextPath}/students?action=list" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/validation.js"></script>
    <script>
        document.getElementById('course').addEventListener('change', function() {
            var otherInput = document.getElementById('courseOther');
            if (this.value === 'other') {
                otherInput.style.display = 'block';
                otherInput.name = 'course';
                this.name = '';
            } else {
                otherInput.style.display = 'none';
                otherInput.name = 'courseOther';
                this.name = 'course';
            }
        });
        (function() {
            var sel = document.getElementById('course');
            if (sel.value === 'other') {
                document.getElementById('courseOther').style.display = 'block';
            }
        })();
    </script>
</body>
</html>
