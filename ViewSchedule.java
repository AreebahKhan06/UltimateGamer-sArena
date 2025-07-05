package project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;


class ViewSchedule extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private String currentUser;
    private String userRole; // Added to track user role

    public ViewSchedule() {
        this(null, null);
    }

    public ViewSchedule(String username) {
        this(username, "Player"); // Default role to Player if not specified
    }

    // Add a new constructor that takes both username and role
    public ViewSchedule(String username, String role) {
        this.currentUser = username;
        this.userRole = role;
        
        // Ensure games are initialized
        gameView.initializeGames();
        
        setTitle("Game Schedule");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 51, 51));

        JLabel titleLabel = new JLabel("Available Game Schedule", JLabel.CENTER);
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 20));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Table Setup
        String[] columns = {"Game Name", "Players", "Price", "Slot 1", "Slot 2"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setEnabled(false);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Load data from file
        loadSchedule();

        // Bottom panel with Back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(0, 51, 51));
        JButton backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(0, 102, 102));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Modified action listener to handle different user roles
        backButton.addActionListener(e -> {
            dispose();
            // Determine which dashboard to go back to based on user role
            if (userRole != null && userRole.equals("Admin")) {
                // No need to create a new AdminDashboard, just close this window
                // The AdminDashboard is still open in the background
            } else {
                // For players, open the PlayerDashboard
                new PlayerDashboard(currentUser);
            }
        });
        
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private void loadSchedule() {
        try (BufferedReader reader = new BufferedReader(new FileReader("added_games.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    model.addRow(new Object[]{
                        parts[0], // Game name
                        parts[1], // Players
                        "rs." + parts[2], // Price with dollar sign
                        parts[3], // Slot 1
                        parts[4]  // Slot 2
                    });
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not load game schedule!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}