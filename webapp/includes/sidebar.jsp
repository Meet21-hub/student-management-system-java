<%-- Shared Sidebar Include --%>
<%
    String _adminUser = (String) session.getAttribute("adminUser");
    String _initials  = (_adminUser != null && !_adminUser.isEmpty())
                        ? _adminUser.substring(0,1).toUpperCase() : "A";
%>
<aside class="sidebar shadow-sm">
  <div class="sidebar-header">
    <i class="fa-solid fa-building-columns text-primary"></i>
    <span>EduManage ERP</span>
  </div>
  
  <nav class="sidebar-nav">
    <ul class="p-0 m-0">
      <li class="nav-item mb-1">
        <a href="<%= request.getContextPath() %>/dashboard" class="nav-link" id="nav-dashboard">
          <i class="fa-solid fa-chart-pie fa-fw"></i> Dashboard
        </a>
      </li>
      <li class="nav-item mb-1">
        <a href="<%= request.getContextPath() %>/students?action=list" class="nav-link" id="nav-students">
          <i class="fa-solid fa-users-rectangle fa-fw"></i> Student Directory
        </a>
      </li>
      <li class="nav-item mb-1">
        <a href="<%= request.getContextPath() %>/students?action=add" class="nav-link" id="nav-add">
          <i class="fa-solid fa-user-plus fa-fw"></i> New Enrollment
        </a>
      </li>
    </ul>
  </nav>

  <div class="sidebar-footer d-flex align-items-center gap-3">
    <div class="rounded-circle bg-primary text-white d-flex justify-content-center align-items-center flex-shrink-0" style="width: 38px; height: 38px; font-weight: 600;">
      <%= _initials %>
    </div>
    <div class="text-truncate" style="font-size: 0.875rem;">
      <div class="fw-semibold text-white"><%= _adminUser != null ? _adminUser : "Admin" %></div>
      <div class="text-white-50" style="font-size: 0.75rem;">Administrator</div>
    </div>
  </div>
</aside>
