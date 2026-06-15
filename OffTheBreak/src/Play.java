// Play is an enum that holds all 6 possible plays a team can run each round
// Each play has a name, a description, and a table of how well it does against each other play
public enum Play {

    FULL_SEND(
        "FULL SEND",
        "All 5 rush the 50 simultaneously off the break",
        new double[]{1.0, 0.6, 1.3, 0.8, 0.7, 1.1}
    ),

    ZONE_LOCK(
        "ZONE LOCK",
        "Hold lanes and focus on eliminations from angles",
        new double[]{1.4, 1.0, 0.7, 1.1, 1.3, 0.8}
    ),

    SNAKE_BLITZ(
        "SNAKE BLITZ",
        "Two players crash snake while three provide lane cover",
        new double[]{0.8, 1.3, 1.0, 0.9, 1.2, 0.7}
    ),

    D_SIDE_PUSH(
        "D-SIDE PUSH",
        "Overload the dorito side with a 3v2 numbers advantage",
        new double[]{1.2, 0.9, 1.1, 1.0, 0.8, 1.3}
    ),

    PASSIVE_HOLD(
        "PASSIVE HOLD",
        "Conservative back-position play, wait for mistakes",
        new double[]{1.3, 0.7, 0.8, 1.2, 1.0, 0.9}
    ),

    PINCH_ATTACK(
        "PINCH ATTACK",
        "Split wide on both flanks to pinch from both sides",
        new double[]{0.9, 1.2, 1.3, 0.7, 1.1, 1.0}
    );

    // Each play stores these three things
    private String displayName;
    private String description;
    private double[] matchupMultipliers;

    // Constructor for the enum
    Play(String displayName, String description, double[] matchupMultipliers) {
        this.displayName = displayName;
        this.description = description;
        this.matchupMultipliers = matchupMultipliers;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    // Returns how effective this play is against the opponent's play
    // We use the opponent play's position in the enum (ordinal) to look up the multiplier
    public double getMultiplierAgainst(Play opponentPlay) {
        return matchupMultipliers[opponentPlay.ordinal()];
    }
}
