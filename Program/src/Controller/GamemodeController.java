package Controller;

import Model.Data;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GamemodeController {

    @FXML private Button btnOk;

    @FXML private TextField textInput;
    public void initialize(){
        textInput.setFocusTraversable(false);
        textInput.setPromptText("Blackjack on: " +Data.valueMap.get("maxPoints") + " (min 21, max 249)");
    }

    public void btnOkClicked(){
        String input = textInput.getText();


        if(input.length() == 0) {
            Stage stage = (Stage) btnOk.getScene().getWindow();
            stage.close(); //Stage gamemode gets closed after button is clicked
            Model.Data.valueMap.put("openStages", 1);
            return;
        }
        //check if input is valid
        if(input.matches("^[0-9]*$") && input.length() > 1 && input.length() < 5){ //length shouldn't be to high
            if(Integer.parseInt(input) < 21){ // max points must be over or equal 21
                btnOk.setText("Invalid");
                return;
            }else if(Integer.parseInt(input) < 250){ // valid input (max 250 points)
                Data.valueMap.put("maxPoints", Integer.parseInt(input));
                Stage stage = (Stage) btnOk.getScene().getWindow();
                stage.close(); //Stage gamemode gets closed after button is clicked
                Model.Data.valueMap.put("openStages", 1);
                return;
            }

        }
        else {
            btnOk.setText("Invalid");
            return;
        }

    }

}
