package Game;

import java.util.ArrayList;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public Deck() {
        for(int c=0;c<6;c++){
            for(int i=0;i<4;i++){
                for(int j=0;j<13;j++){
                     Card card = new Card(j, i);
                     cards.add(card);
                }
            }
        }
    }

    public Card draw(){
        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }






}
