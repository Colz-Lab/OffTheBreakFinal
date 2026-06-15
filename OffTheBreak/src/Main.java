import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Print the title screen
        printTitleScreen();

        System.out.print("  Press ENTER to start...");
        scanner.nextLine();

        // Make the pool of 30 players for the draft
        ArrayList<Player> playerPool = PlayerPool.generatePool();

        // Run the draft - this returns 4 teams with their rosters filled
        ArrayList<Team> teams = Draft.runDraft(scanner, playerPool);

        // Start the tournament with all 4 teams
        Tournament tournament = new Tournament(teams, scanner);
        tournament.run();

        scanner.close();
    }

    // Prints the game title to the console
    private static void printTitleScreen() {
        System.out.println();
        System.out.println("  ================================================");
        System.out.println("  ");
        System.out.println("       OFF THE BREAK");
        System.out.println("       Fantasy NXL Paintball");
        System.out.println("  ");
        System.out.println("  ================================================");
        System.out.println();
        System.out.println("  Draft a team of 5 real NXL players.");
        System.out.println("  Call plays. Eliminate opponents. Win the tournament.");
        System.out.println();
    }
}
