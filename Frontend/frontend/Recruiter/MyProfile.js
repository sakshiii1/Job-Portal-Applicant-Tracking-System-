document.addEventListener("DOMContentLoaded", function () {

  api.request("/profile", {
    method: "GET"
  })
  .then(function (user) {
    document.getElementById("name").innerText = user.fullName;
    document.getElementById("email").innerText = user.email;
  })
  .catch(function (err) {
    console.error(err.message);
  });

});