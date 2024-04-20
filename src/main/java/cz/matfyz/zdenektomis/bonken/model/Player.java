package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base class for players in a game of Bonken.
 */
public abstract class Player {

    private final Position position;
    protected CardHand cardHand;

    /**
     * Creates a new player.
     * @param position the position of the player at the table
     */
    public Player(Position position) {
        this.position = position;
    }

    /**
     * @return the position of the player, i.e. where they sit at the table
     */
    public final Position getPosition() {
        return position;
    }


    /**
     * Requests the player to select a minigame from a list of minigames.
     * @param minigames list of minigames to choose from
     * @param callback callback to call with the selected minigame
     */
    public abstract void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback);

    /**
     * Requests the player to play a card to a trick.
     * @param toTrick the trick to play a card to
     * @param callback callback to call with the selected card
     */
    protected abstract void playCard(Trick toTrick, Action<Card> callback);

    /**
     * Get the player's username.
     * @return the username of the player
     */
    public abstract String getUsername();

    /**
     * Requests the player to play a card to a trick.
     * @param toTrick the trick to play a card to
     * @param callback callback to call with the selected card
     */
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

    /**
     * Sets the cards of the player.
     * @param cardHand the card hand to set
     */
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
