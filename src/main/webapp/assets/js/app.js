document.addEventListener('DOMContentLoaded', function() {
    autoHideAlerts();
});

function autoHideAlerts() {
    var alerts = document.querySelectorAll('.alert-success');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            alert.style.transition = 'opacity 0.5s';
            alert.style.opacity = '0';
            setTimeout(function() {
                alert.style.display = 'none';
            }, 500);
        }, 4000);
    });
}
