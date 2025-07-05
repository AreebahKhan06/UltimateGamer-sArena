
 package project;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Base abstract class
abstract class User implements Serializable {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean authenticate(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public abstract String getRole();

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}

// Subclasses
class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}

class Staff extends User {
    public Staff(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Staff";
    }
}

class Player extends User {
    public Player(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Player";
    }
}

// File manager using serialization
class UserManager {
    public static final String USER_FILE = "users.dat";

    // Saves a single user
    public static void saveUser(User user) {
        ArrayList<User> users = loadUsers();
        users.add(user);
        saveAllUsers(users);
    }

    // Saves the entire user list
    private static void saveAllUsers(ArrayList<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads all users
    public static ArrayList<User> loadUsers() {
        File file = new File(USER_FILE);
        ArrayList<User> users = new ArrayList<>();

        if (!file.exists()) {
            // Create default users
            users.add(new Admin("admin", "Admin123"));
            users.add(new Staff("staff", "Staff123"));
            users.add(new Player("player", "Player123"));
            saveAllUsers(users);
            return users;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USER_FILE))) {
            users = (ArrayList<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return users;
    }

    public static boolean usernameExists(String username) {
        for (User user : loadUsers()) {
            if (user.getUsername().equals(username)) return true;
        }
        return false;
    }

    public static User authenticateUser(String username, String password, String role) {
        for (User user : loadUsers()) {
            if (user.getRole().equals(role) && user.authenticate(username, password)) {
                return user;
            }
        }
        return null;
    }
}

// GUI login system
class LoginSystemWithImage extends JFrame {
    private JComboBox<String> roleDropdown;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;

    public LoginSystemWithImage() {
        setTitle("Ultimate Gamer's Arena");
        setSize(900, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Image
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Image img = new ImageIcon("C:\\Users\\Lenovo\\Desktop\\b.jpeg").getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Login Panel
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        loginPanel.setBackground(new Color(10, 50, 50));

        JLabel titleLabel = new JLabel("Ultimate Gamer's Arena", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        loginPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(10, 50, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel roleLabel = new JLabel("Select Role:");
        roleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(roleLabel, gbc);

        roleDropdown = new JComboBox<>(new String[]{"Player", "Staff", "Admin"});
        gbc.gridx = 1;
        formPanel.add(roleDropdown, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(12);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(12);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(10, 50, 50));
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);

        loginPanel.add(formPanel, BorderLayout.CENTER);
        loginPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, loginPanel);
        splitPane.setDividerLocation(450);
        splitPane.setEnabled(false);

        add(splitPane);
        setVisible(true);

        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> handleSignup());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String role = (String) roleDropdown.getSelectedItem();

        User user = UserManager.authenticateUser(username, password, role);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Welcome, " + user.getRole() + "!");
            dispose();

            switch (role) {
                case "Admin" -> new AdminDashboard(username);
                case "Staff" -> new StaffInterface();
                case "Player" -> new PlayerDashboard(username);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleSignup() {
        String username = usernameField.getText().trim();
        String password = String.valueOf(passwordField.getPassword());
        String role = (String) roleDropdown.getSelectedItem();

        if (!username.matches(".*[a-zA-Z].*")) {
            JOptionPane.showMessageDialog(this, "Username must contain letters!", "Invalid Username", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.isEmpty() || !Character.isUpperCase(password.charAt(0))) {
            JOptionPane.showMessageDialog(this, "Password must start with a capital letter!", "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (UserManager.usernameExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists!", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = switch (role) {
            case "Admin" -> new Admin(username, password);
            case "Staff" -> new Staff(username, password);
            default -> new Player(username, password);
        };

        UserManager.saveUser(newUser);
        JOptionPane.showMessageDialog(this, "Signup successful! You can now login.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gameView.initializeGames();
            new LoginSystemWithImage();
        });
    }
}
