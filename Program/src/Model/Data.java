package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data Class that handles the Data and saves it correspondingly
 * works with static variables to have an easy access
 * @author Busted
 */
public class Data {

    //HashMap valueMap saves default data like: sound, theme, playernumber, botnumber
    static public HashMap<String, Integer> valueMap = new HashMap<>();

    //Hashmap spielerMap saves data of logged in players with their data (name, ID, money, games, wins),
    // Data gets saved in Object from class Spieler
    static public HashMap<Integer, Spieler> spielerMap = new HashMap<>();

    //Hashmap that saves bets for each player
    static public HashMap<Integer, Integer> betMap = new HashMap<>();
    //Hashmap that saves payout for each player
    static public HashMap<Integer, Integer> payoutMap = new HashMap<>();
    //Hashmap that saves if a player won/lost/draw, 0=loose, 1=win, 2=draw
    static public HashMap<Integer, Integer> winMap = new HashMap<>();
    public static boolean getCloseFrame() {
        return closeFrame;
    }
    public static void setCloseFrame(boolean closeFrame) {
        Data.closeFrame = closeFrame;
    }
    //boolean that closes frame if true
    static private boolean closeFrame = false;

    public static boolean getGameRunning() {
        return gameRunning;
    }
    public static void setGameRunning(boolean gameRunning) {
        Data.gameRunning = gameRunning;
    }

    public static int numberPlayers;

    public static int getTurnOfPlayer() {
        return turnOfPlayer;
    }

    public static void setTurnOfPlayer() {
        if(turnOfPlayer+1==numberPlayers)
            Data.turnOfPlayer = 0;
        else
            turnOfPlayer++;
    }
    public static void setTurnOfPlayerToZero() {
        Data.turnOfPlayer = 0;
    }

    private static int turnOfPlayer=0;

    //boolean that is true if game is running
    static private boolean gameRunning = false;


    //saves how many guests are logged in
    static public int guestAnzahl = 1;

    //sets to true if controller gets called first time
    static private boolean init = false;


    /**
     * Method used to set init, which is set to true when Controller class gets called the first time
     * @param init as boolean
     */
    public static void setInit(boolean init) { Data.init = init; }

    /**
     * Method used to get init, which is true when Controller class gets called the first time
     * @return initValue
     */
    public static boolean getInit() { return init; }


    /**
     * Method that returns if the HashMap valueMap has been initialized, valueMap initializes when Controller gets called the first time
     * @return valueMapGotInitialized , can be true or false
     */
    public static boolean getInitInputValueMap() {
        //just search for key "init" in map
        for (String key : valueMap.keySet()){
            if (key.equals("init"))
                return true;
        }
        return false;
    }

    /**
     * Method to return valueMap as String
     * @return valueMap as String
     */
    public static String hashMapToString(){
        StringBuilder sb = new StringBuilder();
        for (String key : valueMap.keySet())
            sb.append(key).append(" ").append(valueMap.get(key)).append("\n");

        return sb.toString();
    }

    /**
     * Method to return spielerMap as String
     * @return spielerMap as String
     */
    public static String spielerMapToString(){
        StringBuilder sb = new StringBuilder();
        for (Integer key : spielerMap.keySet())
            sb.append("\n"+spielerMap.get(key).getSpielername()).append("  ").append(spielerMap.get(key).getID()).append("  ").append(spielerMap.get(key).getGeld()).append("  ").append(spielerMap.get(key).getSpieleAnzahl()).append("  ").append(spielerMap.get(key).getSiegeAnzahl());

        return sb.toString();
    }

    /**
     * Method that returns names of all logged in players as an Arraylist
     * @return names as Arraylist
     */
    public static ArrayList<String> spielerMapGetNames(){
        ArrayList<String> names = new ArrayList<>();
        for (Integer key : spielerMap.keySet())
            names.add(spielerMap.get(key).getSpielername());

        return names;
    }

}
