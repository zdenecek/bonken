package cz.matfyz.zdenektomis.bonken.model.minigames;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Round;
import cz.matfyz.zdenektomis.bonken.utils.Func;


public enum NegativeMinigameType {
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


    NegativeMinigameType(String name, Func<Round, int[]> scoringFunction) {
        this.name = name;
        this.scoringFunction = scoringFunction;
    }
}

