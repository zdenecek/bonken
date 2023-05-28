package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.application.Platform;

import java.util.ArrayList;


public class Trick {
    public final Position firstPlayer;
    public final ArrayList<Card> cards = new ArrayList<>();
    private final Round round;
    private final SimpleEvent<TrickEventData> onTrickStartedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onTrickEndedEvent = new SimpleEvent<>();
    private final SimpleEvent<TrickEventData> onCardPlayedEvent = new SimpleEvent<>();
    private Player currentlyRequestedPlayer;

    public Trick(Round round, Position firstPlayer) {
        this.round = round;
        this.firstPlayer = firstPlayer;
    }

    public Event<TrickEventData> onTrickStarted() {
        return onTrickStartedEvent;
    }

    public Event<TrickEventData> onTrickEnded() {
        return onTrickEndedEvent;
    }

    public Event<TrickEventData> onCardPlayed() {
        return onCardPlayedEvent;
    }

    public void start() {
        requestCardFrom(firstPlayer);
        Platform.runLater(() -> onTrickStartedEvent.fire(new TrickEventData(this)));
    }

    private void requestCardFrom(Position position) {
        currentlyRequestedPlayer = round.game.getPlayer(position);
        currentlyRequestedPlayer.requestCard(this, (card) -> this.addCard(currentlyRequestedPlayer, card));
    }

    public void addCard(Player player, Card card) {

        if (player != currentlyRequestedPlayer) {
            throw new RuntimeException("Player " + player + " tried to play a card, but it's not their turn!");
        }

        Platform.runLater(() -> onCardPlayedEvent.fire(new TrickEventData(this)));

        cards.add(card);

        if (this.isFinished())
            end();
        else
            requestCardFrom(player.getPosition().next());
    }

    private void end() {
        Platform.runLater(() -> onTrickEndedEvent.fire(new TrickEventData(this)));
        cards.get(0).suit();
    }

    public Position getWinner() {
        var trumps = round.getTrumps();
        if (trumps == null) {
            return getTrickWinnerNoTrumps();
        } else {
            return getTrickWinnerWithTrumps(trumps);
        }
    }

    private Position getTrickWinnerWithTrumps(Card.Suit trumps) {
        if (this.cards.stream().anyMatch(card -> card.suit() == trumps)) {
            return getPositionOfHighestCardInSuit(trumps);
        } else {
            return getTrickWinnerNoTrumps();
        }
    }

    private Position getTrickWinnerNoTrumps() {
        return getPositionOfHighestCardInSuit(cards.get(0).suit());
    }

    private Position getPositionOfHighestCardInSuit(Card.Suit suit) {
        Card winner = cards.stream().filter(c -> c.suit() == suit).max((a,b) ->
               a.value().value - b.value().value).get();
        var i = cards.indexOf(winner);
        return firstPlayer.next(i);
    }

    public boolean isFinished() {
        return this.cards.size() == Game.NUM_PLAYERS;
    }

}

