let students = []; // start empty

// Function to convert CSV text into JS objects
function parseCSV(csvText) {
    const lines = csvText.trim().split("\n");
    const headers = lines[0].split(",").map(h => h.trim());
    const data = lines.slice(1).map(line => {
        const values = line.split(",").map(v => v.trim());
        return {
            id: values[0],
            first: values[1],
            last: values[2],
            lab1: Number(values[3]),
            lab2: Number(values[4]),
            lab3: Number(values[5]),
            prelim: Number(values[6]),
            attendance: Number(values[7])
        };
    });
    return data;
}

// Load CSV file on page load
fetch("MOCK_DATA (1).csv")
    .then(response => response.text())
    .then(text => {
        students = parseCSV(text);
        render();
    })
    .catch(err => {
        console.error("Failed to load CSV, starting empty:", err);
        render();
    });

// Render student table
function render() {
    const tableBody = document.getElementById("table-body");
    tableBody.innerHTML = "";

    students.forEach((s, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${s.id}</td>
            <td>${s.first}</td>
            <td>${s.last}</td>
            <td>${s.lab1}</td>
            <td>${s.lab2}</td>
            <td>${s.lab3}</td>
            <td>${s.prelim}</td>
            <td>${s.attendance}</td>
            <td><button class="delete-btn" onclick="deleteStudent(${index})">Delete</button></td>
        `;
        tableBody.appendChild(row);
    });
}

// Add new student
function addStudent() {
    const id = document.getElementById("id-input").value.trim();
    const first = document.getElementById("first-input").value.trim();
    const last = document.getElementById("last-input").value.trim();
    const lab1 = Number(document.getElementById("lab1-input").value);
    const lab2 = Number(document.getElementById("lab2-input").value);
    const lab3 = Number(document.getElementById("lab3-input").value);
    const prelim = Number(document.getElementById("prelim-input").value);
    const attendance = Number(document.getElementById("attendance-input").value);

    if(!id || !first || !last) {
        alert("Student ID, First Name, and Last Name are required!");
        return;
    }

    students.push({ id, first, last, lab1, lab2, lab3, prelim, attendance });
    render();
    document.querySelectorAll(".input-field").forEach(input => input.value = "");
}

// Delete student
function deleteStudent(index) {
    students.splice(index, 1);
    render();
}

// Event listener
document.getElementById("add-btn").addEventListener("click", addStudent);
