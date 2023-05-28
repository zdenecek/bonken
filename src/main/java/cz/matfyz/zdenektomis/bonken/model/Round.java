package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.application.Platform;

import java.util.ArrayList;

public class Round {
    public final Game game;
    public final Position startingPlayer;
    public final ArrayList<Trick> tricks = new ArrayList<>();
    public final Minigame minigame;
    private final SimpleEvent<RoundEventData> onRoundStartedEvent = new SimpleEvent<>();
    private final SimpleEvent<RoundEventData> onRoundEndedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onTrickStartedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onTrickEndedEvent = new SimpleEvent<>();

    public Round(Game game, Minigame minigame, Position startingPlayer) {
        this.game = game;
        this.minigame = minigame;
        this.startingPlayer = startingPlayer;
    }

    public Trick currentTrick() {
        return tricks.get(tricks.size() - 1);
    }

    public Event<RoundEventData> onRoundStarted() {
        return onRoundStartedEvent;
    }

    public Event<RoundEventData> onRoundEnded() {
        return onRoundEndedEvent;
    }

    public Event<TrickEventData> onTrickStarted() {
        return onTrickStartedEvent;
    }

    public Event<TrickEventData> onTrickEnded() {
        return onTrickEndedEvent;
    }

    public Card.Suit getTrumps() {
        return minigame.getTrumps();
    }

    public void start() {
        Platform.runLater(() -> onRoundStartedEvent.fire(new RoundEventData(this)));
        startTrick(startingPlayer);
    }

    private void startTrick(Position firstToPlay) {
        Trick trick = new Trick(this, firstToPlay);
        Platform.runLater(() -> onTrickStartedEvent.fire(new TrickEventData(trick)));
        trick.onTrickEnded().addListener(data -> endTrick());
        tricks.add(trick);
        trick.start();
    }

    private void endTrick() {
        Platform.runLater(() -> onTrickEndedEvent.fire(new TrickEventData(tricks.get(tricks.size() - 1))));
        if (tricks.size() == Game.NUM_CARDS_IN_HAND) {
            Platform.runLater(() -> end());
        } else {
            Position nextToPlay = tricks.get(tricks.size() - 1).getWinner();
            startTrick(nextToPlay);
        }
    }

    private void end() {
        Platform.runLater(() -> onRoundEndedEvent.fire(new RoundEventData(this)));
    }


    public Trick lastFinishedTrick() {
        return tricks.stream().filter(trick -> trick.isFinished()).reduce((a, b) -> b).orElse(null);
    }
}
