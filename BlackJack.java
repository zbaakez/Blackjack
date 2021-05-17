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
            if(getValue(players[turnPlayer])>21){
                notStanding.remove(players[turnPlayer]);
            }
            if(nextTurnPlayer()==false){
                dealersTurn();
            }

        }
        else if(action==Action.STAND){
            notStanding.remove(players[turnPlayer]);
            if(nextTurnPlayer()==false){
                dealersTurn();
            }
        }
        else if(action==Action.DOUBLE_DOWN){

        }
        else if(action==Action.SPLIT){

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

    public int getValue(Player player){
        int value=0;
        for(Card card : player.getHand()){
            if(Card.cardValueToInt(card.getValue())==12 ||Card.cardValueToInt(card.getValue())==11 ||Card.cardValueToInt(card.getValue())==10){
                value+=10;
            }
            else if(Card.cardValueToInt(card.getValue())==0){
                value+=0;
            }
            else{
                value+=Card.cardValueToInt(card.getValue())+1;
            }

        }
        for(Card card : player.getHand()){
            if(Card.cardValueToInt(card.getValue())==0){
                if(value<11){
                    value+=11;
                }
                else{
                    value+=1;
                }
            }
        }
        return value;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void checkWinner(){
        for(Player p : players){
            if(getValue(p)>getValue(getDealer()) && getValue(p)>22){
                System.out.println("WIN");
            }
            else{
                System.out.println("Lose");
            }
        }

    }

    public void dealersTurn(){
        while (getValue(getDealer())<17){
            dealer.addCard(deck.draw());
        }
        checkWinner();

    }







}
