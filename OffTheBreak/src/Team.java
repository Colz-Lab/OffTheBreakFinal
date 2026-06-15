import java.util.ArrayList;
import java.util.Scanner;

// Team is the abstract base class for HumanTeam and AITeam
// It holds all the shared data that every team needs
// It implements Comparable so we can sort teams by points using Collections.sort()
public abstract class Team implements Comparable<Team> {

    // Basic team info
    protected String name;
    protected ArrayList<Player> roster;
    protected int points;
    protected int wins;
    protected int eliminations;

    // Set up a new team with no players and zero points
    public Team(String name) {
        this.name = name;
        this.roster = new ArrayList<Player>();
        this.points = 0;
        this.wins = 0;
        this.eliminations = 0;
    }

    // These methods must be implemented differently by HumanTeam and AITeam
    public abstract Player chooseDraftPick(ArrayList<Player> available, Scanner scanner);
    public abstract Play choosePlay(Scanner scanner);
    public abstract boolean isHuman();

    // compareTo is required by Comparable
    // We sort by points descending so the team with the most points comes first
    public int compareTo(Team otherTeam) {
        if (this.points > otherTeam.points) {
            return -1; // this team comes first
        } else if (this.points < otherTeam.points) {
            return 1;  // other team comes first
        } else {
            return 0;  // same points, same position
        }
    }

    // Add a player to this team's roster
    public void addPlayer(Player p) {
        roster.add(p);
    }

    // Basic getters
    public String getName() {
        return name;
    }

    public ArrayList<Player> getRoster() {
        return roster;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    // Methods to add points, wins, and eliminations after a match
    public void addPoints(int pointsToAdd) {
        this.points = this.points + pointsToAdd;
    }

    public void addWin() {
        this.wins = this.wins + 1;
    }

    public void addEliminations(int elimsToAdd) {
        this.eliminations = this.eliminations + elimsToAdd;
    }

    // Returns a list of only the players who are still alive this round
    public ArrayList<Player> getAlivePlayers() {
        ArrayList<Player> alivePlayers = new ArrayList<Player>();
        for (int i = 0; i < roster.size(); i++) {
            Player p = roster.get(i);
            if (p.isAlive() == true) {
                alivePlayers.add(p);
            }
        }
        return alivePlayers;
    }

    // Resets everyone to alive at the start of each round
    public void resetAlive() {
        for (int i = 0; i < roster.size(); i++) {
            Player p = roster.get(i);
            p.setAlive(true);
        }
    }

    // Gets the average gun skill across the whole roster
    public int getAverageGunSkill() {
        if (roster.size() == 0) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < roster.size(); i++) {
            total = total + roster.get(i).getGunSkill();
        }
        int average = total / roster.size();
        return average;
    }

    // Gets the average agility across the whole roster
    public int getAverageAgility() {
        if (roster.size() == 0) {
            return 0;
        }
        int total = 0;
        for (int i = 0; i < roster.size(); i++) {
            total = total + roster.get(i).getAgility();
        }
        int average = total / roster.size();
        return average;
    }

    // Prints the full roster to the console
    public void printRoster() {
        System.out.println("  Team: " + name);
        System.out.println("  " + "------------------------------------------------------------------");
        for (int i = 0; i < roster.size(); i++) {
            System.out.println("  " + roster.get(i));
        }
    }
}
