import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Main {

    public static void main(String[] args) {

        List<DataRecord> records = null;

        while (true) {
            try {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Downloads"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(null);
                if (result != JFileChooser.APPROVE_OPTION) {
                    System.out.println("No file selected. Please try again.\n");
                    continue;
                }

                File selectedFile = fileChooser.getSelectedFile();
                if (!selectedFile.exists() || !selectedFile.canRead()) {
                    System.out.println("Error: File cannot be read.\n");
                    continue;
                }

                records = readCSV(selectedFile.getAbsolutePath());
                if (records == null || records.isEmpty()) {
                    System.out.println("Error: Invalid or empty CSV file.\n");
                    continue;
                }

                break;

            } catch (Exception e) {
                System.out.println("Error: Invalid file format.\n");
            }
        }

        // Sort by date
        records.sort(Comparator.comparing(DataRecord::getDate));

        // Print to console
        computeMovingAverage(records);

        // Show in GUI
        showTableUI(records);
    }

    private static List<DataRecord> readCSV(String path) {
        List<DataRecord> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 14) continue;
                try {
                    double sales = Double.parseDouble(parts[6].trim());
                    String dateStr = parts[parts.length - 2].trim();
                    if (dateStr.isEmpty()) continue;
                    LocalDate date = LocalDate.parse(dateStr);
                    records.add(new DataRecord(date, sales));
                } catch (Exception ignored) {}
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

    // New method to show results in JTable
    private static void showTableUI(List<DataRecord> records) {
        String[] columns = {"Date", "Sales", "3-Day Avg"};
        String[][] data = new String[records.size()][3];

        for (int i = 0; i < records.size(); i++) {
            double movingAverage = 0;
            String maString = "N/A";
            if (i >= 2) {
                movingAverage = (records.get(i).getSales()
                        + records.get(i - 1).getSales()
                        + records.get(i - 2).getSales()) / 3;
                maString = String.format("%.2f", movingAverage);
            }
            data[i][0] = records.get(i).getDate().toString();
            data[i][1] = String.format("%.2f", records.get(i).getSales());
            data[i][2] = maString;
        }

        JTable table = new JTable(new DefaultTableModel(data, columns));
        JFrame frame = new JFrame("Sales Trend with 3-Record Moving Average");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(table));
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}