import java.util.Random;

// Commentary prints random paintball-style messages during matches
// All the methods are static so we can call them without creating a Commentary object
public class Commentary {

    private static Random random = new Random();

    // Different paintball field positions for commentary
    private static String[] fieldPositions = {
        "Snake", "Dorito", "50-Snake", "50-Dorito", "TCK", "Carwash",
        "Aztec", "Corner", "D-Side", "Knuckle", "Brick", "MT"
    };

    // Things players can do when moving
    private static String[] moveVerbs = {
        "slides to", "dives into", "rushes", "sprints to", "crawls into",
        "makes a move to", "breaks for", "charges to", "slips into", "bumps up to"
    };

    // Templates for when someone gets eliminated
    // %s is a placeholder that gets replaced with a player name or position
    private static String[] eliminationMessages = {
        "%s gets lit up by %s from %s!",
        "%s is OUT — %s catches him moving through %s!",
        "%s takes paint from %s at %s, he is eliminated!",
        "%s walks off — %s put two on him at %s.",
        "%s is hit! %s was laning from %s.",
        "ELIMINATED — %s runs out of luck against %s at %s!",
        "%s got bunkered by %s — caught sleeping at %s!",
        "%s is done. %s read that move from %s perfectly.",
        "%s steps out and eats a ball from %s at %s.",
        "Paint check on %s — he is hit! %s shooting from %s."
    };

    // Templates for movement commentary
    private static String[] moveMessages = {
        "%s %s %s.",
        "%s makes an aggressive move — %s %s!",
        "%s %s %s, looking for the angle.",
        "%s pushes up — %s %s.",
        "Big move! %s %s %s.",
        "%s %s %s, reading the field."
    };

    // Templates for when two players start fighting
    private static String[] duelMessages = {
        "Heads up — %s and %s are going at it!",
        "%s and %s trading paint...",
        "Firefight between %s and %s!",
        "%s is shooting lanes on %s!",
        "%s challenges %s — this is a big gunfight.",
        "Both sides are live — %s vs %s!"
    };

    // Templates for when both players get eliminated
    private static String[] bothOutMessages = {
        "Both %s and %s go down — double elimination!",
        "DOUBLE! %s and %s knock each other out!",
        "They trade — %s and %s are both eliminated!",
        "%s and %s shoot each other out. Double kill!"
    };

    // Templates for when both players survive the duel
    private static String[] bothLiveMessages = {
        "Neither player hit — %s and %s both live!",
        "They burn paint but nothing connects — %s and %s survive.",
        "Close call. %s and %s both walk away clean.",
        "Paint everywhere but no hits — %s and %s still in it."
    };

    // Templates for announcing the start of a round
    private static String[] roundStartMessages = {
        "=== ROUND %d - BREAK! ===",
        "=== ROUND %d - Horns go! ===",
        "=== ROUND %d - Flag is up! ===",
        "=== ROUND %d - PAINT IS IN THE AIR! ===",
        "=== ROUND %d - HERE WE GO! ==="
    };

    // Prints a random round start message
    public static void roundStart(int roundNumber) {
        pause(400);
        System.out.println();
        int index = random.nextInt(roundStartMessages.length);
        String message = String.format(roundStartMessages[index], roundNumber);
        System.out.println("  " + message);
        System.out.println();
    }

    // Announces what play was called this round
    public static void announcePlay(String playName, boolean isPlayerTeam) {
        pause(300);
        if (isPlayerTeam == true) {
            System.out.println("  >> Your team calls: " + playName);
        } else {
            System.out.println("  >> Opponent calls: " + playName);
        }
    }

    // Tells the player if their play matchup is good or bad this round
    public static void playAdvantage(String teamName, double multiplier) {
        pause(200);
        if (multiplier >= 1.25) {
            System.out.println("  ** GREAT MATCHUP for " + teamName + "! That play counters perfectly. **");
        } else if (multiplier >= 1.1) {
            System.out.println("  ** " + teamName + " has a slight edge in the play matchup. **");
        } else if (multiplier <= 0.75) {
            System.out.println("  ** BAD CALL — " + teamName + " is playing right into their opponent's strength! **");
        }
    }

    // Prints a random message when two players are about to duel
    public static void duelOpener(String name1, String name2) {
        pause(250);
        int index = random.nextInt(duelMessages.length);
        String message = String.format(duelMessages[index], name1, name2);
        System.out.println("    " + message);
    }

    // Prints a random elimination message
    public static void elimination(String victimName, String shooterName) {
        pause(300);
        int positionIndex = random.nextInt(fieldPositions.length);
        String position = fieldPositions[positionIndex];

        int messageIndex = random.nextInt(eliminationMessages.length);
        String message = String.format(eliminationMessages[messageIndex], victimName, shooterName, position);
        System.out.println("    *** " + message);
    }

    // Prints a message when both players get eliminated
    public static void bothEliminated(String name1, String name2) {
        pause(300);
        int index = random.nextInt(bothOutMessages.length);
        String message = String.format(bothOutMessages[index], name1, name2);
        System.out.println("    *** " + message);
    }

    // Prints a message when both players survive
    public static void bothSurvive(String name1, String name2) {
        pause(250);
        int index = random.nextInt(bothLiveMessages.length);
        String message = String.format(bothLiveMessages[index], name1, name2);
        System.out.println("    -- " + message);
    }

    // Prints a random player movement message
    public static void playerMove(String playerNickname) {
        pause(150);
        int verbIndex = random.nextInt(moveVerbs.length);
        String verb = moveVerbs[verbIndex];

        int positionIndex = random.nextInt(fieldPositions.length);
        String position = fieldPositions[positionIndex];

        int messageIndex = random.nextInt(moveMessages.length);
        String message = String.format(moveMessages[messageIndex], playerNickname, verb, position);
        System.out.println("    " + message);
    }

    // Prints the result at the end of a round
    public static void roundResult(String winnerTeamName, int aliveOnTeamA, int aliveOnTeamB) {
        pause(300);
        System.out.println();
        System.out.println("  -- Round over. " + winnerTeamName + " takes the point.");
        System.out.println("     Players remaining: " + aliveOnTeamA + " vs " + aliveOnTeamB);
    }

    // Prints the final result at the end of a match
    public static void matchResult(String winnerName, String loserName, int winnerRounds, int loserRounds) {
        pause(400);
        System.out.println();
        System.out.println("  +--------------------------------------+");
        System.out.println("  |  MATCH OVER: " + winnerName + " wins!");
        System.out.println("  |  Final score: " + winnerRounds + " - " + loserRounds);
        System.out.println("  +--------------------------------------+");
    }

    // Pauses for a number of milliseconds to make the output feel more like a broadcast
    private static void pause(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // Do nothing if sleep is interrupted
        }
    }
}
