package View;

import Controller.Controller;
import Model.Data;
import Model.Music;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Objects;

/**
 * Main class of the Model.Blackjack game "Busted"
 * loads Main Menu
 * @author Busted
 */
public class BlackjackMainMenu extends Application  {

    //add this to vm options if music doesnt work
    // --add-modules=javafx.controls,javafx.media

    private Timeline timeline;

    public static Parent root;

    /**
     * Method that opens MainMenu with corresponding attributes
     * Program is started here
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/mainMenu.fxml")));
        primaryStage.setTitle("Busted");
        Scene scene = new Scene(root, Screen.getPrimary().getBounds().getMaxX()*0.75, Screen.getPrimary().getBounds().getMaxY()*0.75);
        scene.getStylesheets().addAll(Objects.requireNonNull(this.getClass().getResource("mainWindowStyle.css")).toExternalForm());
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        //16:9
        primaryStage.setMinHeight(510);
        primaryStage.setMinWidth(906);

        primaryStage.getIcons().add(new Image("/pictures/logosmallpng.png"));

        primaryStage.show();

        //close all windows if X in titlebar is clicked
        primaryStage.setOnCloseRequest(windowEvent -> {
            File decrypted = new File("resources/datadecrypted.csv");
            if(decrypted.exists())
                decrypted.delete();
            System.exit(0);
        });

        nextSong();

    }

    /**
     * Method with Timeline that checks if a song is playing every second
     * if no song is playing a new one starts
     */
    private void nextSong(){
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(Data.valueMap.get("sound") == 1)
                    if(!Music.audioPlayer.isPlaying())
                        Music.playMusic();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Method to start the program
     */
    public static void main(String[] args) {
        launch(args);
    }

}
