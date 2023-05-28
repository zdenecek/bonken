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
 * Class for showing minigames choices to player.
 */
public class MinigameChoicePane extends FlowPane {

    Map<Minigame, Button> minigameButtons = new HashMap<>();

    private final SimpleEvent<Minigame> onMinigameSelected = new SimpleEvent<>();

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

    public Event<Minigame> onMinigameSelected() {
        return onMinigameSelected;
    }

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
