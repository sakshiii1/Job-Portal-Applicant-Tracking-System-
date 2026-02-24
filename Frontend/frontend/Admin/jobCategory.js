document.addEventListener("DOMContentLoaded", function() {
  var form = document.getElementById("adminCategoryForm");
  if (!form) {
    return;
  }
  if (typeof ensureRoleAccess === "function") {
    if (!ensureRoleAccess("ADMIN")) {
      return;
    }
  }
  form.addEventListener("submit", function(e) {
    e.preventDefault();
    if (typeof apiFetch !== "function") {
      return;
    }
    var nameInput = document.getElementById("adminCategoryName");
    var codeInput = document.getElementById("adminCategoryCode");
    var errorEl = document.getElementById("adminCategoryError");
    var successEl = document.getElementById("adminCategorySuccess");
    if (errorEl) {
      errorEl.textContent = "";
    }
    if (successEl) {
      successEl.textContent = "";
    }
    var payload = {
      name: nameInput ? nameInput.value.trim() : "",
      code: codeInput ? codeInput.value.trim() : ""
    };
    apiFetch("/admin/categories", {
      method: "POST",
      body: JSON.stringify(payload)
    }).then(function() {
      if (successEl) {
        successEl.textContent = "Category created.";
      }
      if (typeof showToast === "function") {
        showToast("Category created.");
      }
      if (typeof fetchAdminEntity === "function") {
        fetchAdminEntity("categories");
      }
    }).catch(function(err) {
      if (errorEl) {
        errorEl.textContent = err.message;
      }
    });
  });
});

