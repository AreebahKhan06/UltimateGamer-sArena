
//delete booking
package project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class DeleteGame extends JFrame implements ActionListener {
    private JTextField nameField;
    private JButton deleteButton, backButton;

    public DeleteGame() {
        setTitle("Delete Game");
        setSize(500, 300);
        setLayout(null); // Using manual positioning
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // JLabel heading = new JLabel("Delete a Game");
        // heading.setFont(new Font("Algerian", Font.BOLD, 24));
        // heading.setBounds(150, 20, 300, 30);
        // add(heading);

        // JLabel nameLabel = new JLabel("Enter Game Name:");
        // nameLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        // nameLabel.setBounds(50, 90, 150, 30);
        // add(nameLabel);
        JLabel heading = new JLabel("Delete a Game");
heading.setFont(new Font("Algerian", Font.BOLD, 24));
heading.setForeground(Color.WHITE);  // Add this line
heading.setBounds(150, 20, 300, 30);
add(heading);

JLabel nameLabel = new JLabel("Enter Game Name:");
nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
nameLabel.setForeground(Color.WHITE);  // Add this line
nameLabel.setBounds(50, 90, 150, 30);
add(nameLabel);

        nameField = new JTextField();
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBounds(200, 90, 220, 30);
        add(nameField);

        deleteButton = new JButton("Delete Game");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setBounds(150, 150, 150, 40);
        deleteButton.setBackground(Color.RED);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(this);
        add(deleteButton);

        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBounds(320, 150, 100, 40);
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        add(backButton);

        getContentPane().setBackground(new Color(0, 50, 50)); // Light background
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            String gameName = nameField.getText().trim();
            if (!gameName.isEmpty()) {
                boolean deleted = deleteGame(gameName);
                if (deleted) {
                    JOptionPane.showMessageDialog(this, "Game deleted successfully!");
                    nameField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Game not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a game name.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == backButton) {
            dispose(); // Close window
        }
    }

    private boolean deleteGame(String gameName) {
        ArrayList<String> games = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("added_games.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                if (!details[0].equalsIgnoreCase(gameName)) {
                    games.add(line);
                } else {
                    found = true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("added_games.txt"))) {
                for (String game : games) {
                    writer.write(game);
                    writer.newLine();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return found;
    }

}