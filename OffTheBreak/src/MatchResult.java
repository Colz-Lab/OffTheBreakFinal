// MatchResult stores what happened in a match
// It gets returned by MatchEngine after a match is finished
public class MatchResult {

    // Did team A win?
    public boolean teamAWon;

    // How many eliminations did each team get
    public int eliminationsA;
    public int eliminationsB;

    // How many rounds did each team win
    public int roundWinsA;
    public int roundWinsB;

    // Constructor to set everything up
    public MatchResult(boolean teamAWon, int eliminationsA, int eliminationsB, int roundWinsA, int roundWinsB) {
        this.teamAWon = teamAWon;
        this.eliminationsA = eliminationsA;
        this.eliminationsB = eliminationsB;
        this.roundWinsA = roundWinsA;
        this.roundWinsB = roundWinsB;
    }

    // Calculates the total points team A earned from this match
    // Win = 5 points, each elimination = 2 points, each alive player at end = 1 point
    public int pointsForA(int rosterSize) {
        int totalPoints = 0;

        // Add win bonus if team A won
        if (teamAWon == true) {
            totalPoints = totalPoints + 5;
        }

        // Add 2 points for each elimination team A got
        totalPoints = totalPoints + (eliminationsA * 2);

        // Add 1 point for each player that team A did not lose
        // (rosterSize minus how many of A's players were eliminated)
        int playersStillAlive = rosterSize - eliminationsB;
        if (playersStillAlive < 0) {
            playersStillAlive = 0;
        }
        totalPoints = totalPoints + playersStillAlive;

        return totalPoints;
    }

    // Same calculation but for team B
    public int pointsForB(int rosterSize) {
        int totalPoints = 0;

        if (teamAWon == false) {
            totalPoints = totalPoints + 5;
        }

        totalPoints = totalPoints + (eliminationsB * 2);

        int playersStillAlive = rosterSize - eliminationsA;
        if (playersStillAlive < 0) {
            playersStillAlive = 0;
        }
        totalPoints = totalPoints + playersStillAlive;

        return totalPoints;
    }
}
