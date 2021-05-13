package Model;

/**
 * Spieler class is used as an Object for HashMap "spielerMap" in Data class
 * saves data that a player has which are: name, ID, wins, games, money
 * @author Busted
 */
public class Spieler {

    private String spielername;
    private int ID;
    private int spieleAnzahl;
    private int siegeAnzahl;
    private int geld;

    /**
     * Getter Method to get ID of player
     * @return ID of player
     */
    public int getID() {
        return ID;
    }
    /**
     * Getter Method to get wins of player
     * @return wins of player
     */
    public int getSiegeAnzahl() {
        return siegeAnzahl;
    }
    /**
     * Getter Method to get money of player
     * @return money of player
     */
    public int getGeld() {
        return geld;
    }
    /**
     * Setter Method to set money of player
     * @param geld money of player as Integer
     */
    public void setGeld(int geld) {
        this.geld = geld;
    }
    /**
     * Setter Method to set wins of player
     * @param siegeAnzahl wins of player as Integer
     */
    public void setSiegeAnzahl(int siegeAnzahl) {
        this.siegeAnzahl = siegeAnzahl;
    }
    /**
     * Getter Method to get number of games of a player
     * @return number of games of player
     */
    public int getSpieleAnzahl() {
        return spieleAnzahl;
    }
    /**
     * Setter Method to set number of games of player
     * @param spieleAnzahl number of games of player as Integer
     */
    public void setSpieleAnzahl(int spieleAnzahl) {
        this.spieleAnzahl = spieleAnzahl;
    }
    /**
     * Getter Method to get name of player
     * @return name of player
     */
    public String getSpielername() {
        return spielername;
    }

    /**
     * Constructor to set values of object of Spieler class
     * @param spielername as String
     * @param ID as Integer
     * @param spieleAnzahl as Integer
     * @param siegeAnzahl as Integer
     * @param geld as Integer
     */
    Spieler(String spielername, int ID, int spieleAnzahl, int siegeAnzahl, int geld){
        this.spielername=spielername;
        this.ID=ID;
        this.spieleAnzahl=spieleAnzahl;
        this.siegeAnzahl=siegeAnzahl;
        this.geld=geld;
    }


}
