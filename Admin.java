
package project;

import javax.swing.*;
import java.awt.*;

import java.io.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}

class AdminDashboard extends JFrame {
    private String currentAdmin;

    public AdminDashboard(String username) {
        this.currentAdmin = username;
        
        setTitle("Admin Panel - Ultimate Gamer's Arena");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setBackground(new Color(10, 50, 50));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Title with admin username
        JLabel titleLabel = new JLabel("ADMIN DASHBOARD - " + currentAdmin);
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        
        // Admin functions section titles
        JLabel playerManagementLabel = new JLabel("PLAYER MANAGEMENT");
        playerManagementLabel.setFont(new Font("Algerian", Font.BOLD, 18));
        playerManagementLabel.setForeground(new Color(255, 255, 255));
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(playerManagementLabel, gbc);
        
        // Player management buttons
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.gridx = 0;
        JButton viewPlayersBtn = createButton("VIEW PLAYER ACCOUNTS");
        panel.add(viewPlayersBtn, gbc);
        
        gbc.gridx = 1;
        JButton manageBookingsBtn = createButton("MANAGE BOOKINGS");
        panel.add(manageBookingsBtn, gbc);
        JLabel titleL = new JLabel("BOOKING MANAGEMENT", JLabel.CENTER);
titleLabel.setFont(new Font("Algerian", Font.BOLD, 20)); 
titleLabel.setForeground(Color.WHITE);
        
        // Staff management section
        JLabel staffManagementLabel = new JLabel("STAFF MANAGEMENT");
        staffManagementLabel.setFont(new Font("Algerian", Font.BOLD, 18));
        staffManagementLabel.setForeground(new Color(255, 255, 255));
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(staffManagementLabel, gbc);
        
        // Staff management buttons
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.gridx = 0;
        JButton viewStaffBtn = createButton("VIEW STAFF ACCOUNTS");
        panel.add(viewStaffBtn, gbc);
        
        gbc.gridx = 1;
        JButton manageStaffBtn = createButton("ADD/REMOVE STAFF");
        panel.add(manageStaffBtn, gbc);
        
        // Game management section
        JLabel gameManagementLabel = new JLabel("GAME MANAGEMENT");
        gameManagementLabel.setFont(new Font("Algerian", Font.BOLD, 18));
        gameManagementLabel.setForeground(new Color(255, 255, 255));
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(gameManagementLabel, gbc);
        
        // Game management buttons
        gbc.gridwidth = 1;
        gbc.gridy = 6;
        gbc.gridx = 0;
        
        JButton viewGamesBtn = createButton("VIEW ALL GAMES");
        panel.add(viewGamesBtn, gbc);
      
        gbc.gridx = 1;
        JButton manageGamesBtn = createButton("MANAGE GAMES");
        panel.add(manageGamesBtn, gbc);
        
        // Logout button
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton logoutBtn = createButton("LOG OUT");
        logoutBtn.setBackground(new Color(153, 0, 0));
        panel.add(logoutBtn, gbc);
        
        add(panel);
        
        // Button actions
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginSystemWithImage();
        });
        viewPlayersBtn.addActionListener(e -> {
            new AdminViewUsers("Player");
        });
        viewStaffBtn.addActionListener(e -> {
            new AdminViewUsers("Staff");
        });
        
        manageStaffBtn.addActionListener(e -> {
            new AdminManageStaff();
        });
     
        viewGamesBtn.addActionListener(e -> {
            new ViewSchedule(currentAdmin, "Admin");
        });
        
        
        manageGamesBtn.addActionListener(e -> {
            new AdminManageGames();
        });
        
        manageBookingsBtn.addActionListener(e -> {
            new AdminManageBookings();
        });
        
        setVisible(true);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setBackground(new Color(30, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }
}

class AdminViewUsers extends JFrame {
    private JTable userTable;
    private DefaultTableModel tableModel;
    
    public AdminViewUsers(String userType) {
        setTitle("Admin - View " + userType + " Accounts");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(10, 50, 50));
        
        // Table setup
        String[] columns = {"Username", "Password", "Role"};
        tableModel = new DefaultTableModel(columns, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load users
        loadUsers(userType);
        
        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(10, 50, 50));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    private void loadUsers(String userRole) {
        ArrayList<User> users = UserManager.loadUsers();
        for (User user : users) {
            if (user.getRole().equals(userRole)) {
                tableModel.addRow(new Object[]{
                    user.getUsername(),
                    user.getPassword(),
                    user.getRole()
                });
            }
        }
    }
}

class AdminManageStaff extends JFrame {
    private JTable staffTable;
    private DefaultTableModel tableModel;
    private JButton addStaffBtn, removeStaffBtn, backBtn;
    
    public AdminManageStaff() {
        setTitle("Admin - Manage Staff");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(10, 50, 50));
        
        // Top label
        JLabel titleLabel = new JLabel("Staff Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Table setup
        String[] columns = {"Username", "Password"};
        tableModel = new DefaultTableModel(columns, 0);
        staffTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(staffTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load staff users
        loadStaffUsers();
        
        // Bottom buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(10, 50, 50));
        
        addStaffBtn = new JButton("Add Staff");
        removeStaffBtn = new JButton("Remove Selected Staff");
        backBtn = new JButton("Back");
        
        buttonPanel.add(addStaffBtn);
        buttonPanel.add(removeStaffBtn);
        buttonPanel.add(backBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button actions
        addStaffBtn.addActionListener(e -> showAddStaffDialog());
        
        removeStaffBtn.addActionListener(e -> {
            int selectedRow = staffTable.getSelectedRow();
            if (selectedRow >= 0) {
                String username = (String) tableModel.getValueAt(selectedRow, 0);
                removeStaffUser(username);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Staff removed successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a staff to remove.");
            }
        });
        
        backBtn.addActionListener(e -> dispose());
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void loadStaffUsers() {
        ArrayList<User> users = UserManager.loadUsers();
        for (User user : users) {
            if (user.getRole().equals("Staff")) {
                tableModel.addRow(new Object[]{
                    user.getUsername(),
                    user.getPassword()
                });
            }
        }
    }
    
    private void showAddStaffDialog() {
        JTextField usernameField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Staff",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty!");
                return;
            }
            
            if (UserManager.usernameExists(username)) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
                return;
            }
            
            User newStaff = new Staff(username, password);
            UserManager.saveUser(newStaff);
            
            tableModel.addRow(new Object[]{username, password});
            JOptionPane.showMessageDialog(this, "Staff added successfully!");
        }
    }
    
    private void removeStaffUser(String username) {
        ArrayList<User> users = UserManager.loadUsers();
        ArrayList<User> updatedUsers = new ArrayList<>();
        
        for (User user : users) {
            if (!(user.getRole().equals("Staff") && user.getUsername().equals(username))) {
                updatedUsers.add(user);
            }
        }
        
        // Clear the file and rewrite all users except the removed one
        try (PrintWriter writer = new PrintWriter(new FileWriter(UserManager.USER_FILE))) {
            for (User user : updatedUsers) {
                writer.println(user.getRole() + "," + user.getUsername() + "," + user.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class AdminManageBookings extends JFrame {
    private JTable bookingsTable;
    private DefaultTableModel tableModel;
    private JButton updateBtn, cancelBtn, backBtn;
    private ArrayList<String> bookings = new ArrayList<>();
    
    public AdminManageBookings() {
        setTitle("Admin - Manage Bookings");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(10, 50, 50));
        
        // Top label
        JLabel titleLabel = new JLabel("Booking Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Table setup
        String[] columns = {"Player(s)", "Game", "Time Slot"};
        tableModel = new DefaultTableModel(columns, 0);
        bookingsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookingsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load bookings
        loadBookings();
        
        // Bottom buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(10, 50, 50));
        
        updateBtn = new JButton("Update Selected Booking");
        cancelBtn = new JButton("Cancel Selected Booking");
        backBtn = new JButton("Back");
        
        buttonPanel.add(updateBtn);
        buttonPanel.add(cancelBtn);
        buttonPanel.add(backBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button actions
        updateBtn.addActionListener(e -> {
            int selectedRow = bookingsTable.getSelectedRow();
            if (selectedRow >= 0) {
                updateBooking(selectedRow);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a booking to update.");
            }
        });
        
        cancelBtn.addActionListener(e -> {
            int selectedRow = bookingsTable.getSelectedRow();
            if (selectedRow >= 0) {
                cancelBooking(selectedRow);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a booking to cancel.");
            }
        });
        
        backBtn.addActionListener(e -> dispose());
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void loadBookings() {
        bookings.clear();
        tableModel.setRowCount(0);
        
        try (BufferedReader reader = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(line);
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    tableModel.addRow(new Object[]{
                        parts[0], // Player(s)
                        parts[1], // Game
                        parts[2]  // Time slot
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void updateBooking(int selectedRow) {
        String bookingInfo = bookings.get(selectedRow);
        String[] parts = bookingInfo.split(",");
        
        // Get available slots for the game
        ArrayList<String> availableSlots = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("added_games.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] gameData = line.split(",");
                if (gameData.length >= 5 && gameData[0].equals(parts[1])) {
                    availableSlots.add(gameData[3]); // Slot 1
                    availableSlots.add(gameData[4]); // Slot 2
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Show slot selection dialog
        JComboBox<String> slotComboBox = new JComboBox<>();
        for (String slot : availableSlots) {
            slotComboBox.addItem(slot);
        }
        
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Select new time slot:"));
        panel.add(slotComboBox);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Update Booking",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            
        if (result == JOptionPane.OK_OPTION) {
            String newSlot = (String) slotComboBox.getSelectedItem();
            
            // Update booking
            String[] updatedBooking = parts.clone();
            updatedBooking[2] = newSlot;
            bookings.set(selectedRow, String.join(",", updatedBooking));
            
            // Update table
            tableModel.setValueAt(newSlot, selectedRow, 2);
            
            // Save updated bookings
            saveBookings();
            
            JOptionPane.showMessageDialog(this, "Booking updated successfully!");
        }
    }
    
    private void cancelBooking(int selectedRow) {
        bookings.remove(selectedRow);
        saveBookings();
    }
    
    private void saveBookings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt"))) {
            for (String booking : bookings) {
                writer.write(booking);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class AdminManageGames extends JFrame {
    private JTable gamesTable;
    private DefaultTableModel tableModel;
    private JButton addBtn, updateBtn, deleteBtn, backBtn;
    private ArrayList<String> games = new ArrayList<>();

    
    
    public AdminManageGames() {
        setTitle("Admin - Manage Games");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(10, 50, 50));
        
        // Top label
        JLabel titleLabel = new JLabel("Game Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Table setup
        String[] columns = {"Game Name", "Max Players", "Price", "Slot 1", "Slot 2"};
        tableModel = new DefaultTableModel(columns, 0);
        gamesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(gamesTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load games
        loadGames();
        
        // Bottom buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(10, 50, 50));
        
        addBtn = new JButton("Add Game");
        updateBtn = new JButton("Update Selected Game");
        deleteBtn = new JButton("Delete Selected Game");
        backBtn = new JButton("Back");
        
        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(backBtn);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Button actions
        addBtn.addActionListener(e -> {
            new AddGame();
            dispose();
        });
        
        updateBtn.addActionListener(e -> {
            int selectedRow = gamesTable.getSelectedRow();
            if (selectedRow >= 0) {
                String gameName = (String) tableModel.getValueAt(selectedRow, 0);
                new UpdateGame();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a game to update.");
            }
        });
        
        deleteBtn.addActionListener(e -> {
            int selectedRow = gamesTable.getSelectedRow();
            if (selectedRow >= 0) {
                String gameName = (String) tableModel.getValueAt(selectedRow, 0);
                deleteGame(gameName);
                tableModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Game deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Please select a game to delete.");
            }
        });
        
        backBtn.addActionListener(e -> dispose());
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void loadGames() {
        games.clear();
        tableModel.setRowCount(0);
        
        try (BufferedReader reader = new BufferedReader(new FileReader("added_games.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                games.add(line);
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    tableModel.addRow(new Object[]{
                        parts[0], // Game name
                        parts[1], // Max players
                        parts[2], // Price
                        parts[3], // Slot 1
                        parts[4]  // Slot 2
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void deleteGame(String gameName) {
        ArrayList<String> updatedGames = new ArrayList<>();
        
        for (String game : games) {
            String[] parts = game.split(",");
            if (!parts[0].equals(gameName)) {
                updatedGames.add(game);
            }
        }
        
        games = updatedGames;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("added_games.txt"))) {
            for (String game : games) {
                writer.write(game);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}