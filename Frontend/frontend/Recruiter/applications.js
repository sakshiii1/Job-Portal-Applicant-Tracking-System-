// Show date
document.getElementById("date").textContent = new Date().toDateString();

// Filter by job
document.getElementById("jobFilter").addEventListener("change", function () {
    const selectedJob = this.value;
    const rows = document.querySelectorAll("#applicationTable tr");

    rows.forEach(row => {
        if (selectedJob === "all" || row.dataset.job === selectedJob) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
});

// Shortlist / Reject actions
document.querySelectorAll(".shortlist").forEach(btn => {
    btn.addEventListener("click", function () {
        const statusCell = this.closest("tr").querySelector(".status");
        statusCell.textContent = "Shortlisted";
        statusCell.className = "status shortlisted";
    });
});

document.querySelectorAll(".reject").forEach(btn => {
    btn.addEventListener("click", function () {
        const statusCell = this.closest("tr").querySelector(".status");
        statusCell.textContent = "Rejected";
        statusCell.className = "status rejected";
    });
});
