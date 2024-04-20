package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.model.Game;
import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class for ending screen in a bot game.
 */
public class EndGameView extends View {

    private Label gameFinished;
    private VBox vb;
    private VBox[] player = new VBox[Game.NUM_PLAYERS];
    private Label[] playerScore = new Label[Game.NUM_PLAYERS];
    private HBox hbButtons;
    private HBox hbScore;
    private Button returnButton;
    private Button exitButton;

    private Game game;

    private final SimpleEvent<Void> onShowStartMenuPressed = new SimpleEvent<>();
    private final SimpleEvent<Void> onQuitPressed = new SimpleEvent<>();

    /**
     * Creates a new end game view.
     */
    public EndGameView() {
        vb = new VBox();
        hbScore = new HBox();
        gameFinished = new Label("GAME FINISHED");
        gameFinished.getStyleClass().add("header-label");

        returnButton = new Button("MENU");
        exitButton = new Button("EXIT");

        returnButton.setOnAction(event -> onShowStartMenuPressed.fire(null));
        exitButton.setOnAction(event -> onQuitPressed.fire(null));

        returnButton.getStyleClass().add("endgame-button");
        exitButton.getStyleClass().add("endgame-button");
        hbButtons = new HBox(returnButton, exitButton);
        hbButtons.setSpacing(50);
        hbButtons.setAlignment(Pos.CENTER);

        vb.getChildren().addAll(gameFinished, hbScore, hbButtons);
        vb.setSpacing(50);

    }


    /**
     * Get the event that is fired when the menu button is clicked
     * @return event that is fired when the menu button is clicked
     */
    public Event<Void> onShowStartMenuPressed() {
        return onShowStartMenuPressed;
    }

    /**
     * Get the event that is fired when the quit button is clicked
     * @return event that is fired when the quit button is clicked
     */
    public Event<Void> onQuitPressed() {
        return onQuitPressed;
    }

    /**
     * Set the game that has ended.
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;

        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            playerScore[i] = new Label();
            player[i] = new VBox(new Label(game.getPlayers()[0].getUsername()), playerScore[i]);
            player[i].setSpacing(10);
        }

        hbScore.getChildren().addAll(player);
        hbScore.setSpacing(40);
        hbScore.setAlignment(Pos.CENTER);
    }


    /**
     * Show the end game screen.
     */
    public void show() {

        var sums = game.scoreBoard.getSums();
        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            playerScore[i].setText(Integer.toString(sums[i]));
        }

        vb.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vb, 1080, 720);
        setScene(scene);
    }

}
