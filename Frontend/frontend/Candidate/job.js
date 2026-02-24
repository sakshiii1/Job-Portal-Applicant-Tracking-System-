const savedJobs = [
    { title: "Java Developer", company: "Infosys" },
    { title: "QA Engineer", company: "Wipro" }
];

const savedContainer = document.getElementById("savedJobs");

savedJobs.forEach(job => {
    savedContainer.innerHTML += `
        <div class="info-card">
            <h3>${job.title}</h3>
            <p>${job.company}</p>
            <button onclick="applyJob()">Apply Now</button>
        </div>
    `;
});

function applyJob() {
    alert("Application submitted!");
}