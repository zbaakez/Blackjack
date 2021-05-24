package Game;

import java.util.ArrayList;
import java.util.List;

public class MethodsForBlackJackBot
{
    private int pointsToWin;
    private int maxValueToSplit;

    private int[] remainingCards;
    private List<Card> hand = new ArrayList<>();

    /**
     * constructor of MethodsForBlackJackBot
     * @param remainingCards, amount of cards stored in array
     * @param pointsToWin, pointsToWin
     * @param maxValueToSplit, how many times it is possible to split
     */
    public MethodsForBlackJackBot(int[] remainingCards, int pointsToWin, int maxValueToSplit)
    {
        this.pointsToWin = pointsToWin;
        this.maxValueToSplit = maxValueToSplit;
        this.remainingCards = remainingCards;
    }

    /**
     * determines what move is the best for the bot
     * @param upCard, dealer upCard
     * @param hand, current bot hand
     * @param timesSplit, how many times the bot already splitted
     * @return Action witch the bot performs
     */
    public BlackJack.Action makeMove(Card upCard, List<Card> hand, int timesSplit)
    {
        if(hand.size()<3) {
            if (checkIfSplit(hand) && timesSplit < this.maxValueToSplit)
            {
                System.out.println("split");
                return BlackJack.Action.SPLIT;
            }
            else if (checkIfDouble(upCard, hand))
                return BlackJack.Action.DOUBLE_DOWN;
            else if (takeCardOrNot(this.remainingCards, calculateTotal(hand)))
                return BlackJack.Action.HIT;
            else
                return BlackJack.Action.STAND;
        }
        else
        if(takeCardOrNot(this.remainingCards,calculateTotal(hand)))
            return BlackJack.Action.HIT;
        else
            return BlackJack.Action.STAND;
    }

    /**
     * checkIfItIsPossibleToSplit
     * @param hand, current hand
     * @return, true if it is possible
     */
    private boolean checkIfSplit(List<Card> hand)
    {
        if(hand.get(0).getValue() == hand.get(1).getValue())
            if(makeForNode(new Node(this.remainingCards, hand, null)) > makeForNode(new Node(this.remainingCards, generateHand(hand.get(0),null), null)) + 0.62)
                return true;
        return false;
    }

    /**
     * creates and evaluates the tree
     * @param node, root node
     * @return, probability of Card that gets drawn
     */
    private double makeForNode(Node node)
    {
        double value = 0;
        makeNodesForPlayer(node);
        value = evaluationTree(node);
        System.out.println(value);
        //freeSpace(node);
        return value;
    }

    /**
     * creates tree
     * @param node, current node
     */
    private void makeNodesForPlayer(Node node)
    {
        for(int count = 1; takeCardOrNot(node.getRemainingCards(), calculateTotal(node.getHand())) && count < 11; count++)
            if(node.getRemainingCards()[count - 1] != 0)
                makeNodesForPlayer(node.addChildren(new Node(node.getRemainingCards(), node.getHand(), new Card(0, count))));
    }

    /**
     * check if the bot sould double
     * @param upCard, dealerUpCard
     * @param hand, current bot hand
     * @return, returns true if the bot should double
     */
    private boolean checkIfDouble(Card upCard, List<Card>hand)    //not done
    {
        Node root = new Node(this.remainingCards, generateHand(upCard,null), null);
        makeNodesForDealer(root);

        double test = evaluationTree(root);
        double test1;

        if(this.pointsToWin - calculateTotal(hand) - 11 >= 0) //ass = 11
            test1 = probability(11);
        else
            test1 = probability(1);
        if((test > this.pointsToWin + 1 && calculateTotal(hand) + test1 <= this.pointsToWin) || calculateTotal(hand) + test1 > test && calculateTotal(hand) + test1 <= this.pointsToWin)
            return true;
        else
            return false;
    }

    /**
     * creates tree for dealer
     * @param node, current node
     */
    private void makeNodesForDealer(Node node)
    {
        for(int count = 1; calculateTotal(node.getHand()) < 18 && count < 11; count++)
            if(node.getRemainingCards()[count - 1] != 0)
                makeNodesForDealer(node.addChildren(new Node(node.getRemainingCards(), node.getHand(), new Card(0, count))));

    }

    /**
     * returns probability of aceValue
     * @param aceValue, valueOfAce
     * @return, probability
     */
    private double probability(int aceValue)
    {
        double value1 = this.remainingCards[0] * aceValue;
        int value2 = this.remainingCards[0];
        for(int count = 2; count <= this.remainingCards.length; count++)
        {
            value1 += this.remainingCards[count - 1] * count;
            value2 += this.remainingCards[count - 1];
        }
        return value1 / value2;
    }

    /**
     * calculates if the bot should draw a card or not
     * 0.62 is not a random value, it is the value with the highest probability to win
     * @param remainingCards, remaining cards values that are in the deck
     * @param totalHand, total value of the hand
     * @return, returns true if bot should take a card
     */
    private boolean takeCardOrNot(int[] remainingCards, int totalHand)
    {
        if(totalHand > remainingCards.length)
        {
            int border = this.pointsToWin - totalHand;

            if(!(border > 0 && (calculateAmountOfCards(remainingCards, border, 0) > calculateAmountOfCards( remainingCards, remainingCards.length,border + 1) * 0.62)))
                return false;
        }
        return true;
    }

    /**
     * calculates possibilities of drawing a card that leads the hand over pointsToWin
     * @param remainingCards, remaining cards values that are in the deck
     * @param max, maximum value
     * @param min, minimum value
     * @return, total amount of cards in the range
     */
    private int calculateAmountOfCards(int[] remainingCards, int max, int min)
    {
        int count = 0;

        while(max > min)
        {
            count += remainingCards[min];
            min++;
        }
        return count;
    }

    /**
     * @param node, root Node
     * @return, calculated value
     */
    private double evaluationTree(Node node)
    {
        double[] countingArray = {0, 0};
        countingArray = watchTree(node, countingArray);
        return countingArray[0] / countingArray[1];
    }

    /**
     * evaluates the created tree
     * @param node, current Node
     * @param countingArray, array with values for calculation
     * @return, the array with values
     */
    private double[] watchTree(Node node, double[] countingArray)
    {
        for(int count = 0; count < node.getRemainingCards().length; count++)
            if(node.getChildren().size() == 0)
            {
                countingArray[0] += calculateTotal(node.getHand());
                countingArray[1]++;
            }
        if(node.getChildren().size() > 0)
            for (Node childrenNodes : node.getChildren())
                countingArray = watchTree(childrenNodes, countingArray);

        return countingArray;
    }

    /**
     * creates new hand with two or one card
     * @param one, first card
     * @param two, second card
     * @return, created hand
     */
    private List<Card> generateHand(Card one, Card two) {

        List<Card> hand=new ArrayList<>();
        hand.add(one);

        if(two!=null)
            hand.add(two);
        return hand;
    }

    /**
     * calculate the total amount of the given hand
     * @param hand, given hand
     * @return, total amount of the hand
     */
    private int calculateTotal(List<Card> hand)
    {
        int totalAmount = 0, value;
        boolean aceFlag = false;

        for(Card card: hand)
        {
            value = Card.cardValueToInt(card.getValue())+1;

            if(value > 10)
                value = 10;
            else if(value == 1)
                aceFlag = true;
            totalAmount += value;
        }
        if(aceFlag && totalAmount + 10 <= 21)
            return totalAmount + 10;
        else
            return totalAmount;
    }
}