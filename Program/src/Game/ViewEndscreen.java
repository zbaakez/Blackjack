package Game;

import Model.Data;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class ViewEndscreen {

    public void openEndScreen() throws IOException {
        Data.valueMap.put("openStages", 2);
        //lbl_zeile_kolonne lbl_0_0
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("endscreen.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getMaxX()*0.4, Screen.getPrimary().getBounds().getMaxY()*0.5);
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("endscreenStyle.css")).toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("/pictures/logosmallpng.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();


    }

}
