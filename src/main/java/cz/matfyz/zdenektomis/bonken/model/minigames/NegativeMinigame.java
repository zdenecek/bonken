package cz.matfyz.zdenektomis.bonken.model.minigames;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Round;


public class NegativeMinigame extends Minigame {

    private final NegativeMinigameType type;

    NegativeMinigame(NegativeMinigameType type) {
        this.type = type;
    }

    public Card.Suit getTrumps() {
        return null;
    }

    public String getName() {
        return type.name;
    }

    public int[] score(Round round) {
        return type.scoringFunction.call(round);
    }


}
