/*
MP16 – Randomly display 10 rows from dataset
Program reads a CSV file and randomly selects 10 rows.
*/

const fs = require("fs");
const readline = require("readline");

// Create interface for user input
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

// Ask user for dataset path
rl.question("Enter dataset file path: ", function(path) {

    try {

        // Read file
        const data = fs.readFileSync(path, "utf8");

        // Split rows
        const rows = data.split("\n");

        console.log("\nRandom 10 Rows:\n");

        for (let i = 0; i < 10; i++) {

            let randomIndex = Math.floor(Math.random() * rows.length);

            console.log(rows[randomIndex]);
        }

    } catch (err) {

        console.log("Error reading file.");

    }

    rl.close();

});