function validateForm() {
    var fields = [
        { id: 'studentCode', name: 'Student Code' },
        { id: 'firstName', name: 'First Name' },
        { id: 'lastName', name: 'Last Name' },
        { id: 'email', name: 'Email' },
        { id: 'dateOfBirth', name: 'Date of Birth' },
        { id: 'gender', name: 'Gender' },
        { id: 'enrollmentYear', name: 'Enrollment Year' },
        { id: 'status', name: 'Status' }
    ];

    var isValid = true;
    var firstError = null;

    fields.forEach(function(field) {
        var el = document.getElementById(field.id);
        if (!el) return;
        el.classList.remove('error');
        if (el.value.trim() === '') {
            el.classList.add('error');
            isValid = false;
            if (!firstError) firstError = el;
        }
    });

    var courseSel = document.getElementById('course');
    var courseOther = document.getElementById('courseOther');
    if (courseSel) {
        courseSel.classList.remove('error');
        if (courseOther && courseOther.style.display !== 'none' && courseOther.value.trim() === '') {
            courseOther.classList.add('error');
            isValid = false;
            if (!firstError) firstError = courseOther;
        } else if (courseSel.value === '') {
            courseSel.classList.add('error');
            isValid = false;
            if (!firstError) firstError = courseSel;
        }
    }

    var email = document.getElementById('email');
    if (email && email.value.trim() !== '') {
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(email.value.trim())) {
            email.classList.add('error');
            isValid = false;
            if (!firstError) firstError = email;
        }
    }

    var enrollmentYear = document.getElementById('enrollmentYear');
    if (enrollmentYear && enrollmentYear.value.trim() !== '') {
        var year = parseInt(enrollmentYear.value);
        if (isNaN(year) || year < 2000 || year > 2100) {
            enrollmentYear.classList.add('error');
            isValid = false;
            if (!firstError) firstError = enrollmentYear;
        }
    }

    if (!isValid) {
        alert('Please fill in all required fields correctly.');
        if (firstError) firstError.focus();
    }

    return isValid;
}
