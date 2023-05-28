package cz.matfyz.zdenektomis.bonken.model;

public enum Position {
    NORTH("North"),
    EAST("East"),
    SOUTH("South"),
    WEST("West");

    public final int order;
    public final String name;

    Position(String name) {
        this.order = this.ordinal();
        this.name = name;
    }

    public Position next() {
        return next(1);
    }

    public Position next(int n) {
        return values()[(ordinal() + n) % values().length];
    }
}
