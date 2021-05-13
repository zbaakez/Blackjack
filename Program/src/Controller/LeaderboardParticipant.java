package Controller;

/**
 * Class that is used to save data to the TableView of each player the Controller
 * @author Busted
 */
public class LeaderboardParticipant {

    private String name;
    private final int spiele, siege, geld;

    /**
     * Constructor to set values of each player
     * @param name as String (playername)
     * @param spiele as Integer
     * @param siege as Integer
     * @param geld as Integer
     */
    public LeaderboardParticipant(String name, int spiele, int siege, int geld){
        this.name = name;
        this.spiele = spiele;
        this.siege = siege;
        this.geld = geld;
    }

    /**
     * Getter method for playername
     * @return name of player as String
     */
    public String getName() { return name; }
    /**
     * Setter method for playername
     * @param name as String
     */
    public void setName(String name) { this.name = name; }
    /**
     * Getter method to get number of games of Player
     * @return number of games of player as Integer
     */
    public int getSpiele() { return spiele; }
    /**
     * Getter method to get number of wins of Player
     * @return number of wins of player as Integer
     */
    public int getSiege() { return siege; }
    /**
     * Getter method to get money of Player
     * @return mones of player as Integer
     */
    public int getGeld() { return geld; }

}
