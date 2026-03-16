/**
 * ===============================================
 * Full Name   : Cyrus Mathew C. Magbanua
 * Student ID  : N/A
 * Course Code : Linear Algebra / BSIT
 * Assignment  : Assignment 01 - 3x3 Matrix Determinant Solver
 * Date        : 2026-03-16
 * Description : This JavaScript program solves the determinant of
 *               a fixed 3x3 matrix using cofactor expansion along
 *               the first row. It prints the matrix, each 2x2
 *               minor, each cofactor term, and the final answer.
 * ===============================================
 */

// This function computes the determinant of a 2x2 matrix.
function computeMinor(a, b, c, d) {
    return (a * d) - (b * c);
}

// This helper function prints the 3x3 matrix neatly.
function printMatrix(matrix) {
    for (let i = 0; i < matrix.length; i++) {
        console.log(`| ${matrix[i][0]}  ${matrix[i][1]}  ${matrix[i][2]} |`);
    }
}

// This function solves the determinant using cofactor expansion
// along the first row and prints the steps clearly.
function solveDeterminant(matrix) {
    const a = matrix[0][0];
    const b = matrix[0][1];
    const c = matrix[0][2];

    // Compute each 2x2 minor from row 1.
    const minor11 = computeMinor(matrix[1][1], matrix[1][2], matrix[2][1], matrix[2][2]);
    const minor12 = computeMinor(matrix[1][0], matrix[1][2], matrix[2][0], matrix[2][2]);
    const minor13 = computeMinor(matrix[1][0], matrix[1][1], matrix[2][0], matrix[2][1]);

    // Compute each cofactor contribution.
    const cofactor1 = a * minor11;
    const cofactor2 = -b * minor12;
    const cofactor3 = c * minor13;

    // Add everything to get the final determinant.
    const determinant = cofactor1 + cofactor2 + cofactor3;

    console.log("==================================================");
    console.log("3x3 MATRIX DETERMINANT SOLVER");
    console.log("Student: Cyrus Mathew C. Magbanua");
    console.log("Assigned Matrix:\n");

    printMatrix(matrix);

    console.log("\n==================================================");
    console.log("Expanding along Row 1 (cofactor expansion):\n");

    console.log(`Step 1 - Minor M11: det([${matrix[1][1]},${matrix[1][2]}],[${matrix[2][1]},${matrix[2][2]}]) = (${matrix[1][1]}×${matrix[2][2]}) - (${matrix[1][2]}×${matrix[2][1]}) = ${matrix[1][1] * matrix[2][2]} - ${matrix[1][2] * matrix[2][1]} = ${minor11}`);

    console.log(`Step 2 - Minor M12: det([${matrix[1][0]},${matrix[1][2]}],[${matrix[2][0]},${matrix[2][2]}]) = (${matrix[1][0]}×${matrix[2][2]}) - (${matrix[1][2]}×${matrix[2][0]}) = ${matrix[1][0] * matrix[2][2]} - ${matrix[1][2] * matrix[2][0]} = ${minor12}`);

    console.log(`Step 3 - Minor M13: det([${matrix[1][0]},${matrix[1][1]}],[${matrix[2][0]},${matrix[2][1]}]) = (${matrix[1][0]}×${matrix[2][1]}) - (${matrix[1][1]}×${matrix[2][0]}) = ${matrix[1][0] * matrix[2][1]} - ${matrix[1][1] * matrix[2][0]} = ${minor13}\n`);

    console.log(`Cofactor C11 = (+1) × ${a} × ${minor11} = ${cofactor1}`);
    console.log(`Cofactor C12 = (-1) × ${b} × ${minor12} = ${cofactor2}`);
    console.log(`Cofactor C13 = (+1) × ${c} × ${minor13} = ${cofactor3}\n`);

    console.log(`det(M) = ${cofactor1} + (${cofactor2}) + ${cofactor3}`);

    console.log("\n==================================================");
    console.log(`✓ DETERMINANT = ${determinant}`);

    if (determinant === 0) {
        console.log("The matrix is SINGULAR — it has no inverse.");
    }

    console.log("==================================================");
}

// This is the student's assigned 3x3 matrix.
const matrix = [
    [2, 5, 4],
    [3, 1, 6],
    [4, 2, 3]
];

solveDeterminant(matrix);