package Game;

import Model.Data;
import View.BlackjackMainMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;

public class ControllerEndscreen {


    private String[] name = new String[Data.spielerMap.size()];
    @FXML //lbl_zeile_kolonne lbl_0_0
    private Label lbl_0_0, lbl_0_1, lbl_0_2, lbl_1_0, lbl_1_1, lbl_1_2, lbl_2_0, lbl_2_1, lbl_2_2, lbl_3_0, lbl_3_1, lbl_3_2, lbl_4_0, lbl_4_1, lbl_4_2, lblDealer;
    public void initialize(){

        System.out.println(Data.spielerMap.size());

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
        System.out.println(Data.numberPlayers);
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
                if(Data.payoutMap.get(i) != null)
                    lbl_0_2.setText(String.valueOf(Integer.valueOf(lbl_0_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_0_2.setText("0");
            }else if(playerIn==1) {
                lbl_1_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_1_1.setText("Split");
                if(Data.payoutMap.get(i) != null)
                    lbl_1_2.setText(String.valueOf(Integer.valueOf(lbl_1_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_1_2.setText("0");
            }else if(playerIn==2) {
                lbl_2_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_2_1.setText("Split");
                if(Data.payoutMap.get(i) != null)
                    lbl_2_2.setText(String.valueOf(Integer.valueOf(lbl_2_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_2_2.setText("0");
            } else if(playerIn==3) {
                lbl_3_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_3_1.setText("Split");
                if(Data.payoutMap.get(i) != null)
                    lbl_3_2.setText(String.valueOf(Integer.valueOf(lbl_3_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_3_2.setText("0");
            }else if(playerIn==4) {
                lbl_4_0.setText(Data.spielerMap.get(i).getSpielername());
                lbl_4_1.setText("Split");
                if(Data.payoutMap.get(i) != null)
                    lbl_4_2.setText(String.valueOf(Integer.valueOf(lbl_4_2.getText()) + Data.payoutMap.get(safeSecondPlayer)));
                else
                    lbl_4_2.setText("0");
            }
            System.out.println(Data.valueMap.get("spieler")+Data.valueMap.get("bot"));
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
