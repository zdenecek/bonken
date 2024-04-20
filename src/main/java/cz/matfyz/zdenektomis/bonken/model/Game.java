package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.model.minigames.PositiveMinigame;
import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a game of Bonken.
 */
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

    /**
     * @return players in the game
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * @param position of the player
     * @return player at the given position
     */
    public Player getPlayer(Position position) {
        return players[position.order];
    }

    /**
     * @return current round
     */
    public Round currentRound() {
        return rounds.get(rounds.size() - 1);
    }

    /**
     * @return event that is fired when a round starts
     */
    public Event<RoundEventData> onRoundStarted() {
        return onRoundStartedEvent;
    }

    /**
     * @return event that is fired when a round ends
     */
    public Event<RoundEventData> onRoundEnded() {
        return onRoundEndedEvent;
    }

    /**
     * @return event that is fired when a game starts
     */
    public Event<GameEventData> onGameStarted() {
        return onGameStartedEvent;
    }

    /**
     * @return event that is fired when a game ends
     */
    public Event<GameEventData> onGameEnded() {
        return onGameEndedEvent;
    }

    /**
     * @return event that is fired when a trick starts
     */
    public Event<TrickEventData> onTrickStarted() {
        return onTrickEndedEvent;
    }

    /**
     * @return event that is fired when a trick ends
     */
    public Event<TrickEventData> onTrickEnded() {
        return onTrickEndedEvent;
    }

    /**
     * @return event that is fired when a card is played
     */
    public Event<TrickEventData> onCardPlayed() {
        return onCardPlayedEvent;
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        Platform.runLater(() -> onGameStartedEvent.fire(new GameEventData(this)));
        Platform.runLater(this::startRound);
    }

    private void startRound() {

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
