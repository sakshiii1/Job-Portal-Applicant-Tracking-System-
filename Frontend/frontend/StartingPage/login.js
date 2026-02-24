document
  .getElementById("loginForm")
  .addEventListener("submit", function (e) {

    e.preventDefault();

    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;

    api.auth.login(email, password)
  .then(function (token) {

    api.setToken(token);

    var decoded = decodeJWT(token);
    var role = decoded.role;

    if (role === "ADMIN") {
      window.location.href = "../Admin/admin.html";
    }
    else if (role === "RECRUITER") {
      window.location.href = "../Recruiter/recruiter.html";
    }
    else if (role === "CANDIDATE") {
      window.location.href = "../Candidate/candidate.html";
    }
    else {
      alert("Invalid role");
    }
  })
  .catch(function (err) {
  if (err.message.includes("403")) {
    alert("Recruiter not approved by admin yet.");
  } else {
    alert("Login failed");
  }
});
});
