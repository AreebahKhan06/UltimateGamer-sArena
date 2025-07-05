package project;

//update game
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class UpdateGame extends JFrame {
    private JTextField oldGameNameField, nameField, playersField, priceField, slot1Field, slot2Field;
    private JButton searchButton, updateButton;
    private JPanel updatePanel;
    private String oldGameName;

    public UpdateGame() {
        setTitle("Update Game - Ultimate Gamer's Arena");
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(0, 50, 50));

        // Top Panel - Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        searchPanel.setBackground(new Color(0, 50, 50));
        //searchPanel.setBorder(BorderFactory.createTitledBorder("Search Game"));
        searchPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN), "Search Game"));
((javax.swing.border.TitledBorder) searchPanel.getBorder()).setTitleColor(Color.WHITE);
        JLabel searchLabel = new JLabel("Enter Old Game Name:");
        searchLabel.setForeground(Color.WHITE);
        searchPanel.add(searchLabel);
        oldGameNameField = new JTextField(15);
        searchPanel.add(oldGameNameField);
        searchButton = new JButton("Search");
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Center Panel - Details
        updatePanel = new JPanel(new GridLayout(6, 2, 10, 10));
        updatePanel.setBackground(new Color(0, 50, 50));
       // updatePanel.setBorder(BorderFactory.createTitledBorder("Update Game Details"));
       updatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN), "Update Game Details"));
       ((javax.swing.border.TitledBorder) updatePanel.getBorder()).setTitleColor(Color.WHITE);
        nameField = new JTextField();
        playersField = new JTextField();
        priceField = new JTextField();
        slot1Field = new JTextField();
        slot2Field = new JTextField();

        addLabelAndField(updatePanel, "Game Name:", nameField);
        addLabelAndField(updatePanel, "Players (max 2):", playersField);
        addLabelAndField(updatePanel, "Price:", priceField);
        addLabelAndField(updatePanel, "Time Slot 1:", slot1Field);
        addLabelAndField(updatePanel, "Time Slot 2:", slot2Field);

        setFieldsEnabled(false);
        add(updatePanel, BorderLayout.CENTER);

        // Bottom Panel - Update Button
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(new Color(0, 50, 50));
        updateButton = new JButton("Update Game");
        updateButton.setEnabled(false);
        bottomPanel.add(updateButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        searchButton.addActionListener(e -> {
            oldGameName = oldGameNameField.getText().trim();
            if (searchAndFill(oldGameName)) {
                setFieldsEnabled(true);
                updateButton.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Game not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(e -> updateGame());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void setSize(int i, int j) {
        super.setSize(i, j);
    }

    @Override
    public void setTitle(String string) {
        super.setTitle(string);
    }

    private void addLabelAndField(JPanel panel, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.CYAN);
        panel.add(label);
        panel.add(field);
    }

    private void setFieldsEnabled(boolean enabled) {
        nameField.setEnabled(enabled);
        playersField.setEnabled(enabled);
        priceField.setEnabled(enabled);
        slot1Field.setEnabled(enabled);
        slot2Field.setEnabled(enabled);
    }

    private boolean searchAndFill(String gameName) {
        try (BufferedReader reader = new BufferedReader(new FileReader("added_games.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(gameName)) {
                    nameField.setText(parts[0]);
                    playersField.setText(parts.length > 1 ? parts[1] : "");
                    priceField.setText(parts.length > 2 ? parts[2] : "");
                    slot1Field.setText(parts.length > 3 ? parts[3] : "");
                    slot2Field.setText(parts.length > 4 ? parts[4] : "");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateGame() {
        List<String> updatedGames = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("added_games.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equalsIgnoreCase(oldGameName)) {
                    String newLine = nameField.getText().trim() + "," +
                            playersField.getText().trim() + "," +
                            priceField.getText().trim() + "," +
                            slot1Field.getText().trim() + "," +
                            slot2Field.getText().trim();
                    updatedGames.add(newLine);
                    updated = true;
                } else {
                    updatedGames.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("added_games.txt"))) {
                for (String g : updatedGames) {
                    writer.write(g);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Game updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Game not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}