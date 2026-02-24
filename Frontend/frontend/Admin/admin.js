const BASE_URL = "http://localhost:8080/api/admin";

// ---------- NAVIGATION ----------
function showSection(sectionId, el) {
  document.querySelectorAll(".section").forEach(sec =>
    sec.classList.remove("active")
  );
  document.getElementById(sectionId).classList.add("active");

  document.querySelectorAll(".sidebar li").forEach(li =>
    li.classList.remove("active")
  );
  el.classList.add("active");

  if (sectionId === "users") loadUsers();
  if (sectionId === "jobs") loadCategories();
}

// ---------- DASHBOARD ----------
window.onload = () => {
  loadDashboardStats();
};

function loadDashboardStats() {
  console.log("Dashboard loaded");
}

// ---------- USERS (GET /api/admin/users) ----------
async function loadUsers() {
  try {
    const res = await fetch(`${BASE_URL}/users`);
    const users = await res.json();

    const tbody = document.getElementById("usersTableBody");
    tbody.innerHTML = "";

    users.forEach(user => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
        <td>${user.name}</td>
        <td>${user.email}</td>
        <td>${user.role}</td>
        <td>
          <button class="danger" onclick="deleteUserRow(this)">Delete</button>
        </td>
      `;
      tbody.appendChild(tr);
    });
  } catch (err) {
    console.error("Error fetching users", err);
  }
}

// frontend-only delete (backend delete can be added later)
function deleteUserRow(btn) {
  if (confirm("Delete this user?")) {
    btn.closest("tr").remove();
  }
}

// ---------- JOB CATEGORIES ----------

// GET /api/admin/category
async function loadCategories() {
  try {
    const res = await fetch(`${BASE_URL}/category`);
    const categories = await res.json();

    const list = document.getElementById("categoryList");
    list.innerHTML = "";

    categories.forEach(cat => {
      const li = document.createElement("li");
      li.innerHTML = `
        ${cat.name}
        <button onclick="deleteCategory(${cat.id})">❌</button>
      `;
      list.appendChild(li);
    });
  } catch (err) {
    console.error("Error loading categories", err);
  }
}

// POST /api/admin/category
async function addCategory() {
  const name = prompt("Enter job category name");
  if (!name) return;

  await fetch(`${BASE_URL}/category`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name })
  });

  loadCategories();
}

// DELETE /api/admin/category/{id}
async function deleteCategory(id) {
  if (!confirm("Delete this category?")) return;

  await fetch(`${BASE_URL}/category/${id}`, {
    method: "DELETE"
  });

  loadCategories();
}

// ---------- SETTINGS ----------
function updateSettings() {
  const pass = document.getElementById("adminPass").value;
  if (pass.length < 6) {
    alert("Password must be at least 6 characters");
    return;
  }
  alert("Password updated (connect backend later)");
}

// ---------- LOGOUT ----------
function logout() {
  if (confirm("Logout?")) {
    window.location.href = "../login.html";
  }
}