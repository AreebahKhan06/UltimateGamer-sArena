package project;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class StaffOperations {
    private static final String FILE_NAME = "staff_games.txt";

    public static void addGame(String gameName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(gameName);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> loadGames() {
        ArrayList<String> games = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                games.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return games;
    }

    public static void updateGame(String oldGame, String newGame) {
        ArrayList<String> games = loadGames();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String game : games) {
                if (game.equals(oldGame)) {
                    writer.write(newGame);
                } else {
                    writer.write(game);
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteGame(String gameName) {
        ArrayList<String> games = loadGames();
        games.removeIf(game -> game.equals(gameName));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String game : games) {
                writer.write(game);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class StaffInterface extends JFrame {

    public StaffInterface() {
        setTitle("Staff Panel - Ultimate Gamer's Arena");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(10, 50, 50));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);

        JLabel title = new JLabel("STAFF");
        title.setFont(new Font("Algerian", Font.BOLD, 24));
        title.setForeground(new Color(255, 255, 255));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(title, gbc);

        JButton addButton = createButton("ADD GAME");
        JButton updateButton = createButton("UPDATE DETAILS");
        JButton deleteButton = createButton("DELETE GAME");
        JButton logoutButton = createButton("LOG OUT");

        gbc.gridy++;
        add(addButton, gbc);
        gbc.gridy++;
        add(updateButton, gbc);
        gbc.gridy++;
        add(deleteButton, gbc);
        gbc.gridy++;
        add(logoutButton, gbc);

        addButton.addActionListener(e -> {
            dispose(); // Close Staff Interface
            new AddGame(); // Open AddGame Panel
        });

        updateButton.addActionListener(e -> {
           new UpdateGame();
        });

        deleteButton.addActionListener(e -> {
            new DeleteGame();
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginSystemWithImage(); // Go back to login page
        });

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 80, 80));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }
}