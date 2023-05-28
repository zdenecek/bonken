package com.bonken.model.minigames;

import com.bonken.model.Card;
import com.bonken.model.Round;
import com.bonken.model.Trick;
import com.bonken.utils.Func;
import com.bonken.utils.Funcc;

public class Scoring {

    public static Func<Round, int[]> scoreCardsWon(Func<Card, Integer> penaltyCardScoring) {
        return scoreTricksWon((__, trick) ->
                trick.cards.stream().map(penaltyCardScoring::call).reduce(0, Integer::sum)
        );
    }

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
