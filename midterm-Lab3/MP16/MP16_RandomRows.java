import java.io.*;
import java.util.*;

/*
MP16 – Randomly display 10 rows from dataset
Program reads a CSV file and randomly selects 10 rows to display.
*/

public class MP16_RandomRows {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Ask user for dataset file path
        System.out.print("Enter dataset file path: ");
        String filePath = input.nextLine();

        ArrayList<String> rows = new ArrayList<>();

        try {
            // Read CSV file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            // Store each row in ArrayList
            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }

            reader.close();

            // Random generator
            Random rand = new Random();

            System.out.println("\nRandom 10 Rows:\n");

            // Display 10 random rows
            for (int i = 0; i < 10; i++) {
                int index = rand.nextInt(rows.size());
                System.out.println(rows.get(index));
            }

        } catch (Exception e) {
            System.out.println("Error reading file.");
        }

    }
}