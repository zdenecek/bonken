package com.bonken.model;

import com.bonken.model.minigames.Minigame;
import com.bonken.model.minigames.PositiveMinigame;
import com.bonken.utils.Event;
import com.bonken.utils.SimpleEvent;
import javafx.application.Platform;

import java.util.ArrayList;


public class Game {

    public static final int NUM_PLAYERS = 4;
    public static final int NUM_CARDS_IN_HAND = 13;

    private Player[] players;
    public Player[] getPlayers() {
        return players;
    }

    private final boolean[] PlayerHasSelectedPositiveMinigame = new boolean[NUM_PLAYERS];

    public Player getPlayer(Position position) {
        return players[position.order];
    }
    public ArrayList<Round> rounds = new ArrayList<>();

    private SimpleEvent<RoundEventData> onRoundStartedEvent = new SimpleEvent<>();
    public Event<RoundEventData> onRoundStarted() {
        return onRoundStartedEvent;
    }
    private SimpleEvent<RoundEventData> onRoundEndedEvent = new SimpleEvent<>();
    public Event<RoundEventData> onRoundEnded() {
        return onRoundEndedEvent;
    }
    private SimpleEvent<GameEventData> onGameStartedEvent = new SimpleEvent<>();
    public Event<GameEventData> onGameStarted() {
        return onGameStartedEvent;
    }
    private SimpleEvent<GameEventData> onGameEndedEvent = new SimpleEvent<>();
    public Event<GameEventData> onGameEnded() {
        return onGameEndedEvent;
    }

    private SimpleEvent<TrickEventData> onTrickStartedEvent = new SimpleEvent<>();
    public Event<TrickEventData> onTrickStarted() {
        return onTrickEndedEvent;
    }

    private SimpleEvent<TrickEventData> onTrickEndedEvent = new SimpleEvent<>();
    public Event<TrickEventData> onTrickEnded() {
        return onTrickEndedEvent;
    }

    private SimpleEvent<TrickEventData> onCardPlayedEvent = new SimpleEvent<>();
    public Event<TrickEventData> onCardPlayed() {
        return onCardPlayedEvent;
    }


    public ArrayList<Minigame> minigames = new ArrayList<>(Minigame.all());
    public ScoreBoard scoreBoard;

    public Game(Player[] players) {
        this.players = players;
        scoreBoard = new ScoreBoard(this);
    }

    private Position startingPlayer = Position.NORTH;

    public void startGame() {
        Platform.runLater(() -> onGameStartedEvent.fire(new GameEventData(this)));
        Platform.runLater(this::startRound);
    }

    private void onMinigameChosen(Minigame minigame) {
        minigames.remove(minigame);
        if(minigame.getClass() == PositiveMinigame.class) {
            PlayerHasSelectedPositiveMinigame[startingPlayer.order] = true;
        }

        Round round = createRound(minigame);
        rounds.add(round);
        round.onRoundEnded().addListener(data -> endRound());
        Platform.runLater(() -> onRoundStartedEvent.fire(new RoundEventData( round)));
        Platform.runLater(() -> round.start());
    }

    private Round createRound(Minigame minigame) {
        var round = new Round(this, minigame, startingPlayer.next(3));
        round.onTrickEnded().addListener(data -> onTrickEndedEvent.fire(data));
        round.onTrickStarted().addListener(data -> {
            onTrickStartedEvent.fire(data);
            data.trick().onCardPlayed().addListener(cardData -> onCardPlayedEvent.fire(cardData));
        });

        return round;
    }


    public void startRound() {

        var minigames = this.minigames.stream().toList();
        // If player hasn't chosen a positive minigame and this is his last minigame, he has to choose one.
        if(!PlayerHasSelectedPositiveMinigame[startingPlayer.order] && minigames.size() <= NUM_PLAYERS) {
            minigames =  minigames.stream().filter(
                    minigame -> minigame.getClass() == PositiveMinigame.class
            ).toList();
        }
        // If a player already chose a positive minigame, he can't choose another one.
        else if(PlayerHasSelectedPositiveMinigame[startingPlayer.order]) {
            minigames = minigames.stream().filter(
                    minigame -> minigame.getClass() != PositiveMinigame.class
            ).toList();
        }

        players[startingPlayer.order].requestSelectMinigame(minigames, this::onMinigameChosen);
    }

    private void endRound()
    {
        Platform.runLater(() -> onRoundEndedEvent.fire(new RoundEventData(rounds.get(rounds.size() - 1))));
        scoreBoard.updateScoreBoard(rounds.get(rounds.size() - 1));
        if(rounds.size() == Minigame.all().size())
            end();
        else {
            this.startingPlayer = startingPlayer.next();
            startRound();
        }
    }
    private void end() {
        Platform.runLater(() -> onGameEndedEvent.fire(new GameEventData(this)));
    }


}
