package Game;

import Game.Frame;
import Model.Data;

public class MainGUI {
    public static void main() throws Exception {
        Data.setGameRunning(true);
        Data.numberPlayers=Data.valueMap.get("spieler")+Data.valueMap.get("bot");
        Data.setTurnOfPlayerToZero();
        Frame frame = new Frame();
        frame.startGame();
    }
}
