<think>
First, the user wants to modify the Java Swing program so that when adding a record, it saves to the MOCK_DATA.csv file.


## Modifying Program Functionality  
- The task is to update the Java Swing program to save new records to MOCK_DATA.csv.
</think>```java
// Programmer: Cyrus Mathew C. Magbanua - BSCS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class StudentRecordSystem extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, firstNameField, lastNameField, lab1Field, lab2Field, lab3Field, prelimField, attendanceField;
    private JButton addButton, deleteButton;

    public StudentRecordSystem() {
        // Set modern look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Student Record Management System - Cyrus Mathew C. Magbanua (BSCS)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));

        // Table setup with 8 columns
        model = new DefaultTableModel(new String[]{
            "Student ID", "First Name", "Last Name", "Lab Work 1", "Lab Work 2", "Lab Work 3", "Prelim Exam", "Attendance Grade"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Student Records"));
        add(scrollPane, BorderLayout.CENTER);

        // Input panel with modern layout
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Record"));

        // Create form panel with GridBagLayout for better alignment
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Row 1: Student ID
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1;
        idField = new JTextField(15);
        idField.setToolTipText("Enter unique Student ID");
        formPanel.add(idField, gbc);

        // Row 2: First Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        firstNameField = new JTextField(15);
        firstNameField.setToolTipText("Enter First Name");
        formPanel.add(firstNameField, gbc);

        // Row 3: Last Name
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField(15);
        lastNameField.setToolTipText("Enter Last Name");
        formPanel.add(lastNameField, gbc);

        // Row 4: Lab Work 1
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Lab Work 1:"), gbc);
        gbc.gridx = 1;
        lab1Field = new JTextField(15);
        lab1Field.setToolTipText("Enter Lab Work 1 Grade");
        formPanel.add(lab1Field, gbc);

        // Row 5: Lab Work 2
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Lab Work 2:"), gbc);
        gbc.gridx = 1;
        lab2Field = new JTextField(15);
        lab2Field.setToolTipText("Enter Lab Work 2 Grade");
        formPanel.add(lab2Field, gbc);

        // Row 6: Lab Work 3
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Lab Work 3:"), gbc);
        gbc.gridx = 1;
        lab3Field = new JTextField(15);
        lab3Field.setToolTipText("Enter Lab Work 3 Grade");
        formPanel.add(lab3Field, gbc);

        // Row 7: Prelim Exam
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Prelim Exam:"), gbc);
        gbc.gridx = 1;
        prelimField = new JTextField(15);
        prelimField.setToolTipText("Enter Prelim Exam Grade");
        formPanel.add(prelimField, gbc);

        // Row 8: Attendance Grade
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Attendance Grade:"), gbc);
        gbc.gridx = 1;
        attendanceField = new JTextField(15);
        attendanceField.setToolTipText("Enter Attendance Grade");
        formPanel.add(attendanceField, gbc);

        inputPanel.add(formPanel);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        addButton = new JButton("Add Record");
        addButton.setToolTipText("Add the new record to the table");
        buttonPanel.add(addButton);

        deleteButton = new JButton("Delete Selected");
        deleteButton.setToolTipText("Delete the selected record from the table");
        buttonPanel.add(deleteButton);

        inputPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.SOUTH);

        // Status bar
        JLabel statusLabel = new JLabel("Ready");
        statusLabel.setBorder(BorderFactory.createLoweredBevelBorder());
        add(statusLabel, BorderLayout.NORTH);

        // Load CSV on startup
        loadCSV();

        // Add button action
        addButton.addActionListener(e -> {
            String[] rowData = new String[]{
                idField.getText().trim(), firstNameField.getText().trim(), lastNameField.getText().trim(),
                lab1Field.getText().trim(), lab2Field.getText().trim(), lab3Field.getText().trim(),
                prelimField.getText().trim(), attendanceField.getText().trim()
            };
            boolean emptyField = false;
            for (String s : rowData) {
                if (s.isEmpty()) {
                    emptyField = true;
                    break;
                }
            }
            if (!emptyField) {
                model.addRow(rowData);
                // Save to CSV
                saveToCSV(rowData);
                // Clear fields
                idField.setText("");
                firstNameField.setText("");
                lastNameField.setText("");
                lab1Field.setText("");
                lab2Field.setText("");
                lab3Field.setText("");
                prelimField.setText("");
                attendanceField.setText("");
                statusLabel.setText("Record added and saved successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
                statusLabel.setText("Error: All fields must be filled.");
            }
        });

        // Delete button action with confirmation
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeRow(selectedRow);
                    statusLabel.setText("Record deleted.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                statusLabel.setText("Error: No row selected.");
            }
        });

        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void loadCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
            String line;
            boolean firstLine = true; // Skip header
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                } // Skip header
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].trim(); // Remove spaces
                    }
                    model.addRow(parts);
                } else {
                    System.out.println("Skipped invalid line: " + line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV file: " + e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToCSV(String[] rowData) {
        try (FileWriter fw = new FileWriter("MOCK_DATA.csv", true)) { // Append mode
            fw.write(String.join(",", rowData) + "\n");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to CSV file: " + e.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentRecordSystem::new);
    }
}
```