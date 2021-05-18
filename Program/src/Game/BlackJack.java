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

    public void action(Action action) throws IOException, CryptoException {
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
            Data.betMap.put(turnPlayer, Data.betMap.get(turnPlayer)*2);
            players[turnPlayer].addCard(deck.draw());
            if(getValue(players[turnPlayer])>=21){
                notStanding.remove(players[turnPlayer]);
            }
            if(nextTurnPlayer()==false){
                dealersTurn();
            }
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

    public void checkWinner() throws CryptoException, IOException {
        int ii = 0;
        Data.valueMap.put("openStages", 2);
        int dealerValue = getValue(getDealer());
        int playerValue;
        int win;
        for(Player p : players){
            Data.winMap.put(ii,0);
            win=0;
            playerValue = getValue(p);
            //first check if player is below or even on 21
            if(playerValue <= 21){
                if(playerValue==21 && dealerValue != 21){ //blackjack
                    //payout 1:2,5
                    if(Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, (int)Math.round(Data.betMap.get(ii)*2.5));
                    win=1;
                    Data.winMap.put(ii,1);
                }
                else if(playerValue>dealerValue){ // player wins
                    //payout 2:1
                    if(Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii,  Data.betMap.get(ii)*2);
                    win=1;
                    Data.winMap.put(ii,1);
                }else if(playerValue==dealerValue){ //draw
                    //payout 1:1
                    if(Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, Data.betMap.get(ii));
                    Data.winMap.put(ii,2);
                }else if(dealerValue>playerValue && dealerValue<=21){ // dealer wins
                    //payout none
                    if(Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, 0);
                }else if(dealerValue > 21){ // player wins
                    //payout 1:2
                    if(Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, Data.betMap.get(ii)*2);
                    win=1;
                    Data.winMap.put(ii,1);
                }

            }else{ // busted
                if(Data.betMap.get(ii) != null)
                    Data.payoutMap.put(ii, 0);
            }
            //add payout, win and number of games++ to csv and spielermap (only if players arent bots)
            if(Data.spielerMap.get(ii) != null && Data.betMap.get(ii) != null) {
                // first to hashmap
                int money = Data.spielerMap.get(ii).getGeld()+Data.payoutMap.get(ii);
                if(money == 0)
                    money=1;
                Spieler spieler = new Spieler(Data.spielerMap.get(ii).getSpielername(), Data.spielerMap.get(ii).getID(), Data.spielerMap.get(ii).getSpieleAnzahl()+1, Data.spielerMap.get(ii).getSiegeAnzahl()+win, money);
                Data.spielerMap.put(ii, spieler);

                // now save to csv

                //password for encrypting and decrypting
                String key = "iMtheEncrypter!1";
                //Files that are needed
                File encryptedFile = new File("resources/dataencrypted.csv");
                File decryptedFile = new File("resources/datadecrypted.csv");
                if(!encryptedFile.exists()){
                   break; //error, someone deleted the encryptedFile
                } else { //if file exists
                    //decrypt file
                    Model.CryptoUtils.decrypt(key, encryptedFile, decryptedFile); //decrypt file here
                }
                //now we have a decrypted file

                Scanner scanner = new Scanner(decryptedFile);
                String line;
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder();
                int x = 0;
                while(scanner.hasNextLine()){
                    if(x == Data.spielerMap.get(ii).getID()){
                        line = scanner.nextLine();
                        //now change money, games and wins
                        String[] splitter = line.split("§");
                        //splitter[3] == number games
                        //splitter[4] == number wins
                        //splitter[5] == money
                        sb2.delete(0,sb2.length());
                        int moneyx = Data.spielerMap.get(ii).getGeld();
                        if(moneyx==0) //you cant have 0 money
                            moneyx=1;
                        sb2.append(splitter[0]).append("§").append(splitter[1]).append("§").append(splitter[2]).append("§").append(Data.spielerMap.get(ii).getSpieleAnzahl()).append("§").append(Data.spielerMap.get(ii).getSiegeAnzahl()).append("§").append(moneyx);
                        line=sb2.toString();
                    }
                    else
                        line = scanner.nextLine();
                    sb.append(line);
                    if(scanner.hasNextLine())
                        sb.append("\n");
                    x++;
                }

                scanner.close();
                FileWriter writer = new FileWriter(decryptedFile);
                writer.write(sb.toString());
                writer.close();

                // now encrypt file again
                Model.CryptoUtils.encrypt(key, decryptedFile, encryptedFile);
                //delete decrypted file
                decryptedFile.delete();

                //data now saved

            }
            ii++;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ViewEndscreen viewEndscreen = new ViewEndscreen();
                try {
                    viewEndscreen.openEndScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void dealersTurn() throws IOException, CryptoException {
        while (getValue(getDealer())<17){
            dealer.addCard(deck.draw());
        }
        checkWinner();

    }







}
