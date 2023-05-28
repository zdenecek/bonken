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


public class GuiPlayer extends Player {

    String username;
    private final SimpleEvent<List<Minigame>> minigameRequired = new SimpleEvent<>();
    private final SimpleEvent<CardRequiredEventData> cardRequired = new SimpleEvent<>();
    private boolean isMinigameRequired = false;
    private boolean isCardRequired = false;
    private Action<Minigame> minigameSelectedCallback;
    private Action<Card> cardPlayCallback;

    public GuiPlayer(Position position, String username) {
        super(position);
        this.username = username;
    }

    public Event<List<Minigame>> minigameRequired() {
        return minigameRequired;
    }

    public Event<CardRequiredEventData> cardRequired() {
        return cardRequired;
    }

    @Override
    public void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback) {
        isMinigameRequired = true;
        this.minigameSelectedCallback = callback;
        Platform.runLater(() -> minigameRequired.fire(minigames));
    }

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

    public void cardSelected(Card card) {
        if (!isCardRequired) return;
        isCardRequired = false;
        cardPlayCallback.call(card);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public record CardRequiredEventData(Trick trick, List<Card> cards, Set<Card> playableCards) {
    }
}
