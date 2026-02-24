document.addEventListener("DOMContentLoaded", function () {
  var form = document.getElementById("loginForm");
  if (!form) return;

  var emailInput = document.getElementById("loginEmail");
  var passwordInput = document.getElementById("loginPassword");
  var errorBox = document.getElementById("loginError");
  var successBox = document.getElementById("loginSuccess");
  var submitButton = document.getElementById("loginSubmit");

  function setBusy(isBusy) {
    if (submitButton) {
      submitButton.disabled = isBusy;
    }
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

    if (!emailInput || !passwordInput) return;

    var email = emailInput.value.trim();
    var password = passwordInput.value.trim();

    if (!email || !password) {
      showError("Email and password are required.");
      return;
    }

    setBusy(true);
    showError("");
    showSuccess("");

    api.auth
      .login(email, password)
      .then(function () {

        // ✅ Role comes from backend (stored inside api.auth.login)
        var role = api.getRole();

        showSuccess("Signed in successfully.");

        var target = "candidateDashboard.html";

        if (role === "ADMIN") {
          target = "adminDashboard.html";
        } else if (role === "RECRUITER") {
          target = "recruiterDashboard.html";
        }

        window.location.href = target;
      })
      .catch(function (error) {
        showError(error.message || "Unable to sign in.");
      })
      .finally(function () {
        setBusy(false);
      });
  });
});