import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<DataRecord> records = null;

        while (true) {
            try {
                System.out.print("Enter dataset file path: ");
                String path = scanner.nextLine();

                File file = new File(path);

                if (!file.exists()) {
                    System.out.println("Error: File does not exist.\n");
                    continue;
                }

                if (!file.canRead()) {
                    System.out.println("Error: File cannot be read.\n");
                    continue;
                }

                records = readCSV(path);

                if (records == null || records.isEmpty()) {
                    System.out.println("Error: Invalid or empty CSV file.\n");
                    continue;
                }

                break; // exit loop if valid

            } catch (Exception e) {
                System.out.println("Error: Invalid file format.\n");
            }
        }

        // Sort by date
        records.sort(Comparator.comparing(DataRecord::getDate));

        // Compute moving average
        computeMovingAverage(records);
    }

    private static List<DataRecord> readCSV(String path) {
        List<DataRecord> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            String line;
            br.readLine(); // skip header

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(",");

                if (parts.length != 2) {
                    return null; // invalid format
                }

                LocalDate date = LocalDate.parse(parts[0].trim());
                double sales = Double.parseDouble(parts[1].trim());

                records.add(new DataRecord(date, sales));
            }

        } catch (Exception e) {
            return null;
        }

        return records;
    }

    private static void computeMovingAverage(List<DataRecord> records) {

        System.out.println("\nSales Trend with 3-Record Moving Average");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-12s %-12s %-15s\n", "Date", "Sales", "3-Day Avg");
        System.out.println("-------------------------------------------------");

        for (int i = 0; i < records.size(); i++) {

            double movingAverage = 0;

            if (i >= 2) {
                double sum = records.get(i).getSales()
                        + records.get(i - 1).getSales()
                        + records.get(i - 2).getSales();

                movingAverage = sum / 3;
            }

            System.out.printf("%-12s %-12.2f ",
                    records.get(i).getDate(),
                    records.get(i).getSales());

            if (i >= 2) {
                System.out.printf("%-15.2f\n", movingAverage);
            } else {
                System.out.printf("%-15s\n", "N/A");
            }
        }
    }
}