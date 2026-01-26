import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class AttendanceTracker {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AttendanceTracker::createUI);
    }

    private static void createUI() {
        JFrame frame = new JFrame("Attendance Tracker");
        frame.setSize(420, 340); // 👈 compact size
        frame.setResizable(false); // 👈 prevents stretching
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(245, 247, 250));
        frame.setContentPane(root);

        // ===== Title =====
        JLabel title = new JLabel("Attendance Tracker");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(new EmptyBorder(12, 15, 10, 15));
        root.add(title, BorderLayout.NORTH);

        // ===== Center Wrapper (keeps form centered) =====
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        root.add(centerWrapper, BorderLayout.CENTER);

        // ===== Card =====
        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(360, 230)); // 👈 fixed width
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        centerWrapper.add(card); // centers the card

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.NONE; // 👈 stop stretching

        // ===== Fields =====
        JTextField nameField = createField();
        JTextField courseField = createField();
        JTextField timeField = createField();
        JTextField signatureField = createField();

        timeField.setEditable(false);
        signatureField.setEditable(false);

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy | hh:mm a");
        timeField.setText(LocalDateTime.now().format(formatter));

        addRow(card, gbc, 0, "Attendance Name", nameField);
        addRow(card, gbc, 1, "Course / Year", courseField);
        addRow(card, gbc, 2, "Time In", timeField);
        addRow(card, gbc, 3, "E-Signature", signatureField);

        // ===== Submit Button =====
        JButton submit = new JButton("Submit Attendance");
        submit.setPreferredSize(new Dimension(180, 32));
        submit.setBackground(new Color(52, 120, 246));
        submit.setForeground(Color.WHITE);
        submit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        submit.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(12, 6, 0, 6);
        card.add(submit, gbc);

        submit.addActionListener(e -> {
            if (nameField.getText().isEmpty() ||
                courseField.getText().isEmpty()) {

                JOptionPane.showMessageDialog(frame,
                        "Please complete all required fields.",
                        "Incomplete Form",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String eSignature = "SIG-" +
                    System.currentTimeMillis() + "-" +
                    UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            signatureField.setText(eSignature);

            JOptionPane.showMessageDialog(frame,
                    "Attendance Recorded Successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        frame.setVisible(true);
    }

    // ===== Helpers =====
    private static JTextField createField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 26));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200)),
                new EmptyBorder(4, 6, 4, 6)
        ));
        return field;
    }

    private static void addRow(JPanel panel, GridBagConstraints gbc,
                               int row, String labelText, JTextField field) {

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST; // 👈 aligns labels nicely
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }
}
    