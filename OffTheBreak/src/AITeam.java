import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

// AITeam extends Team
// This is a computer controlled team
// It makes its own decisions without any input from the player
public class AITeam extends Team {

    // We need a Random object to make random decisions
    private Random random;

    public AITeam(String name) {
        super(name);
        this.random = new Random();
    }

    // This team is never human controlled
    public boolean isHuman() {
        return false;
    }

    // The AI picks a player automatically
    // It sorts by overall rating and picks randomly from the top 3
    // This makes it good but not perfect
    public Player chooseDraftPick(ArrayList<Player> available, Scanner scanner) {

        // Make a copy of the list so we can sort it
        ArrayList<Player> sortedList = new ArrayList<Player>();
        for (int i = 0; i < available.size(); i++) {
            sortedList.add(available.get(i));
        }

        // Sort by overall rating highest to lowest
        Collections.sort(sortedList, new Comparator<Player>() {
            public int compare(Player playerA, Player playerB) {
                if (playerA.getOverall() > playerB.getOverall()) {
                    return -1;
                } else if (playerA.getOverall() < playerB.getOverall()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        // Pick randomly from the top 3 players
        // Math.min makes sure we don't go out of bounds if fewer than 3 players are left
        int randomIndex = random.nextInt(3);
        if (randomIndex >= sortedList.size()) {
            randomIndex = sortedList.size() - 1;
        }

        Player pickedPlayer = sortedList.get(randomIndex);

        // Remove from the original available list
        available.remove(pickedPlayer);

        return pickedPlayer;
    }

    // The AI picks a random play every round
    public Play choosePlay(Scanner scanner) {
        Play[] allPlays = Play.values();
        int randomIndex = random.nextInt(allPlays.length);
        return allPlays[randomIndex];
    }
}
