package Game;

import java.util.ArrayList;
import java.util.List;

public class Node
{
    private ArrayList<Node> children;
    private int[] remainingCards;
    private List<Card> hand = new ArrayList<>();

    /**
     * returns children of current node
     * @return, children nodes
     */
    public ArrayList<Node> getChildren() { return children; }

    /**
     * adds a children node to current node
     * @param node, children node
     * @return, children node
     */
    public Node addChildren(Node node) { this.children.add(node); return node; }

    /**
     * returns hand of current node
     * @return, hand
     */
    public List<Card> getHand() { return this.hand; }

    /**
     * returns array of current node remainingCards
     * @return, remainingCards
     */
    public int[] getRemainingCards() { return remainingCards; }

    /**
     * constructor of node, initialises all attributes, and adds new card to
     * current node hand
     * @param remainingCards, list of amount of cards that are left in the deck
     * @param hand, hand with cards
     * @param card, card that gets added to the hand
     */
    public Node(int[] remainingCards, List<Card> hand, Card card)
    {
        this.children = new ArrayList<>();
        copyRemainingCards(remainingCards);
        this.hand = new ArrayList<>(hand);

        if(card != null)
        {
            int cardValue=Card.cardValueToInt(card.getValue());
            if(cardValue>9)
                cardValue=9;
            this.remainingCards[cardValue]--;
            this.hand.add(card);
        }
    }

    /**
     * copy the array remainingCards
     * @param remainingCards, array that needs to be copied
     */
    private void copyRemainingCards(int[] remainingCards)
    {
        this.remainingCards = new int[remainingCards.length];

        for(int count = 0; count < remainingCards.length; count++)
            this.remainingCards[count] = remainingCards[count];
    }
}
