import java.util.ArrayList;
import java.util.Collections;

// PlayerPool creates all 30 NXL players that go into the draft
// 15 are BackPlayers (shooters) and 15 are FrontPlayers (movers)
// The pool gets shuffled so the order is random each game
public class PlayerPool {

    // Builds and returns the full list of 30 players
    public static ArrayList<Player> generatePool() {

        ArrayList<Player> pool = new ArrayList<Player>();

        // --- BACK PLAYERS ---
        // Back players have higher gun skill
        // Their overall rating is weighted 65% gun / 35% agility

        pool.add(new BackPlayer("Ryan Greenspan",     "Greenspan",   "Infamous",           95, 72));
        pool.add(new BackPlayer("Justin Rabackoff",   "Rabackoff",   "San Diego Dynasty",  93, 68));
        pool.add(new BackPlayer("Mikkel Hansen",      "Hansen",      "Edmonton Impact",    90, 70));
        pool.add(new BackPlayer("Marcello Margott",   "Margott",     "Tampa Bay Damage",   91, 78));
        pool.add(new BackPlayer("Kyle Spicka",        "Spicka",      "San Diego Dynasty",  89, 80));
        pool.add(new BackPlayer("Alex Spence",        "Spence",      "Impact",             88, 76));
        pool.add(new BackPlayer("Konstantin Federov", "KF",          "Moscow Red Legion",  88, 73));
        pool.add(new BackPlayer("Dalton Vanderbyl",   "DVB",         "Houston Heat",       87, 79));
        pool.add(new BackPlayer("Parker Stonecipher", "Stoney",      "San Diego Dynasty",  86, 74));
        pool.add(new BackPlayer("Alex Goldman",       "Goldman",     "Infamous",           86, 82));
        pool.add(new BackPlayer("Chad George",        "C-George",    "Houston Heat",       85, 81));
        pool.add(new BackPlayer("Tyler Harlow",       "T-Har",       "Houston Heat",       84, 83));
        pool.add(new BackPlayer("Tim Montressor",     "Monty",       "Tampa Bay Damage",   84, 77));
        pool.add(new BackPlayer("Corey Field",        "C-Field",     "Impact",             83, 85));
        pool.add(new BackPlayer("Ryan Moorhead",      "R-Moore",     "Houston Heat",       82, 80));

        // --- FRONT PLAYERS ---
        // Front players have higher agility
        // Their overall rating is weighted 40% gun / 60% agility

        pool.add(new FrontPlayer("Damian Ryan",       "D-Ryan",      "Infamous",           76, 95));
        pool.add(new FrontPlayer("Lane Carpenter",    "Lane",        "Impact",             78, 93));
        pool.add(new FrontPlayer("Dalen Nguyen",      "D-Nguyen",    "Houston Heat",       75, 93));
        pool.add(new FrontPlayer("Michael Hinman",    "Hinman",      "Houston Heat",       74, 94));
        pool.add(new FrontPlayer("Zack Wake",         "Wake",        "Tampa Bay Damage",   77, 92));
        pool.add(new FrontPlayer("Nick Slowiak",      "Slowiak",     "San Diego Dynasty",  80, 91));
        pool.add(new FrontPlayer("Alexis Luttenauer", "Lutt",        "Toulouse Black Sox", 76, 92));
        pool.add(new FrontPlayer("Kyle Kopp",         "K-Kopp",      "San Diego Dynasty",  79, 90));
        pool.add(new FrontPlayer("Yosh Rau",          "Yosh",        "Tampa Bay Damage",   85, 88));
        pool.add(new FrontPlayer("Bart Yachimec",     "Yachi",       "Edmonton Impact",    81, 88));
        pool.add(new FrontPlayer("Justin Cornell",    "J-Cornell",   "Impact",             80, 89));
        pool.add(new FrontPlayer("Bryan Lim",         "B-Lim",       "Tampa Bay Damage",   78, 91));
        pool.add(new FrontPlayer("Billy Bernacchia",  "Billy-B",     "Infamous",           82, 87));
        pool.add(new FrontPlayer("Greg Siewers",      "Siewers",     "San Diego Dynasty",  83, 86));
        pool.add(new FrontPlayer("Ryan Greenspan Jr.","Greeny Jr.",  "Infamous",           77, 90));

        // Shuffle so every draft is different
        Collections.shuffle(pool);

        return pool;
    }
}
