import java.util.ArrayList;
import java.util.Scanner;

public class Draft {

    private static String CPU_TEAM_1 = "Houston Heat";
    private static String CPU_TEAM_2 = "Tampa Bay Damage";
    private static String CPU_TEAM_3 = "San Diego Dynasty";

    public static ArrayList<Team> runDraft(Scanner scanner, ArrayList<Player> pool) {

        System.out.println();
        System.out.println("  +-------------------------------------------+");
        System.out.println("  |       WELCOME TO OFF THE BREAK            |");
        System.out.println("  |      Fantasy NXL Paintball Draft          |");
        System.out.println("  +-------------------------------------------+");
        System.out.println();

        System.out.print("  Enter your team name: ");
        String teamName = scanner.nextLine().trim();
        if (teamName.isEmpty()) {
            teamName = "My Team";
        }

        Team playerTeam = new HumanTeam(teamName);
        Team cpu1       = new AITeam(CPU_TEAM_1);
        Team cpu2       = new AITeam(CPU_TEAM_2);
        Team cpu3       = new AITeam(CPU_TEAM_3);

        ArrayList<Team> allTeams = new ArrayList<Team>();
        allTeams.add(playerTeam);
        allTeams.add(cpu1);
        allTeams.add(cpu2);
        allTeams.add(cpu3);

        ArrayList<Player> available = new ArrayList<Player>();
        for (int i = 0; i < pool.size(); i++) {
            available.add(pool.get(i));
        }

        System.out.println();
        System.out.println("  Draft order: YOU -> Houston Heat -> Tampa Bay Damage -> San Diego Dynasty");
        System.out.println("  5 rounds. 30 players in the pool. Build your squad.");
        System.out.println("  TIP: Balance FRONT players (movers) and BACK players (gunners).");
        System.out.println();

        // Run 5 rounds of drafting
        for (int round = 1; round <= 5; round++) {

            System.out.println("  -----------------------------------------------");
            System.out.println("  ROUND " + round + " OF 5  (" + available.size() + " players remaining)");
            System.out.println("  -----------------------------------------------");

            // Each team picks one player per round in order
            for (int teamIndex = 0; teamIndex < allTeams.size(); teamIndex++) {
                Team currentTeam = allTeams.get(teamIndex);

                // chooseDraftPick is polymorphic
                // HumanTeam shows a menu and waits for input
                // AITeam just picks automatically
                Player pick = currentTeam.chooseDraftPick(available, scanner);

                // Add the picked player to that team's roster
                currentTeam.addPlayer(pick);

                // Print who picked who
                if (currentTeam.isHuman() == true) {
                    System.out.println("  You drafted: " + pick.getName() + " [" + pick.getRoleLabel() + "]");
                    System.out.println();
                } else {
                    System.out.println("  " + currentTeam.getName() + " selects: "
                            + pick.getName()
                            + " (" + pick.getRealTeam() + ")"
                            + " [" + pick.getRoleLabel() + "]"
                            + " OVR " + pick.getOverall());
                }
            }

            System.out.println();
        }

        // Show the player their finished roster
        System.out.println();
        System.out.println("  +----------------------------------------------+");
        System.out.println("  |              DRAFT COMPLETE                  |");
        System.out.println("  +----------------------------------------------+");
        System.out.println();
        System.out.println("  YOUR ROSTER:");
        System.out.println("  ------------------------------------------------------------------");
        playerTeam.printRoster();
        System.out.println();

        return allTeams;
    }
}
