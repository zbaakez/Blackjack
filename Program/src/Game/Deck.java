package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    /**
     * constructor of Deck which consistsof
     * 6 Decks (52*6 Cards)
     */
    public Deck() {
        for(int c=0;c<6;c++){
            for(int i=0;i<4;i++){
                for(int j=0;j<13;j++){
                     Card card = new Card(j, i);
                     cards.add(card);
                }
            }
        }
        for(int count = 0; count < 5; count++)
            Collections.shuffle(cards);

    }
    /**
     * a card from the list is returned and then removed from the list
     * @return
     */
    public Card draw(){
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }

    /**
     * counts remaining cards of deck
     * @return remaining cards
     */
    public int[] countDeckCards(){
        int[] remainingCards=new int[10];
        int cardValue;

        for (Card card:cards) {
            cardValue=Card.cardValueToInt(card.getValue());
            if(cardValue>9)
                cardValue=9;
            remainingCards[cardValue]++;
        }
        return remainingCards;
    }
}
