package project;
import java.io.*;
public class gameView {

    private static final String FILE_NAME = "added_games.txt";
    
    public static void initializeGames() {
        // Check if file exists and has content
        File file = new File(FILE_NAME);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Initializing default games...");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                // Single player games (5)
                writer.write("Call of Duty,1,15.99,10:00 AM - 12:00 PM,2:00 PM - 4:00 PM");
                writer.newLine();
                writer.write("Fortnite,1,12.99,9:00 AM - 11:00 AM,3:00 PM - 5:00 PM");
                writer.newLine();
                writer.write("Minecraft,1,9.99,11:00 AM - 1:00 PM,4:00 PM - 6:00 PM");
                writer.newLine();
                writer.write("GTA V,1,19.99,8:00 AM - 10:00 AM,5:00 PM - 7:00 PM");
                writer.newLine();
                writer.write("Cyberpunk 2077,1,24.99,12:00 PM - 2:00 PM,6:00 PM - 8:00 PM");
                writer.newLine();
                
                // Two player games (5)
                writer.write("FIFA 23,2,29.99,9:30 AM - 11:30 AM,1:30 PM - 3:30 PM");
                writer.newLine();
                writer.write("Mario Kart,2,19.99,10:30 AM - 12:30 PM,2:30 PM - 4:30 PM");
                writer.newLine();
                writer.write("Mortal Kombat,2,24.99,11:30 AM - 1:30 PM,3:30 PM - 5:30 PM");
                writer.newLine();
                writer.write("Street Fighter,2,22.99,12:30 PM - 2:30 PM,4:30 PM - 6:30 PM");
                writer.newLine();
                writer.write("Tekken 8,2,34.99,1:30 PM - 3:30 PM,5:30 PM - 7:30 PM");
                writer.newLine();
                
                System.out.println("Default games initialized successfully!");
            } catch (IOException e) {
                System.out.println("Error initializing games: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Games file already exists, skipping initialization.");
        }
    }
}
