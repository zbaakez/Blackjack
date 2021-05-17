package Game;

public class Card {
    public enum CardType{
        HEARTS,
        CLUBS,
        DIAMONDS,
        SPADES
    }

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
        KING
    }

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
        return CardValue.ACE;
    }

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

    public static CardType parseCardType(int type){
        switch (type){
            case 1:
                return CardType.HEARTS;
            case 2:
                return CardType.CLUBS;
            case 3:
                return CardType.DIAMONDS;
            case 4:
                return CardType.SPADES;
        }
        return CardType.SPADES;
    }

    public static int cardTypeToInt(CardType type){
        switch (type){
            case HEARTS:
                return 1;
            case CLUBS:
                return 2;
            case DIAMONDS:
                return 3;
            case SPADES:
                return 4;
        }
        return -1;
    }

    private CardValue value;
    private CardType type;

    public Card(CardValue value, CardType type) {
        this.value = value;
        this.type = type;
    }

    public Card(int value, int type){
        this.value = parseCardValue(value);
        this.type = parseCardType(type);
    }


    public CardValue getValue() {
        return value;
    }

    public CardType getType() {
        return type;
    }

}
