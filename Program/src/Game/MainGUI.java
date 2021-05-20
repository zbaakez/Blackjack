package Game;

import Game.Frame;
import Model.Data;

public class MainGUI {
    public static void main() throws Exception {
        Data.setGameRunning(true);
        Frame frame = new Frame();
        frame.startGame();
    }
}
