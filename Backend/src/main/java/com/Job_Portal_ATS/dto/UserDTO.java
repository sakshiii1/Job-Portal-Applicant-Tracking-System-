package com.Job_Portal_ATS.dto;

public class UserDTO {


private Long userId;
private String fullName;
private String email;
private String role;
private String accountStatus;

public Long getUserId() {
    return userId;
}

public void setUserId(Long userId) {
    this.userId = userId;
}

public String getFullName() {
    return fullName;
}

public void setFullName(String fullName) {
    this.fullName = fullName;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getRole() {
    return role;
}

public void setRole(String role) {
    this.role = role;
}

public String getAccountStatus() {
    return accountStatus;
}

public void setAccountStatus(String accountStatus) {
    this.accountStatus = accountStatus;
}


}
