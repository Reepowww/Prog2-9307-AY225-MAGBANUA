// Programmer: Cyrus Mathew C. Magbanua - BSCS
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class StudentRecordSystem extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, nameField, gradeField;
    private JButton addButton, deleteButton;

    public StudentRecordSystem() {
        setTitle("Records - Cyrus Mathew C. Magbanua (BSCS)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Grade"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("ID:"));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);
        addButton = new JButton("Add");
        inputPanel.add(addButton);
        deleteButton = new JButton("Delete");
        inputPanel.add(deleteButton);
        add(inputPanel, BorderLayout.SOUTH);

        // Load CSV on startup
        loadCSV();

        // Add button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String name = nameField.getText();
                String grade = gradeField.getText();
                if (!id.isEmpty() && !name.isEmpty() && !grade.isEmpty()) {
                    model.addRow(new Object[]{id, name, grade});
                    idField.setText("");
                    nameField.setText("");
                    gradeField.setText("");
                }
            }
        });

        // Delete button action
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                }
            }
        });

        pack();
        setVisible(true);
    }

    private void loadCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader("MOCK_DATA.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    model.addRow(parts);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading CSV file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentRecordSystem());
    }
}