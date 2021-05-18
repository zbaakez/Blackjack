package Game;

import Model.Data;

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

    public int getTurnPlayer(){
        return turnPlayer;
    }

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
            if(getValue(players[turnPlayer])>=21){
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
        int ii = 0;
        Data.valueMap.put("openStages", 2);
        int dealerValue = getValue(getDealer());
        int playerValue;
        boolean win;
        for(Player p : players){
            win=false;
            playerValue = getValue(p);
            //first check if player is below or even on 21
            if(playerValue <= 21){
                if(playerValue==21 && dealerValue != 21){ //blackjack
                    //payout 1:2,5
                    Data.payoutMap.put(ii, (int)Math.round(Data.betMap.get(ii)*2.5));
                    win=true;
                }
                else if(playerValue>dealerValue){ // player wins
                    //payout 2:1
                    Data.payoutMap.put(ii,  Data.betMap.get(ii)*2);
                    win=true;
                }else if(playerValue==dealerValue){ //draw
                    //payout 1:1
                    Data.payoutMap.put(ii, Data.betMap.get(ii));
                }else if(dealerValue>playerValue && dealerValue<=21){ // dealer wins
                    //payout none
                    Data.payoutMap.put(ii, 0);
                }else if(dealerValue > 21){ // player wins
                    //payout 1:2
                    Data.payoutMap.put(ii, Data.betMap.get(ii)*2);
                    win=true;
                }

            }else{ // busted
                Data.payoutMap.put(ii, 0);
            }
            //add payout, win and number of games++ to csv and spielermap



            ii++;
        }

    }

    public void dealersTurn(){
        while (getValue(getDealer())<17){
            dealer.addCard(deck.draw());
        }
        checkWinner();

    }







}
