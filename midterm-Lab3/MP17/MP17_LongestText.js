const fs = require("fs");
const readline = require("readline");

/*
MP17 – Find the longest text entry in dataset
This program reads a CSV file and finds the row
with the longest text entry.
*/

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

rl.question("Enter dataset file path: ", function(path) {
    try {
        const data = fs.readFileSync(path, "utf8");

        // Remove extra line ending characters
        const rows = data.split("\n").map(row => row.trimEnd());

        let longestText = "";

        rows.forEach(row => {
            if (row.length > longestText.length) {
                longestText = row;
            }
        });

        console.log("\nLongest Text Entry:");
        console.log(longestText);
        console.log("\nLength:", longestText.length, "characters");

    } catch (err) {
        console.log("Error reading file.");
    }

    rl.close();
});