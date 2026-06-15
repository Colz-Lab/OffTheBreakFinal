public abstract class Player {

    private String name;
    private String nickname;
    private String realTeam;
    protected int gunSkill;
    protected int agility;
    private boolean alive;

    public Player(String name, String nickname, String realTeam, int gunSkill, int agility) {
        this.name = name;
        this.nickname = nickname;
        this.realTeam = realTeam;
        this.gunSkill = gunSkill;
        this.agility = agility;
        this.alive = true;
    }

    // These two methods must be filled in by the subclass (FrontPlayer or BackPlayer)
    public abstract String getRoleLabel();
    public abstract int getOverall();


    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRealTeam() {
        return realTeam;
    }

    public int getGunSkill() {
        return gunSkill;
    }

    public int getAgility() {
        return agility;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    // Shows the player's info in a readable format when printed
    public String toString() {
        String output = "";
        output = output + String.format("%-22s", name);
        output = output + String.format(" %-16s", "[" + realTeam + "]");
        output = output + String.format(" [%-5s]", getRoleLabel());
        output = output + "  GUN: " + gunSkill;
        output = output + "  AGI: " + agility;
        output = output + "  OVR: " + getOverall();
        return output;
    }
}
