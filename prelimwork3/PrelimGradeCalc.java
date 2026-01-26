import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrelimGradeCalc{
    public static void main(String[] args) {
        // Main window
        JFrame frame = new JFrame("Prelim Grade Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null); // Center on screen

        // Layout
        frame.setLayout(new BorderLayout());

        // Left panel: Results
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new GridLayout(10, 1, 5, 5));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        JLabel labAverageLabel = new JLabel("Lab Work Average: ");
        JLabel classStandingLabel = new JLabel("Class Standing: ");
        JLabel requiredPassLabel = new JLabel("Required Prelim Exam (Pass): ");
        JLabel requiredExcellentLabel = new JLabel("Required Prelim Exam (Excellent): ");
        JLabel statusLabel = new JLabel("Academic Status: ");

        resultPanel.add(labAverageLabel);
        resultPanel.add(classStandingLabel);
        resultPanel.add(requiredPassLabel);
        resultPanel.add(requiredExcellentLabel);
        resultPanel.add(statusLabel);

        // Right panel: Inputs
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Scores"));

        JTextField attendanceField = new JTextField();
        JTextField lab1Field = new JTextField();
        JTextField lab2Field = new JTextField();
        JTextField lab3Field = new JTextField();

        inputPanel.add(new JLabel("Attendance (0-100): "));
        inputPanel.add(attendanceField);
        inputPanel.add(new JLabel("Lab Work 1 (0-100): "));
        inputPanel.add(lab1Field);
        inputPanel.add(new JLabel("Lab Work 2 (0-100): "));
        inputPanel.add(lab2Field);
        inputPanel.add(new JLabel("Lab Work 3 (0-100): "));
        inputPanel.add(lab3Field);

        JButton computeButton = new JButton("Compute Grades");
        inputPanel.add(new JLabel()); // empty space
        inputPanel.add(computeButton);

        // Add panels to frame
        frame.add(resultPanel, BorderLayout.WEST);
        frame.add(inputPanel, BorderLayout.CENTER);

        // Action listener for compute button
        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double attendance = parseScore(attendanceField.getText(), "Attendance");
                    double lab1 = parseScore(lab1Field.getText(), "Lab Work 1");
                    double lab2 = parseScore(lab2Field.getText(), "Lab Work 2");
                    double lab3 = parseScore(lab3Field.getText(), "Lab Work 3");

                    // Computations
                    double labAverage = Math.min((lab1 + lab2 + lab3) / 3.0, 100.0);
                    double classStanding = Math.min(0.4 * attendance + 0.6 * labAverage, 100.0);
                    double requiredPass = Math.min((75 - 0.3 * classStanding) / 0.7, 100.0);
                    double requiredExcellent = Math.min((100 - 0.3 * classStanding) / 0.7, 100.0);

                    String status;
                    if (classStanding < 50) status = "AT RISK";
                    else if (requiredPass > 75) status = "NEEDS IMPROVEMENT";
                    else if (requiredExcellent <= 100 && classStanding >= 80) status = "EXCELLENT";
                    else status = "PASSED";

                    // Update results
                    labAverageLabel.setText("Lab Work Average: " + Math.round(labAverage));
                    classStandingLabel.setText("Class Standing: " + Math.round(classStanding));
                    requiredPassLabel.setText("Required Prelim Exam (Pass): " + Math.round(requiredPass));
                    requiredExcellentLabel.setText("Required Prelim Exam (Excellent): " + Math.round(requiredExcellent));
                    statusLabel.setText("Academic Status: " + status);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    // Helper method for input validation
    private static double parseScore(String text, String fieldName) throws NumberFormatException {
        double value;
        try {
            value = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(fieldName + " must be a numeric value.");
        }
        if (value < 0 || value > 100) {
            throw new NumberFormatException(fieldName + " must be between 0 and 100.");
        }
        return value;
    }
}
