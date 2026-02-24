document.addEventListener("click", function(e) {
  var target = e.target;
  if (!target || !target.matches || !target.matches("[data-admin-deactivate-job]")) {
    return;
  }
  var jobId = target.getAttribute("data-admin-deactivate-job");
  if (!jobId) {
    return;
  }
  if (typeof ensureRoleAccess === "function") {
    if (!ensureRoleAccess("ADMIN")) {
      return;
    }
  }
  if (typeof apiFetch !== "function") {
    return;
  }
  apiFetch("/admin/jobs/" + encodeURIComponent(jobId) + "/deactivate", {
    method: "POST"
  }).then(function() {
    if (typeof showToast === "function") {
      showToast("Job deactivated.");
    }
    if (typeof fetchAdminEntity === "function") {
      fetchAdminEntity("jobs");
    }
  }).catch(function() {});
});

