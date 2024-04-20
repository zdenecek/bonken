package cz.matfyz.zdenektomis.bonken.model.minigames;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Round;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a minigame in the game of Bonken.
 */
public abstract class Minigame {
    public static final List<Minigame> all;

    static {
        var negative = Arrays.stream(NegativeMinigame.Type.values()).map(type -> new NegativeMinigame(type));
        var positive = Arrays.stream(Card.Suit.values()).map(suit -> new PositiveMinigame(suit));

        all = Stream.concat(negative, positive).toList();
    }

    /**
     * @param minigame
     * @return index of the minigame in the list of all minigames
     */
    public static int indexOf(Minigame minigame) {
        return all.indexOf(minigame);
    }


    /**
     * @return all minigames

     */
    public static List<Minigame> all() {
        return all;
    }


    /**
     * @return trump suit for the minigame. Some minigames do not have trumps.
     */
    public abstract Card.Suit getTrumps();

    /**
     * @return friendly name of the minigame
     */
    public abstract String getName();

    /**
     * @param round The finished round to score.
     * @return Scores for the round based on the minigame.
     */
    public abstract int[] score(Round round);
}
