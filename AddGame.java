//add game
package project;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class AddGame extends JFrame {
    private JTextField nameField, playersField, priceField, slot1Field, slot2Field;
    private JButton addButton, backButton;

    public AddGame() {
        setTitle("Add Game - Ultimate Gamer's Arena");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(0, 50, 50));

        // Heading
        JLabel heading = new JLabel("ADD GAME", SwingConstants.CENTER);
        heading.setFont(new Font("Algerian", Font.BOLD, 20));
        heading.setForeground(Color.WHITE);
        add(heading, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(new Color(0, 50, 50));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        nameField = new JTextField();
        playersField = new JTextField();
        priceField = new JTextField();
        slot1Field = new JTextField();
        slot2Field = new JTextField();

        addLabelAndField(formPanel, "Game Name:", nameField);
        addLabelAndField(formPanel, "Players (max 2):", playersField);
        addLabelAndField(formPanel, "Price:", priceField);
        addLabelAndField(formPanel, "Time Slot 1:", slot1Field);
        addLabelAndField(formPanel, "Time Slot 2:", slot2Field);

        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(0, 50, 50));
        addButton = new JButton("Add Game");
        backButton = new JButton("Return to Dashboard");
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action for Add Game
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String players = playersField.getText().trim();
            String price = priceField.getText().trim();
            String slot1 = slot1Field.getText().trim();
            String slot2 = slot2Field.getText().trim();

            if (name.isEmpty() || players.isEmpty() || price.isEmpty() || slot1.isEmpty() || slot2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("added_games.txt", true))) {
                writer.write(name + "," + players + "," + price + "," + slot1 + "," + slot2);
                writer.newLine();
                JOptionPane.showMessageDialog(this, "Game added successfully!");
                clearFields();  // Optional: clear fields after adding
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Action for Return to Dashboard
        backButton.addActionListener(e -> {
            dispose();
            new StaffInterface();
        });

        setLocationRelativeTo(null); // Center on screen
        setVisible(true);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.CYAN);
        panel.add(label);
        panel.add(field);
    }

    private void clearFields() {
        nameField.setText("");
        playersField.setText("");
        priceField.setText("");
        slot1Field.setText("");
        slot2Field.setText("");
    }
}