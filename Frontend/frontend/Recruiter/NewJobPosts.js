function saveJob() {

  var jobId = document.getElementById("editJobId").value;
  var title = document.getElementById("editTitle").value;
  var description = document.getElementById("editDescription").value;

  var methodType = jobId ? "PUT" : "POST";
  var url = jobId 
      ? "/recruiter/jobs/" + jobId
      : "/recruiter/jobs";

  api.request(url, {
    method: methodType,
    body: JSON.stringify({
      title: title,
      description: description
    })
  })
  .then(function () {
    alert(jobId ? "Job Updated" : "Job Created");
    window.location.href = "MyPosts.html";
  })
  .catch(function (err) {
    alert(err.message);
  });
}