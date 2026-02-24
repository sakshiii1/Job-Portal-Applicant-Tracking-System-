var API_BASE_URL = "http://localhost:8080";

var API_STORAGE_KEYS = {
  token: "ats_jwt",
  role: "ats_role",
  email: "ats_email"  // 🔥 Email bhi store karenge
};

// Helper to extract role from token (fallback)
function extractRoleFromToken(token) {
  if (!token) return null;
  
  try {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    var payload = JSON.parse(window.atob(base64));
    return payload.role || payload.roles || payload.authorities || null;
  } catch (e) {
    return null;
  }
}

var api = {
  baseUrl: API_BASE_URL,

  // ================= TOKEN =================
  getToken: function () {
    return window.localStorage.getItem(API_STORAGE_KEYS.token);
  },

  setToken: function (token) {
    if (token) {
      window.localStorage.setItem(API_STORAGE_KEYS.token, token);
    }
  },

  clearToken: function () {
    window.localStorage.removeItem(API_STORAGE_KEYS.token);
  },

  // ================= ROLE =================
  getRole: function () {
    return window.localStorage.getItem(API_STORAGE_KEYS.role);
  },

  setRole: function (role) {
    if (role) {
      window.localStorage.setItem(API_STORAGE_KEYS.role, role);
    }
  },

  clearRole: function () {
    window.localStorage.removeItem(API_STORAGE_KEYS.role);
  },

  // ================= EMAIL =================
  getEmail: function () {
    return window.localStorage.getItem(API_STORAGE_KEYS.email);
  },

  setEmail: function (email) {
    if (email) {
      window.localStorage.setItem(API_STORAGE_KEYS.email, email);
    }
  },

  clearEmail: function () {
    window.localStorage.removeItem(API_STORAGE_KEYS.email);
  },

  // Clear all
  clearAll: function () {
    api.clearToken();
    api.clearRole();
    api.clearEmail();
  },

  // ================= CORE REQUEST =================
  request: function (path, options) {
    var opts = options || {};

    var headers = Object.assign(
      { "Content-Type": "application/json" },
      opts.headers || {}
    );

    var token = api.getToken();
    if (token) {
      headers.Authorization = "Bearer " + token;
    }

    var url = API_BASE_URL + path;

    return window
      .fetch(url, Object.assign({}, opts, { headers: headers }))
      .then(function (res) {
        return res.text().then(function (text) {
          var data = null;

          if (text) {
            try {
              data = JSON.parse(text);
            } catch (e) {
              data = text;
            }
          }

          if (!res.ok) {
            // Auto logout if unauthorized
            if (res.status === 401 || res.status === 403) {
              api.clearAll();
              window.location.href = "../StartingPage/login.html";
            }

            var message =
              data && data.message
                ? data.message
                : "Request failed with status " + res.status;

            throw new Error(message);
          }

          return data;
        });
      });
  },

  // ================= AUTH =================
  auth: {
    login: function (email, password) {
      return api
        .request("/api/auth/login", {
          method: "POST",
          body: JSON.stringify({
            email: email,
            password: password
          })
        })
        .then(function (response) {
          console.log("🔥 Full login response:", response); // Debug log

          // ✅ Directly response se values lo (kyunki Postman mein exact yehi aa raha hai)
          var token = response.token;
          var role = response.role;
          var email = response.email;  // Email bhi store karo

          // Validate
          if (!token) {
            throw new Error("Token not found in login response");
          }

          // Store everything
          api.setToken(token);
          
          if (role) {
            api.setRole(role);
            console.log("✅ Role stored:", role);
          }
          
          if (email) {
            api.setEmail(email);
            console.log("✅ Email stored:", email);
          }

          // Return full user data
          return {
            token: token,
            role: role,
            email: email
          };
        });
    },

    register: function (fullName, email, password, role) {
      return api.request("/api/auth/register", {
        method: "POST",
        body: JSON.stringify({
          fullName: fullName,
          email: email,
          password: password,
          role: role
        })
      });
    },
    
    // Get current user info
    getCurrentUser: function() {
      return {
        token: api.getToken(),
        role: api.getRole(),
        email: api.getEmail()
      };
    }
  }
};

window.api = api;
window.API_STORAGE_KEYS = API_STORAGE_KEYS;