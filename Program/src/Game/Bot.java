package Game;

/**
 * class to distinguish bot and player
 */
public class Bot extends Player{

    /**
     * constructor of bot
     * @param id, player id
     * @param blackJack, current blackJack object
     */
    public Bot(int id, BlackJack blackJack) {
        super(id, blackJack);
    }

    public Bot(Player bot)
    {
        super(bot);
    }
}