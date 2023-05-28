package com.bonken.model;

import com.bonken.model.minigames.Minigame;
import com.bonken.utils.Action;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {

    private final Position position;

    public final Position getPosition() {
        return position;
    }

    public abstract void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback);

    public final void requestCard(Trick toTrick, Action<Card> callback) {
        playCard(toTrick, card -> {
            if(!checkFollowsSuit(card, toTrick))
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

    protected abstract void playCard(Trick toTrick, Action<Card> callback);

    public void setCardHand(CardHand cardHand) {
        this.cardHand = cardHand;
    }
    public CardHand getCardHand() {
        return cardHand;
    }

    public abstract String getUsername();

    protected CardHand cardHand;

    protected List<Card> getPlayableCards(Trick toTrick) {
        if(toTrick.cards.size() == 0) return cardHand;
        Card.Suit trickSuit = toTrick.cards.get(0).suit();
        if(cardHand.stream().anyMatch(c -> c.suit() == trickSuit)) {
            return cardHand.stream().filter(c -> c.suit() == trickSuit).toList();
        }
        return cardHand;
    }

    public Player(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return  this.getUsername() + " @ " + position.name();
    }

}
