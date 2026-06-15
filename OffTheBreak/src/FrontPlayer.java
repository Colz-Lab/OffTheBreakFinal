public class FrontPlayer extends Player {

    // Constructor just passes everything up to the Player constructor
    public FrontPlayer(String name, String nickname, String realTeam, int gunSkill, int agility) {
        super(name, nickname, realTeam, gunSkill, agility);
    }

    public String getRoleLabel() {
        return "FRONT";
    }

    // Front player overall = 40% gun skill + 60% agility
    // Agility matters most for a front player because they need to move fast
    public int getOverall() {
        double gunPart = gunSkill * 0.40;
        double agiPart = agility * 0.60;
        int overall = (int) Math.round(gunPart + agiPart);
        return overall;
    }
}
