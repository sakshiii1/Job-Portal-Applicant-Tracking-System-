document.addEventListener("DOMContentLoaded", function () {
  var form = document.getElementById("registerForm");
  if (!form) {
    return;
  }
  var nameInput = document.getElementById("registerFullName");
  var emailInput = document.getElementById("registerEmail");
  var passwordInput = document.getElementById("registerPassword");
  var roleSelect = document.getElementById("registerRole");
  var errorBox = document.getElementById("registerError");
  var successBox = document.getElementById("registerSuccess");
  var submitButton = document.getElementById("registerSubmit");

  function setBusy(isBusy) {
    if (!submitButton) {
      return;
    }
    submitButton.disabled = isBusy;
  }

  function showError(message) {
    if (errorBox) {
      errorBox.textContent = message;
      errorBox.style.display = "block";
    }
    if (successBox) {
      successBox.textContent = "";
      successBox.style.display = "none";
    }
  }

  function showSuccess(message) {
    if (successBox) {
      successBox.textContent = message;
      successBox.style.display = "block";
    }
    if (errorBox) {
      errorBox.textContent = "";
      errorBox.style.display = "none";
    }
  }

  form.addEventListener("submit", function (event) {
    event.preventDefault();
    if (!nameInput || !emailInput || !passwordInput || !roleSelect) {
      return;
    }
    var fullName = nameInput.value.trim();
    var email = emailInput.value.trim();
    var password = passwordInput.value.trim();
    var role = roleSelect.value;

    if (!fullName) {
      showError("Full name is required.");
      return;
    }
    if (!email) {
      showError("Email is required.");
      return;
    }
    if (!password) {
      showError("Password is required.");
      return;
    }
    if (!role) {
      showError("Select a role to continue.");
      return;
    }

    setBusy(true);
    showError("");
    showSuccess("");

    api.auth
      .register(fullName, email, password, role)
      .then(function () {
        showSuccess("Account created. You can sign in now.");
      })
      .catch(function (error) {
        showError(error.message || "Unable to create account.");
      })
      .finally(function () {
        setBusy(false);
      });
  });
});

