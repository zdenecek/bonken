package cz.matfyz.zdenektomis.bonken.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.logging.Logger;

/**
 * Main GUI class, combines CardPane at the bottom, TrickPane and MinigameChoicePane in the middle and StatusPane in the top left corner.
 */
public class GameView extends View {

    private static final Logger LOGGER = Logger.getLogger(GameView.class.getName());
    private final CardPane cardPane;
    private final MinigameChoicePane minigameChoicePane;
    private final TrickPane trickPane;
    private final StackPane centerPane;
    private final BorderPane borderPane;
    private final Rectangle blockingRec;
    private final StackPane wholeScreen;

    /**
     * Create a new game view.
     * @param minigamePane the minigame choice pane
     * @param cardPane the card pane
     * @param trickPane the trick pane
     */
    public GameView(MinigameChoicePane minigamePane, CardPane cardPane, TrickPane trickPane) {

        this.cardPane = cardPane;
        cardPane.setMaxWidth(1080);
        cardPane.setAlignment(Pos.BOTTOM_CENTER);
        cardPane.setPrefHeight(150);
        cardPane.getStyleClass().add("card-box");

        this.minigameChoicePane = minigamePane;
        minigamePane.setPrefHeight(500);

        this.trickPane = trickPane;

        centerPane = new StackPane();
        centerPane.getChildren().add(trickPane);
        centerPane.getChildren().add(minigamePane);

        borderPane = new BorderPane();
        borderPane.setCenter(centerPane);
        borderPane.setBottom(cardPane);
        borderPane.setMaxWidth(1080);

        blockingRec = new Rectangle(1080, 200);

        blockingRec.setFill(Color.TRANSPARENT);

        wholeScreen = new StackPane();
        StackPane.setAlignment(blockingRec, Pos.BOTTOM_CENTER);
        wholeScreen.getChildren().addAll(blockingRec, borderPane);

        setMinigameChoiceVisible(false);

        Scene scene = new Scene(wholeScreen, 1080, 720);
        setScene(scene);
    }


    /**
     * Set the screen to show or hide the minigame choice.
     * @param visible whether the minigame choice should be visible
     */
    public void setMinigameChoiceVisible(boolean visible) {
        minigameChoicePane.setVisible(visible);
        trickPane.setVisible(!visible);
    }

    /**
     * Show the blocking rectangle to block the user from playing cards.
     * Used when animations are playing.
     */
    public void showBlockingRec() {
        blockingRec.toFront();
    }

    /**
     * Hide the blocking rectangle.
     */
    public void hideBlockingRec() {
        borderPane.toFront();
    }


}
