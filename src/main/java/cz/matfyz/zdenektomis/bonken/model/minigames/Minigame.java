package cz.matfyz.zdenektomis.bonken.model.minigames;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Round;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public abstract class Minigame {
    public static final List<Minigame> all;

    static {
        var negative = Arrays.stream(NegativeMinigameType.values()).map(type -> new NegativeMinigame(type));
        var positive = Arrays.stream(Card.Suit.values()).map(suit -> new PositiveMinigame(suit));

        all = Stream.concat(negative, positive).toList();
    }

    public static int indexOf(Minigame minigame) {
        return all.indexOf(minigame);
    }

    public static List<Minigame> all() {
        return all;
    }

    public abstract Card.Suit getTrumps();

    public abstract String getName();

    public abstract int[] score(Round round);
}
