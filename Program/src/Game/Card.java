package Game;

public class Card {
    /**
     * enum which has different card types as value
     */
    public enum CardType{
        HEARTS,
        CLUBS,
        DIAMONDS,
        SPADES,
        TurnedCard
    }

    /**
     * enum which has different card values as values
     */
    public enum CardValue{
        ACE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING,
        TurnedCard
    }

    /**
     * an int value as parameter returns the corresponding cardvalue from the enum CardValue
     * @param value, int value of card
     * @return Cardvalue of card
     */
    public static CardValue parseCardValue(int value){
        switch (value){
            case 0:
                return CardValue.ACE;
            case 1:
                return CardValue.TWO;
            case 2:
                return CardValue.THREE;
            case 3:
                return CardValue.FOUR;
            case 4:
                return CardValue.FIVE;
            case 5:
                return CardValue.SIX;
            case 6:
                return CardValue.SEVEN;
            case 7:
                return CardValue.EIGHT;
            case 8:
                return CardValue.NINE;
            case 9:
                return CardValue.TEN;
            case 10:
                return CardValue.JACK;
            case 11:
                return CardValue.QUEEN;
            case 12:
                return CardValue.KING;
        }
        return CardValue.TurnedCard;
    }

    /**
     * a CardValue as parameter returns the corresponding int value
     * @param value, Cardvalue of card
     * @return int value of card
     */
    public static int cardValueToInt(CardValue value){
        switch (value){
            case ACE:
                return 0;
            case TWO:
                return 1;
            case THREE:
                return 2;
            case FOUR:
                return 3;
            case FIVE:
                return 4;
            case SIX:
                return 5;
            case SEVEN:
                return 6;
            case EIGHT:
                return 7;
            case NINE:
                return 8;
            case TEN:
                return 9;
            case JACK:
                return 10;
            case QUEEN:
                return 11;
            case KING:
                return 12;
        }
        return -1;
    }

    /**
     * an int parameter returns the corresponding CardType value
     * @param type, type of card as int
     * @return CardType
     */
    public static CardType parseCardType(int type){
        switch (type){
            case 0:
                return CardType.HEARTS;
            case 1:
                return CardType.CLUBS;
            case 2:
                return CardType.DIAMONDS;
            case 3:
                return CardType.SPADES;
        }
        return CardType.TurnedCard;
    }

    /**
     * a CardType as parameter returns the corresponding int value
     * @param type, Cardtype
     * @return int type of card
     */
    public static int cardTypeToInt(CardType type){
        switch (type){
            case HEARTS:
                return 0;
            case CLUBS:
                return 1;
            case DIAMONDS:
                return 2;
            case SPADES:
                return 3;
        }
        return -1;
    }

    private CardValue value;
    private CardType type;

    /**
     * constructor of Card with parameters value CardValue and type as CardType
     * @param value, Cardvalue of card
     * @param type Cardtype of card
     */
    public Card(CardValue value, CardType type) {
        this.value = value;
        this.type = type;
    }

    /**
     * constructor of Card with parameters value as int and type as int
     * @param value, int value of card
     * @param type, int type of card
     */
    public Card(int value, int type){
        this.value = parseCardValue(value);
        this.type = parseCardType(type);
    }


    /**
     * getter that returs CardValue of Card
     * @return
     */
    public CardValue getValue() {
        return value;
    }

    /**
     * getter that returns Cardtype of Card
     * @return
     */
    public CardType getType() {
        return type;
    }
}