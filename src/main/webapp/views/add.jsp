<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Student - Student Management System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
    <%@ include file="header.jspf" %>
    <div class="container">
        <h2 class="page-title">Add New Student</h2>

        <form action="${pageContext.request.contextPath}/students" method="post" class="student-form" onsubmit="return validateForm()">
            <input type="hidden" name="action" value="insert">

            <div class="form-grid">
                <div class="form-group">
                    <label for="studentCode">Student Code <span class="required">*</span></label>
                    <input type="text" id="studentCode" name="studentCode" required>
                </div>

                <div class="form-group">
                    <label for="firstName">First Name <span class="required">*</span></label>
                    <input type="text" id="firstName" name="firstName" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name <span class="required">*</span></label>
                    <input type="text" id="lastName" name="lastName" required>
                </div>

                <div class="form-group">
                    <label for="email">Email <span class="required">*</span></label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="tel" id="phone" name="phone">
                </div>

                <div class="form-group">
                    <label for="dateOfBirth">Date of Birth <span class="required">*</span></label>
                    <input type="date" id="dateOfBirth" name="dateOfBirth" required>
                </div>

                <div class="form-group">
                    <label for="gender">Gender <span class="required">*</span></label>
                    <select id="gender" name="gender" required>
                        <option value="">Select Gender</option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="course">Course <span class="required">*</span></label>
                    <select id="course" name="course" required>
                        <option value="">Select Course</option>
                        <c:forEach var="c" items="${courses}">
                            <option value="${c}">${c}</option>
                        </c:forEach>
                        <option value="other">-- Other --</option>
                    </select>
                    <input type="text" id="courseOther" name="courseOther" placeholder="Enter custom course" style="display:none; margin-top:6px;">
                </div>

                <div class="form-group">
                    <label for="enrollmentYear">Enrollment Year <span class="required">*</span></label>
                    <input type="number" id="enrollmentYear" name="enrollmentYear" min="2000" max="2100" required>
                </div>

                <div class="form-group">
                    <label for="status">Status <span class="required">*</span></label>
                    <select id="status" name="status" required>
                        <option value="">Select Status</option>
                        <option value="Active">Active</option>
                        <option value="Inactive">Inactive</option>
                        <option value="Graduated">Graduated</option>
                        <option value="Suspended">Suspended</option>
                    </select>
                </div>
            </div>

            <div class="form-group form-group-full">
                <label for="address">Address</label>
                <textarea id="address" name="address" rows="3"></textarea>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">Save Student</button>
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
    </script>
</body>
</html>
