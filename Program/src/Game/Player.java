package Game;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;
    private List<Card> hand = new ArrayList<>();
    private Player parent=null;
    private Player child=null;
    private BlackJack blackJack;

    public Player(int id, BlackJack blackJack) {
        this.id = id;
        this.blackJack = blackJack;
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

    public boolean canSplit(){
        if(child!=null){
            return false;
        }
        if(parent!=null){
            return false;
        }
        if(hand.size()!=2) {
            return false;
        }
        if(hand.get(0).getValue()==(hand.get(1).getValue())){
            return true;

        }
        return false;
    }

    public Player split(){
        Player player = new Player(id, blackJack);
        player.addCard(hand.get(1));
        hand.remove(1);
        player.addCard(blackJack.getDeck().draw());
        addCard(blackJack.getDeck().draw());
        player.parent=this;
        this.child=player;

        return player;
    }

}
