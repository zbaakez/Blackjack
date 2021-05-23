package Game;

import Model.CryptoException;
import Model.Data;
import Model.Spieler;
import View.BlackjackMainMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ControllerEndscreen {


    private String[] name = new String[Data.spielerMap.size()];
    @FXML //lbl_zeile_kolonne lbl_0_0
    private Label lbl_0_0, lbl_0_1, lbl_0_2, lbl_1_0, lbl_1_1, lbl_1_2, lbl_2_0, lbl_2_1, lbl_2_2, lbl_3_0, lbl_3_1, lbl_3_2, lbl_4_0, lbl_4_1, lbl_4_2, lblDealer;
    public void initialize() throws CryptoException, IOException {

        lbl_0_0.setText("");
        lbl_0_1.setText("");
        lbl_0_2.setText("");
        lbl_1_0.setText("");
        lbl_1_1.setText("");
        lbl_1_2.setText("");
        lbl_2_0.setText("");
        lbl_2_1.setText("");
        lbl_2_2.setText("");
        lbl_3_0.setText("");
        lbl_3_1.setText("");
        lbl_3_2.setText("");
        lbl_4_0.setText("");
        lbl_4_1.setText("");
        lbl_4_2.setText("");


        lblDealer.setText("Dealer value: " + Data.valueMap.get("dealerPoints"));
        int playerIn = -1;
        int safeSecondPlayer=-1;
        int players = Data.valueMap.get("spieler")+ Data.valueMap.get("bot");
        //set values to labels
        for(int i = 0; i < Data.spielerMap.size(); i++){
            if(i>=Data.numberPlayers)
                break;
            playerIn=-1;
            safeSecondPlayer=-1;
            //safe spielername to an array (useful if splitted)
            name[i] = Data.spielerMap.get(i).getSpielername();

            //check if array contains the name already
            for(int x = 0; x<players; x++){
                if(name[i].equals(name[x]) && i!=x && i!=0 && i<Data.numberPlayers){
                    playerIn=x;
                    safeSecondPlayer=i;
                }
            }
            if(playerIn==0){
                lbl_0_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_0_1.setText("Split");
                // 0=loose, 1=win, 2=draw
                if(Data.winMap.get(playerIn) != 1){ //check if a split has a win
                    if(Data.winMap.get(safeSecondPlayer) == 1)
                        Data.winMap.put(playerIn, 1);
                }
                if(Data.payoutMap.get(i) != null)
                    lbl_0_2.setText(String.valueOf(Integer.valueOf(lbl_0_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_0_2.setText("0");
            }else if(playerIn==1) {
                lbl_1_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_1_1.setText("Split");
                if(Data.winMap.get(playerIn) != 1){ //check if a split has a win
                    if(Data.winMap.get(safeSecondPlayer) == 1)
                        Data.winMap.put(playerIn, 1);
                }
                if(Data.payoutMap.get(i) != null)
                    lbl_1_2.setText(String.valueOf(Integer.valueOf(lbl_1_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_1_2.setText("0");
            }else if(playerIn==2) {
                lbl_2_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_2_1.setText("Split");
                if(Data.winMap.get(playerIn) != 1){ //check if a split has a win
                    if(Data.winMap.get(safeSecondPlayer) == 1)
                        Data.winMap.put(playerIn, 1);
                }
                if(Data.payoutMap.get(i) != null)
                    lbl_2_2.setText(String.valueOf(Integer.valueOf(lbl_2_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_2_2.setText("0");
            } else if(playerIn==3) {
                lbl_3_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_3_1.setText("Split");
                if(Data.winMap.get(playerIn) != 1){ //check if a split has a win
                    if(Data.winMap.get(safeSecondPlayer) == 1)
                        Data.winMap.put(playerIn, 1);
                }
                if(Data.payoutMap.get(i) != null)
                    lbl_3_2.setText(String.valueOf(Integer.valueOf(lbl_3_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_3_2.setText("0");
            }else if(playerIn==4) {
                lbl_4_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_4_1.setText("Split");
                if(Data.winMap.get(playerIn) != 1){ //check if a split has a win
                    if(Data.winMap.get(safeSecondPlayer) == 1)
                        Data.winMap.put(playerIn, 1);
                }
                if(Data.payoutMap.get(i) != null)
                    lbl_4_2.setText(String.valueOf(Integer.valueOf(lbl_4_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_4_2.setText("0");
            }
            if(i==0){
                lbl_0_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_0_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_0_1.setText("Win");
                else
                    lbl_0_1.setText("Draw");
                if(Data.payoutMap.get(i) != null)
                    lbl_0_2.setText(String.valueOf(Data.payoutMap.get(i)));
                else
                    lbl_0_2.setText("0");
            }else if(i==1 && i+1<=Data.valueMap.get("spieler")+Data.valueMap.get("bot")){
                lbl_1_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_1_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_1_1.setText("Win");
                else
                    lbl_1_1.setText("Draw");
                if(Data.payoutMap.get(i) != null)
                    lbl_1_2.setText(String.valueOf(Data.payoutMap.get(i)));
                else
                    lbl_1_2.setText("0");
            }else if(i==2 && i+1<=Data.valueMap.get("spieler")+Data.valueMap.get("bot")){
                lbl_2_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_2_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_2_1.setText("Win");
                else
                    lbl_2_1.setText("Draw");
                if(Data.payoutMap.get(i) != null)
                    lbl_2_2.setText(String.valueOf(Data.payoutMap.get(i)));
                else
                    lbl_2_2.setText("0");
            }else if(i==3 && i+1<=Data.valueMap.get("spieler")+Data.valueMap.get("bot")){
                lbl_3_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_3_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_3_1.setText("Win");
                else
                    lbl_3_1.setText("Draw");
                if(Data.payoutMap.get(i) != null)
                    lbl_3_2.setText(String.valueOf(Data.payoutMap.get(i)));
                else
                    lbl_3_2.setText("0");
            }else if(i==4 && i+1<=Data.valueMap.get("spieler")+Data.valueMap.get("bot")){
                lbl_4_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_4_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_4_1.setText("Win");
                else
                    lbl_4_1.setText("Draw");
                if(Data.payoutMap.get(i) != null) //payout map is "null" for bots
                    lbl_4_2.setText(String.valueOf(Data.payoutMap.get(i)));
                else
                    lbl_4_2.setText("0");
            }
        }
        for(int i = 0; i<Data.spielerMap.size(); i++){
            if(i>Data.valueMap.get("spieler")+Data.valueMap.get("bot")){
                Data.spielerMap.remove(i);
            }
        }

        for(int l=0; l<Data.valueMap.get("spieler")+Data.valueMap.get("bot"); l++) {
            //add payout, win and number of games++ to csv and spielermap (only if players arent bots)
            if (Data.spielerMap.get(l) != null && Data.betMap.get(l) != null) {
                // first to hashmap
                int money = Data.spielerMap.get(l).getGeld();

                if(l==0)
                    money+=Integer.parseInt(lbl_0_2.getText());
                else if(l==1)
                    money+=Integer.parseInt(lbl_1_2.getText());
                else if(l==2)
                    money+=Integer.parseInt(lbl_2_2.getText());
                else if(l==3)
                    money+=Integer.parseInt(lbl_3_2.getText());
                else if(l==4)
                    money+=Integer.parseInt(lbl_4_2.getText());

                if (money == 0)
                    money = 1;

                Spieler spieler = new Spieler(Data.spielerMap.get(l).getSpielername(), Data.spielerMap.get(l).getID(), Data.spielerMap.get(l).getSpieleAnzahl() + 1, Data.spielerMap.get(l).getSiegeAnzahl() + Data.winMap.get(l), money);
                Data.spielerMap.put(l, spieler);

                // now save to csv

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
                    if (x == Data.spielerMap.get(l).getID()) {
                        line = scanner.nextLine();
                        //now change money, games and wins
                        String[] splitter = line.split("§");
                        //splitter[3] == number games
                        //splitter[4] == number wins
                        //splitter[5] == money
                        sb2.delete(0, sb2.length());
                        int moneyx = Data.spielerMap.get(l).getGeld();
                        if (moneyx == 0) //you cant have 0 money
                            moneyx = 1;
                        sb2.append(splitter[0]).append("§").append(splitter[1]).append("§").append(splitter[2]).append("§").append(Data.spielerMap.get(l).getSpieleAnzahl()).append("§").append(Data.spielerMap.get(l).getSiegeAnzahl()).append("§").append(moneyx);
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

                //data now saved

            }
        }

    }

    @FXML
    private Button btnExit;
    public void handleExitBtn(){
        File decrypted = new File("resources/datadecrypted.csv");
        if(decrypted.exists())
            decrypted.delete();
        //exit program
        System.exit(0);
    }
    public void handleMainMenuBtn() throws Exception {
        Data.guestAnzahl=1;
        Data.setGameRunning(false);
        Data.setCloseFrame(true);
        for(int i =0; i<Data.betMap.size(); i++){
            Data.payoutMap.remove(i);
            Data.winMap.remove(i);
        }
        for(int i = 0; i<=Data.spielerMap.size(); i++ ){
            Data.spielerMap.remove(i);
        }
        for(int i =0; i<Data.betMap.size(); i++){
            Data.betMap.put(i,0);
        }

        JavaFXInitializer fx = new JavaFXInitializer();
        fx.start(new Stage());
        Data.valueMap.put("openStages", 1);
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close(); //Stage szene gets closed after a button is clicked

    }
    public void handleRestartBtn() throws Exception {
        Data.setCloseFrame(true);
        for(int i = 0; i<Data.spielerMap.size(); i++){
            if(i>Data.valueMap.get("spieler")+Data.valueMap.get("bot")){
                Data.spielerMap.remove(i);
            }
        }
        Data.numberPlayers=Data.valueMap.get("spieler")+Data.valueMap.get("bot");

        for(int i =0; i<Data.betMap.size(); i++){
            Data.betMap.put(i,0);
        }
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close(); //Stage szene gets closed after a button is clicked

        Thread.sleep(200);
        Game.MainGUI.main();

    }


}
