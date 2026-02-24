document.addEventListener("DOMContentLoaded", function () {
  loadJobs();
});

function loadJobs() {
  api.request("/recruiter/jobs", {
    method: "GET"
  })
  .then(function (jobs) {
    var container = document.getElementById("postsContainer");
    container.innerHTML = "";

    if (!jobs || jobs.length === 0) {
      container.innerHTML = "<p>No job posts found.</p>";
      return;
    }

    jobs.forEach(function (job) {
      var div = document.createElement("div");
      div.className = "job-card";

      div.innerHTML = `
        <h3>${job.title}</h3>
        <p>${job.description}</p>
        <button onclick="viewApplications(${job.id})">Applications</button>
        <button onclick="editJob(${job.id}, '${job.title}', \`${job.description}\`)">Edit</button>
        <button onclick="deleteJob(${job.id})">Delete</button>
        <hr/>
      `;

      container.appendChild(div);
    });
  })
  .catch(function (err) {
    console.error(err.message);
  });
}

function viewApplications(jobId) {
  window.location.href = "ViewApplications.html?jobId=" + jobId;
}

function deleteJob(jobId) {
  if (!confirm("Are you sure you want to delete this job?")) return;

  api.request("/recruiter/jobs/" + jobId, {
    method: "DELETE"
  })
  .then(function () {
    alert("Job deleted successfully");
    loadJobs();
  })
  .catch(function (err) {
    alert(err.message);
  });
}

function editJob(id, title, description) {
  document.getElementById("editJobId").value = id;
  document.getElementById("editTitle").value = title;
  document.getElementById("editDescription").value = description;
}