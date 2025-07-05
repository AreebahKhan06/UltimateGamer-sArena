
package project;

import javax.swing.*;
import java.awt.*;

public class PlayerDashboard extends JFrame {
    private String currentUser;

    public PlayerDashboard() {
        this(null);
    }

    public PlayerDashboard(String username) {
        this.currentUser = username;
        
        setTitle("Player Panel - Ultimate Gamer's Arena");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 51, 51));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // Title with username if available
        JLabel titleLabel;
        if (currentUser != null) {
            titleLabel = new JLabel("PLAYER - " + currentUser);
        } else {
            titleLabel = new JLabel("PLAYER");
        }
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Buttons
        JButton viewScheduleBtn = createButton("VIEW SCHEDULE");
        JButton bookSlotBtn = createButton("BOOK SLOT");
        JButton updateBookingBtn = createButton("UPDATE BOOKING");
        JButton cancelBookingBtn = createButton("CANCEL BOOKING");
        JButton logoutBtn = createButton("LOG OUT");

        gbc.gridy = 1;
        panel.add(viewScheduleBtn, gbc);

        gbc.gridy = 2;
        panel.add(bookSlotBtn, gbc);

        gbc.gridy = 3;
        panel.add(updateBookingBtn, gbc);

        gbc.gridy = 4;
        panel.add(cancelBookingBtn, gbc);

        gbc.gridy = 5;
        panel.add(logoutBtn, gbc);

        add(panel);
        setVisible(true);

        // Button actions
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginSystemWithImage();
        });

        viewScheduleBtn.addActionListener(e -> {
            dispose();
            new ViewSchedule(currentUser);
        });
        
        bookSlotBtn.addActionListener(e -> {
            dispose();
            new BookSlot(currentUser);
        });
        
        updateBookingBtn.addActionListener(e -> {
            dispose();
            new UpdateBooking(currentUser);
        });
        
        cancelBookingBtn.addActionListener(e -> {
            dispose();
            new DeleteBooking(currentUser);
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 102, 102));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }
}