<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.sms.model.Student, java.util.List" %>
<%
    String adminUser = (String) session.getAttribute("adminUser");
    if (adminUser == null) { response.sendRedirect(request.getContextPath()+"/login"); return; }

    List<Student> students  = (List<Student>) request.getAttribute("students");
    int totalRecords        = (Integer) request.getAttribute("totalRecords");
    int currentPage         = (Integer) request.getAttribute("currentPage");
    int totalPages          = (Integer) request.getAttribute("totalPages");
    String search           = (String)  request.getAttribute("search");
    if (search == null) search = "";
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Directory - EduManage ERP</title>
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
    <!-- Topbar -->
    <header class="topbar shadow-sm">
      <div>
        <h4 class="mb-0 fw-bold text-dark">Student Directory</h4>
        <nav aria-label="breadcrumb">
          <ol class="breadcrumb mb-0 small mt-1">
            <li class="breadcrumb-item"><a href="<%= ctx %>/dashboard" class="text-decoration-none">Dashboard</a></li>
            <li class="breadcrumb-item active" aria-current="page">Students</li>
          </ol>
        </nav>
      </div>
      <div>
        <a href="<%= ctx %>/students?action=add" class="btn btn-primary">
          <i class="fa-solid fa-plus me-1"></i> New Enrollment
        </a>
      </div>
    </header>

    <!-- Content Area -->
    <section class="content-area">
      <!-- Flash Messages -->
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

      <div class="card shadow-sm border-0">
        <div class="card-header bg-white py-3 border-bottom">
          <div class="row align-items-center">
            <div class="col-md-5">
              <h5 class="mb-0 fw-semibold text-dark">
                <i class="fa-solid fa-table-list text-muted me-2"></i> Records 
                <span class="badge bg-light text-secondary border ms-1"><%= totalRecords %></span>
              </h5>
            </div>
            <div class="col-md-7 mt-3 mt-md-0">
              <form method="get" action="<%= ctx %>/students" class="d-flex justify-content-md-end gap-2">
                <input type="hidden" name="action" value="list">
                <div class="input-group" style="max-width: 350px;">
                  <span class="input-group-text bg-white border-end-0"><i class="fa-solid fa-magnifying-glass text-muted"></i></span>
                  <input type="text" name="search" class="form-control border-start-0 ps-0" value="<%= search %>" placeholder="Search by name or ID...">
                  <button class="btn btn-primary" type="submit">Search</button>
                </div>
                <% if (!search.isEmpty()) { %>
                  <a href="<%= ctx %>/students?action=list" class="btn btn-outline-secondary" title="Clear Search">
                    <i class="fa-solid fa-rotate-left"></i> Reset
                  </a>
                <% } %>
              </form>
            </div>
          </div>
        </div>

        <div class="card-body p-0">
          <div class="table-responsive">
            <% if (students == null || students.isEmpty()) { %>
              <div class="text-center p-5 text-muted">
                <i class="fa-solid fa-magnifying-glass mb-3" style="font-size: 3rem; opacity: 0.3;"></i>
                <h5>No Results Found</h5>
                <p>We couldn't find any students matching your criteria.</p>
                <% if (!search.isEmpty()) { %>
                  <a href="<%= ctx %>/students?action=list" class="btn btn-outline-primary mt-2">Clear Search</a>
                <% } %>
              </div>
            <% } else { %>
              <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                  <tr>
                    <th class="ps-4">Student ID</th>
                    <th>Name</th>
                    <th>Contact Info</th>
                    <th>Department</th>
                    <th>Year</th>
                    <th>CGPA</th>
                    <th class="text-end pe-4">Actions</th>
                  </tr>
                </thead>
                <tbody>
                  <% for (Student st : students) { %>
                    <tr>
                      <td class="ps-4"><span class="badge bg-secondary">#<%= st.getStudentId() %></span></td>
                      <td>
                        <div class="fw-medium text-dark"><%= st.getFullName() %></div>
                      </td>
                      <td>
                        <div class="small text-muted mb-1"><i class="fa-regular fa-envelope me-1"></i><%= st.getEmail() %></div>
                        <div class="small text-muted"><i class="fa-solid fa-phone me-1"></i><%= st.getPhone() %></div>
                      </td>
                      <td>
                        <span class="badge bg-info bg-opacity-10 text-primary border border-info-subtle px-2 py-1">
                          <%= st.getDepartment() %>
                        </span>
                      </td>
                      <td>Year <%= st.getYear() %></td>
                      <td>
                        <span class="cgpa-badge fw-bold" data-cgpa="<%= st.getCgpa() %>">
                          <%= String.format("%.2f", st.getCgpa()) %>
                        </span>
                      </td>
                      <td class="text-end pe-4">
                        <div class="btn-group shadow-sm">
                          <a href="<%= ctx %>/students?action=edit&id=<%= st.getStudentId() %>" class="btn btn-sm btn-light border text-primary" title="Edit Record">
                            <i class="fa-solid fa-pen"></i>
                          </a>
                          <button type="button" class="btn btn-sm btn-light border text-danger btn-delete" data-id="<%= st.getStudentId() %>" data-name="<%= st.getFullName() %>" data-bs-toggle="modal" data-bs-target="#deleteModal" title="Delete Record">
                            <i class="fa-solid fa-trash-can"></i>
                          </button>
                        </div>
                      </td>
                    </tr>
                  <% } %>
                </tbody>
              </table>
            <% } %>
          </div>
        </div>
        
        <!-- Pagination -->
        <% if (totalPages > 1) { %>
        <div class="card-footer bg-white py-3 d-flex flex-column flex-md-row justify-content-between align-items-center border-top">
          <span class="text-muted small mb-3 mb-md-0">Showing page <%= currentPage %> of <%= totalPages %></span>
          <nav aria-label="Student records pagination">
            <ul class="pagination pagination-sm mb-0">
              <% String prevHref = ctx + "/students?action=list&page=" + (currentPage-1) + (!search.isEmpty() ? "&search="+search : ""); %>
              <li class="page-item <%= currentPage <= 1 ? "disabled" : "" %>">
                <a class="page-link" href="<%= currentPage > 1 ? prevHref : "#" %>" aria-label="Previous">
                  <span aria-hidden="true">&laquo; Previous</span>
                </a>
              </li>
              
              <% for (int p = 1; p <= totalPages; p++) {
                   String ph = ctx + "/students?action=list&page=" + p + (!search.isEmpty() ? "&search="+search : "");
              %>
                <li class="page-item <%= p == currentPage ? "active" : "" %>"><a class="page-link" href="<%= ph %>"><%= p %></a></li>
              <% } %>
              
              <% String nextHref = ctx + "/students?action=list&page=" + (currentPage+1) + (!search.isEmpty() ? "&search="+search : ""); %>
              <li class="page-item <%= currentPage >= totalPages ? "disabled" : "" %>">
                <a class="page-link" href="<%= currentPage < totalPages ? nextHref : "#" %>" aria-label="Next">
                  <span aria-hidden="true">Next &raquo;</span>
                </a>
              </li>
            </ul>
          </nav>
        </div>
        <% } %>
      </div>
    </section>
  </main>
</div>

<!-- Includes -->
<%@ include file="includes/delete-modal.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="<%= ctx %>/js/app.js"></script>
</body>
</html>
