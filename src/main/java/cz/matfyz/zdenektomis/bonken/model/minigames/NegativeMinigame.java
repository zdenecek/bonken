package cz.matfyz.zdenektomis.bonken.model.minigames;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Round;
import cz.matfyz.zdenektomis.bonken.utils.Func;

/**
 * Represents a negative minigame in the game of Bonken. Such minigames are based on
 * assigning negative scores to players based on the cards they took.
 */
public class NegativeMinigame extends Minigame {

    private final Type type;

    NegativeMinigame(Type type) {
        this.type = type;
    }

    @Override
    public Card.Suit getTrumps() {
        return null;
    }

    @Override
    public String getName() {
        return type.name;
    }


    @Override
    public int[] score(Round round) {
        return type.scoringFunction.call(round);
    }

    /**
     * Types of negative minigames.
     */
    public enum Type {
        KingsAndJacks("Kings and Jacks", Scoring.scoreCardsWon(card ->
                switch (card.value()) {
                    case JACK -> -10;
                    case KING -> -20;
                    default -> 0;
                }
        )),
        Queens("Queens", Scoring.scoreCardsWon(card -> card.value() == Card.Value.QUEEN ? -15 : 0)),
        KingOfHearts("King of Hearts", Scoring.scoreCardsWon(card ->
                (card.value() == Card.Value.KING && card.suit() == Card.Suit.HEARTS) ? -45 : 0)),
        NoHearts("No Hearts", Scoring.scoreCardsWon(card ->
                (card.suit() == Card.Suit.HEARTS) ? -5 : 0)),
        NoTricks("No Tricks", Scoring.scoreTricksWon((__, trick) -> -10)),
        LastTrick("Last Trick", Scoring.scoreTricksWon((index, __) -> index == 12 ? -50 : 0)),
        BeerCard("Beer Card", Scoring.scoreCardsWon(card ->
                (card.value() == Card.Value.SEVEN && card.suit() == Card.Suit.DIAMONDS) ? -50 : 0));

        public final String name;
        public final Func<Round, int[]> scoringFunction;


        Type(String name, Func<Round, int[]> scoringFunction) {
            this.name = name;
            this.scoringFunction = scoringFunction;
        }
    }
}
