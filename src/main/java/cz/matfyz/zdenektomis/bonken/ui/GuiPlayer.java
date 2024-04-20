package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Player;
import cz.matfyz.zdenektomis.bonken.model.Position;
import cz.matfyz.zdenektomis.bonken.model.Trick;
import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Action;
import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.application.Platform;

import java.util.List;
import java.util.Set;

/**
 * Player that can be controlled by the GUI.
 */
public class GuiPlayer extends Player {

    String username;
    private final SimpleEvent<List<Minigame>> minigameRequired = new SimpleEvent<>();
    private final SimpleEvent<CardRequiredEventData> cardRequired = new SimpleEvent<>();
    private boolean isMinigameRequired = false;
    private boolean isCardRequired = false;
    private Action<Minigame> minigameSelectedCallback;
    private Action<Card> cardPlayCallback;

    /**
     * Creates a new GUI player.
     * @param position position of the player
     * @param username username of the player
     */
    public GuiPlayer(Position position, String username) {
        super(position);
        this.username = username;
    }


    /**
     * Get event that is fired when a minigame is required to be selected.
     * @return event that is fired when a minigame is required to be selected
     */
    public Event<List<Minigame>> minigameRequired() {
        return minigameRequired;
    }

    /**
     * Get event that is fired when a card is required to be played.
     * @return event that is fired when a card is required to be played
     */
    public Event<CardRequiredEventData> cardRequired() {
        return cardRequired;
    }

    @Override
    public void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback) {
        isMinigameRequired = true;
        this.minigameSelectedCallback = callback;
        Platform.runLater(() -> minigameRequired.fire(minigames));
    }

    /**
     * Handler for minigame selection.
     * @param minigame selected minigame
     */
    public void minigameSelected(Minigame minigame) {

        if (!isMinigameRequired) return;
        isMinigameRequired = false;
        Platform.runLater(() -> minigameSelectedCallback.call(minigame));
    }

    @Override
    protected void playCard(Trick toTrick, Action<Card> callback) {
        isCardRequired = true;
        cardPlayCallback = callback;
        Platform.runLater(() -> cardRequired.fire(new CardRequiredEventData(toTrick, cardHand, this.getPlayableCardsAsSet(toTrick))));
    }

    /**
     * Handler for card selection.
     * @param card selected card
     */
    public void cardSelected(Card card) {
        if (!isCardRequired) return;
        isCardRequired = false;
        cardPlayCallback.call(card);
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Event data for card required event.
     * @param trick trick to play into
     *              cards cards in the hand
     * @param cards cards in the hand
     * @param playableCards cards that can be played
     */
    public record CardRequiredEventData(Trick trick, List<Card> cards, Set<Card> playableCards) {
    }
}
