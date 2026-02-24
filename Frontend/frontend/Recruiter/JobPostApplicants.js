document.addEventListener("DOMContentLoaded", function () {

  // Role protection
  if (typeof ensureRoleAccess === "function") {
    if (!ensureRoleAccess("RECRUITER")) {
      return;
    }
  }

  var container = document.getElementById("applicationsTable");
  if (!container) return;

  var params = new URLSearchParams(window.location.search);
  var jobId = params.get("jobId");

  if (!jobId) {
    container.textContent = "No jobId provided. Pass ?jobId=123 in the URL.";
    return;
  }

  container.textContent = "Loading applicants...";

  // 🔥 CHANGED FROM apiFetch → api.request
  api.request("/recruiter/jobs/" + encodeURIComponent(jobId) + "/applications", {
    method: "GET"
  })
  .then(function (data) {

    var items = Array.isArray(data) ? data : (data && data.content) || [];

    if (items.length === 0) {
      container.textContent = "No applicants for this job yet.";
      return;
    }

    var html = `
      <table class="table">
        <thead>
          <tr>
            <th>Candidate</th>
            <th>Email</th>
            <th>Status</th>
            <th>Note</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
    `;

    items.forEach(function (app) {

      var name = app.candidateName || (app.candidate && app.candidate.name) || "Candidate";
      var email = app.candidateEmail || (app.candidate && app.candidate.email) || "";
      var status = app.status || "Submitted";
      var note = app.note || "";

      html += `
        <tr>
          <td>${name}</td>
          <td>${email}</td>
          <td>${status}</td>
          <td>${note}</td>
          <td>
            <select onchange="updateStatus(${app.id}, this.value)">
              <option value="">Change</option>
              <option value="SHORTLISTED">Shortlist</option>
              <option value="REJECTED">Reject</option>
              <option value="HIRED">Hire</option>
            </select>
          </td>
        </tr>
      `;
    });

    html += "</tbody></table>";
    container.innerHTML = html;

  })
  .catch(function (err) {
    container.textContent = "Could not load applicants.";
    console.error(err.message);
  });

});


// 🔥 NEW FUNCTION: Update Status
function updateStatus(applicationId, status) {

  if (!status) return;

  api.request("/recruiter/applications/" + applicationId + "/status", {
    method: "PUT",
    body: JSON.stringify({ status: status })
  })
  .then(function () {
    alert("Status updated successfully");
    location.reload();
  })
  .catch(function (err) {
    alert(err.message);
  });

}