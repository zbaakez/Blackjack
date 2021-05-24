package Game;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;
    private List<Card> hand = new ArrayList<>();
    private BlackJack blackJack;
    private int timesSplit = 0;

    /**
     * player gets id
     * @param id, int value to identify player
     */
    public Player(int id, BlackJack blackJack) {
        this.id = id;
        this.blackJack = blackJack;
    }

    public Player(Player player)
    {
        this.id = player.id;
        this.hand = new ArrayList<Card>(player.hand);
        this.blackJack = player.blackJack;
        this.timesSplit = player.timesSplit;
    }

    /**
     * returns how many times a player already splitted
     * @return
     */
    public int getTimesSplit() { return this.timesSplit; }

    /**
     * timesSplit gets added by one
     * @return
     */
    public void addTimesSplit() { this.timesSplit++; }

    /**
     * hand of player gets resetted
     */
    public void resetHand(){
        hand.clear();
    }

    /**
     * Card is added to players hand
     * @param card, Card parameter
     */
    public void addCard(Card card){
        hand.add(card);
    }

    /**
     * getter of player id
     * @return, player id
     */
    public int getId() {
        return id;
    }

    /**
     * return hand of player
     * @return, hand
     */
    public List<Card> getHand() {
        return hand;
    }

    public boolean canSplit()
    {
        if(this.timesSplit == 0 && this.hand.size() == 2 && hand.get(0).getValue() == hand.get(1).getValue())
            return true;
        else
            return false;
    }

    /**
     * creates new player object with the splitted hand
     * @return, new player object
     */
    public Player split(){
        Player player;
        this.removeLastCard();
        this.addTimesSplit();
        if(this instanceof Bot)
            player = new Bot(this);
        else
            player = new Player(this);

        this.addCard(blackJack.getDeck().draw());
        player.addCard(blackJack.getDeck().draw());
        return player;
    }

    public void removeLastCard()
    {
        this.hand.remove(this.hand.size() - 1);
    }
}