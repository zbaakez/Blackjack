package Game;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;
    private List<Card> hand = new ArrayList<>();

    public Player(int id) {
        this.id = id;
    }

    public void resetHand(){
        hand.clear();
    }

    public void addCard(Card card){
        hand.add(card);
    }

    public int getId() {
        return id;
    }

    public List<Card> getHand() {
        return hand;
    }
}
