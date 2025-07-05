package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class DeleteBooking extends JFrame implements ActionListener {
    private JComboBox<String> bookingDropdown;
    private JButton deleteButton, backButton;
    private ArrayList<String> bookings = new ArrayList<>();

    public DeleteBooking(String currentUser) {
        setTitle("Cancel Booking");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0, 51, 51));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel titleLabel = new JLabel("Cancel Your Booking");
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel selectLabel = new JLabel("Select Booking to Cancel:");
        selectLabel.setForeground(Color.WHITE);
        mainPanel.add(selectLabel, gbc);

        bookingDropdown = new JComboBox<>();
        bookingDropdown.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        mainPanel.add(bookingDropdown, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        deleteButton = new JButton("Cancel Booking");
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        mainPanel.add(deleteButton, gbc);

        gbc.gridx = 1;
        backButton = new JButton("Back to Dashboard");
        backButton.setBackground(new Color(0, 102, 102));
        backButton.setForeground(Color.WHITE);
        mainPanel.add(backButton, gbc);

        add(mainPanel);
        loadBookings();

        deleteButton.addActionListener(this);
        backButton.addActionListener(this);

        setVisible(true);
    }

    // public DeleteBooking(String currentUser) {
    //     //TODO Auto-generated constructor stub
    // }

    private void loadBookings() {
        bookings.clear();
        bookingDropdown.removeAllItems();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                bookings.add(line);
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String displayText = parts[0] + " - " + parts[1] + " (" + parts[2] + ")";
                    bookingDropdown.addItem(displayText);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading bookings!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            int selectedIndex = bookingDropdown.getSelectedIndex();
            if (selectedIndex != -1) {
                bookings.remove(selectedIndex);
                
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt"))) {
                    for (String booking : bookings) {
                        writer.write(booking);
                        writer.newLine();
                    }
                    JOptionPane.showMessageDialog(this, "Booking cancelled successfully!");
                    dispose();
                    new PlayerDashboard();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving changes!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (e.getSource() == backButton) {
            dispose();
            new PlayerDashboard();
        }
    }
}