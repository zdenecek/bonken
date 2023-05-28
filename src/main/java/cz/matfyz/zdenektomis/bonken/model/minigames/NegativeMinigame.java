package com.bonken.model.minigames;

import com.bonken.model.Card;
import com.bonken.model.Round;


public class NegativeMinigame extends Minigame {

    private final NegativeMinigameType type;
    public Card.Suit getTrumps() {
        return null;
    }

    public String getName() {
        return type.name;
    }

    public int[] score(Round round) {
        return type.scoringFunction.call(round);
    }
    NegativeMinigame(NegativeMinigameType type) {
        this.type = type;
    }


}
