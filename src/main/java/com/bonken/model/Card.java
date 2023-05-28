package com.bonken.model;



public record Card (Suit suit, Value value) {

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
        Suit( String name) {
            this.name = name;
        }
    }

    /** to be moved */
//    private String image;
//    public final String[] png = new String[]
//            {"2c.png", "3c.png", "4c.png", "5c.png", "6c.png", "7c.png", "8c.png", "9c.png", "10c.png", "jc.png", "qc.png", "kc.png", "ac.png",
//                    "2d.png", "3d.png","4d.png","5d.png","6d.png","7d.png","8d.png","9d.png","10d.png","jd.png","qd.png","kd.png","ad.png",
//                    "2h.png","3h.png","4h.png","5h.png","6h.png","7h.png","8h.png","9h.png","10h.png","jh.png","qh.png","kh.png","ah.png",
//                    "2s.png","3s.png","4s.png","5s.png","6s.png","7s.png","8s.png","9s.png","10s.png","js.png","qs.png","ks.png","as.png"};
//
//
//    public String getImage() {
//        return image;
//    }

    @Override
    public String toString() {
        return value.name + " of " + suit.name;
    }

}

