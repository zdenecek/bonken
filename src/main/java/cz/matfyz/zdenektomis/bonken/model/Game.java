package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.model.minigames.PositiveMinigame;
import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;


public class Game {

    public static final int NUM_PLAYERS = 4;
    public static final int NUM_CARDS_IN_HAND = 13;
    private final boolean[] PlayerHasSelectedPositiveMinigame = new boolean[NUM_PLAYERS];
    public ArrayList<Round> rounds = new ArrayList<>();
    public ArrayList<Minigame> minigames = new ArrayList<>(Minigame.all());
    public ScoreBoard scoreBoard;
    private final Player[] players;
    private final SimpleEvent<RoundEventData> onRoundStartedEvent = new SimpleEvent<>();
    private final SimpleEvent<RoundEventData> onRoundEndedEvent = new SimpleEvent<>();
    private final SimpleEvent<GameEventData> onGameStartedEvent = new SimpleEvent<>();
    private final SimpleEvent<GameEventData> onGameEndedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onTrickStartedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onTrickEndedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onCardPlayedEvent = new SimpleEvent<>();
    private Position startingPlayer = Position.NORTH;

    public Game(Player[] players) {
        this.players = players;
        scoreBoard = new ScoreBoard(this);
    }

    public Player[] getPlayers() {
        return players;
    }

    public Player getPlayer(Position position) {
        return players[position.order];
    }

    public Round currentRound() {
        return rounds.get(rounds.size() - 1);
    }

    public Event<RoundEventData> onRoundStarted() {
        return onRoundStartedEvent;
    }

    public Event<RoundEventData> onRoundEnded() {
        return onRoundEndedEvent;
    }

    public Event<GameEventData> onGameStarted() {
        return onGameStartedEvent;
    }

    public Event<GameEventData> onGameEnded() {
        return onGameEndedEvent;
    }

    public Event<TrickEventData> onTrickStarted() {
        return onTrickEndedEvent;
    }

    public Event<TrickEventData> onTrickEnded() {
        return onTrickEndedEvent;
    }

    public Event<TrickEventData> onCardPlayed() {
        return onCardPlayedEvent;
    }

    public void startGame() {
        Platform.runLater(() -> onGameStartedEvent.fire(new GameEventData(this)));
        Platform.runLater(this::startRound);
    }

    private void onMinigameChosen(Minigame minigame) {
        minigames.remove(minigame);
        if (minigame.getClass() == PositiveMinigame.class) {
            PlayerHasSelectedPositiveMinigame[startingPlayer.order] = true;
        }

        Round round = createRound(minigame);
        rounds.add(round);
        round.onRoundEnded().addListener(data -> endRound());
        Platform.runLater(() -> onRoundStartedEvent.fire(new RoundEventData(round)));
        Platform.runLater(() -> round.start());
    }

    private void deal() {
        List<CardHand> hands = Deck.deal();
        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            players[i].setCardHand(hands.get(i));
        }
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

        deal();

        var minigames = this.minigames.stream().toList();
        // If player hasn't chosen a positive minigame and this is his last minigame, he has to choose one.
        if (!PlayerHasSelectedPositiveMinigame[startingPlayer.order] && minigames.size() <= NUM_PLAYERS) {
            minigames = minigames.stream().filter(
                    minigame -> minigame.getClass() == PositiveMinigame.class
            ).toList();
        }
        // If a player already chose a positive minigame, he can't choose another one.
        else if (PlayerHasSelectedPositiveMinigame[startingPlayer.order]) {
            minigames = minigames.stream().filter(
                    minigame -> minigame.getClass() != PositiveMinigame.class
            ).toList();
        }

        players[startingPlayer.order].requestSelectMinigame(minigames, this::onMinigameChosen);
    }

    private void endRound() {
        Platform.runLater(() -> onRoundEndedEvent.fire(new RoundEventData(rounds.get(rounds.size() - 1))));
        scoreBoard.updateScoreBoard(rounds.get(rounds.size() - 1));
        if (rounds.size() == Minigame.all().size())
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
