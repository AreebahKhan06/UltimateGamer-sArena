

//update booking
package project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

class UpdateBooking extends JFrame {
    private JComboBox<String> bookingDropdown, slotDropdown;
    private JButton updateButton, backButton;
    private List<String[]> allBookings = new ArrayList<>();
    private List<String[]> allGames = new ArrayList<>();
    private String currentUser;

    public UpdateBooking() {
        this(null);
    }

    public UpdateBooking(String username) {
        this.currentUser = username;
        
        setTitle("Update Booking");
        setSize(550, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0, 51, 51));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Update Your Booking");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Booking Selection
        gbc.gridy++;
        mainPanel.add(createLabel("Select Your Booking:"), gbc);
        bookingDropdown = createDropdown();
        gbc.gridx = 1;
        mainPanel.add(bookingDropdown, gbc);

        // Slot Selection
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(createLabel("Select New Time Slot:"), gbc);
        slotDropdown = createDropdown();
        gbc.gridx = 1;
        mainPanel.add(slotDropdown, gbc);

        // Buttons
        updateButton = new JButton("Update Slot");
        backButton = new JButton("Back to Dashboard");
        styleButton(updateButton);
        styleButton(backButton);

        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(updateButton, gbc);
        gbc.gridx = 1;
        mainPanel.add(backButton, gbc);

        add(mainPanel, BorderLayout.CENTER);

        loadGames();
        loadBookings();

        bookingDropdown.addActionListener(e -> loadSlotOptions());
        updateButton.addActionListener(e -> updateBookingSlot());
        backButton.addActionListener(e -> {
            dispose();
            new PlayerDashboard(currentUser);
        });

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        return label;
    }

    private JComboBox<String> createDropdown() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(250, 35));
        comboBox.setBackground(Color.WHITE);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 15));
        return comboBox;
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(0, 102, 102));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 15));
    }

    private void loadBookings() {
        allBookings.clear();
        bookingDropdown.removeAllItems();
        try (BufferedReader br = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] booking = line.split(",");
                // Only show current user's bookings if username is provided
                if (currentUser == null || booking[0].contains(currentUser)) {
                    allBookings.add(booking);
                    bookingDropdown.addItem(booking[0] + " - " + booking[1] + " (" + booking[2] + ")");
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading bookings!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadGames() {
        allGames.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("added_games.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] game = line.split(",");
                if (game.length >= 5) {
                    allGames.add(game);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading games!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadSlotOptions() {
        slotDropdown.removeAllItems();
        int selectedIndex = bookingDropdown.getSelectedIndex();
        if (selectedIndex >= 0) {
            String[] selectedBooking = allBookings.get(selectedIndex);
            String bookedGame = selectedBooking[1]; // Get the game name from booking

            for (String[] game : allGames) {
                if (game[0].equals(bookedGame)) {
                    slotDropdown.addItem(game[3]); // Slot 1
                    slotDropdown.addItem(game[4]); // Slot 2
                    break;
                }
            }
        }
    }

    private void updateBookingSlot() {
        int selectedBookingIndex = bookingDropdown.getSelectedIndex();
        String selectedSlot = (String) slotDropdown.getSelectedItem();

        if (selectedBookingIndex >= 0 && selectedSlot != null) {
            allBookings.get(selectedBookingIndex)[2] = selectedSlot; // Update only the slot

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt"))) {
                for (String[] booking : allBookings) {
                    bw.write(String.join(",", booking));
                    bw.newLine();
                }
                JOptionPane.showMessageDialog(this, "Booking Slot Updated Successfully!");
                dispose();
                new PlayerDashboard(currentUser);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving booking!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a slot!", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}