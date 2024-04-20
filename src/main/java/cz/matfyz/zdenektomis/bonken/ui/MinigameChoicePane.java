package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class for showing minigame choice to player.
 */
public class MinigameChoicePane extends FlowPane {

    Map<Minigame, Button> minigameButtons = new HashMap<>();

    private final SimpleEvent<Minigame> onMinigameSelected = new SimpleEvent<>();

    /**
     * Creates a new minigame choice pane.
     *
     * @param minigameList the list of minigames to choose from
     */
    public MinigameChoicePane(List<Minigame> minigameList) {
        super();

        Rectangle space = new Rectangle(20, 20);
        space.setFill(Color.TRANSPARENT);

        this.setAlignment(Pos.CENTER);

        for (var minigame : minigameList) {
            Button button = new Button(minigame.getName());
            button.getStyleClass().add("minigame-choice-button");

            button.setOnAction(event -> {
                onMinigameSelected.fire(minigame);
            });
            this.getChildren().add(button);
            minigameButtons.put(minigame, button);
        }
    }

    /**
     * Get event that is fired when user selects a minigame.
     * @return event that is fired when user selects a minigame
     */
    public Event<Minigame> onMinigameSelected() {
        return onMinigameSelected;
    }

    /**
     * Sets the available minigames for the player to choose from.
     *
     * @param availableMinigames the available minigames
     */
    public void setAvailableMinigames(Set<Minigame> availableMinigames) {

        for (var minigame : minigameButtons.keySet()) {
            minigameButtons.get(minigame).setDisable(!availableMinigames.contains(minigame));
            if (availableMinigames.contains(minigame)) {
                minigameButtons.get(minigame).getStyleClass().remove("minigame-choice-button-disabled");
            } else {
                minigameButtons.get(minigame).getStyleClass().add("minigame-choice-button-disabled");
            }
        }
    }

}
