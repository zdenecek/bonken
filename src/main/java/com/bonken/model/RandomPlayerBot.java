package com.bonken.model;

import com.bonken.model.minigames.Minigame;
import com.bonken.utils.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomPlayerBot extends Player {

    private final Random random = new Random();

    protected int getChoice(int max) {
        assert max > 0;
        return random.nextInt(max);
    }

    @Override
    public void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback) {
        callback.call(minigames.get(getChoice(minigames.size())));
    }

    @Override
    protected void playCard(Trick toTrick, Action<Card> callback) {
        var playableCards = getPlayableCards(toTrick);
        var i = getChoice(playableCards.size());
        callback.call(playableCards.get(i));
    }

    @Override
    public String getUsername() {
        return "Bot";
    }

    public RandomPlayerBot(Position position) {
        super(position);
    }
}
