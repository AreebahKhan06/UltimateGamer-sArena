//game
package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

class Game implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private int maxPlayers;
    private double price;
    private HashMap<String, ArrayList<String>> availableTimeSlots; // Date -> List of available time slots

    public Game(String title, int maxPlayers, double price) {
        this.title = title;
        this.maxPlayers = maxPlayers;
        this.price = price;
        this.availableTimeSlots = new HashMap<>();
    }

    // Add available time slots for a specific date
    public void addTimeSlots(String date, ArrayList<String> timeSlots) {
        availableTimeSlots.put(date, timeSlots);
    }

    // Remove a time slot when booked
    public void removeTimeSlot(String date, String timeSlot) {
        if (availableTimeSlots.containsKey(date)) {
            availableTimeSlots.get(date).remove(timeSlot);
        }
    }

    // Add a time slot back when booking is canceled
    public void addTimeSlot(String date, String timeSlot) {
        if (availableTimeSlots.containsKey(date)) {
            if (!availableTimeSlots.get(date).contains(timeSlot)) {
                availableTimeSlots.get(date).add(timeSlot);
            }
        } else {
            ArrayList<String> slots = new ArrayList<>();
            slots.add(timeSlot);
            availableTimeSlots.put(date, slots);
        }
    }

    // Getters
    public String getTitle() { return title; }
    public int getMaxPlayers() { return maxPlayers; }
    public double getPrice() { return price; }
    public HashMap<String, ArrayList<String>> getAvailableTimeSlots() { return availableTimeSlots; }
}