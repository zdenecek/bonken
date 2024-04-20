package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Action;
import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.application.Platform;

import java.util.ArrayList;

/**
 * Represents a round in the game of Bonken.
 */
public class Round {
    public final Game game;
    public final Position startingPlayer;
    public final ArrayList<Trick> tricks = new ArrayList<>();
    public final Minigame minigame;
    private final SimpleEvent<RoundEventData> onRoundStartedEvent = new SimpleEvent<>();
    private final SimpleEvent<RoundEventData> onRoundEndedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onTrickStartedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onTrickEndedEvent = new SimpleEvent<>();

    private final Action<Runnable> executeLater;

    /**
     * Creates a new round.
     * @param game the game the round is played in
     * @param minigame the minigame of the round
     * @param startingPlayer the player that starts the round
     * @param executeLater a function that executes a runnable later
     */
    public Round(Game game, Minigame minigame, Position startingPlayer, Action<Runnable> executeLater) {
        this.game = game;
        this.minigame = minigame;
        this.startingPlayer = startingPlayer;
        this.executeLater = executeLater;
    }

    /**
     * Get the current trick.
     * @return current trick, null if no trick has been started yet
     */
    public Trick currentTrick() {
        return tricks.get(tricks.size() - 1);
    }

    /**
     * Get the event that is fired when the round starts.
     * @return the event that is fired when the round starts
     */
    public Event<RoundEventData> onRoundStarted() {
        return onRoundStartedEvent;
    }

    /**
     * Get the event that is fired when the round ends.
     * @return the event that is fired when the round ends
     */
    public Event<RoundEventData> onRoundEnded() {
        return onRoundEndedEvent;
    }

    /**
     * Get the event that is fired when a trick starts.
     * @return the event that is fired when a trick starts
     */
    public Event<TrickEventData> onTrickStarted() {
        return onTrickStartedEvent;
    }

    /**
     * @return the event that is fired when a trick ends
     */
    public Event<TrickEventData> onTrickEnded() {
        return onTrickEndedEvent;
    }

    /**
     * Get the trumps of the round.
     * @return the trumps of the round, null if no trumps have been selected yet or the game has no trumps
     */
    public Card.Suit getTrumps() {
        return minigame.getTrumps();
    }

    /**
     * Starts the round.
     */
    public void start() {
        this.executeLater.call(() -> onRoundStartedEvent.fire(new RoundEventData(this)));
        startTrick(startingPlayer);
    }

    /**
     * Get the last finished trick.
     * @return the last finished trick, null if no trick has been finished yet
     */
    public Trick getLastFinishedTrick() {
        return tricks.stream().filter(trick -> trick.isFinished()).reduce((a, b) -> b).orElse(null);
    }



    private void startTrick(Position firstToPlay) {
        Trick trick = new Trick(this, firstToPlay, executeLater);
        this.executeLater.call(() -> onTrickStartedEvent.fire(new TrickEventData(trick)));
        trick.onTrickEnded().addListener(data -> endTrick());
        tricks.add(trick);
        trick.start();
    }

    private void endTrick() {
        this.executeLater.call(() -> onTrickEndedEvent.fire(new TrickEventData(tricks.get(tricks.size() - 1))));
        if (tricks.size() == Game.NUM_CARDS_IN_HAND) {
            this.executeLater.call(() -> end());
        } else {
            Position nextToPlay = tricks.get(tricks.size() - 1).getWinner();
            startTrick(nextToPlay);
        }
    }

    private void end() {
        this.executeLater.call(() -> onRoundEndedEvent.fire(new RoundEventData(this)));
    }

}
