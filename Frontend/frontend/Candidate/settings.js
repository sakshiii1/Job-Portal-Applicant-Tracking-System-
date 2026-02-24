// Load saved theme
const darkToggle = document.getElementById("darkMode");

if (localStorage.getItem("theme") === "dark") {
    document.body.classList.add("dark");
    darkToggle.checked = true;
}

// Dark mode toggle
darkToggle.addEventListener("change", () => {
    if (darkToggle.checked) {
        document.body.classList.add("dark");
        localStorage.setItem("theme", "dark");
    } else {
        document.body.classList.remove("dark");
        localStorage.setItem("theme", "light");
    }
});

function saveProfile() {
    alert("Profile updated successfully!");
}

function changePassword() {
    alert("Password changed successfully!");
}