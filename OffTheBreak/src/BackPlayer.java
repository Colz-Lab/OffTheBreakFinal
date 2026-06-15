// BackPlayer extends Player
// Back players are the shooters and laners on the team
// Their overall rating cares more about gun skill than agility
public class BackPlayer extends Player {

    // Constructor just passes everything up to the Player constructor
    public BackPlayer(String name, String nickname, String realTeam, int gunSkill, int agility) {
        super(name, nickname, realTeam, gunSkill, agility);
    }

    // Back players are labeled as BACK in the draft list
    public String getRoleLabel() {
        return "BACK";
    }

    // Back player overall = 65% gun skill + 35% agility
    // Gun skill matters most for a back player because they lane and shoot
    public int getOverall() {
        double gunPart = gunSkill * 0.65;
        double agiPart = agility * 0.35;
        int overall = (int) Math.round(gunPart + agiPart);
        return overall;
    }
}
