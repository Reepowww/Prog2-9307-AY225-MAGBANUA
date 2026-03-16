/*
MP18 – Remove rows with empty fields
This program reads a CSV dataset and displays only rows
that do not contain any empty fields.
*/

const fs = require("fs");
const readline = require("readline");

// Create input interface
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

// Ask user for dataset file path
rl.question("Enter dataset file path: ", function(path) {

    try {

        // Read the file
        const data = fs.readFileSync(path, "utf8");

        // Split rows and remove extra line endings
        const rows = data.split("\n").map(row => row.trimEnd());

        console.log("\nRows without empty fields:\n");

        rows.forEach(row => {

            if (row.trim() === "") {
                return;
            }

            const columns = row.split(",");

            let hasEmptyField = false;

            // Check each column
            for (let col of columns) {
                if (col.trim() === "") {
                    hasEmptyField = true;
                    break;
                }
            }

            // Print row if no empty fields
            if (!hasEmptyField) {
                console.log(row);
            }

        });

    } catch (err) {

        console.log("Error reading file.");

    }

    rl.close();

});