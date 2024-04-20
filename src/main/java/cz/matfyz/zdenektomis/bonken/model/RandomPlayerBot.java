package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Action;

import java.util.List;
import java.util.Random;

/**
 * A bot that makes random choices.
 */
public class RandomPlayerBot extends Player {

    private final Random random = new Random();

    /**
     * Creates a new bot.
     * @param position the position of the bot at the table
     */
    public RandomPlayerBot(Position position) {
        super(position);
    }

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
}
