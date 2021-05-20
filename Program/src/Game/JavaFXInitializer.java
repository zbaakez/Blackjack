package Game;

import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXInitializer extends Application {
    @Override
    public void start(Stage st) throws Exception {
        View.BlackjackMainMenu b = new View.BlackjackMainMenu();
    }
}