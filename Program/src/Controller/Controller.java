package Controller;

import Model.Data;
import View.BlackjackMainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Controller Class that handles the View Elements (example: Buttons) of most windows
 * @author Busted
 */
public class Controller {

    @FXML
    private Button btnMute, btnStartGame, btnSpieleranzahl, btnBotanzahl, btnSzene, btnRegeln, btnExit, btnGamemode;

    private final Model.Model model = new Model.Model();

    Scene mainMenuScene;
    @FXML private Slider sliderSpieler, sliderBot;

    /**
     * Method initialize gets called when Controller is initialized
     * puts values into Hashmap, which is placed in Data Class, where basic things are saved
     * starts the music with a call to the Music class
     * @throws MalformedURLException
     */
    public void initialize() throws MalformedURLException {

        if(!Model.Data.getInit()) {
            if (!Model.Data.getInitInputValueMap()) {// set values of hashmap
                Model.Data.valueMap.put("init", 1); //hashmap has been initialized
                Model.Data.valueMap.put("sound", 1); //sound on
                Model.Data.valueMap.put("spieler", 1); //spieleranzahl
                Model.Data.valueMap.put("bot", 0); //botanzahl
                Model.Data.valueMap.put("szene", 1); // 1 == Standard, 2 == Tirol, 3 == Strand, 4 == Normaler Tisch
                Model.Data.valueMap.put("openStages", 1); //1 Main Menu, 2 Main Menu and Spieleranzahl, 3 Main Menu and Botanzahl, 4 Main Menu and Szene, 5 Main Menu and Rules, 6 Login Windows, 7 Leaderboard
                Model.Data.valueMap.put("maxPoints", 21);
            }
            Model.Data.setInit(true); //set init to true
            Model.Music.playMusic();
        }
        if(Model.Data.valueMap.get("openStages") == 2) //handles Spieleranzahlwindow
                sliderSpieler.setValue(Model.Data.valueMap.get("spieler"));
        if(Model.Data.valueMap.get("openStages") == 3) //handles Botanzahlwindow
                sliderBot.setValue(Model.Data.valueMap.get("bot"));

    }

    /**
     * Method buttonClicked handles all buttons that can be clicked in the MainMenu window
     * calls Methods that handle the buttonevent
     * @param evt , evt is the button that is clicked
     * @throws IOException
     */
    public void buttonClicked(ActionEvent evt) throws Exception {

        mainMenuScene = btnStartGame.getScene();
        Button clickedButton = (Button) evt.getTarget();

        //handle Button btnMute (mutes and unmutes music)
        if (clickedButton.equals(btnMute)){
            if(Model.Data.valueMap.get("sound") == 1) {
                btnMute.setStyle("-fx-background-image: url('/pictures/soundoff.jpg')");
                Model.Data.valueMap.put("sound", 0);
                Model.Music.stopMusic();
            }
            else if(Model.Data.valueMap.get("sound") == 0) {
                btnMute.setStyle("-fx-background-image: url('/pictures/soundon.jpg')");
                Model.Data.valueMap.put("sound", 1);
                Model.Music.resumeMusic();
            }
        }
        //handle btnExit, exits the program
        else if(clickedButton.equals(btnExit)){
            //in case the decrypted data file still exists it gets deleted
            File decrypted = new File("resources/datadecrypted.csv");
            if(decrypted.exists())
                decrypted.delete();
            //exit program
            System.exit(0);
        }
        //handle btnStartGame
        else if(!Model.Data.getGameRunning() && clickedButton.equals(btnStartGame) && (Model.Data.valueMap.get("openStages") == 1 || Model.Data.valueMap.get("openstages") == 6)){
            for(int i = 0; i<=Data.spielerMap.size(); i++ ){
                Data.spielerMap.remove(i);
            }
            //first login window for player pops out
            model.handleLoginWindow(0);

        }
        //call method that will open Spieleranzahl window
        else if(clickedButton.equals(btnSpieleranzahl) && Model.Data.valueMap.get("openStages") == 1)
            model.spieleranzahlBtnClicked();
            //call method that will open Botanzahl window
        else if(clickedButton.equals(btnBotanzahl) && Model.Data.valueMap.get("openStages") == 1)
            model.botanzahlBtnClicked();
            //call method that will open Theme window
        else if(clickedButton.equals(btnSzene) && Model.Data.valueMap.get("openStages") == 1)
            model.szeneBtnClicked();
            //call method that will open Rule window
        else if(clickedButton.equals(btnRegeln) && Model.Data.valueMap.get("openStages") == 1)
            model.regelnBtnClicked();
        else if(clickedButton.equals(btnGamemode) && Model.Data.valueMap.get("openStages") == 1)
            model.gamemodeBtnClicked();

    }

    @FXML
    private Button btnPlayerOk;
    /**
     * Method that handles btnPlayerOk button
     * that button belongs to the Spieleranzahl window
     * checks if spieleranzahl+botanzahl doesn't exceed 5 (5 is max players)
     * puts the value of the slider of the window to the Hashmap in Data class
     * similar to the method btnBotOkClicked, but for the playernumber
      */
    public void btnPlayerOkClicked() {
        int playerinput = (int) sliderSpieler.getValue(); //get value of slider
        if(playerinput + Model.Data.valueMap.get("bot") > 5){ //max player number is exceed
            btnPlayerOk.setText("Insgesamt zu viele Spieler!");
        }else { //if there aren't to many players+bots
            Model.Data.valueMap.put("spieler", playerinput); //put value into hashmap
            Stage stage = (Stage) sliderSpieler.getScene().getWindow();
            stage.close(); //Stage spielerauswahl gets closed after button is clicked
            Model.Data.valueMap.put("openStages", 1);
        }
    }

    @FXML
    private Button btnBotOk;
    /**
     * Method that handles btnBotOk button
     * that button belongs to the Botanzahl window
     * checks if spieleranzahl+botanzahl doesn't exceed 5 (5 is max players)
     * puts the value of the slider of the window to the Hashmap in Data class
     * similar to the method btnPlayerOkClicked, but for the botnumber
     */
    public void btnBotOkClicked() {
        int botInput = (int) sliderBot.getValue(); //get value of slider
        if (botInput + Model.Data.valueMap.get("spieler") > 5) { //max player number is exceed
            btnBotOk.setText("Insgesamt zu viele Spieler!");
        } else { //if there aren't to many players+bots
            Model.Data.valueMap.put("bot", (int) sliderBot.getValue()); //put value into hashmap
            Stage stage = (Stage) sliderBot.getScene().getWindow();
            stage.close(); //Stage spielerauswahl gets closed after button is clicked
            Model.Data.valueMap.put("openStages", 1);
        }

    }

    //Szene
    @FXML
    private Button btnCasino, btnTirol, btnStrand, btnWeltall;

    /**
     * Method that handles the Buttons in the Theme window
     * changes the theme (music and background)
     * saves theme selection to the hashmap in Data class
     * @param evt , evt is the Button that is clicked
     */
    public void szeneButtonClicked(ActionEvent evt) {

        int initialSzene = Model.Data.valueMap.get("szene");

        //change background according to the scene and save theme to hashmap in Data class
        if(evt.getSource().equals(btnCasino)) {
            Model.Data.valueMap.put("szene", 1);
            BlackjackMainMenu.root.setStyle("-fx-background-image: url('/pictures/menumain.jpg')");
        }
        else if(evt.getSource().equals(btnTirol)){
            Model.Data.valueMap.put("szene", 2);
            BlackjackMainMenu.root.setStyle("-fx-background-image: url('/pictures/tirolMain.jpg')");
        }
        else if(evt.getSource().equals(btnStrand)){
            Model.Data.valueMap.put("szene", 3);
            BlackjackMainMenu.root.setStyle("-fx-background-image: url('/pictures/strandMain.jpg')");
        }
        else if(evt.getSource().equals(btnWeltall)){
            Model.Data.valueMap.put("szene", 4);
            BlackjackMainMenu.root.setStyle("-fx-background-image: url('/pictures/weltallMain.jpg')");
        }
        //if theme changed, the music changes too
        if(initialSzene != Model.Data.valueMap.get("szene")) {
            Model.Music.stopMusic();
            Model.Music.playMusic();
        } //if theme stays the same, music just goes on

        Stage stage = (Stage) btnCasino.getScene().getWindow();
        stage.close(); //Stage szene gets closed after a button is clicked
        Model.Data.valueMap.put("openStages", 1);

    }

    //Regeln
    @FXML private Button btnRulesOk;
    /**
     * Method that handles btnRulesOk, button on Rule window
     * closes the Rule window
     */
    public void btnRulesOkClicked(){
        Stage stage = (Stage) btnRulesOk.getScene().getWindow();
        stage.close(); //Stage rules gets closed after button is clicked
        Model.Data.valueMap.put("openStages", 1);
    }


    //Login
    @FXML private Button btnLogin, btnAsGuest, btnRegistrieren;
    @FXML private PasswordField passwordFieldLogin;
    @FXML private TextField usernameFieldLogin;
    @FXML private Label lblSpielerLogin;
    /**
     * Method that handles the loginWindow, this window opens when start game button is clicked
     * handles registering, login in and playing as guest buttons
     * checks or writes new users to a encrypted csv file
     * if login/register failes it shows on the corresponding buttons
     * @param evt
     * @throws IOException
     */
    public void loginWindowBtnClicked(ActionEvent evt) throws Exception {

        //password for encrypting and decrypting
        String key = "iMtheEncrypter!1";
        //Files that are needed
        File encryptedFile = new File("resources/dataencrypted.csv");
        File decryptedFile = new File("resources/datadecrypted.csv");

        //get input of the the textfields
        String usernameInput = usernameFieldLogin.getText();
        String passwordInput = passwordFieldLogin.getText();

        //boolean that changes to true if the login/registering/play as guest has worked
        boolean worked = false;

        //username cant equal guest
        if(usernameInput.equals("guest")) {
            if (evt.getSource().equals(btnRegistrieren)) {
                btnRegistrieren.setStyle("-fx-background-color: red");
                btnRegistrieren.setText("Name ung??ltig!");
            } else if (evt.getSource().equals(btnLogin)) {
                btnLogin.setStyle("-fx-background-color: red");
                btnLogin.setText("Name ung??ltig!");
            }
            return;
        }

        //handle btnRegistrieren Button
        if(evt.getSource().equals(btnRegistrieren)) {
            //first check for validity of name

            //csv splitter is invalid in the name
            if(usernameInput.indexOf("??") != -1){
                btnRegistrieren.setStyle("-fx-background-color: red");
                btnRegistrieren.setText("Name ung??ltig!");
            } else {
                //username needs to have a length of 3 characters
                if (usernameInput.length() < 3) {
                    //passwords needs to be at least 8 characters
                    if (passwordInput.length() < 8)
                        btnRegistrieren.setText("Input zu kurz");
                    else
                        btnRegistrieren.setText("Username zu kurz");
                    btnRegistrieren.setStyle("-fx-background-color: red");
                } else if (passwordInput.length() < 8) {
                    btnRegistrieren.setText("Passwort zu kurz");
                    btnRegistrieren.setStyle("-fx-background-color: red");
                } // password or username cant be over 100 chars
                else if (passwordInput.length() > 100 || usernameInput.length() > 100){
                    if(usernameInput.length() > 100 && passwordInput.length() > 100)
                        btnRegistrieren.setText("Input zu lang");
                    else if(passwordInput.length() > 100)
                        btnRegistrieren.setText("Passwort zu lang");
                    else
                        btnRegistrieren.setText("Username zu lang");
                    btnRegistrieren.setStyle("-fx-background-color: red");

                    //if name and password are OK
                } else {

                    //get data of encryptedFile
                    if(!encryptedFile.exists()){ //create a new file if no encrypted file exists
                        decryptedFile.createNewFile();
                    }else { //File exists
                        //decrypt encrypted file
                        Model.CryptoUtils.decrypt(key, encryptedFile, decryptedFile); //decrypt file here

                        //check if name is already in use
                        Scanner scanner = new Scanner(decryptedFile);
                        String line;
                        while (scanner.hasNextLine()) {
                            line = scanner.nextLine();
                            line = line.substring(0, line.indexOf("??"));
                            if (line.equals(usernameInput)) { //name is already in use
                                //if name is already in use user gets called to use a other name
                                btnRegistrieren.setText("Name vergeben!");
                                btnRegistrieren.setStyle("-fx-background-color: red");
                                scanner.close();
                                decryptedFile.delete();
                                return;
                            }
                        }
                        scanner.close();
                    }

                    //if name isn't in use it gets saved to the decrypted file

                    //first get number of rows (so we can calculate the ID of the player)
                    Scanner scanner1 = new Scanner(decryptedFile);
                    int rows=0;
                    while (scanner1.hasNextLine()){
                        scanner1.nextLine();
                        rows++;
                    }
                    scanner1.close();

                    //now save to csv with default data
                    FileWriter writer = new FileWriter(decryptedFile, true);
                    if(rows==0)
                        writer.write(usernameInput+"??"+passwordInput+"??"+rows+"??0??0??1000"); //1000 is money
                    else
                        writer.write("\n"+usernameInput+"??"+passwordInput+"??"+rows+"??0??0??1000");
                    writer.close();
                    //Data is now in the csv

                    //encrypt the file again
                    Model.CryptoUtils.encrypt(key, decryptedFile, encryptedFile);
                    //delete decrypted file
                    decryptedFile.delete();

                    //now safe data of player to hashmap
                    int spieler1 = Integer.parseInt(lblSpielerLogin.getText().replaceAll("\\D+", ""));
                    model.safePlayerDataToHashmap(usernameInput, rows, 0,0,1000, spieler1);
                    //set worked to true, new user has registered correctly
                    worked=true;

                    //After everything is done -> next loginWindow or game start
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    stage.close(); //Stage szene gets closed after button is clicked
                }
            }
        }
        //handle btnLogin button
        else if(evt.getSource().equals(btnLogin)){

            //check if encryptedFile exits
            if(!encryptedFile.exists()){
                //if no encryptedFile exists there cant be any logged in users
                btnLogin.setText("User nicht vorhanden\nRegistriere dich!");
                btnLogin.setStyle("-fx-background-color: red");
                return;
            } else { //if file exists
                //decrypt file
                Model.CryptoUtils.decrypt(key, encryptedFile, decryptedFile); //decrypt file here
            }
            //now we have a decrypted file

            //check if input length is 0, invalid input
            if(usernameInput.length() == 0){
                btnLogin.setText("Kein Input");
                btnLogin.setStyle("-fx-background-color: red");
                return;
            }

            //search for name in csv
            Scanner scanner = new Scanner(decryptedFile);
            String line;

            //0 = name not found, 1 = name found but password wrong, 2 = logged in correctly, 3 = user is already signed in, a user cant sign in twice
            int stateLogin = 0;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                String[] values = line.split("??");
                if (usernameInput.equals(values[0])) { //name found
                    //now check if this user is already logged in
                    ArrayList<String> nameList;
                    nameList = Model.Data.spielerMapGetNames();
                    for (String name : nameList) {
                        if (name.equals(usernameInput)) {
                            stateLogin = 3; //user already signed in
                            break;
                        }
                    }
                }
                else {
                    ArrayList<String> nameList;
                    nameList = Model.Data.spielerMapGetNames();
                    for (String name : nameList) {
                        if (name.equals(usernameInput)) {
                            stateLogin = 3; //user already signed in
                            break;
                        }
                    }
                    continue;
                }

                if (stateLogin!=3){
                    //name is found
                    stateLogin = 1;
                    //check password
                    if (passwordInput.equals(values[1])) { //password is correct
                        stateLogin = 2; //user is logged in
                        //save data from csv (data in array) to hashmap
                        int spieler1 = Integer.parseInt(lblSpielerLogin.getText().replaceAll("\\D+", ""));
                        model.safePlayerDataToHashmap(usernameInput, Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]), spieler1);
                        //user is logged in correctly, set worked to true
                        worked = true;
                    }
                }
            }
            scanner.close();
            //encrypt file again
            Model.CryptoUtils.encrypt(key, decryptedFile, encryptedFile);
            //delete decryptedFile
            decryptedFile.delete();

            //if login didnt work due to some reason (reason definded in stateLogin)
            if(stateLogin==0){
                btnLogin.setText("User nicht vorhanden");
                btnLogin.setStyle("-fx-background-color: red");
            }else if(stateLogin==1) {
                btnLogin.setText("Falsche Daten");
                btnLogin.setStyle("-fx-background-color: red");
            }else if(stateLogin==3){
                btnLogin.setText("User bereits eingeloggt");
                btnLogin.setStyle("-fx-background-color: red");
            }else{  // login worked correctly
                Stage stage = (Stage) btnLogin.getScene().getWindow();
                stage.close(); //Stage szene gets closed after button is clicked
            }
        }else if(evt.getSource().equals(btnAsGuest)){  //handle btnAsGuest button

            if(!decryptedFile.exists())
                decryptedFile.delete();

            int spieler1 = Integer.parseInt(lblSpielerLogin.getText().replaceAll("\\D+", ""));
            //Save guest to Hashmap with default data, guest 1 gets name guest1 with ID -1, guest 2 gets guest2 with ID-2
            model.safePlayerDataToHashmap("guest"+Model.Data.guestAnzahl, (Model.Data.guestAnzahl)*(-1), 0,0,1000, spieler1);
            Model.Data.guestAnzahl++;
            //After everything is done -> next login or game start
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close(); //Stage szene gets closed after button is clicked
            worked=true;
        }

        int spieler = Integer.parseInt(lblSpielerLogin.getText().replaceAll("\\D+", ""));
        if(worked) {
            Model.Data.valueMap.put("openStages", 1);
            if(Model.Data.valueMap.get("spieler") > spieler)
                model.handleLoginWindow(spieler);
        }

        if(worked) {
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close(); //Stage szene gets closed after button is clicked
	        if(Model.Data.valueMap.get("spieler") == spieler) { // all players logged in, game can be started
	            //for(int i = 0; i< Data.spielerMap.size(); i++)
                  //  System.out.println("h  " + Data.spielerMap.get(i).getSpielername());
                model.startGameBtnClicked();
            }
        }
    }

    //open Leaderboard
    /**
     * Method that calls method to open leaderboard
     * gets called from click on btnLeaderboard button
     */
    public void btnLeaderboardClicked() throws IOException {
        if(Model.Data.valueMap.get("openStages") == 1)
            model.handleLeaderboardWindow();
    }

}

