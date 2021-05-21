package Game;

import Model.CryptoException;
import Model.Data;
import Model.Spieler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ControllerEscScreen {


    public void initialize(){

        if(Model.Data.valueMap.get("sound") == 0)
            btnMute.setStyle("-fx-background-image: url('/pictures/soundoff.jpg')");
        else if(Model.Data.valueMap.get("sound") == 1)
            btnMute.setStyle("-fx-background-image: url('/pictures/soundon.jpg')");

    }

    @FXML
    private Button btnBack, btnMute;

    public void handleBtnMute(){
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

    public void handleExitBtn() throws IOException, CryptoException {

        //remove bet from money of player so you cant cheat with leaving game if you loose
        //now safe to csv
        safeDataToCsv();

        File decrypted = new File("resources/datadecrypted.csv");
        if(decrypted.exists())
            decrypted.delete();
        //exit program
        System.exit(0);
    }
    public void handleBackBtn(){
        Data.valueMap.put("openStages", 1);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close(); //Stage szene gets closed after a button is clicked
    }
    public void handleMainMenuBtn() throws Exception {
        Data.guestAnzahl=1;

        //remove bet from money of player so you cant cheat with leaving game if you loose
        //now safe to csv
        safeDataToCsv();

        Data.setGameRunning(false);
        Data.setCloseFrame(true);
        for(int i =0; i<Data.betMap.size(); i++){
            Data.spielerMap.remove(i);
            Data.payoutMap.remove(i);
            Data.winMap.remove(i);
        }
        for(int i =0; i<Data.betMap.size(); i++){
            Data.betMap.put(i,0);
        }
        JavaFXInitializer fx = new JavaFXInitializer();
        fx.start(new Stage());
        Data.valueMap.put("openStages", 1);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        stage.close(); //Stage szene gets closed after a button is clicked
    }

    private void safeDataToCsv() throws CryptoException, IOException {
        // now save to csv

        for (int i = 0; i < Data.spielerMap.size(); i++) {
            //password for encrypting and decrypting
            String key = "iMtheEncrypter!1";
            //Files that are needed
            File encryptedFile = new File("resources/dataencrypted.csv");
            File decryptedFile = new File("resources/datadecrypted.csv");
            if (!encryptedFile.exists()) {
                break; //error, someone deleted the encryptedFile
            } else { //if file exists
                //decrypt file
                Model.CryptoUtils.decrypt(key, encryptedFile, decryptedFile); //decrypt file here
            }
            //now we have a decrypted file

            Scanner scanner = new Scanner(decryptedFile);
            String line;
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            int x = 0;
            while (scanner.hasNextLine()) {
                if (x == Data.spielerMap.get(i).getID()) {
                    line = scanner.nextLine();
                    //now change money, games and wins
                    String[] splitter = line.split("§");
                    //splitter[3] == number games
                    //splitter[4] == number wins
                    //splitter[5] == money
                    sb2.delete(0, sb2.length());
                    int moneyx = Data.spielerMap.get(i).getGeld();
                    if (moneyx == 0) //you cant have 0 money
                        moneyx = 1;
                    sb2.append(splitter[0]).append("§").append(splitter[1]).append("§").append(splitter[2]).append("§").append(Data.spielerMap.get(i).getSpieleAnzahl()).append("§").append(Data.spielerMap.get(i).getSiegeAnzahl()).append("§").append(moneyx);
                    line = sb2.toString();
                } else
                    line = scanner.nextLine();
                sb.append(line);
                if (scanner.hasNextLine())
                    sb.append("\n");
                x++;
            }

            scanner.close();
            FileWriter writer = new FileWriter(decryptedFile);
            writer.write(sb.toString());
            writer.close();

            // now encrypt file again
            Model.CryptoUtils.encrypt(key, decryptedFile, encryptedFile);
            //delete decrypted file
            decryptedFile.delete();
        }
    }

}
