/**
 * EduManage ERP - Main JavaScript
 */

document.addEventListener('DOMContentLoaded', function () {
  
  // -- Active Link Highlight in Sidebar --
  const currentPath = window.location.pathname + window.location.search;
  const navLinks = document.querySelectorAll('.sidebar-nav .nav-link');
  
  navLinks.forEach(link => {
    // Basic exact matching or contains
    if (link.getAttribute('href') && currentPath.includes(link.getAttribute('href').split('?')[0])) {
      // Remove active from all first
      navLinks.forEach(nl => nl.classList.remove('active'));
      link.classList.add('active');
    }
  });

  // -- CGPA Styling Logic --
  const cgpaBadges = document.querySelectorAll('.cgpa-badge');
  cgpaBadges.forEach(badge => {
    const cgpa = parseFloat(badge.getAttribute('data-cgpa'));
    if (!isNaN(cgpa)) {
      if (cgpa >= 8.5) {
        badge.classList.add('text-success');
      } else if (cgpa >= 6.5) {
        badge.classList.add('text-warning');
      } else {
        badge.classList.add('text-danger');
      }
    }
  });

  // -- Setup Delete Modal Data Transfer --
  // Using Bootstrap 5 Modal events
  const deleteModal = document.getElementById('deleteModal');
  if (deleteModal) {
    deleteModal.addEventListener('show.bs.modal', function (event) {
      // Button that triggered the modal
      const button = event.relatedTarget;
      // Extract info from data-* attributes
      const studentId = button.getAttribute('data-id');
      const studentName = button.getAttribute('data-name');
      
      // Update the modal's content
      const modalStudentName = deleteModal.querySelector('#deleteStudentName');
      const confirmDeleteBtn = deleteModal.querySelector('#confirmDeleteBtn');
      
      modalStudentName.textContent = studentName;
      // Construct the delete URL
      const ctxPath = window.location.pathname.substring(0, window.location.pathname.indexOf('/', 1));
      confirmDeleteBtn.setAttribute('href', ctxPath + '/students?action=delete&id=' + studentId);
    });
  }

  // -- Auto dismiss alerts --
  // Bootstrap alerts auto-dismiss can be handled with standard setTimeout
  const alerts = document.querySelectorAll('.alert[role="alert"]');
  alerts.forEach(alert => {
    // Only auto-dismiss success alerts to ensure errors are read
    if(alert.classList.contains('alert-success')) {
       setTimeout(() => {
         const bsAlert = new bootstrap.Alert(alert);
         bsAlert.close();
       }, 4000);
    }
  });
});
