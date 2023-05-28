package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.model.Game;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class for showing scoreboard in offline game.
 */
public class ScoreboardView extends VBox {
    private final Label[] labels = new Label[4];
    private final HBox[] playerBoxes = new HBox[4];
    Game game;

    public ScoreboardView() {
        this.setVisible(false);
    }

    public void setGame(Game game) {
        this.game = game;

        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            labels[i] = new Label();
            playerBoxes[i] = new HBox(new Label(game.getPlayers()[i].getUsername() + ":"), labels[i]);
            playerBoxes[i].setSpacing(10);
        }

        playerBoxes[1].setPadding(new Insets(0, 20, 0, 0));
        playerBoxes[2].setPadding(new Insets(0, 20, 0, 0));
        playerBoxes[3].setPadding(new Insets(0, 20, 0, 0));

        this.getChildren().addAll(playerBoxes);
        this.getStyleClass().add("score");
        this.setPadding(new Insets(20, 20, 20, 20));
        this.setSpacing(20);
    }

    public void show() {
        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            var sums = game.scoreBoard.getSums();
            labels[i].setText(String.valueOf(sums[i]));
        }
        this.setVisible(true);
    }

    public void hide() {
        this.setVisible(false);
    }

}
