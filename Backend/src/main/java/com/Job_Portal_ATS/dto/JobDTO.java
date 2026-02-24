package com.Job_Portal_ATS.dto;

public class JobDTO {


private Long jobId;
private String title;
private String description;
private String location;
private String jobType;
private Long categoryId;
private Long recruiterId;
private String jobStatus;

public Long getJobId() {
    return jobId;
}

public void setJobId(Long jobId) {
    this.jobId = jobId;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public String getDescription() {
    return description;
}

public void setDescription(String description) {
    this.description = description;
}

public String getLocation() {
    return location;
}

public void setLocation(String location) {
    this.location = location;
}

public String getJobType() {
    return jobType;
}

public void setJobType(String jobType) {
    this.jobType = jobType;
}

public Long getCategoryId() {
    return categoryId;
}

public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
}

public Long getRecruiterId() {
    return recruiterId;
}

public void setRecruiterId(Long recruiterId) {
    this.recruiterId = recruiterId;
}

public String getJobStatus() {
    return jobStatus;
}

public void setJobStatus(String jobStatus) {
    this.jobStatus = jobStatus;
}


}
