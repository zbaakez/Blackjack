package View;

import Model.Data;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

/**
 * View class is used to open Windows and set some attributes to them
 * @author Busted
 */
public class View {

    /**
     * Opens spielerAnzahlWindow
     * @throws IOException
     */
    public void openSpieleranzahlWindow() throws IOException {
        Data.valueMap.put("openStages", 2);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("spielerAnzahlWindow.fxml")));
        openWindow(root);
    }

    /**
     * Opens BotanzahlWindow
     * @throws IOException
     */
    public void openBotanzahlWindow() throws IOException {
        Data.valueMap.put("openStages", 3);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("botAnzahlWindow.fxml")));
        openWindow(root);
    }

    /**
     * gets called from openBotanzahlWindow() or openSpielerAnzahlWindow() and sets corresponding attributes
     * @param root as Parent
     */
    private void openWindow(Parent root){
        Stage stage = new Stage();
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getMaxX()*0.25, Screen.getPrimary().getBounds().getMaxX()*0.2);
        scene.getStylesheets().addAll(this.getClass().getResource("spielerWindowStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("/pictures/logosmallpng.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Opens SzeneAuswahlWindow with corresponding attributes
     * @throws IOException
     */
    public void openSzeneAuswahlWindow() throws IOException {
        Data.valueMap.put("openStages", 4); //main menu and szene are open
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("szeneWahlWindow.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 300);
        scene.getStylesheets().addAll(this.getClass().getResource("szeneWindowStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("/pictures/logosmallpng.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Opens RegelnWindow with corresponding attributes
     * @throws IOException
     */
    public void openRegelnWindow() throws IOException {
        Data.valueMap.put("openStages", 5); //main menu and regeln are open
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("regelnWindow.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().addAll(this.getClass().getResource("regelnWindowStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.getIcons().add(new Image("/pictures/logosmallpng.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    /**
     * Opens LoginWindow with corresponding attributes
     * @throws IOException
     */
    public void openLoginWindow(int numberPlayer) throws IOException {
        Data.valueMap.put("openStages", 6);
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("loginWindow.fxml"))));
        Stage stage = new Stage();
        Scene loginScene = new Scene(root, 600, 300);
        loginScene.getStylesheets().addAll(this.getClass().getResource("loginWindowStyle.css").toExternalForm());
        stage.setScene(loginScene);
        stage.getIcons().add(new Image("/pictures/logosmallpng.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();

        Label label = (Label) loginScene.lookup("#lblSpielerLogin");
        int out = numberPlayer+1;
        label.setText("Login: Spieler " + out);
    }

    /**
     * Opens LeaderboardWindow with corresponding attributes
     * @throws IOException
     */
    public void openLeaderboardWindow() throws IOException {
        Data.valueMap.put("openStages", 7);
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("leaderboardWindow.fxml"))));
        Stage stage = new Stage();
        Scene leaderboardScene = new Scene(root, 800, 600);
        leaderboardScene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("leaderboardWindowStyle.css")).toExternalForm());
        stage.setScene(leaderboardScene);
        stage.getIcons().add(new Image("/pictures/logosmallpng.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

    public void openGamemodeWindow() throws IOException {
        Data.valueMap.put("openStages", 7);
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("gamemodeWindow.fxml"))));
        Stage stage = new Stage();
        Scene leaderboardScene = new Scene(root, 600, 400);
        leaderboardScene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("gamemodeWindowStyle.css")).toExternalForm());
        stage.setScene(leaderboardScene);
        stage.getIcons().add(new Image("/pictures/logosmallpng.png"));
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();
    }

}

