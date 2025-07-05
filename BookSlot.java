//add slot
package project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;


class BookSlot extends JFrame {
    private JComboBox<String> playerCountBox, gameDropdown, slotDropdown;
    private JTextField player1Field, player2Field;
    private JButton bookButton, confirmButton, backButton;
    private JTextArea receiptArea;
    private JPanel inputPanel, receiptPanel;
    private List<String[]> matchedGames = new ArrayList<>();
    private String currentUser;

    public BookSlot() {
        this(null);
    }

    public BookSlot(String username) {
        this.currentUser = username;
        
        setTitle("Book Game Slot");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(7, 2, 10, 10));
        inputPanel.setBackground(new Color(0, 51, 51));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(label("Select Number of Players:"));
        playerCountBox = new JComboBox<>(new String[]{"1", "2"});
        inputPanel.add(playerCountBox);

        inputPanel.add(label("Player 1 Name:"));
        player1Field = new JTextField();
        if (currentUser != null) {
            player1Field.setText(currentUser);
            player1Field.setEditable(false);
        }
        inputPanel.add(player1Field);

        inputPanel.add(label("Player 2 Name:"));
        player2Field = new JTextField();
        player2Field.setEnabled(false);
        inputPanel.add(player2Field);

        inputPanel.add(label("Select Game:"));
        gameDropdown = new JComboBox<>();
        inputPanel.add(gameDropdown);

        inputPanel.add(label("Select Time Slot:"));
        slotDropdown = new JComboBox<>();
        inputPanel.add(slotDropdown);

        bookButton = new JButton("Book Slot");
        confirmButton = new JButton("Confirm Booking");
        confirmButton.setEnabled(false);

        backButton = new JButton("Back to Dashboard");

        inputPanel.add(bookButton);
        inputPanel.add(confirmButton);

        receiptPanel = new JPanel(new BorderLayout());
        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        receiptPanel.add(backButton, BorderLayout.SOUTH);

        add(inputPanel, BorderLayout.NORTH);
        add(receiptPanel, BorderLayout.CENTER);

        playerCountBox.addActionListener(e -> togglePlayer2());
        gameDropdown.addActionListener(e -> updateSlots());
        bookButton.addActionListener(e -> generateReceipt());
        confirmButton.addActionListener(e -> confirmBooking());
        backButton.addActionListener(e -> {
            dispose();
            new PlayerDashboard(currentUser);
        });

        loadGames();
        setVisible(true);
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    private void togglePlayer2() {
        player2Field.setEnabled(playerCountBox.getSelectedIndex() == 1);
        loadGames(); // reload games based on new player count
    }

    private void loadGames() {
        gameDropdown.removeAllItems();
        matchedGames.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("added_games.txt"))) {
            String line;
            int playerCount = Integer.parseInt((String) playerCountBox.getSelectedItem());
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5 && Integer.parseInt(data[1]) == playerCount) {
                    gameDropdown.addItem(data[0]);
                    matchedGames.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void updateSlots() {
        slotDropdown.removeAllItems();
        String selectedGame = (String) gameDropdown.getSelectedItem();
        for (String[] game : matchedGames) {
            if (game[0].equals(selectedGame)) {
                slotDropdown.addItem(game[3]); // Slot 1
                slotDropdown.addItem(game[4]); // Slot 2
                break;
            }
        }
    }

    private void generateReceipt() {
        String player1 = player1Field.getText().trim();
        String player2 = player2Field.isEnabled() ? player2Field.getText().trim() : "";
        String game = (String) gameDropdown.getSelectedItem();
        String slot = (String) slotDropdown.getSelectedItem();
        String price = "";

        for (String[] gameData : matchedGames) {
            if (gameData[0].equals(game)) {
                price = gameData[2];
                break;
            }
        }

        receiptArea.setText("***** Booking Receipt *****\n");
        receiptArea.append("Player Name: " + player1 + (player2.isEmpty() ? "" : " & " + player2) + "\n");
        receiptArea.append("Game: " + game + "\n");
        receiptArea.append("Time Slot: " + slot + "\n");
        receiptArea.append("Total: rs." + price + "\n");
        receiptArea.append("\nPlease confirm your booking to proceed.");
        confirmButton.setEnabled(true);
    }

    private void confirmBooking() {
        String player1 = player1Field.getText().trim();
        String player2 = player2Field.isEnabled() ? player2Field.getText().trim() : "";
        String game = (String) gameDropdown.getSelectedItem();
        String slot = (String) slotDropdown.getSelectedItem();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("bookings.txt", true))) {
            bw.write(player1 + (player2.isEmpty() ? "" : " & " + player2) + "," + game + "," + slot + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, "Take a snapshot of your receipt and show it on the day of your slot along with the money mentioned. Thank you!");
        dispose();
        new PlayerDashboard(currentUser);
    }
}
