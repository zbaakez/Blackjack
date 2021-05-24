package Game;

import Model.CryptoException;
import Model.Data;
import Model.Spieler;
import View.View;
import javafx.application.Platform;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    private int bets;
    Frame frame;

    public int getTurnPlayer(){
        return turnPlayer;
    }

    public BlackJack(int numberOfPlayers, Frame frame) {
        this.players = new Player[numberOfPlayers];
        for(int i=0;i<numberOfPlayers;i++){
            Player player = new Player(i, this);
            players[i] = player;
        }
        this.dealer=new Player(-1, this);
        this.frame=frame;
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


        players[0].resetHand();
        players[0].addCard(new Card(0 , 0));
        players[0].addCard(new Card(0 , 0));

    }

    public void action(Action action) throws IOException, CryptoException {
        int maxPoints = Data.valueMap.get("maxPoints");
        if(action==Action.HIT){
            players[turnPlayer].addCard(deck.draw());
            if(getValue(players[turnPlayer])>=maxPoints){
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
            if(Data.spielerMap.get(turnPlayer).getGeld()*2 > Data.betMap.get(turnPlayer)){

                Data.betMap.put(turnPlayer, Data.betMap.get(turnPlayer)*2);
                players[turnPlayer].addCard(deck.draw());
                if(getValue(players[turnPlayer])>=maxPoints){
                    notStanding.remove(players[turnPlayer]);
                }
                if(nextTurnPlayer()==false){
                    dealersTurn();
                }
            }
        }
        else if(action==Action.SPLIT){
            if(!players[turnPlayer].canSplit()){
                return;
            }
            Data.numberPlayers++;
            Player player = players[turnPlayer].split();
            Spieler spieler = new Spieler(Data.spielerMap.get(turnPlayer).getSpielername(), Data.spielerMap.get(turnPlayer).getID(),Data.spielerMap.get(turnPlayer).getSpieleAnzahl(), Data.spielerMap.get(turnPlayer).getSiegeAnzahl(),Data.spielerMap.get(turnPlayer).getGeld());
            Data.spielerMap.put(Data.spielerMap.size(),spieler);
            Data.betMap.put(Data.numberPlayers-1, Data.betMap.get(turnPlayer));
            
            //remove bet a second time
            Spieler spieler1 = new Spieler(Data.spielerMap.get(turnPlayer).getSpielername(), Data.spielerMap.get(turnPlayer).getID(),Data.spielerMap.get(turnPlayer).getSpieleAnzahl(), Data.spielerMap.get(turnPlayer).getSiegeAnzahl(),Data.spielerMap.get(turnPlayer).getGeld()-Data.betMap.get(turnPlayer));
            Data.spielerMap.put(turnPlayer, spieler1);

            Player[] players1 = new Player[players.length+1];
            int foundTurnPlayer=0;
            for(int i=0;i<players.length;i++){
                players1[i+foundTurnPlayer]=players[i];
                if(i==turnPlayer){
                    players1[i+1]=player;
                    foundTurnPlayer=1;
                }

            }
            players=players1;
            frame.deleteTextfields();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            frame.setTextfields();
            notStanding.add(player);
        }

    }

    public boolean nextTurnPlayer(){
        if(notStanding.isEmpty()){
            return false;
        }
        do{
            turnPlayer+=1;
            Data.setTurnOfPlayer();
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

    public Deck getDeck(){
        return deck;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void checkWinner() throws CryptoException, IOException {
        int ii = 0;
        int maxPoints = Data.valueMap.get("maxPoints");
        Data.valueMap.put("openStages", 2);
        int dealerValue = getValue(getDealer());
        int playerValue;
        Data.valueMap.put("dealerPoints", dealerValue);
        for(Player p : players) {
            Data.winMap.put(ii, 0);
            playerValue = getValue(p);
            //first check if player is below or even on maxPoints (21- Blackjack)
            if (playerValue <= maxPoints) {
                if (playerValue == maxPoints && dealerValue != maxPoints) { //blackjack
                    //payout 1:2,5
                    if (Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, (int) Math.round(Data.betMap.get(ii) * 2.5));
                    Data.winMap.put(ii, 1);
                } else if (playerValue > dealerValue) { // player wins
                    //payout 2:1
                    if (Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, Data.betMap.get(ii) * 2);
                    Data.winMap.put(ii, 1);
                } else if (playerValue == dealerValue) { //draw
                    //payout 1:1
                    if (Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, Data.betMap.get(ii));
                    Data.winMap.put(ii, 2);
                } else if (dealerValue > playerValue && dealerValue <= maxPoints) { // dealer wins
                    //payout none
                    if (Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, 0);
                } else if (dealerValue > maxPoints) { // player wins
                    //payout 1:2
                    if (Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, Data.betMap.get(ii) * 2);
                    Data.winMap.put(ii, 1);
                }

            } else { // busted
                if (Data.betMap.get(ii) != null)
                    Data.payoutMap.put(ii, 0);
            }
            ii++;
        }
    }

    public void dealersTurn() throws IOException, CryptoException {

        int maxPoints = Data.valueMap.get("maxPoints");
        while (getValue(getDealer())<maxPoints-4){ // same as 21-4 == 17
            dealer.addCard(deck.draw());
        }
        checkWinner();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {
            ViewEndscreen viewEndscreen = new ViewEndscreen();
            try {
                viewEndscreen.openEndScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }







}
