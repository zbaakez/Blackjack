package Game;

import Game.Frame;
import Model.Data;

public class MainGUI {
    public static void main() throws Exception {
        Data.setGameRunning(true);
        System.out.println(Data.valueMap.get("maxPoints"));
        Frame frame = new Frame();
        frame.startGame();
    }
}
