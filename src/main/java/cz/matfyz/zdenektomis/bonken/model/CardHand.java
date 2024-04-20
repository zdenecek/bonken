package cz.matfyz.zdenektomis.bonken.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class CardHand extends ArrayList<Card> {
    public CardHand() {
        super();
    }

    public CardHand(List<Card> cards) {
        super(cards);
    }

    public void sortHand() {
        sort(Comparator.comparing(Card::suit).thenComparing(Card::value));
    }

    public Iterable<Card> getPlayableCards(Card.Suit suit) {
        // If the hand contains a card of the same suit as the first card, return only those cards
        if (this.stream().anyMatch(c -> c.suit() == suit)) {
            return this.stream().filter(c -> c.suit() == suit).collect(Collectors.toList());
        }
        // Otherwise, return all cards
        return this;
    }

}
