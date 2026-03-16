import java.io.*;
import java.util.*;

/*
MP18 – Remove rows with empty fields
This program reads a CSV dataset and displays only rows
that do not contain empty fields.
*/

public class MP18_RemoveEmptyRows {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Ask user for dataset file path
        System.out.print("Enter dataset file path: ");
        String filePath = input.nextLine();

        try {

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            System.out.println("\nRows without empty fields:\n");

            // Read each row of the CSV
            while ((line = reader.readLine()) != null) {

                // Split row into columns
                String[] columns = line.split(",");

                boolean hasEmptyField = false;

                // Check if any column is empty
                for (String col : columns) {

                    if (col.trim().isEmpty()) {
                        hasEmptyField = true;
                        break;
                    }

                }

                // Display row only if it has no empty fields
                if (!hasEmptyField) {
                    System.out.println(line);
                }

            }

            reader.close();

        } catch (Exception e) {

            System.out.println("Error reading file.");

        }

    }
}
