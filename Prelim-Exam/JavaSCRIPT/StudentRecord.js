// Programmer: Cyrus Mathew C. Magbanua - BSCS
const csvData = `1,John Doe,A
2,Jane Smith,B
3,Bob Johnson,C`;

let records = [];

function parseCSV() {
    const lines = csvData.split('\n');
    records = lines.map(line => {
        const [id, name, grade] = line.split(',');
        return { id, name, grade };
    });
}

function render() {
    const tableBody = document.getElementById('tableBody');
    tableBody.innerHTML = '';
    records.forEach((record, index) => {
        const row = `<tr>
            <td>${record.id}</td>
            <td>${record.name}</td>
            <td>${record.grade}</td>
            <td><button onclick="deleteRecord(${index})">Delete</button></td>
        </tr>`;
        tableBody.innerHTML += row;
    });
}

function addRecord() {
    const id = document.getElementById('idInput').value;
    const name = document.getElementById('nameInput').value;
    const grade = document.getElementById('gradeInput').value;
    if (id && name && grade) {
        records.push({ id, name, grade });
        document.getElementById('idInput').value = '';
        document.getElementById('nameInput').value = '';
        document.getElementById('gradeInput').value = '';
        render();
    }
}

function deleteRecord(index) {
    records.splice(index, 1);
    render();
}

document.getElementById('addButton').addEventListener('click', addRecord);

parseCSV();
render();