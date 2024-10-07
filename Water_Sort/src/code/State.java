package code;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class State {
    private String[] bottles; // Array of bottle states, each represented as a string of colors
    private final int capacity; // fixed number that cannot change

    public State(String[] bottles, int capacity) {
        this.bottles = bottles;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bottles.length; i++) {
            sb.append("Bottle ").append(i).append(": ").append(bottles[i]).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Arrays.equals(bottles, state.bottles); // assuming bottles is an array or similar structure
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bottles); // hash code based on the bottles array
    }

    public String[] getBottles() {
        return bottles;
    }

    // checking if we reached the goal or not
    
    public boolean isGoalState() {
        for (String bottle : bottles) {
            String bottleWithoutEmpty = bottle.replaceAll("e", ""); // Remove empty spaces
            if (!bottleWithoutEmpty.isEmpty() && !isUniform(bottleWithoutEmpty)) {
                return false; // Not a goal state if not uniform
            }
        }
        return true; // All bottles are either empty or have uniform color
    }
    
    
      // checking if we have a bottle with one single color
    
    private boolean isUniform(String bottle) {
        char firstColor = bottle.charAt(0);
        return bottle.chars().allMatch(c -> c == firstColor);
    }

    // Checks if it's possible to pour from bottle i to bottle j
    public boolean checkPour(int i, int j) {
        System.out.println("Checking pour from bottle " + i + " to bottle " + j);

        String fromBottle = bottles[i];
        String toBottle = bottles[j];

        // Condition 1: Bottle i should not be empty
        if (fromBottle.isEmpty() || fromBottle.replaceAll("e", "").isEmpty()) {
            System.out.println("Cannot pour from bottle " + i + ": it's empty.");
            return false;
        }

        // Get the top colors of the bottles (ignoring 'e' for empty layers)
        char topFrom = getTopColor(fromBottle);
        char topTo = getTopColor(toBottle);

        System.out.println("From bottle top color: " + topFrom);
        System.out.println("To bottle top color: " + topTo);

        // Condition 2: Bottle j must have at least one empty space
        if (toBottle.replaceAll("e", "").length() == capacity) {
            System.out.println("Cannot pour into bottle " + j + ": it's full.");
            return false;
        }

        // Condition 3: Either bottle j is empty or the top colors match
        if (topTo == 'e' || topFrom == topTo) {
            System.out.println("Pouring allowed.");
            return true;
        }

        System.out.println("Cannot pour: top colors do not match.");
        return false;
    }

    // Helper method to get the topmost non-empty color in the bottle
    
    private char getTopColor(String bottle) {
        for (int i = bottle.length() - 1; i >= 0; i--) {
            if (bottle.charAt(i) != 'e') {
                return bottle.charAt(i);
            }
        }
        return 'e'; // Return 'e' if the bottle is completely empty
    }

    // Pour from bottle i to bottle j
    
    public State pour(int m, int n) {
        String fromBottle = bottles[m];
        String toBottle = bottles[n];

        // Check if pouring is allowed
        if (!checkPour(m, n)) {
            return null; // Pour not allowed
        }

        // Get the top color of the source bottle
        char topColor = getTopColor(fromBottle);
        int layersToPour = 0;

        // Count how many layers of the top color are in the source bottle
        for (int i = fromBottle.length() - 1; i >= 0; i--) {
            if (fromBottle.charAt(i) == topColor) {
                layersToPour++;
            } else if (fromBottle.charAt(i) != 'e') { // Stop counting when a different color is encountered
                break;
            }
        }

        // Count empty spaces in the target bottle
        int emptySpaces = (int) toBottle.chars().filter(c -> c == 'e').count();

        // Determine how many layers can be poured based on empty spaces
        layersToPour = Math.min(layersToPour, emptySpaces); // Pour up to the number of empty spaces

        // Debugging output for layers to pour
        System.out.println("Pouring " + layersToPour + " layer(s) of color " + topColor + " from bottle " + m + " to bottle " + n);

        // Prepare new states for the bottles
        StringBuilder newFromBottle = new StringBuilder(fromBottle);
        StringBuilder newToBottle = new StringBuilder(toBottle);

        // Pour the calculated number of layers into the target bottle
        for (int j = 0; j < layersToPour; j++) {
            // Remove the top layer from the source bottle
            for (int k = newFromBottle.length() - 1; k >= 0; k--) {
                if (newFromBottle.charAt(k) == topColor) {
                    newFromBottle.setCharAt(k, 'e'); // Change the top layer to 'e'
                    break; // Remove one layer at a time
                }
            }

            // Find the first empty space in the target bottle
            for (int k = newToBottle.length() - 1; k >= 0; k--) {
                if (newToBottle.charAt(k) == 'e') {
                    newToBottle.setCharAt(k, topColor); // Set the empty space with the top color
                    break;
                }
            }
        }

        // Create new bottles array with updated states
        String[] newBottles = bottles.clone();
        newBottles[m] = newFromBottle.toString();
        newBottles[n] = newToBottle.toString();

        // Return new state with updated bottles
        return new State(newBottles, capacity);
    }
    public String visualize() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bottles.length; i++) {
            sb.append("Bottle ").append(i).append(": ").append(bottles[i]).append("\n");
        }
        return sb.toString();
    }

}
