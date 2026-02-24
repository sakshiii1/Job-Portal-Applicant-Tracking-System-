package com.Job_Portal_ATS.dto;

import com.Job_Portal_ATS.entity.ApprovalStatus;

public class RecruiterApprovalDTO {

    private Long recruiterId;
    private ApprovalStatus status;   // enum

    public Long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Long recruiterId) {
        this.recruiterId = recruiterId;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }
}
