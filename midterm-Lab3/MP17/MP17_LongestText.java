import java.io.*;
import java.util.*;

/*
MP17 – Find the longest text entry in dataset
This program reads a CSV file and finds the row that contains
the longest text entry based on character length.
*/

public class MP17_LongestText {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Ask user for dataset path
        System.out.print("Enter dataset file path: ");
        String filePath = input.nextLine();

        String longestText = "";   // Stores the longest row

        try {

            // Read the CSV file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            // Loop through every row
            while ((line = reader.readLine()) != null) {

                // Compare length of current row with longest stored row
                if (line.length() > longestText.length()) {
                    longestText = line;
                }

            }

            reader.close();

            // Display result
            System.out.println("\nLongest Text Entry:");
            System.out.println(longestText);
            System.out.println("\nLength: " + longestText.length() + " characters");

        } catch (Exception e) {

            System.out.println("Error reading file.");

        }

    }
}