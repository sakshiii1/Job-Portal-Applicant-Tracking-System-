// Show date
document.getElementById("date").textContent = new Date().toDateString();

// Handle shortlist / reject buttons
document.querySelectorAll(".shortlist").forEach(button => {
    button.addEventListener("click", function () {
        const row = this.closest("tr");
        const statusCell = row.querySelector(".status");
        statusCell.textContent = "Shortlisted";
        statusCell.className = "status shortlisted";
    });
});

document.querySelectorAll(".reject").forEach(button => {
    button.addEventListener("click", function () {
        const row = this.closest("tr");
        const statusCell = row.querySelector(".status");
        statusCell.textContent = "Rejected";
        statusCell.className = "status rejected";
    });
});
