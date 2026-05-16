<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sms.model.Student, java.util.List" %>
<%
    String adminUser = (String) session.getAttribute("adminUser");
    if (adminUser == null) { response.sendRedirect(request.getContextPath()+"/login"); return; }
    int totalStudents       = (Integer) request.getAttribute("totalStudents");
    List<Student> recent    = (List<Student>) request.getAttribute("recentStudents");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - EduManage ERP</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
<div class="layout-wrapper">
  
  <%@ include file="includes/sidebar.jsp" %>
  
  <main class="main-wrapper">
    <!-- Topbar -->
    <header class="topbar shadow-sm">
      <div>
        <h4 class="mb-0 fw-bold text-dark">Dashboard</h4>
        <span class="text-muted small">System Overview & Statistics</span>
      </div>
      <div class="d-flex align-items-center gap-3">
        <span class="text-muted small">Logged in as <strong><%= adminUser %></strong></span>
        <a href="<%= request.getContextPath() %>/logout" class="btn btn-outline-secondary btn-sm" title="Logout">
          <i class="fa-solid fa-arrow-right-from-bracket"></i>
        </a>
      </div>
    </header>

    <!-- Content Area -->
    <section class="content-area">
      
      <!-- Flash messages -->
      <% String sMsg = (String) session.getAttribute("successMsg");
         String eMsg = (String) session.getAttribute("errorMsg");
         if (sMsg != null) { session.removeAttribute("successMsg"); %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fa-solid fa-circle-check me-2"></i> <%= sMsg %>
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      <% } if (eMsg != null) { session.removeAttribute("errorMsg"); %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
          <i class="fa-solid fa-triangle-exclamation me-2"></i> <%= eMsg %>
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      <% } %>

      <!-- Statistics Row -->
      <div class="row g-4 mb-4">
        <div class="col-md-3 col-sm-6">
          <div class="card stat-card h-100">
            <div class="card-body d-flex align-items-center">
              <div class="bg-primary bg-opacity-10 text-primary rounded p-3 me-3">
                <i class="fa-solid fa-users stat-icon"></i>
              </div>
              <div>
                <h6 class="text-muted mb-1 small fw-semibold text-uppercase">Total Enrolled</h6>
                <h3 class="mb-0 fw-bold text-dark"><%= totalStudents %></h3>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-3 col-sm-6">
          <div class="card stat-card h-100">
            <div class="card-body d-flex align-items-center">
              <div class="bg-success bg-opacity-10 text-success rounded p-3 me-3">
                <i class="fa-solid fa-user-clock stat-icon"></i>
              </div>
              <div>
                <h6 class="text-muted mb-1 small fw-semibold text-uppercase">Recent Admits</h6>
                <h3 class="mb-0 fw-bold text-dark"><%= recent != null ? recent.size() : 0 %></h3>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-3 col-sm-6">
          <div class="card stat-card h-100">
            <div class="card-body d-flex align-items-center">
              <div class="bg-info bg-opacity-10 text-info rounded p-3 me-3">
                <i class="fa-solid fa-server stat-icon"></i>
              </div>
              <div>
                <h6 class="text-muted mb-1 small fw-semibold text-uppercase">System Status</h6>
                <h5 class="mb-0 fw-bold text-success mt-1">Operational</h5>
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-3 col-sm-6">
          <div class="card stat-card h-100">
            <div class="card-body d-flex align-items-center">
              <div class="bg-warning bg-opacity-10 text-warning rounded p-3 me-3">
                <i class="fa-solid fa-calendar-days stat-icon"></i>
              </div>
              <div>
                <h6 class="text-muted mb-1 small fw-semibold text-uppercase">Academic Year</h6>
                <h5 class="mb-0 fw-bold text-dark mt-1">2026-27</h5>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Recent Students Table -->
      <div class="card shadow-sm border-0 mb-4">
        <div class="card-header bg-white d-flex justify-content-between align-items-center py-3">
          <h5 class="mb-0 fw-semibold text-dark">
            <i class="fa-solid fa-clock-rotate-left text-muted me-2"></i>Recent Enrollments
          </h5>
          <a href="<%= request.getContextPath() %>/students?action=list" class="btn btn-outline-primary btn-sm">
            View Directory
          </a>
        </div>
        <div class="card-body p-0">
          <div class="table-responsive">
            <% if (recent == null || recent.isEmpty()) { %>
              <div class="text-center p-5 text-muted">
                <i class="fa-solid fa-folder-open mb-3" style="font-size: 3rem; opacity: 0.5;"></i>
                <h5>No Records Found</h5>
                <p>There are currently no students registered in the system.</p>
                <a href="<%= request.getContextPath() %>/students?action=add" class="btn btn-primary mt-2">New Enrollment</a>
              </div>
            <% } else { %>
              <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                  <tr>
                    <th class="ps-4">Student ID</th>
                    <th>Full Name</th>
                    <th>Department</th>
                    <th>Year</th>
                    <th>CGPA</th>
                    <th class="text-end pe-4">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <% for (Student st : recent) { %>
                    <tr>
                      <td class="ps-4"><span class="badge bg-secondary">#<%= st.getStudentId() %></span></td>
                      <td class="fw-medium text-dark"><%= st.getFullName() %></td>
                      <td><%= st.getDepartment() %></td>
                      <td>Year <%= st.getYear() %></td>
                      <td>
                        <span class="cgpa-badge fw-bold" data-cgpa="<%= st.getCgpa() %>">
                          <%= String.format("%.2f", st.getCgpa()) %>
                        </span>
                      </td>
                      <td class="text-end pe-4">
                        <a href="<%= request.getContextPath() %>/students?action=edit&id=<%= st.getStudentId() %>" class="btn btn-sm btn-light border text-primary" title="Edit">
                          <i class="fa-solid fa-pen"></i> Edit
                        </a>
                      </td>
                    </tr>
                  <% } %>
                </tbody>
              </table>
            <% } %>
          </div>
        </div>
      </div>

    </section>
  </main>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="<%= request.getContextPath() %>/js/app.js"></script>
</body>
</html>
