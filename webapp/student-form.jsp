<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sms.model.Student" %>
<%
    String adminUser = (String) session.getAttribute("adminUser");
    if (adminUser == null) { response.sendRedirect(request.getContextPath()+"/login"); return; }

    Student student = (Student) request.getAttribute("student");
    boolean editMode = Boolean.TRUE.equals(request.getAttribute("editMode"));
    String error     = (String) request.getAttribute("error");
    String ctx       = request.getContextPath();

    // Prefill values
    String fullName   = (student != null && student.getFullName()   != null) ? student.getFullName()   : "";
    String email      = (student != null && student.getEmail()      != null) ? student.getEmail()      : "";
    String phone      = (student != null && student.getPhone()      != null) ? student.getPhone()      : "";
    String department = (student != null && student.getDepartment() != null) ? student.getDepartment() : "";
    int    year       = (student != null) ? student.getYear() : 1;
    String cgpa       = (student != null && student.getCgpa() > 0)
                        ? String.format("%.2f", student.getCgpa()) : "";
    int    id         = (student != null) ? student.getStudentId() : 0;

    String pageTitle  = editMode ? "Edit Student Record" : "New Student Enrollment";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= pageTitle %> - EduManage ERP</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
<div class="layout-wrapper">
  <%@ include file="includes/sidebar.jsp" %>
  
  <main class="main-wrapper">
    <header class="topbar shadow-sm">
      <div>
        <h4 class="mb-0 fw-bold text-dark"><%= pageTitle %></h4>
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb mb-0 small mt-1">
            <li class="breadcrumb-item"><a href="<%= ctx %>/dashboard" class="text-decoration-none">Dashboard</a></li>
            <li class="breadcrumb-item"><a href="<%= ctx %>/students?action=list" class="text-decoration-none">Students</a></li>
            <li class="breadcrumb-item active" aria-current="page"><%= editMode ? "Edit Record" : "Registration" %></li>
          </ol>
        </nav>
      </div>
      <div>
        <a href="<%= ctx %>/students?action=list" class="btn btn-outline-secondary btn-sm">
          <i class="fa-solid fa-arrow-left me-1"></i> Back to Directory
        </a>
      </div>
    </header>

    <section class="content-area d-flex justify-content-center">
      <div class="card shadow-sm border-0 w-100" style="max-width: 800px;">
        <div class="card-header bg-white py-3 border-bottom">
          <h5 class="mb-0 fw-semibold text-dark d-flex align-items-center">
            <div class="bg-primary bg-opacity-10 text-primary rounded p-2 me-3">
              <i class="fa-solid <%= editMode ? "fa-user-pen" : "fa-user-plus" %>"></i>
            </div>
            <%= editMode ? "Modify Student Profile [ID: #" + id + "]" : "Student Registration Form" %>
          </h5>
        </div>
        
        <div class="card-body p-4 p-md-5">

          <% if (error != null && !error.isEmpty()) { %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <i class="fa-solid fa-triangle-exclamation me-2"></i> <%= error %>
              <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
          <% } %>

          <form id="studentForm" method="post" action="<%= ctx %>/students?action=<%= editMode ? "edit" : "add" %>" class="needs-validation" novalidate>
            <% if (editMode) { %>
              <input type="hidden" name="studentId" value="<%= id %>">
            <% } %>

            <h6 class="text-primary text-uppercase fw-bold mb-3 small letter-spacing">Personal Details</h6>
            <div class="row g-4 mb-5">
              <div class="col-md-12">
                <label for="fullName" class="form-label fw-medium">Full Name <span class="text-danger">*</span></label>
                <input type="text" id="fullName" name="fullName" class="form-control" value="<%= fullName %>" placeholder="e.g. John Doe" required maxlength="100">
                <div class="invalid-feedback">Please provide the student's full name.</div>
              </div>
              <div class="col-md-6">
                <label for="email" class="form-label fw-medium">Email Address <span class="text-danger">*</span></label>
                <div class="input-group">
                  <span class="input-group-text bg-light"><i class="fa-regular fa-envelope"></i></span>
                  <input type="email" id="email" name="email" class="form-control" value="<%= email %>" placeholder="e.g. student@domain.com" required maxlength="150">
                  <div class="invalid-feedback">Please provide a valid email format.</div>
                </div>
              </div>
              <div class="col-md-6">
                <label for="phone" class="form-label fw-medium">Contact Number <span class="text-danger">*</span></label>
                <div class="input-group">
                  <span class="input-group-text bg-light"><i class="fa-solid fa-phone"></i></span>
                  <input type="tel" id="phone" name="phone" class="form-control" value="<%= phone %>" placeholder="10-digit mobile number" required maxlength="10" pattern="\d{10}">
                  <div class="invalid-feedback">Please enter exactly 10 digits.</div>
                </div>
              </div>
            </div>

            <h6 class="text-primary text-uppercase fw-bold mb-3 small letter-spacing">Academic Information</h6>
            <div class="row g-4 mb-4">
              <div class="col-md-6">
                <label for="department" class="form-label fw-medium">Department <span class="text-danger">*</span></label>
                <select id="department" name="department" class="form-select" required>
                  <option value="" disabled <%= department.isEmpty() ? "selected" : "" %>>Choose department...</option>
                  <% String[] depts = {"Computer Science","Information Technology","Electronics","Mechanical","Civil Engineering","Chemical Engineering","Biotechnology","MBA"};
                     for (String d : depts) { %>
                  <option value="<%= d %>" <%= department.equals(d) ? "selected" : "" %>><%= d %></option>
                  <% } %>
                </select>
                <div class="invalid-feedback">Please select an academic department.</div>
              </div>
              <div class="col-md-3">
                <label for="year" class="form-label fw-medium">Year <span class="text-danger">*</span></label>
                <select id="year" name="year" class="form-select" required>
                  <% for (int y = 1; y <= 4; y++) { %>
                  <option value="<%= y %>" <%= year == y ? "selected" : "" %>>Year <%= y %></option>
                  <% } %>
                </select>
              </div>
              <div class="col-md-3">
                <label for="cgpa" class="form-label fw-medium">Current CGPA <span class="text-danger">*</span></label>
                <input type="number" id="cgpa" name="cgpa" class="form-control" value="<%= cgpa %>" placeholder="0.00" min="0" max="10" step="0.01" required>
                <div class="invalid-feedback">Value must be between 0.00 and 10.00.</div>
              </div>
            </div>

            <div class="d-flex justify-content-end gap-3 mt-5 pt-3 border-top">
              <a href="<%= ctx %>/students?action=list" class="btn btn-light border px-4">Cancel</a>
              <button type="submit" class="btn btn-primary px-5">
                <i class="fa-solid fa-floppy-disk me-2"></i> <%= editMode ? "Update Record" : "Save Record" %>
              </button>
            </div>
          </form>
        </div>
      </div>
    </section>
  </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="<%= ctx %>/js/app.js"></script>
<script>
  // Bootstrap client-side validation logic
  (() => {
    'use strict'
    const forms = document.querySelectorAll('.needs-validation')
    Array.from(forms).forEach(form => {
      form.addEventListener('submit', event => {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add('was-validated')
      }, false)
    })
  })()
</script>
</body>
</html>
