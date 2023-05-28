package cz.matfyz.zdenektomis.bonken.ui;


import cz.matfyz.zdenektomis.bonken.model.Game;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class for showing game status in offline game.
 */
public class StatusPane extends VBox {

    Game game;

    private final Label minigameLabel;
    private final Label roundLabel;
    private final Label roundHeader;
    private final Label scoreLabel;
    private final HBox minigameBox;
    private final HBox roundBox;
    private final VBox scoreboardBox;
    private final ScoreboardView scoreboard;

    public StatusPane() {
        super();

        roundHeader = new Label("Round ");
        roundLabel = new Label();
        roundBox = new HBox(roundHeader, roundLabel);

        minigameLabel = new Label();
        minigameBox = new HBox(minigameLabel);

        scoreLabel = new Label("Score");
        scoreboard = new ScoreboardView();
        scoreLabel.hoverProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                scoreboard.show();

            } else {
                scoreboard.hide();
            }
        });
        scoreboardBox = new VBox(scoreLabel, scoreboard);
        this.getChildren().addAll(roundBox, minigameBox, scoreboardBox);

    }

    public void setGame(Game game) {
        this.game = game;
        scoreboard.setGame(game);
    }

    public void update() {
        int gameCounter = game.rounds.size();
        roundLabel.setText(gameCounter + " / 11");
        minigameLabel.setText(game.currentRound().minigame.getName());
    }
}
