import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

// HumanTeam extends Team
// This is the team controlled by the person playing the game
// It asks the player to make choices instead of deciding automatically
public class HumanTeam extends Team {

    public HumanTeam(String name) {
        super(name);
    }

    // This team is always human controlled
    public boolean isHuman() {
        return true;
    }

    // Shows the player a list of available players and lets them pick one
    // The list is sorted by overall rating so the best players show at the top
    public Player chooseDraftPick(ArrayList<Player> available, Scanner scanner) {

        // Make a copy of the list so we can sort it without changing the original
        ArrayList<Player> sortedList = new ArrayList<Player>();
        for (int i = 0; i < available.size(); i++) {
            sortedList.add(available.get(i));
        }

        // Sort the list by overall rating from highest to lowest
        // We use an anonymous Comparator class to define how to compare two players
        Collections.sort(sortedList, new Comparator<Player>() {
            public int compare(Player playerA, Player playerB) {
                if (playerA.getOverall() > playerB.getOverall()) {
                    return -1; // playerA goes first
                } else if (playerA.getOverall() < playerB.getOverall()) {
                    return 1;  // playerB goes first
                } else {
                    return 0;
                }
            }
        });

        // Print the sorted list so the player can see their options
        System.out.println("  Available players (sorted by OVR):");
        System.out.println("  ------------------------------------------------------------------");
        for (int i = 0; i < sortedList.size(); i++) {
            int displayNumber = i + 1;
            System.out.println("  [" + displayNumber + "] " + sortedList.get(i));
        }
        System.out.println("  ------------------------------------------------------------------");

        // Show the player their current roster if they have already picked someone
        if (roster.size() > 0) {
            System.out.println("  Your current roster:");
            for (int i = 0; i < roster.size(); i++) {
                Player p = roster.get(i);
                System.out.println("       " + p.getName() + " [" + p.getRoleLabel() + "]  GUN:" + p.getGunSkill() + "  AGI:" + p.getAgility());
            }
        }

        System.out.println();
        System.out.print("  Pick your player (1-" + sortedList.size() + "): ");

        // Keep asking until we get a valid number
        int choice = 0;
        boolean validInput = false;
        while (validInput == false) {
            try {
                String inputText = scanner.nextLine().trim();
                choice = Integer.parseInt(inputText);
                if (choice >= 1 && choice <= sortedList.size()) {
                    validInput = true;
                } else {
                    System.out.print("  Please enter a number between 1 and " + sortedList.size() + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("  That is not a number. Try again: ");
            }
        }

        // Get the player they picked from the sorted list
        Player pickedPlayer = sortedList.get(choice - 1);

        // Remove them from the original available list so nobody else can pick them
        available.remove(pickedPlayer);

        return pickedPlayer;
    }

    // Shows the 6 play options and lets the player pick one
    public Play choosePlay(Scanner scanner) {
        Play[] allPlays = Play.values();

        System.out.println();
        System.out.println("  +-- CALL YOUR PLAY --------------------------------------------------+");
        for (int i = 0; i < allPlays.length; i++) {
            int displayNumber = i + 1;
            System.out.println("  | [" + displayNumber + "] " + allPlays[i].getDisplayName() + " - " + allPlays[i].getDescription());
        }
        System.out.println("  +--------------------------------------------------------------------+");
        System.out.print("  Your call (1-6): ");

        // Keep asking until we get a number from 1 to 6
        int choice = 0;
        boolean validInput = false;
        while (validInput == false) {
            try {
                String inputText = scanner.nextLine().trim();
                choice = Integer.parseInt(inputText);
                if (choice >= 1 && choice <= 6) {
                    validInput = true;
                } else {
                    System.out.print("  Enter a number from 1 to 6: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("  That is not a number. Try again: ");
            }
        }

        return allPlays[choice - 1];
    }
}
