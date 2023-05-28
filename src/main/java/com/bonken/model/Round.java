package com.bonken.model;

import com.bonken.model.minigames.Minigame;
import com.bonken.utils.Event;
import com.bonken.utils.SimpleEvent;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class Round {
    public final Game game;
    public final Position startingPlayer;
    public final ArrayList<Trick> tricks = new ArrayList<>();
    public final Minigame minigame;

    private SimpleEvent<RoundEventData> onRoundStartedEvent = new SimpleEvent<>();
    public Event<RoundEventData> onRoundStarted() {
        return onRoundStartedEvent;
    }
    private SimpleEvent<RoundEventData> onRoundEndedEvent = new SimpleEvent<>();
    public Event<RoundEventData> onRoundEnded() {
        return onRoundEndedEvent;
    }
    private SimpleEvent<TrickEventData> onTrickStartedEvent = new SimpleEvent<>();
    public Event<TrickEventData> onTrickStarted() {
        return onTrickStartedEvent;
    }
    private SimpleEvent<TrickEventData> onTrickEndedEvent = new SimpleEvent<>();
    public Event<TrickEventData> onTrickEnded() {
        return onTrickEndedEvent;
    }

    public Round(Game game, Minigame minigame, Position startingPlayer) {
        this.game = game;
        this.minigame = minigame;
        this.startingPlayer = startingPlayer;
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
        if(tricks.size() == Game.NUM_CARDS_IN_HAND) {
            Platform.runLater(() -> end());
        } else {
            Position nextToPlay = tricks.get(tricks.size() - 1).getWinner();
            startTrick(nextToPlay);
        }
    }

    private void end() {
        Platform.runLater(() -> onRoundEndedEvent.fire(new RoundEventData(this)));
    }


}
