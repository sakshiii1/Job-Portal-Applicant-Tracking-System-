document.addEventListener("DOMContentLoaded", function () {
  console.log("🚀 Admin dashboard loaded");

  var usersEl = document.getElementById("adminUsers");
  var pendingEl = document.getElementById("pendingRecruiters");
  var pendingCountEl = document.getElementById("pendingCount");
  
  if (!usersEl) {
    console.error("❌ adminUsers element not found");
    return;
  }

  // Show loading state
  usersEl.innerHTML = '<div style="text-align: center; padding: 20px;"><i class="fas fa-spinner fa-spin"></i> Loading users...</div>';
  if (pendingEl) {
    pendingEl.innerHTML = '<div style="text-align: center; padding: 20px;"><i class="fas fa-spinner fa-spin"></i> Loading pending approvals...</div>';
  }

  // Check if UserApi exists
  if (typeof UserApi === "undefined") {
    usersEl.innerHTML = '<div style="color: #ef4444; padding: 20px;">❌ UserApi not found. Check if user.js is loaded.</div>';
    console.error("UserApi is undefined");
    return;
  }

  if (typeof UserApi.listAll !== "function") {
    usersEl.innerHTML = '<div style="color: #ef4444; padding: 20px;">❌ UserApi.listAll is not a function</div>';
    console.error("UserApi.listAll is not a function", UserApi);
    return;
  }

  // Call API
  UserApi.listAll()
    .then(function (response) {
      console.log("✅ API Response:", response); // Debug: dekho data ka structure
      
      // Handle different response structures
      var users = response;
      
      // Agar response mein data field ho (e.g., { data: [...] })
      if (response && response.data && Array.isArray(response.data)) {
        users = response.data;
      }
      
      // Agar response object ho array nahi
      if (!Array.isArray(users)) {
        console.error("Response is not an array:", users);
        usersEl.innerHTML = '<div style="color: #ef4444; padding: 20px;">❌ Invalid response format</div>';
        return;
      }

      usersEl.innerHTML = ""; // Clear loading

      if (users.length === 0) {
        usersEl.innerHTML = '<div style="text-align: center; padding: 20px; color: rgba(148,163,184,0.5);">📭 No users found.</div>';
        return;
      }

      // ========== PENDING RECRUITERS SECTION ==========
      if (pendingEl) {
        var pendingRecruiters = users.filter(function(user) {
          var role = (user.role || '').toUpperCase();
          return role === 'RECRUITER' && (user.approvalStatus === 'PENDING' || !user.approvalStatus);
        });
        
        if (pendingCountEl) {
          pendingCountEl.textContent = pendingRecruiters.length;
        }
        
        if (pendingRecruiters.length === 0) {
          pendingEl.innerHTML = `
            <div style="text-align: center; padding: 20px; color: rgba(148,163,184,0.5);">
              <i class="fas fa-check-circle" style="font-size: 24px; margin-bottom: 10px; color: #10b981;"></i>
              <p>No pending approvals</p>
            </div>
          `;
        } else {
          var pendingHtml = '<div style="display: flex; flex-direction: column; gap: 10px;">';
          
          pendingRecruiters.forEach(function(recruiter) {
            var name = recruiter.fullName || recruiter.name || "No Name";
            var email = recruiter.email || "No Email";
            
            pendingHtml += `
              <div style="display: flex; align-items: center; justify-content: space-between; background: rgba(255,255,255,0.02); padding: 15px; border-radius: 12px; border-left: 4px solid #f59e0b;">
                <div>
                  <div style="font-weight: 600; margin-bottom: 4px;">${name}</div>
                  <div style="font-size: 12px; color: rgba(148,163,184,0.7);">
                    <i class="fas fa-envelope"></i> ${email}
                  </div>
                </div>
                <div style="display: flex; gap: 8px;">
                  <button class="dash-button" style="padding: 6px 16px; background: rgba(16,185,129,0.1); color: #10b981; border: 1px solid rgba(16,185,129,0.2); border-radius: 8px; cursor: pointer;" onclick="approveRecruiter(${recruiter.id})">
                    <i class="fas fa-check"></i> Approve
                  </button>
                  <button class="dash-button" style="padding: 6px 16px; background: rgba(239,68,68,0.1); color: #ef4444; border: 1px solid rgba(239,68,68,0.2); border-radius: 8px; cursor: pointer;" onclick="rejectRecruiter(${recruiter.id})">
                    <i class="fas fa-times"></i> Reject
                  </button>
                </div>
              </div>
            `;
          });
          
          pendingHtml += '</div>';
          pendingEl.innerHTML = pendingHtml;
        }
      }

      // ========== CALCULATE STATS ==========
      var stats = {
        totalUsers: users.length,
        admins: 0,
        recruiters: 0,
        candidates: 0,
        pendingRecruiters: 0
      };
      
      users.forEach(function(user) {
        var role = (user.role || '').toUpperCase();
        if (role === 'ADMIN') stats.admins++;
        else if (role === 'RECRUITER') {
          stats.recruiters++;
          if (user.approvalStatus === 'PENDING' || !user.approvalStatus) stats.pendingRecruiters++;
        }
        else if (role === 'CANDIDATE') stats.candidates++;
      });
      
      // Update stats display if elements exist
      if (document.getElementById('totalUsers')) {
        document.getElementById('totalUsers').textContent = stats.totalUsers;
      }
      if (document.getElementById('totalAdmins')) {
        document.getElementById('totalAdmins').textContent = stats.admins;
      }
      if (document.getElementById('totalRecruiters')) {
        document.getElementById('totalRecruiters').textContent = stats.recruiters;
      }
      if (document.getElementById('totalCandidates')) {
        document.getElementById('totalCandidates').textContent = stats.candidates;
      }

      // ========== CREATE USER TABLE ==========
      var table = document.createElement("table");
      table.style.width = "100%";
      table.style.borderCollapse = "collapse";
      
      // Table header
      table.innerHTML = `
        <thead>
          <tr style="border-bottom: 1px solid rgba(148,163,184,0.2);">
            <th style="text-align: left; padding: 12px;">Name</th>
            <th style="text-align: left; padding: 12px;">Email</th>
            <th style="text-align: left; padding: 12px;">Role</th>
            <th style="text-align: left; padding: 12px;">Status</th>
            <th style="text-align: left; padding: 12px;">Actions</th>
          </tr>
        </thead>
        <tbody id="userTableBody"></tbody>
      `;
      
      usersEl.appendChild(table);
      var tbody = document.getElementById("userTableBody");

      users.forEach(function (user) {
        // Handle different possible field names
        var name = user.fullName || user.name || user.full_name || user.username || "No Name";
        var email = user.email || user.emailAddress || user.mail || "No Email";
        var role = user.role || user.userRole || user.roles || "UNKNOWN";
        var status = user.approvalStatus || (role === 'RECRUITER' ? 'PENDING' : 'APPROVED');
        
        // Create row
        var row = document.createElement("tr");
        row.style.borderBottom = "1px solid rgba(148,163,184,0.1)";
        row.style.transition = "background 0.3s";
        row.onmouseover = function() { this.style.background = "rgba(255,255,255,0.02)"; };
        row.onmouseout = function() { this.style.background = "transparent"; };
        
        // Role badge color
        var roleColor = "#94a3b8";
        if (role.toUpperCase() === 'ADMIN') roleColor = "#ef4444";
        else if (role.toUpperCase() === 'RECRUITER') roleColor = "#f59e0b";
        else if (role.toUpperCase() === 'CANDIDATE') roleColor = "#10b981";
        
        // Status badge color
        var statusColor = status === 'APPROVED' ? '#10b981' : (status === 'REJECTED' ? '#ef4444' : '#f59e0b');
        var statusBg = status === 'APPROVED' ? 'rgba(16,185,129,0.1)' : (status === 'REJECTED' ? 'rgba(239,68,68,0.1)' : 'rgba(245,158,11,0.1)');
        
        row.innerHTML = `
          <td style="padding: 12px; font-weight: 500;">${name}</td>
          <td style="padding: 12px; color: rgba(148,163,184,0.8);">${email}</td>
          <td style="padding: 12px;">
            <span style="
              background: ${roleColor}10;
              color: ${roleColor};
              padding: 4px 12px;
              border-radius: 999px;
              font-size: 11px;
              font-weight: 500;
              border: 1px solid ${roleColor}30;
            ">${role}</span>
          </td>
          <td style="padding: 12px;">
            <span style="
              background: ${statusBg};
              color: ${statusColor};
              padding: 4px 12px;
              border-radius: 999px;
              font-size: 11px;
              font-weight: 500;
              border: 1px solid ${statusColor}30;
            ">${status}</span>
          </td>
          <td style="padding: 12px;">
            ${role === 'RECRUITER' && status !== 'APPROVED' ? `
              <button class="action-btn" style="background: rgba(16,185,129,0.1); color: #10b981; border: 1px solid rgba(16,185,129,0.2); padding: 6px 12px; border-radius: 6px; margin-right: 5px; cursor: pointer;" onclick="approveRecruiter(${user.id})">
                <i class="fas fa-check"></i>
              </button>
            ` : ''}
            <button class="action-btn" style="background: rgba(129,140,248,0.1); color: rgb(129,140,248); border: 1px solid rgba(129,140,248,0.2); padding: 6px 12px; border-radius: 6px; margin-right: 5px; cursor: pointer;" onclick="editUser(${user.id})">
              <i class="fas fa-edit"></i>
            </button>
            <button class="action-btn delete" style="background: rgba(239,68,68,0.1); color: #ef4444; border: 1px solid rgba(239,68,68,0.2); padding: 6px 12px; border-radius: 6px; cursor: pointer;" onclick="deleteUser(${user.id})">
              <i class="fas fa-trash"></i>
            </button>
          </td>
        `;
        
        tbody.appendChild(row);
      });

      // Add count
      var countDiv = document.createElement("div");
      countDiv.style.marginTop = "15px";
      countDiv.style.padding = "10px";
      countDiv.style.background = "rgba(129,140,248,0.1)";
      countDiv.style.borderRadius = "8px";
      countDiv.style.fontSize = "13px";
      countDiv.style.color = "rgb(129,140,248)";
      countDiv.innerHTML = `<i class="fas fa-users"></i> Total Users: ${users.length} | 
        <i class="fas fa-user-tie"></i> Admins: ${stats.admins} | 
        <i class="fas fa-user-plus"></i> Recruiters: ${stats.recruiters} | 
        <i class="fas fa-user-graduate"></i> Candidates: ${stats.candidates} |
        <i class="fas fa-clock"></i> Pending: ${stats.pendingRecruiters}`;
      usersEl.appendChild(countDiv);

    })
    .catch(function (err) {
      console.error("❌ Error loading users:", err);
      
      // Show detailed error
      var errorMsg = "Failed to load users.";
      if (err.message) errorMsg += " " + err.message;
      if (err.status) errorMsg += " (Status: " + err.status + ")";
      
      usersEl.innerHTML = `<div style="color: #ef4444; padding: 20px;">
        <i class="fas fa-exclamation-circle"></i> ${errorMsg}<br>
        <small style="opacity: 0.7;">Check console for details (F12)</small>
      </div>`;
    });

});

// ========== RECRUITER APPROVAL FUNCTIONS ==========
window.approveRecruiter = function(recruiterId) {
  if (confirm('Are you sure you want to approve this recruiter?')) {
    // Call API to approve recruiter
    fetch('/api/admin/recruiter/' + recruiterId + '/status', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('ats_jwt')
      },
      body: JSON.stringify({ status: 'APPROVED' })
    })
    .then(res => res.json())
    .then(data => {
      alert('Recruiter approved successfully!');
      location.reload(); // Refresh page
    })
    .catch(err => {
      console.error('Error approving recruiter:', err);
      alert('Failed to approve recruiter');
    });
  }
};

window.rejectRecruiter = function(recruiterId) {
  if (confirm('Are you sure you want to reject this recruiter?')) {
    // Call API to reject recruiter
    fetch('/api/admin/recruiter/' + recruiterId + '/status', {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('ats_jwt')
      },
      body: JSON.stringify({ status: 'REJECTED' })
    })
    .then(res => res.json())
    .then(data => {
      alert('Recruiter rejected!');
      location.reload(); // Refresh page
    })
    .catch(err => {
      console.error('Error rejecting recruiter:', err);
      alert('Failed to reject recruiter');
    });
  }
};

window.editUser = function(userId) {
  alert('Edit user: ' + userId);
  // Navigate to edit page
  // window.location.href = 'edit-user.html?id=' + userId;
};

window.deleteUser = function(userId) {
  if (confirm('Are you sure you want to delete this user? This action cannot be undone.')) {
    alert('Delete user: ' + userId);
    // Call API to delete user
  }
};