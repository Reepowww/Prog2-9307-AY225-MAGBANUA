/**
 * ===============================================
 * Name        : Cyrus Mathew C. Magbanua
 * Course      : BSIT
 * Assignment  : 3x3 Matrix Determinant Solver
 * Description : Computes the determinant of a
 *               fixed 3x3 matrix using cofactor
 *               expansion along the first row
 *               and prints the full solution.
 * // Determinant Solver - Linear Algebra Assignment
 * ===============================================
 */

public class DeterminantSolver {

    // Computes determinant of a 2x2 matrix
    public static int computeMinor(int a, int b, int c, int d) {
        return (a * d) - (b * c);
    }

    // Prints the matrix nicely
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("| %d %d %d |%n",
                    matrix[i][0],
                    matrix[i][1],
                    matrix[i][2]);
        }
    }

    public static void solveDeterminant(int[][] m) {

        int a = m[0][0];
        int b = m[0][1];
        int c = m[0][2];

        int minor11 = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
        int minor12 = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
        int minor13 = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);

        int cofactor1 = a * minor11;
        int cofactor2 = -b * minor12;
        int cofactor3 = c * minor13;

        int determinant = cofactor1 + cofactor2 + cofactor3;

        System.out.println("==============================================");
        System.out.println("3x3 MATRIX DETERMINANT SOLVER");
        System.out.println("Student: Cyrus Mathew C. Magbanua");
        System.out.println("Assigned Matrix:\n");

        printMatrix(m);

        System.out.println("\n==============================================");
        System.out.println("Expanding along Row 1 (cofactor expansion):\n");

        System.out.printf("Step 1 - Minor M11: det([%d,%d],[%d,%d]) = (%d*%d) - (%d*%d) = %d - %d = %d%n",
                m[1][1], m[1][2], m[2][1], m[2][2],
                m[1][1], m[2][2], m[1][2], m[2][1],
                m[1][1] * m[2][2], m[1][2] * m[2][1], minor11);

        System.out.printf("Step 2 - Minor M12: det([%d,%d],[%d,%d]) = (%d*%d) - (%d*%d) = %d - %d = %d%n",
                m[1][0], m[1][2], m[2][0], m[2][2],
                m[1][0], m[2][2], m[1][2], m[2][0],
                m[1][0] * m[2][2], m[1][2] * m[2][0], minor12);

        System.out.printf("Step 3 - Minor M13: det([%d,%d],[%d,%d]) = (%d*%d) - (%d*%d) = %d - %d = %d%n\n",
                m[1][0], m[1][1], m[2][0], m[2][1],
                m[1][0], m[2][1], m[1][1], m[2][0],
                m[1][0] * m[2][1], m[1][1] * m[2][0], minor13);

        System.out.printf("Cofactor C11 = (+1) * %d * %d = %d%n", a, minor11, cofactor1);
        System.out.printf("Cofactor C12 = (-1) * %d * %d = %d%n", b, minor12, cofactor2);
        System.out.printf("Cofactor C13 = (+1) * %d * %d = %d%n\n", c, minor13, cofactor3);

        System.out.printf("det(M) = %d + (%d) + %d%n", cofactor1, cofactor2, cofactor3);

        System.out.println("\n==============================================");
        System.out.println("DETERMINANT = " + determinant);
        System.out.println("==============================================");
    }

    public static void main(String[] args) {

        int[][] matrix = {
                {2, 5, 4},
                {3, 1, 6},
                {4, 2, 3}
        };

        solveDeterminant(matrix);
    }
}