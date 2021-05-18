package Game;

import Model.Data;
import View.BlackjackMainMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.File;

public class ControllerEndscreen {

    @FXML //lbl_zeile_kolonne lbl_0_0
    private Label lbl_0_0, lbl_0_1, lbl_0_2, lbl_1_0, lbl_1_1, lbl_1_2, lbl_2_0, lbl_2_1, lbl_2_2, lbl_3_0, lbl_3_1, lbl_3_2, lbl_4_0, lbl_4_1, lbl_4_2;
    public void initialize(){
        //set values to labels
        for(int i = 0; i < Data.spielerMap.size(); i++){
            if(i==0){
                lbl_0_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_0_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_0_1.setText("Win");
                else
                    lbl_0_1.setText("Draw");
                lbl_0_2.setText(String.valueOf(Data.payoutMap.get(i)));
            }else if(i==1){
                lbl_1_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_1_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_1_1.setText("Win");
                else
                    lbl_1_1.setText("Draw");
                lbl_1_2.setText(String.valueOf(Data.payoutMap.get(i)));
            }else if(i==2){
                lbl_2_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_2_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_2_1.setText("Win");
                else
                    lbl_2_1.setText("Draw");
                lbl_2_2.setText(String.valueOf(Data.payoutMap.get(i)));
            }else if(i==3){
                lbl_3_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_3_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_3_1.setText("Win");
                else
                    lbl_3_1.setText("Draw");
                lbl_3_2.setText(String.valueOf(Data.payoutMap.get(i)));
            }else if(i==4){
                lbl_4_0.setText(Data.spielerMap.get(i).getSpielername());
                if(Data.winMap.get(i) == 0)
                    lbl_4_1.setText("Loose");
                else if(Data.winMap.get(i) == 1)
                    lbl_4_1.setText("Win");
                else
                    lbl_4_1.setText("Draw");
                lbl_4_2.setText(String.valueOf(Data.payoutMap.get(i)));
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
        for(int i =0; i<Data.betMap.size(); i++){
            Data.betMap.put(i,0);
        }
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close(); //Stage szene gets closed after a button is clicked
        Game.MainGUI.main();
    }


}
