package cz.matfyz.zdenektomis.bonken.model;

/**
 * Represents a position of a player at a table in the game of Bonken.
 */
public enum Position {
    NORTH("North"),
    EAST("East"),
    SOUTH("South"),
    WEST("West");

    public final int order;
    public final String name;


    /**
     * Creates a new position.
     * @param name the name of the position
     */
    Position(String name) {
        this.order = this.ordinal();
        this.name = name;
    }


    /**
     * Get the next position in the order.
     * @return the next position in the order
     */
    public Position next() {
        return next(1);
    }

    /**
     * @param n number of positions to skip
     * @return the position n positions ahead in the order
     */
    public Position next(int n) {
        return values()[(ordinal() + n) % values().length];
    }
}
