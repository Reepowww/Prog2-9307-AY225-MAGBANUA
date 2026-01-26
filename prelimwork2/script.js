let users = [];
let currentUser = null;
let timeInterval = null; // For live timestamp

// ADMIN ACCOUNT
users.push({
    name: "Administrator",
    userId: "admin1",
    password: "pass2",
    created: new Date().toLocaleString(),
    lastLogin: null
});

function showSignup() {
    hideAll();
    document.getElementById("signupBox").classList.remove("hidden");
}

function backToLogin() {
    hideAll();
    document.getElementById("loginBox").classList.remove("hidden");
}

function signup() {
    const name = signupName.value;
    const user = signupUser.value;
    const pass = signupPass.value;

    if (!name || !user || !pass) {
        showError();
        return;
    }

    users.push({
        name,
        userId: user,
        password: pass,
        created: new Date().toLocaleString(),
        lastLogin: null
    });

    showSignupSuccess();
}

function showSignupSuccess() {
    document.getElementById("signupSuccessModal").classList.remove("hidden");
}

function proceedToLogin() {
    document.getElementById("signupSuccessModal").classList.add("hidden");
    backToLogin();
}

function login() {
    const user = loginUser.value;
    const pass = loginPass.value;

    const found = users.find(u => u.userId === user && u.password === pass);

    if (!found) {
        document.getElementById("beepSound").play();
        showError();
        return;
    }

    currentUser = found;
    currentUser.lastLogin = new Date().toLocaleString();
    showSuccess();
}

function showSuccess() {
    document.getElementById("successModal").classList.remove("hidden");
}

function proceedToDashboard() {
    document.getElementById("successModal").classList.add("hidden");
    openDashboard();
}

function openDashboard() {
    hideAll();

    if (currentUser.userId === "admin1") {
        // Show Admin Dashboard
        document.getElementById("adminDashboard").classList.remove("hidden");
        populateUserTable();
    } else {
        // Show User Dashboard
        document.getElementById("userDashboard").classList.remove("hidden");
        userNameDisplay.innerText = currentUser.name;
        userIdDisplay.innerText = currentUser.userId;
        lastLoginDisplay.innerText = currentUser.lastLogin || "Never logged in";
        updateTime(); // Start live time
        timeInterval = setInterval(updateTime, 1000);
    }
}

function populateUserTable() {
    const tbody = document.getElementById("userTableBody");
    tbody.innerHTML = ""; // Clear existing rows

    users.forEach(u => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${u.userId}</td>
            <td>${u.name}</td>
            <td>${u.created}</td>
            <td>${u.lastLogin || "Never logged in"}</td>
        `;
        tbody.appendChild(row);
    });
}

function updateTime() {
    timeDisplay.innerText = new Date().toLocaleString();
}

function logout() {
    currentUser = null;
    if (timeInterval) {
        clearInterval(timeInterval);
        timeInterval = null;
    }
    backToLogin();
}

function hideAll() {
    loginBox.classList.add("hidden");
    signupBox.classList.add("hidden");
    userDashboard.classList.add("hidden");
    adminDashboard.classList.add("hidden");
}

function showError() {
    errorModal.classList.remove("hidden");
}

function closeModal() {
    errorModal.classList.add("hidden");
}

// FILE OUTPUT (Admin only - Modified for CSV)
function downloadSummary() {
    if (currentUser.userId !== "admin1") return; // Enforce role-based access

    const generatedOn = new Date().toLocaleString();
    let csvContent = "Report Generated,User ID,Username,Date Created,Last Login Date\n"; // CSV headers

    users.forEach(u => {
        // Quote fields with commas (e.g., dates) to ensure proper CSV parsing
        const reportGenerated = `"${generatedOn}"`;
        const userId = `"${u.userId}"`;
        const username = `"${u.name}"`;
        const dateCreated = `"${u.created}"`;
        const lastLogin = `"${u.lastLogin || "Never logged in"}"`;

        // Build the row
        const row = `${reportGenerated},${userId},${username},${dateCreated},${lastLogin}\n`;
        csvContent += row;
    });

    const blob = new Blob([csvContent], { type: "text/csv" });
    const link = document.createElement("a");

    link.href = URL.createObjectURL(blob);
    link.download = "attendance_summary.csv"; // Updated filename
    link.click();
}