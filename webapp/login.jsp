<%@ page contentType="text/html;charset=UTF-8" %>
<%
    HttpSession s = request.getSession(false);
    if (s != null && s.getAttribute("adminUser") != null) {
        response.sendRedirect(request.getContextPath() + "/dashboard");
        return;
    }
    String error = (String) request.getAttribute("error");
    String msg   = request.getParameter("msg");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login - EduManage ERP</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body class="login-body">
  <div class="container d-flex justify-content-center align-items-center min-vh-100">
    <div class="card login-card p-4">
      <div class="text-center mb-4">
        <i class="fa-solid fa-building-columns text-primary mb-3" style="font-size: 3rem;"></i>
        <h4 class="fw-bold text-dark">EduManage ERP</h4>
        <p class="text-muted small">Sign in to access the administrator portal</p>
      </div>

      <% if ("logout".equals(msg)) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
          <i class="fa-solid fa-circle-check me-2"></i> Successfully logged out.
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      <% } %>

      <% if (error != null && !error.isEmpty()) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
          <i class="fa-solid fa-triangle-exclamation me-2"></i> <%= error %>
          <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
      <% } %>

      <form action="<%= request.getContextPath() %>/login" method="post" id="loginForm">
        <div class="mb-3">
          <label for="username" class="form-label fw-semibold text-dark">Username</label>
          <div class="input-group">
            <span class="input-group-text bg-light"><i class="fa-solid fa-user text-muted"></i></span>
            <input type="text" id="username" name="username" class="form-control" placeholder="Enter username" required>
          </div>
        </div>
        
        <div class="mb-4">
          <label for="password" class="form-label fw-semibold text-dark">Password</label>
          <div class="input-group">
            <span class="input-group-text bg-light"><i class="fa-solid fa-lock text-muted"></i></span>
            <input type="password" id="password" name="password" class="form-control" placeholder="Enter password" required>
          </div>
        </div>

        <button type="submit" class="btn btn-primary w-100 fw-semibold py-2">
          Sign In
        </button>
      </form>

      <div class="text-center mt-4 text-muted small">
        <p class="mb-0">Default credentials: <strong>admin</strong> / <strong>admin123</strong></p>
      </div>
    </div>
  </div>
  
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  <script src="<%= request.getContextPath() %>/js/app.js"></script>
</body>
</html>
