package cz.matfyz.zdenektomis.bonken.model;


public record Card(Suit suit, Value value) {

    @Override
    public String toString() {
        return value.name + " of " + suit.name;
    }

    public enum Value {
        TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"), SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"),
        JACK(11, "Jack"), QUEEN(12, "Queen"), KING(13, "King"), ACE(14, "Ace");
        public final int value;
        public final String name;

        Value(int value, String name) {
            this.value = value;
            this.name = name;
        }
    }


    public enum Suit {
        CLUBS("Clubs"),
        DIAMONDS("Diamonds"),
        HEARTS("Hearts"),
        SPADES("Spades");
        public final String name;

        Suit(String name) {
            this.name = name;
        }
    }

}

