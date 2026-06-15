import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class MatchEngine {

    private static Random random = new Random();

    private static int ROUNDS_PER_MATCH = 5;

    // Runs a full match between two teams
    // If isVisible is true, commentary is printed to the console
    // Returns a MatchResult with all the details of what happened
    public static MatchResult simulateMatch(Team teamA, Team teamB, Scanner scanner, boolean isVisible) {

        int roundWinsA = 0;
        int roundWinsB = 0;
        int eliminationsA = 0;
        int eliminationsB = 0;

        if (isVisible == true) {
            System.out.println();
            System.out.println("  +--------------------------------------------+");
            System.out.println("  |  " + teamA.getName() + " vs " + teamB.getName());
            System.out.println("  +--------------------------------------------+");
        }

        for (int roundNumber = 1; roundNumber <= ROUNDS_PER_MATCH; roundNumber++) {

            // Reset everyone to alive at the start of each round
            teamA.resetAlive();
            teamB.resetAlive();

            // Each team chooses their play for this round
            // This is polymorphic - HumanTeam asks the player, AITeam picks randomly
            Play playA = teamA.choosePlay(scanner);
            Play playB = teamB.choosePlay(scanner);

            if (isVisible == true) {
                Commentary.roundStart(roundNumber);
                Commentary.announcePlay(playA.getDisplayName(), teamA.isHuman());
                Commentary.announcePlay(playB.getDisplayName(), teamB.isHuman());

                // Check if either team has a play advantage this round
                double multA = playA.getMultiplierAgainst(playB);
                double multB = playB.getMultiplierAgainst(playA);

                if (multA > multB) {
                    Commentary.playAdvantage(teamA.getName(), multA);
                } else if (multB > multA) {
                    Commentary.playAdvantage(teamB.getName(), multB);
                }

                System.out.println();
            }

            // Simulate all the 1v1 fights in this round
            // roundElims[0] = how many of teamA were eliminated
            // roundElims[1] = how many of teamB were eliminated
            int[] roundElims = simulateRound(teamA, teamB, playA, playB, isVisible);

            int elimsOnA = roundElims[0];
            int elimsOnB = roundElims[1];

            // Team A's eliminations = how many of team B were eliminated
            eliminationsA = eliminationsA + elimsOnB;
            eliminationsB = eliminationsB + elimsOnA;

            // Count how many players are still alive on each side
            int aliveA = teamA.getAlivePlayers().size();
            int aliveB = teamB.getAlivePlayers().size();

            // The team with more players alive wins the round
            // If it is tied, the team with more eliminations wins the round
            boolean teamAWinsRound;
            if (aliveA > aliveB) {
                teamAWinsRound = true;
            } else if (aliveB > aliveA) {
                teamAWinsRound = false;
            } else {
                // Tiebreak: who eliminated more this round
                if (elimsOnB >= elimsOnA) {
                    teamAWinsRound = true;
                } else {
                    teamAWinsRound = false;
                }
            }

            if (teamAWinsRound == true) {
                roundWinsA = roundWinsA + 1;
            } else {
                roundWinsB = roundWinsB + 1;
            }

            if (isVisible == true) {
                String roundWinnerName;
                if (teamAWinsRound == true) {
                    roundWinnerName = teamA.getName();
                } else {
                    roundWinnerName = teamB.getName();
                }
                Commentary.roundResult(roundWinnerName, aliveA, aliveB);
                System.out.println("  Score after round " + roundNumber + ": " + roundWinsA + " - " + roundWinsB);
            }
        }

        // The team with more round wins takes the match
        boolean teamAWinsMatch;
        if (roundWinsA > roundWinsB) {
            teamAWinsMatch = true;
        } else {
            teamAWinsMatch = false;
        }

        if (isVisible == true) {
            String winnerName;
            String loserName;
            int winnerRounds;
            int loserRounds;

            if (teamAWinsMatch == true) {
                winnerName = teamA.getName();
                loserName = teamB.getName();
                winnerRounds = roundWinsA;
                loserRounds = roundWinsB;
            } else {
                winnerName = teamB.getName();
                loserName = teamA.getName();
                winnerRounds = roundWinsB;
                loserRounds = roundWinsA;
            }

            Commentary.matchResult(winnerName, loserName, winnerRounds, loserRounds);
        }

        return new MatchResult(teamAWinsMatch, eliminationsA, eliminationsB, roundWinsA, roundWinsB);
    }

    // Simulates one round of 1v1 duels between players from each team
    // Returns int[]{elimsOnTeamA, elimsOnTeamB}
    private static int[] simulateRound(Team teamA, Team teamB, Play playA, Play playB, boolean isVisible) {

        int elimsOnA = 0;
        int elimsOnB = 0;

        // Get lists of alive players from each team
        ArrayList<Player> aliveOnA = teamA.getAlivePlayers();
        ArrayList<Player> aliveOnB = teamB.getAlivePlayers();

        // Get the play multipliers for this round
        double multA = playA.getMultiplierAgainst(playB);
        double multB = playB.getMultiplierAgainst(playA);

        // Print some movement commentary before the fighting starts
        if (isVisible == true) {
            ArrayList<Player> allAlivePlayers = new ArrayList<Player>();
            for (int i = 0; i < aliveOnA.size(); i++) {
                allAlivePlayers.add(aliveOnA.get(i));
            }
            for (int i = 0; i < aliveOnB.size(); i++) {
                allAlivePlayers.add(aliveOnB.get(i));
            }

            Collections.shuffle(allAlivePlayers);

            int moveCount = 2 + random.nextInt(3);
            for (int i = 0; i < moveCount && i < allAlivePlayers.size(); i++) {
                Commentary.playerMove(allAlivePlayers.get(i).getNickname());
            }
        }

        // Shuffle so matchups are random each round
        Collections.shuffle(aliveOnA);
        Collections.shuffle(aliveOnB);

        // Pair players up and simulate each 1v1 duel
        int numberOfDuels;
        if (aliveOnA.size() < aliveOnB.size()) {
            numberOfDuels = aliveOnA.size();
        } else {
            numberOfDuels = aliveOnB.size();
        }

        for (int i = 0; i < numberOfDuels; i++) {
            Player playerA = aliveOnA.get(i);
            Player playerB = aliveOnB.get(i);

            if (isVisible == true) {
                Commentary.duelOpener(playerA.getNickname(), playerB.getNickname());
            }

            // --- COMBAT FORMULA ---
            // p = probability that player A hits player B
            // p = GunSkill_A / (GunSkill_A + Agility_B)
            double p = (double) playerA.getGunSkill() / (playerA.getGunSkill() + playerB.getAgility());

            // q = probability that player B hits player A
            // q = GunSkill_B / (GunSkill_B + Agility_A)
            double q = (double) playerB.getGunSkill() / (playerB.getGunSkill() + playerA.getAgility());

            // Apply the play multipliers (this can make p and q higher or lower)
            // Clamp between 0.05 and 0.95 so nothing is impossible
            p = p * multA;
            if (p < 0.05) { p = 0.05; }
            if (p > 0.95) { p = 0.95; }

            q = q * multB;
            if (q < 0.05) { q = 0.05; }
            if (q > 0.95) { q = 0.95; }

            // Calculate the four possible outcomes and their probabilities
            double chanceAWins   = p * (1 - q);  // A hits, B misses
            double chanceBWins   = q * (1 - p);  // B hits, A misses
            double chanceBothOut = p * q;         // Both hit each other
            // If none of the above happen, both players survive (implicit fourth outcome)

            // Roll a random number and see which outcome we land on
            double roll = random.nextDouble();

            if (roll < chanceAWins) {
                // Player A wins the duel
                playerB.setAlive(false);
                elimsOnB = elimsOnB + 1;
                if (isVisible == true) {
                    Commentary.elimination(playerB.getNickname(), playerA.getNickname());
                }

            } else if (roll < chanceAWins + chanceBWins) {
                // Player B wins the duel
                playerA.setAlive(false);
                elimsOnA = elimsOnA + 1;
                if (isVisible == true) {
                    Commentary.elimination(playerA.getNickname(), playerB.getNickname());
                }

            } else if (roll < chanceAWins + chanceBWins + chanceBothOut) {
                // Both players are eliminated
                playerA.setAlive(false);
                playerB.setAlive(false);
                elimsOnA = elimsOnA + 1;
                elimsOnB = elimsOnB + 1;
                if (isVisible == true) {
                    Commentary.bothEliminated(playerA.getNickname(), playerB.getNickname());
                }

            } else {
                // Both players survive
                if (isVisible == true) {
                    Commentary.bothSurvive(playerA.getNickname(), playerB.getNickname());
                }
            }
        }

        // Return how many players were eliminated on each side
        int[] results = new int[2];
        results[0] = elimsOnA;
        results[1] = elimsOnB;
        return results;
    }

    // Simulates a match between two AI teams without printing anything
    // Uses a stat-weighted coin flip to decide the winner
    // Points are applied directly to each team
    public static void simulateAIMatch(Team teamA, Team teamB) {

        // Teams with higher combined stats win more often
        double strengthA = teamA.getAverageGunSkill() + teamA.getAverageAgility();
        double strengthB = teamB.getAverageGunSkill() + teamB.getAverageAgility();

        // Calculate the probability that team A wins
        double winChanceA = strengthA / (strengthA + strengthB);

        boolean teamAWins;
        if (random.nextDouble() < winChanceA) {
            teamAWins = true;
        } else {
            teamAWins = false;
        }

        // Generate random but realistic point totals
        int elimsForWinner = 3 + random.nextInt(8);
        int elimsForLoser  = 1 + random.nextInt(6);
        int aliveForWinner = 1 + random.nextInt(3);

        int pointsForWinner = 5 + (elimsForWinner * 2) + aliveForWinner;
        int pointsForLoser  = elimsForLoser * 2;

        // Apply points to the right teams
        if (teamAWins == true) {
            teamA.addPoints(pointsForWinner);
            teamA.addWin();
            teamA.addEliminations(elimsForWinner);
            teamB.addPoints(pointsForLoser);
            teamB.addEliminations(elimsForLoser);
        } else {
            teamB.addPoints(pointsForWinner);
            teamB.addWin();
            teamB.addEliminations(elimsForWinner);
            teamA.addPoints(pointsForLoser);
            teamA.addEliminations(elimsForLoser);
        }
    }
}
