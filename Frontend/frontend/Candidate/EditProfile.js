document.addEventListener("DOMContentLoaded", function() {
  if (typeof ensureRoleAccess === "function") {
    if (!ensureRoleAccess("CANDIDATE")) {
      return;
    }
  }
  var profileContainer = document.getElementById("candidateProfile");
  var form = document.getElementById("candidateProfileForm");
  if (profileContainer) {
    loadProfile(profileContainer);
  }
  if (form) {
    bindProfileForm(form);
  }
});
function loadProfile(target) {
  if (typeof apiFetch !== "function") {
    target.textContent = "Profile endpoint not wired yet.";
    return;
  }
  apiFetch("/candidate/profile", {
    method: "GET"
  }).then(function(data) {
    var name = data && (data.name || data.fullName || "");
    var email = data && (data.email || "");
    var headline = data && (data.headline || "");
    var summary = data && (data.summary || "");
    target.innerHTML = "<div><strong>" + (name || "Candidate") + "</strong></div>" +
      "<div class=\"helper-text\">" + (email || "Email not set") + "</div>" +
      "<div class=\"helper-text\" style=\"margin-top:6px;\">" + (headline || "Add a headline on the edit page.") + "</div>" +
      "<div class=\"helper-text\" style=\"margin-top:6px;\">" + (summary || "Add a short summary on the edit page.") + "</div>";
    var nameInput = document.getElementById("profileName");
    var emailInput = document.getElementById("profileEmail");
    var headlineInput = document.getElementById("profileHeadline");
    var summaryInput = document.getElementById("profileSummary");
    if (nameInput) {
      nameInput.value = name || "";
    }
    if (emailInput) {
      emailInput.value = email || "";
    }
    if (headlineInput) {
      headlineInput.value = headline || "";
    }
    if (summaryInput) {
      summaryInput.value = summary || "";
    }
  }).catch(function() {
    target.textContent = "Could not load profile. Confirm /candidate/profile endpoint.";
  });
}
function bindProfileForm(form) {
  var errorEl = document.getElementById("profileError");
  var successEl = document.getElementById("profileSuccess");
  form.addEventListener("submit", function(e) {
    e.preventDefault();
    if (typeof apiFetch !== "function") {
      if (errorEl) {
        errorEl.textContent = "apiFetch not available.";
      }
      return;
    }
    if (errorEl) {
      errorEl.textContent = "";
    }
    if (successEl) {
      successEl.textContent = "";
    }
    var nameInput = document.getElementById("profileName");
    var emailInput = document.getElementById("profileEmail");
    var headlineInput = document.getElementById("profileHeadline");
    var summaryInput = document.getElementById("profileSummary");
    var payload = {
      name: nameInput ? nameInput.value.trim() : "",
      email: emailInput ? emailInput.value.trim() : "",
      headline: headlineInput ? headlineInput.value.trim() : "",
      summary: summaryInput ? summaryInput.value.trim() : ""
    };
    apiFetch("/candidate/profile", {
      method: "PUT",
      body: JSON.stringify(payload)
    }).then(function() {
      if (successEl) {
        successEl.textContent = "Profile updated.";
      }
      if (typeof showToast === "function") {
        showToast("Profile updated.");
      }
    }).catch(function(err) {
      if (errorEl) {
        errorEl.textContent = err.message;
      }
    });
  });
}

