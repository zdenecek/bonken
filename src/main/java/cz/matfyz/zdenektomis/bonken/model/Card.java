package cz.matfyz.zdenektomis.bonken.model;


/**
 * Represents a card in a deck of cards.
 * @param suit clubs, diamonds, hearts, spades
 * @param value 2 to 10, Jack, Queen, King, Ace
 */
public record Card(Suit suit, Value value) {

    @Override
    public String toString() {
        return value.name + " of " + suit.name;
    }

    /**
     * Represents a value of a card. 2 to 10, Jack, Queen, King, Ace
     */
    public enum Value {
        TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"),
        JACK(11, "Jack"), QUEEN(12, "Queen"), KING(13, "King"), ACE(14, "Ace");
        public final int value;
        public final String name;

        Value(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public char toChar() {
            if (this.value >= 10) return this.name().charAt(0);
            return (char) ('0' + this.value);
        }

    }


    /**
     * Represents a suit of a card. Clubs, Diamonds, Hearts, Spades
     */
    public enum Suit {
        CLUBS("Clubs"),
        DIAMONDS("Diamonds"),
        HEARTS("Hearts"),
        SPADES("Spades");
        public final String name;

        Suit(String name) {
            this.name = name;
        }

        /**
         * @return a pretty character representing the suit
         */
        public char toPrettyChar() {
            switch (this) {
                case CLUBS:
                    return '♣';
                case DIAMONDS:
                    return '♦';
                case HEARTS:
                    return '♥';
                case SPADES:
                    return '♠';
                default:
                    return '?';
            }
        }
    }

}

