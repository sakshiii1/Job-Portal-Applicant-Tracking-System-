// Date
document.getElementById("date").textContent = new Date().toDateString();

// Profile image preview
document.getElementById("profileUpload").addEventListener("change", function () {
    const file = this.files[0];
    if (file) {
        document.getElementById("profileImg").src = URL.createObjectURL(file);
    }
});

// Save profile
document.getElementById("saveProfile").addEventListener("click", function () {
    alert("Profile updated successfully!");
});

// Change password
document.getElementById("changePassword").addEventListener("click", function () {
    const newPass = document.getElementById("newPassword").value;
    const confirmPass = document.getElementById("confirmPassword").value;

    if (newPass === "" || confirmPass === "") {
        alert("Please fill all password fields");
        return;
    }

    if (newPass !== confirmPass) {
        alert("Passwords do not match");
        return;
    }

    alert("Password updated successfully!");
});
