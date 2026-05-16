<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%
    int statusCode = response.getStatus();
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error <%= statusCode %> - EduManage ERP</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- FontAwesome Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body class="error-page-wrapper bg-light">
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8 col-lg-6 text-center">
        
        <div class="mb-4">
          <% if (statusCode == 404) { %>
            <i class="fa-solid fa-map-location-dot text-secondary" style="font-size: 6rem; opacity: 0.3;"></i>
          <% } else { %>
            <i class="fa-solid fa-server text-danger" style="font-size: 6rem; opacity: 0.3;"></i>
          <% } %>
        </div>
        
        <h1 class="display-1 fw-bolder text-dark mb-0"><%= statusCode %></h1>
        <h3 class="h4 mb-4 fw-semibold text-secondary">
          <%= statusCode == 404 ? "Page Not Found" : "Internal Server Error" %>
        </h3>
        
        <p class="text-muted mb-5 lead fs-6">
          <%= statusCode == 404 
              ? "The resource you are looking for might have been removed, had its name changed, or is temporarily unavailable." 
              : "We are experiencing an internal server problem. Our engineers have been notified. Please try again later." %>
        </p>
        
        <a href="<%= ctx %>/dashboard" class="btn btn-primary px-4 py-2 fw-medium">
          <i class="fa-solid fa-house me-2"></i> Return to Dashboard
        </a>
      </div>
    </div>
  </div>
</body>
</html>
