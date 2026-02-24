const BASE_URL = "http://localhost:8080/api/candidate";
const userId = localStorage.getItem("userId");

const greeting = document.getElementById("greeting");
const hour = new Date().getHours();

if (hour < 12) greeting.textContent = "Good Morning 👋";
else if (hour < 18) greeting.textContent = "Good Afternoon 👋";
else greeting.textContent = "Good Evening 👋";

async function loadDashboardStats() {
    try {
        const response = await fetch(`${BASE_URL}/applications/${userId}`);
        const applications = await response.json();

        document.querySelector(".stats .card:nth-child(1) p").textContent =
            applications.length;

    } catch (error) {
        console.error("Error loading stats:", error);
    }
}

loadDashboardStats();

async function applyJob(jobId) {
    try {
        const response = await fetch(
            `${BASE_URL}/apply?jobId=${jobId}&userId=${userId}`,
            { method: "POST" }
        );

        if (response.ok) {
            alert("✅ Job applied successfully!");
            loadDashboardStats(); // refresh count
        } else {
            alert("❌ Failed to apply job");
        }
    } catch (error) {
        console.error("Apply error:", error);
    }
}

document.getElementById("logout").addEventListener("click", () => {
    if (confirm("Are you sure you want to logout?")) {
        localStorage.clear();
        window.location.href = "login.html";
    }
});

async function loadProfile() {
    const res = await fetch(`${BASE_URL}/profile/${userId}`);
    const profile = await res.json();

    document.querySelector(".profile span").textContent = profile.fullName;
}