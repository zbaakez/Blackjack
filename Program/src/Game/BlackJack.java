package Game;

import Model.CryptoException;
import Model.Data;
import Model.Spieler;
import javafx.application.Platform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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


    /**
     * @return, player`s turn
     */
    public int getTurnPlayer(){
        return turnPlayer;
    }

    /**
     * constructor with number of players, creates a player object for each and the dealer
     * @param numberOfPlayers, number of players in the game
     * @param numberOfBots, number of bots in the game
     * @param frame, current Frame
     */
    public BlackJack(int numberOfPlayers, int numberOfBots, Frame frame) {
        this.players = new Player[numberOfPlayers+numberOfBots];
        for(int i=0;i<numberOfPlayers;i++){
            Player player = new Player(i, this);
            players[i] = player;
        }
        for(int i=numberOfPlayers;i<numberOfBots+numberOfPlayers;i++){
            Player bot = new Bot(i,this);
            Data.betMap.put(i, 100);
            players[i]=bot;
        }
        this.dealer=new Player(-1, this);
        this.frame=frame;
    }

    /**
     * to start the game the players hand is reset, the players and the dealer receive the card and then the deck is drawn
     */
    public void start(){
        for(int i=0;i<players.length;i++){
            players[i].resetHand();
            players[i].addCard(deck.draw());
            players[i].addCard(deck.draw());
            notStanding.add(players[i]);
        }
        dealer.resetHand();
        dealer.addCard(deck.draw());
        dealer.addCard(new Card(-1, -1));

        players[0].resetHand();
        players[0].addCard(new Card(0, 0));
        players[0].addCard(new Card(0, 0));
    }

    /**
     * if the player hits then a card is added to his hand and if the player is over 21, he is removed from the game and cannot take action anymore
     * if the player stands the his nextturn is set to false, if the action is double down then the player gets an additional card and his bet is doubled
     * if the player splits then he plays with two diffrent hands
     * after every players move it gets checked if a bot has to play, if a bot has to play the bot plays
     * @param action, enum type that describes the action the player wants to make
     * @throws IOException
     * @throws CryptoException
     */
    public void action(Action action) throws IOException, CryptoException
    {
        if(action == Action.HIT)
            players[turnPlayer].addCard(deck.draw());
        else if(action == Action.DOUBLE_DOWN)
        {
            if(Data.spielerMap.get(turnPlayer).getGeld() * 2 >= Data.betMap.get(turnPlayer) && players[turnPlayer].getHand().size() == 2)
            {
                Data.betMap.put(turnPlayer, Data.betMap.get(turnPlayer)*2);
                players[turnPlayer].addCard(deck.draw());
            }
        }
        else if(action == Action.SPLIT)
            if(players[turnPlayer].canSplit() && Data.spielerMap.get(turnPlayer).getGeld() * 2 >= Data.betMap.get(turnPlayer))
            {
                players[turnPlayer].addTimesSplit();
                Player player = players[turnPlayer].split();

                /*Spieler spieler = new Spieler(Data.spielerMap.get(turnPlayer).getSpielername(), Data.spielerMap.get(turnPlayer).getID(),Data.spielerMap.get(turnPlayer).getSpieleAnzahl(), Data.spielerMap.get(turnPlayer).getSiegeAnzahl(),Data.spielerMap.get(turnPlayer).getGeld());
                Data.spielerMap.put(Data.spielerMap.size(),spieler);
                Data.betMap.put(Data.numberPlayers-1, Data.betMap.get(turnPlayer));*/

                Player[] newPlayers = new Player[players.length + 1];

                int foundTurnPlayer = 0;
                for(int i = 0; i < players.length; i++)
                {
                    newPlayers[i + foundTurnPlayer] = players[i];
                    if(i == turnPlayer)
                    {
                        newPlayers[i + 1] = player;
                        foundTurnPlayer = 1;
                    }
                }
                players = newPlayers;
                frame.deleteTextfields();
                frame.setTextfields();
                notStanding.add(player);
            }

        if(action == Action.STAND || (action == Action.DOUBLE_DOWN && Data.spielerMap.get(turnPlayer).getGeld() * 2 >= Data.betMap.get(turnPlayer)) || getValue(players[turnPlayer]) >= Data.valueMap.get("maxPoints"))
            turnPlayer++;

        if(turnPlayer >= players.length)
        {
            dealersTurn();
            return;
        }

        if(players[turnPlayer] instanceof Bot)
        {
            MethodsForBlackJackBot bot = new MethodsForBlackJackBot(deck.countDeckCards(), Data.valueMap.get("maxPoints"), 1);
            action(bot.makeMove(dealer.getHand().get(0), players[turnPlayer].getHand(), players[turnPlayer].getTimesSplit()));
        }
    }

    /**
     * @return the dealer of the current game is returned
     */
    public Player getDealer() {
        return dealer;
    }

    /**
     * the value of the players hand is calculated and returned as a int value, player is the current player
     * @param player, current player
     * @return hand value of player
     */
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

    /**
     * @return current deck
     */
    public Deck getDeck(){
        return deck;
    }

    /**
     * @return the Player array is returned
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * when the game is over the method checks if a player has won or loss and gives the payout or subtracts the money based
     * on if he won with a blackjack or with a normal hand. In a draw situation the payout is 1:1, with blackjack and higher value as the dealer 1:2,5.
     * If the player goes bust then he loses his bet.
     * The data is then added to the hashmap to keep track of the players statistics.
     * @throws CryptoException
     * @throws IOException
     */
    public void checkWinner() throws CryptoException, IOException {
        int ii = 0;
        int maxPoints = Data.valueMap.get("maxPoints");
        Data.valueMap.put("openStages", 2);
        int dealerValue = getValue(getDealer());
        int playerValue;
        int win;
        Data.valueMap.put("dealerPoints", dealerValue);
        for(Player p : players){
            Data.winMap.put(ii,0);
            win=0;
            playerValue = getValue(p);
            //first check if player is below or even on maxPoints (21- Blackjack)
            if(playerValue <= maxPoints){
                if(playerValue==maxPoints && dealerValue != maxPoints){ //blackjack
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
                }else if(dealerValue>playerValue && dealerValue<=maxPoints){ // dealer wins
                    //payout none
                    if(Data.betMap.get(ii) != null)
                        Data.payoutMap.put(ii, 0);
                }else if(dealerValue > maxPoints){ // player wins
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

        /**
         * calls javaFX to show the endscreen with the game results
         */
        Platform.runLater(() -> {
            ViewEndscreen viewEndscreen = new ViewEndscreen();
            try {
                viewEndscreen.openEndScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * the dealer hits if his hand value is under 17, otherwise he stands
     * @throws IOException
     * @throws CryptoException
     */
    public void dealersTurn() throws IOException, CryptoException {
        int maxPoints = Data.valueMap.get("maxPoints");
        dealer.removeLastCard();
        while (getValue(getDealer())<maxPoints-4){ // same as 21-4 == 17
            dealer.addCard(deck.draw());
        }
        checkWinner();
    }
}
