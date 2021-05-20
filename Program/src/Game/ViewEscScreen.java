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

public class ViewEscScreen {
    public void openEscScreen() throws IOException {

        Data.valueMap.put("openStages", 2);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("escScreen.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 150);
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("escscreenStyle.css")).toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("/pictures/logosmallpng.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();

    }
}
