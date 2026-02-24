const user = {
    name: "John Doe",
    email: "john@gmail.com",
    role: "Backend Developer"
};

document.getElementById("name").textContent = user.name;
document.getElementById("email").textContent = user.email;
document.getElementById("role").textContent = user.role;

function editProfile() {
    window.location.href = "EditProfile.html";
}