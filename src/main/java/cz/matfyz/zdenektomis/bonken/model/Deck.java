package cz.matfyz.zdenektomis.bonken.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a deck of cards. Used for dealing hands.
 */
public class Deck extends ArrayList<Card> {
    public Deck() {

        super(Arrays.stream(Card.Suit.values()).flatMap(
                suit -> Arrays.stream(Card.Value.values()).map(
                        value -> new Card(suit, value)
                )
        ).toList());
    }

    /**
     * Deals hands to players.
     * @return List of 4 hands each containing 13 cards.
     */
    public static List<CardHand> deal() {

        var deck = new Deck();
        deck.shuffle();

        var hands = new ArrayList<CardHand>();
        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            var cards = deck.subList(i * Game.NUM_CARDS_IN_HAND, (i + 1) * Game.NUM_CARDS_IN_HAND);
            var hand = new CardHand(cards);
            hands.add(hand);
            hand.sortHand();
        }
        return hands;
    }

    /**
     * Shuffles the deck.
     */
    public void shuffle() {
        Collections.shuffle(this);
    }

    @Override
    public String toString() {
        return this.stream().map(c -> c.toString()).collect(Collectors.joining(", "));
    }
}
