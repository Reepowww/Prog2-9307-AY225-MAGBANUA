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
        setTitle("Records - Cyrus Mathew C. Magbanua (BSCS)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup with 8 columns
        model = new DefaultTableModel(new String[]{
            "StudentID", "First Name", "Last Name", "Lab 1", "Lab 2", "Lab 3", "Prelim Exam", "Attendance"
        }, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel (8 fields + 2 buttons)
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        idField = new JTextField(); inputPanel.add(new JLabel("Student ID:")); inputPanel.add(idField);
        firstNameField = new JTextField(); inputPanel.add(new JLabel("First Name:")); inputPanel.add(firstNameField);
        lastNameField = new JTextField(); inputPanel.add(new JLabel("Last Name:")); inputPanel.add(lastNameField);
        lab1Field = new JTextField(); inputPanel.add(new JLabel("Lab Work 1:")); inputPanel.add(lab1Field);
        lab2Field = new JTextField(); inputPanel.add(new JLabel("Lab Work 2:")); inputPanel.add(lab2Field);
        lab3Field = new JTextField(); inputPanel.add(new JLabel("Lab Work 3:")); inputPanel.add(lab3Field);
        prelimField = new JTextField(); inputPanel.add(new JLabel("Prelim Exam:")); inputPanel.add(prelimField);
        attendanceField = new JTextField(); inputPanel.add(new JLabel("Attendance Grade:")); inputPanel.add(attendanceField);

        addButton = new JButton("Add"); inputPanel.add(addButton);
        deleteButton = new JButton("Delete"); inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.SOUTH);

        // Load CSV on startup
        loadCSV();

        // Add button action
        addButton.addActionListener(e -> {
            String[] rowData = new String[]{
                idField.getText(), firstNameField.getText(), lastNameField.getText(),
                lab1Field.getText(), lab2Field.getText(), lab3Field.getText(),
                prelimField.getText(), attendanceField.getText()
            };
            boolean emptyField = false;
            for(String s : rowData) if(s.isEmpty()) emptyField = true;
            if(!emptyField){
                model.addRow(rowData);
                // clear fields
                idField.setText(""); firstNameField.setText(""); lastNameField.setText("");
                lab1Field.setText(""); lab2Field.setText(""); lab3Field.setText("");
                prelimField.setText(""); attendanceField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        });

        // Delete button action
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) model.removeRow(selectedRow);
        });

        pack();
        setVisible(true);
    }

    private void loadCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
            String line;
            boolean firstLine = true; // skip header
            while ((line = br.readLine()) != null) {
                if(firstLine) { firstLine = false; continue; } // skip header
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    for(int i = 0; i < parts.length; i++) parts[i] = parts[i].trim(); // remove spaces
                    model.addRow(parts);
                } else {
                    System.out.println("Skipped line: " + line);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentRecordSystem::new);
    }
}
