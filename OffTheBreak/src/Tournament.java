import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

// Tournament runs the full game from preliminary stage through to the grand final
public class Tournament {

    private ArrayList<Team> teams;
    private Scanner scanner;

    // Constructor stores the teams and scanner we need
    public Tournament(ArrayList<Team> teams, Scanner scanner) {
        this.teams = teams;
        this.scanner = scanner;
    }

    // Runs the whole tournament from start to finish
    public void run() {

        // Find the human team so we know who the player is
        Team playerTeam = getPlayerTeam();

        System.out.println();
        System.out.println("  +----------------------------------------------+");
        System.out.println("  |         PRELIMINARY STAGE BEGINS             |");
        System.out.println("  |   Every team plays every other team once.    |");
        System.out.println("  +----------------------------------------------+");
        System.out.println();
        System.out.println("  Points system:");
        System.out.println("  Win = 5 points");
        System.out.println("  Each elimination = 2 points");
        System.out.println("  Each player alive at round end = 1 point");
        System.out.println();

        waitForEnter("  Press ENTER to begin...");

        // --- AI vs AI MATCHES ---
        // The three AI teams play each other off screen
        // We do not show any commentary for these matches

        // Build a list of just the AI teams
        ArrayList<Team> cpuTeams = new ArrayList<Team>();
        for (int i = 0; i < teams.size(); i++) {
            Team t = teams.get(i);
            if (t.isHuman() == false) {
                cpuTeams.add(t);
            }
        }

        System.out.println();
        System.out.println("  [Simulating AI vs AI matches behind the scenes...]");
        System.out.println();

        // Every AI team plays every other AI team once
        for (int i = 0; i < cpuTeams.size(); i++) {
            for (int j = i + 1; j < cpuTeams.size(); j++) {
                Team teamA = cpuTeams.get(i);
                Team teamB = cpuTeams.get(j);

                System.out.println("  * " + teamA.getName() + " vs " + teamB.getName() + "...");

                // Pause for effect
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    // Do nothing
                }

                MatchEngine.simulateAIMatch(teamA, teamB);
            }
        }

        System.out.println();
        System.out.println("  AI matches done.");
        System.out.println();

        // --- PLAYER MATCHES ---
        // The player now plays one visible match against each AI team

        for (int i = 0; i < cpuTeams.size(); i++) {
            Team cpuOpponent = cpuTeams.get(i);

            System.out.println();
            System.out.println("  ==============================================");
            System.out.println("  YOUR MATCH: " + playerTeam.getName() + " vs " + cpuOpponent.getName());
            System.out.println("  ==============================================");

            waitForEnter("  Press ENTER to start this match...");

            // Simulate the match with commentary visible
            MatchResult result = MatchEngine.simulateMatch(playerTeam, cpuOpponent, scanner, true);

            // Calculate points earned
            int pointsForPlayer = result.pointsForA(5);
            int pointsForCpu    = result.pointsForB(5);

            // Add the points to each team
            playerTeam.addPoints(pointsForPlayer);
            cpuOpponent.addPoints(pointsForCpu);

            // Record the win and eliminations
            if (result.teamAWon == true) {
                playerTeam.addWin();
            } else {
                cpuOpponent.addWin();
            }

            playerTeam.addEliminations(result.eliminationsA);
            cpuOpponent.addEliminations(result.eliminationsB);

            // Show the points awarded after the match
            System.out.println();
            System.out.println("  Points awarded this match:");
            System.out.println("    " + playerTeam.getName() + ": +" + pointsForPlayer + " points");
            System.out.println("    " + cpuOpponent.getName()  + ": +" + pointsForCpu    + " points");

            waitForEnter("  Press ENTER to continue...");
        }

        // --- STANDINGS ---
        // Sort all 4 teams by points to get the standings
        // Collections.sort works here because Team implements Comparable
        ArrayList<Team> standings = new ArrayList<Team>();
        for (int i = 0; i < teams.size(); i++) {
            standings.add(teams.get(i));
        }
        Collections.sort(standings);

        System.out.println();
        printStandings(standings);

        // The top 2 teams advance to the grand final
        Team finalist1   = standings.get(0);
        Team finalist2   = standings.get(1);
        Team eliminated3 = standings.get(2);
        Team eliminated4 = standings.get(3);

        System.out.println();
        System.out.println("  ADVANCING TO THE GRAND FINAL:");
        System.out.println("  1st: " + finalist1.getName() + " -> FINALS");
        System.out.println("  2nd: " + finalist2.getName() + " -> FINALS");
        System.out.println("  3rd: " + eliminated3.getName() + " -> ELIMINATED");
        System.out.println("  4th: " + eliminated4.getName() + " -> ELIMINATED");
        System.out.println();

        waitForEnter("  Press ENTER to play the GRAND FINAL...");

        // --- GRAND FINAL ---
        runGrandFinal(finalist1, finalist2);
    }

    // Runs the grand final between the top two teams
    private void runGrandFinal(Team finalist1, Team finalist2) {

        System.out.println();
        System.out.println("  ##############################################");
        System.out.println("  ##            GRAND FINAL                  ##");
        System.out.println("  ##  " + finalist1.getName() + " vs " + finalist2.getName());
        System.out.println("  ##############################################");
        System.out.println();

        // Check if the player's team is in the final
        boolean playerIsInFinal = finalist1.isHuman() || finalist2.isHuman();

        if (playerIsInFinal == true) {
            // The player is in the final so we run a visible match

            // Figure out which team is the player and which is the CPU
            Team humanTeam;
            Team cpuTeam;

            if (finalist1.isHuman() == true) {
                humanTeam = finalist1;
                cpuTeam   = finalist2;
            } else {
                humanTeam = finalist2;
                cpuTeam   = finalist1;
            }

            // Run the match with the human team as team A
            MatchResult finalResult = MatchEngine.simulateMatch(humanTeam, cpuTeam, scanner, true);

            System.out.println();

            if (finalResult.teamAWon == true) {
                // Player won!
                System.out.println("  ##############################################");
                System.out.println("  ##                                          ##");
                System.out.println("  ##   CONGRATULATIONS! YOU WIN!              ##");
                System.out.println("  ##   " + humanTeam.getName() + " are champions!");
                System.out.println("  ##                                          ##");
                System.out.println("  ##############################################");
            } else {
                // Player lost the final
                System.out.println("  +------------------------------------------+");
                System.out.println("  |  Tough loss. You made it to the final.   |");
                System.out.println("  |  " + cpuTeam.getName() + " take the title.");
                System.out.println("  +------------------------------------------+");
            }

        } else {
            // Both finalists are AI teams so we simulate it off screen
            System.out.println("  [Simulating the AI grand final...]");

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                // Do nothing
            }

            MatchEngine.simulateAIMatch(finalist1, finalist2);

            // Sort the two finalists to find the winner
            ArrayList<Team> finalists = new ArrayList<Team>();
            finalists.add(finalist1);
            finalists.add(finalist2);
            Collections.sort(finalists);

            Team champion = finalists.get(0);

            System.out.println();
            System.out.println("  " + champion.getName() + " are the champions!");
            System.out.println();
            System.out.println("  You did not make it to the final this time. Better luck next season.");
        }

        System.out.println();
        System.out.println("  Thanks for playing OFF THE BREAK.");
        System.out.println("  ----------------------------------------------");
    }

    // Prints the standings table sorted by points
    private void printStandings(ArrayList<Team> sortedTeams) {

        System.out.println("  +----------------------------------------------------+");
        System.out.println("  |         PRELIMINARY STAGE STANDINGS                |");
        System.out.println("  +------+----------------------------+----------------+");
        System.out.println("  | Rank | Team                       | Points         |");
        System.out.println("  +------+----------------------------+----------------+");

        for (int i = 0; i < sortedTeams.size(); i++) {
            Team t = sortedTeams.get(i);
            int rank = i + 1;

            String teamLabel = t.getName();
            if (t.isHuman() == true) {
                teamLabel = teamLabel + " (YOU)";
            }

            System.out.printf("  | %-4d | %-26s | %-14d |%n", rank, teamLabel, t.getPoints());
        }

        System.out.println("  +------+----------------------------+----------------+");
    }

    // Searches the team list for the human controlled team and returns it
    private Team getPlayerTeam() {
        for (int i = 0; i < teams.size(); i++) {
            Team t = teams.get(i);
            if (t.isHuman() == true) {
                return t;
            }
        }
        // Fallback in case something goes wrong
        return teams.get(0);
    }

    // Waits for the player to press Enter before continuing
    private void waitForEnter(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
    }
}
