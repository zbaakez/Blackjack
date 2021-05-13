package Game;

import java.util.ArrayList;
import java.util.List;

public class BlackJack {

    public enum Action{
        HIT,
        STAND,
        SPLIT,
        DOUBLE_DOWN
    }

    private Player[] players;
    private List<Player> notStanding = new ArrayList<>();
    private Player dealer;
    private Deck deck = new Deck();
    private int turnPlayer=0;

    public BlackJack(int numberOfPlayers) {
        this.players = new Player[numberOfPlayers];
        for(int i=0;i<numberOfPlayers;i++){
            Player player = new Player(i);
            players[i] = player;
        }
        this.dealer=new Player(-1);
    }

    public void start(){
        for(int i=0;i<players.length;i++){
            players[i].resetHand();
            players[i].addCard(deck.draw());
            players[i].addCard(deck.draw());
            notStanding.add(players[i]);
        }
        dealer.resetHand();
        dealer.addCard(deck.draw());
    }

    public void action(Action action){
        if(action==Action.HIT){
            players[turnPlayer].addCard(deck.draw());
            nextTurnPlayer();
        }
        else if(action==Action.STAND){
            notStanding.remove(players[turnPlayer]);
            nextTurnPlayer();
        }

    }

    public boolean nextTurnPlayer(){
        if(notStanding.isEmpty()){
            return false;
        }
        do{
            turnPlayer+=1;
            if(turnPlayer>=players.length) {
                turnPlayer = 0;
            }
        }while(!notStanding.contains(players[turnPlayer]));

        return true;
    }


    public Player getDealer() {
        return dealer;
    }

    public int getValue(int player){
        int value=0;
        for(Card card : players[player].getHand()){
            value+=Card.cardValueToInt(card.getValue());
        }


        return value;
    }

    public Player[] getPlayers() {
        return players;
    }
}
