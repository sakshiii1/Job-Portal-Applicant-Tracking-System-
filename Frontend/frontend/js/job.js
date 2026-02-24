var JobApi = {

  // GET ALL JOBS (Public / Candidate)
  list: function () {
    return api.request("/api/jobs", "GET");
  },

  // GET MY JOBS (Recruiter)
  mine: function () {
    return api.request("/api/recruiter/jobs", "GET");
  },

  // CREATE JOB (Recruiter)
  create: function (jobData) {
    return api.request("/api/recruiter/job", "POST", jobData);
  },

  // UPDATE JOB
  update: function (jobId, jobData) {
    return api.request("/api/recruiter/job/" + jobId, "PUT", jobData);
  },

  // DELETE JOB
  delete: function (jobId) {
    return api.request("/api/recruiter/job/" + jobId, "DELETE");
  }
};

window.JobApi = JobApi;