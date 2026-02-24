document
  .getElementById("registerForm")
  .addEventListener("submit", function (e) {

    e.preventDefault();

    var fullName = document.getElementById("fullName").value;
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var role = document.getElementById("role").value;

    api.auth.register(fullName, email, password, role)
      .then(function () {
        alert("Registered Successfully!");
        window.location.href = "loginPage.html";
      })
      .catch(function (err) {
        alert("Registration Failed: " + err.message);
      });
});
