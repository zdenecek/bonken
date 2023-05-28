package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Player {

    private final Position position;
    protected CardHand cardHand;

    public Player(Position position) {
        this.position = position;
    }

    public final Position getPosition() {
        return position;
    }

    public abstract void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback);

    protected abstract void playCard(Trick toTrick, Action<Card> callback);

    public abstract String getUsername();

    public final void requestCard(Trick toTrick, Action<Card> callback) {
        playCard(toTrick, card -> {
            if (!checkFollowsSuit(card, toTrick))
                // we throw exception. Player implementations should check it themselves
                throw new RuntimeException("Player " + this + " tried to play a card that doesn't follow suit!");
            this.cardHand.remove(card);
            callback.call(card);
        });
    }

    private boolean checkFollowsSuit(Card card, Trick trick) {
        if (trick.cards.size() == 0) return true;
        if (card.suit() == trick.cards.get(0).suit()) return true;
        for (Card c : cardHand) {
            if (c.suit() == trick.cards.get(0).suit()) return false;
        }
        return true;
    }

    public CardHand getCardHand() {
        return cardHand;
    }

    public void setCardHand(CardHand cardHand) {
        this.cardHand = cardHand;
    }

    protected List<Card> getPlayableCards(Trick toTrick) {
        if (toTrick.cards.size() == 0) return cardHand;
        Card.Suit trickSuit = toTrick.cards.get(0).suit();
        if (cardHand.stream().anyMatch(c -> c.suit() == trickSuit)) {
            return cardHand.stream().filter(c -> c.suit() == trickSuit).toList();
        }
        return cardHand;
    }

    protected Set<Card> getPlayableCardsAsSet(Trick toTrick) {
        if (toTrick.cards.size() == 0) return new HashSet<>(cardHand);
        Card.Suit trickSuit = toTrick.cards.get(0).suit();
        if (cardHand.stream().anyMatch(c -> c.suit() == trickSuit)) {
            return cardHand.stream().filter(c -> c.suit() == trickSuit).collect(Collectors.toSet());
        }
        return new HashSet<>(cardHand);
    }

    @Override
    public String toString() {
        return this.getUsername() + " @ " + position.name();
    }

}
