document.addEventListener("DOMContentLoaded", function () {

  api.request("/recruiter/jobs", {
    method: "GET"
  })
  .then(function (jobs) {

    var container = document.getElementById("postsContainer");
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

  })
  .catch(function (err) {
    console.error(err.message);
  });

});

function viewApplications(jobId) {
  window.location.href = "ViewApplications.html?jobId=" + jobId;
}