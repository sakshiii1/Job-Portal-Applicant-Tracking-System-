// Date
document.getElementById("date").textContent = new Date().toDateString();

// Form Submit
document.getElementById("jobForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const jobData = {
        title: document.getElementById("jobTitle").value,
        company: document.getElementById("companyName").value,
        location: document.getElementById("location").value,
        type: document.getElementById("jobType").value,
        experience: document.getElementById("experience").value,
        salary: document.getElementById("salary").value,
        description: document.getElementById("description").value
    };

    // Simple validation
    if (!jobData.title || !jobData.company || !jobData.location || !jobData.type || !jobData.experience || !jobData.description) {
        alert("Please fill all required fields");
        return;
    }

    // Frontend success (replace with backend)
    alert("Job posted successfully!");

    // Reset form
    document.getElementById("jobForm").reset();

    // 🔗 Backend ready (Java / Spring Boot)
    /*
    fetch("http://localhost:8080/jobs", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(jobData)
    })
    .then(res => res.json())
    .then(data => alert(data.message))
    .catch(err => alert("Server error"));
    */
});
