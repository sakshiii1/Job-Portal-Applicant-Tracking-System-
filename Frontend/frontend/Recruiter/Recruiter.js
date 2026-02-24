document.addEventListener("DOMContentLoaded", function () {

  // Load all recruiter jobs
  api.request("/recruiter/jobs", {
    method: "GET"
  })
  .then(function (jobs) {
    console.log("Jobs:", jobs);
    displayJobs(jobs);
  })
  .catch(function (err) {
    console.error(err.message);
  });

});

function displayJobs(jobs) {
  var container = document.getElementById("jobsContainer");
  container.innerHTML = "";

  jobs.forEach(function (job) {
    var div = document.createElement("div");
    div.innerHTML = `
      <h3>${job.title}</h3>
      <p>${job.description}</p>
      <button onclick="viewApplications(${job.id})">View Applications</button>
    `;
    container.appendChild(div);
  });
}

function viewApplications(jobId) {
  window.location.href = "../Applications/applications.html?jobId=" + jobId;
}