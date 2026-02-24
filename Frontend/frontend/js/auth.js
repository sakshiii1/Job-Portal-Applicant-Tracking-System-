var Auth = {
  saveSession: function (token, role, email) {
    api.setToken(token);
    if (role) api.setRole(role);
    if (email) api.setEmail(email);
  },

  logout: function () {
    api.clearAll();
    window.location.href = "../pages/loginPage.html";
  },

  requireAuth: function () {
    var token = api.getToken();

    if (!token) {
      redirectToLogin();
      return;
    }

    var role = api.getRole();
    var decoded = decodeJWT(token);

    if (!decoded) {
      api.clearAll();
      redirectToLogin();
      return;
    }

    // Check expiry
    if (decoded.exp && Date.now() >= decoded.exp * 1000) {
      api.clearAll();
      alert("Session expired. Please login again.");
      redirectToLogin();
      return;
    }

    // Agar role stored nahi hai to token se nikaalo
    if (!role) {
      role = decoded.role || null;
      if (role) api.setRole(role);
    }

    var pageRole = document.body.getAttribute("data-role");

    if (pageRole) {
      if (!role) {
        alert("Access Denied: No role assigned");
        redirectToLogin();
        return;
      }
      
      if (role !== pageRole) {
        alert("Access Denied: This page requires " + pageRole + " role");
        redirectToLogin();
        return;
      }
    }
    
    console.log("✅ Auth successful for role:", role);
  }
};

function decodeJWT(token) {
  try {
    return JSON.parse(atob(token.split(".")[1]));
  } catch (e) {
    return null;
  }
}

function redirectToLogin() {
  window.location.href = "../pages/loginPage.html";
}

window.Auth = Auth;

document.addEventListener("DOMContentLoaded", function () {
  var body = document.body;
  if (!body) return;

  var requireAuth = body.getAttribute("data-require-auth");
  if (requireAuth === "true") {
    Auth.requireAuth();
  }
});