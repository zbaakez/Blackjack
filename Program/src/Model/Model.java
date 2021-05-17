package Model;

import View.View;

import java.io.IOException;
import Game.*;

/**
 * Model class is used to handle and pass on actions
 * @author Busted
 */
public class Model {

    private View view = new View();

    /**
     * Getter Method to return if sound is turned on or off
     * @return sound on as boolean
     */
    public boolean getSoundOn() {
        return Data.valueMap.get("sound").equals(1);
    }

    /**
     * handle what happens after users are logged in
     * game starts from here
     */
    public void startGameBtnClicked() throws Exception {
        //Object of game with object(Data.valueMap(), Data.spielerMap);
        Game.MainGUI.main();
        System.out.println(Data.hashMapToString());
    }

    /**
     * calls Method in View class that opens spieleranzahlwindow
     * @throws IOException
     */
    public void spieleranzahlBtnClicked() throws IOException {
        view.openSpieleranzahlWindow();
    }

    /**
     * calls Method in View class that opens spieleranzahlwindow
     * @throws IOException
     */
    public void botanzahlBtnClicked() throws IOException {
        view.openBotanzahlWindow();
    }

    /**
     * calls Method in View class that opens szeneAuswahlWindow
     * @throws IOException
     */
    public void szeneBtnClicked() throws IOException {
        view.openSzeneAuswahlWindow();
    }

    /**
     * calls Method in View class that opens regelnWindow
     * @throws IOException
     */
    public void regelnBtnClicked() throws IOException {
        view.openRegelnWindow();
    }

    /**
     * calls Method in View class that opens loginWindow
     * @param numberPlayer number of players specified as Integer
     * @throws IOException
     */
    public void handleLoginWindow(int numberPlayer) throws IOException {
        view.openLoginWindow(numberPlayer);
    }

    /**
     * calls Method in View class that opens leaderboardWindow
     * @throws IOException
     */
    public void handleLeaderboardWindow() throws IOException {
        view.openLeaderboardWindow();
    }

    /**
     * safes data to HashMap "valueMap" in data
     * @param spielername as String
     * @param ID as Integer
     * @param spieleAnzahl as Integer
     * @param siegeAnzahl as Integer
     * @param geld as Integer
     */
    public void safePlayerDataToHashmap(String spielername, int ID, int spieleAnzahl, int siegeAnzahl, int geld) {
        Spieler spieler = new Spieler(spielername, ID, spieleAnzahl, siegeAnzahl, geld);
        Data.spielerMap.put(Data.spielerMap.size(), spieler);
    }

}
