const userId = localStorage.getItem("userId");

fetch(`http://localhost:8080/api/candidate/applications/${userId}`)
  .then(res => res.json())
  .then(data => {
    const table = document.getElementById("applicationsTable");
    table.innerHTML = "";

    data.forEach(app => {
      const row = document.createElement("tr");

      row.innerHTML = `
        <td>${app.jobTitle}</td>
        <td>${app.companyName}</td>
        <td>${app.status}</td>
        <td>${new Date(app.appliedDate).toLocaleDateString()}</td>
        <td>
          <button onclick="withdraw(${app.applicationId})">
            Withdraw
          </button>
        </td>
      `;

      table.appendChild(row);
    });
  });

// ================= WITHDRAW APPLICATION =================
function withdraw(applicationId) {
  fetch(`http://localhost:8080/api/candidate/withdraw/${applicationId}`, {
    method: "PUT"
  })
  .then(res => res.text())
  .then(msg => {
    alert(msg);
    location.reload();
  });
}
// ================= LOGOUT =================
document.getElementById("logout").addEventListener("click", () => {
    localStorage.clear();
    window.location.href = "login.html";
});