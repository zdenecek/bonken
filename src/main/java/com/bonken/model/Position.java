package com.bonken.model;

public enum Position {
    NORTH("North"),
    EAST("East"),
    SOUTH("South"),
    WEST("West");

    public final int order;
    public final String name;

    private Position(String name) {
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
