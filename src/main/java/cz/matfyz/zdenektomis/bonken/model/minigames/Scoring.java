package cz.matfyz.zdenektomis.bonken.model.minigames;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Round;
import cz.matfyz.zdenektomis.bonken.model.Trick;
import cz.matfyz.zdenektomis.bonken.utils.Func;
import cz.matfyz.zdenektomis.bonken.utils.Funcc;

/**
 * Utility class for scoring minigames.
 */
public class Scoring {


    /**
     * @param penaltyCardScoring Function that assigns a score to a card.
     * @return Function that assigns scores to players based on the cards they took.
     */
    public static Func<Round, int[]> scoreCardsWon(Func<Card, Integer> penaltyCardScoring) {
        return scoreTricksWon((__, trick) ->
                trick.cards.stream().map(penaltyCardScoring::call).reduce(0, Integer::sum)
        );
    }

    /**
     * @param trickScoring Function that assigns a score to a trick.
     * @return Function that assigns scores to players based on the tricks they won.
     */
    public static Func<Round, int[]> scoreTricksWon(Funcc<Integer, Trick, Integer> trickScoring) {
        return round -> {
            int[] scores = new int[round.game.getPlayers().length];
            int index = 0;
            for (var trick : round.tricks) {
                if (!trick.isFinished()) {
                    continue;
                }
                var winner = trick.getWinner();
                scores[winner.order] += trickScoring.call(index, trick);
                index++;
            }
            return scores;
        };
    }
}
