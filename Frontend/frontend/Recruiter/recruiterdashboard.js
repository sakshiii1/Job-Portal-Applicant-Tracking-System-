const BASE_URL = "http://localhost:8080/api/recruiter";

// ---------------- DATE ----------------
document.getElementById("date").textContent =
  new Date().toDateString();

// ---------------- LOAD DASHBOARD ----------------
window.onload = () => {
  loadRecruiterProfile();
  loadDashboardData();
};

// ---------------- PROFILE ----------------
async function loadRecruiterProfile() {
  try {
    const res = await fetch(`${BASE_URL}/profile`, {
      credentials: "include"
    });

    if (!res.ok) throw new Error("Unauthorized");

    const profile = await res.json();

    document.getElementById("profileName").innerText =
      profile.fullName || "Recruiter";

    document.getElementById("profilePhoto").src =
      profile.photoUrl || "default-profile.png";

  } catch (err) {
    console.error("Profile load failed", err);
  }
}

// ---------------- DASHBOARD STATS ----------------
async function loadDashboardData() {
  try {
    const jobsRes = await fetch(`${BASE_URL}/jobs`, {
      credentials: "include"
    });

    const jobs = await jobsRes.json();

    let totalJobs = jobs.length;
    let totalApplicants = 0;
    let shortlisted = 0;
    let rejected = 0;
    let recentApps = [];

    for (const job of jobs) {
      const appsRes = await fetch(
        `${BASE_URL}/jobs/${job.id}/applications`,
        { credentials: "include" }
      );

      const apps = await appsRes.json();
      totalApplicants += apps.length;

      apps.forEach(app => {
        if (app.status === "SHORTLISTED") shortlisted++;
        if (app.status === "REJECTED") rejected++;
        recentApps.push({
          name: app.candidateName,
          job: job.title,
          status: app.status
        });
      });
    }

    updateCards(totalJobs, totalApplicants, shortlisted, rejected);
    loadRecentApplications(recentApps.slice(0, 5));

  } catch (err) {
    console.error("Dashboard load failed", err);
  }
}

// ---------------- UPDATE CARDS ----------------
function updateCards(jobs, applicants, shortlisted, rejected) {
  const cards = document.querySelectorAll(".card p");

  cards[0].innerText = jobs;
  cards[1].innerText = applicants;
  cards[2].innerText = shortlisted;
  cards[3].innerText = rejected;
}

// ---------------- RECENT APPLICATIONS TABLE ----------------
function loadRecentApplications(applications) {
  const tbody = document.querySelector("table tbody");
  tbody.innerHTML = "";

  applications.forEach(app => {
    const tr = document.createElement("tr");

    tr.innerHTML = `
      <td>${app.name}</td>
      <td>${app.job}</td>
      <td class="status ${app.status.toLowerCase()}">
        ${app.status}
      </td>
    `;

    tbody.appendChild(tr);
  });
}

// ---------------- LOGOUT ----------------
document.getElementById("logout").addEventListener("click", () => {
  if (confirm("Logout from ATS?")) {
    window.location.href = "/logout";
  }
});