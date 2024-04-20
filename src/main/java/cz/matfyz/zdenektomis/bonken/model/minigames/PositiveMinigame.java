package cz.matfyz.zdenektomis.bonken.model.minigames;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Round;

/**
 * Represents a positive minigame in the game of Bonken. Positive minigames are based on
 * assigning positive scores to players based on the number of tricks they won.
 */
public class PositiveMinigame extends Minigame {

    private final Card.Suit trumps;

    PositiveMinigame(Card.Suit trumps) {
        this.trumps = trumps;
    }

    @Override
    public Card.Suit getTrumps() {
        return trumps;
    }

    @Override
    public String getName() {
        return trumps.name;
    }

    @Override
    public int[] score(Round round) {
        return Scoring.scoreTricksWon((__, ___) -> 10).call(round);
    }

}
