package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.model.Game;
import cz.matfyz.zdenektomis.bonken.model.Player;
import cz.matfyz.zdenektomis.bonken.model.Position;
import cz.matfyz.zdenektomis.bonken.model.RandomPlayerBot;
import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.HashSet;

/**
 * Controller class for the game.
 * Bridges game logic with the GUI.
 */
public class Controller {

    private final StartMenuView startMenuView;
    private final CardPane cardPane;
    private final MinigameChoicePane minigameChoicePane;
    private final GameView gameView;
    private final NameInputView nameInputView;
    private final TrickPane trickPane;
    private final EndGameView endGameView;
    Game game;
    Stage stage;
    private GuiPlayer guiPlayer;
    private String username;

    /**
     * Creates a new controller.
     * @param stage the stage to show the game on
     */
    public Controller(Stage stage) {
        this.stage = stage;
        this.startMenuView = getStartMenuView();
        this.nameInputView = getNameInputView();
        this.minigameChoicePane = getMinigameChoicePane();
        this.cardPane = getCardPane();
        this.trickPane = getTrickPane();
        this.gameView = getGameView();
        this.endGameView = getEndGameView(stage);
    }


    /**
     * Starts the game.
     */
    public void start() {
        stage.setScene(startMenuView.getScene());
        stage.show();
    }

    /**
     * Closes the game.
     */
    public void close() {
        if (trickPane != null) trickPane.killTimer();
    }


    private EndGameView getEndGameView(Stage stage) {
        var view = new EndGameView();
        view.onShowStartMenuPressed().addListener(__ ->
                stage.setScene(startMenuView.getScene())
        );
        view.onQuitPressed().addListener(__ -> {
            stage.close();
            this.close();
        });
        return view;
    }

    private TrickPane getTrickPane() {
        return new TrickPane(Position.NORTH,
                () -> gameView.showBlockingRec(),
                () -> gameView.hideBlockingRec());
    }

    private CardPane getCardPane() {
        var view = new CardPane();
        view.cardClicked().addListener(card -> {
            guiPlayer.cardSelected(card);
            view.update(guiPlayer.getCardHand());
            trickPane.packUpTrick();
        });
        return view;
    }

    private GameView getGameView() {
        var view = new GameView(minigameChoicePane, cardPane, trickPane);
        return view;
    }

    private MinigameChoicePane getMinigameChoicePane() {
        var view = new MinigameChoicePane(Minigame.all);
        view.onMinigameSelected().addListener(minigame -> {
            stage.setScene(gameView.getScene());
            gameView.setMinigameChoiceVisible(false);
            Platform.runLater(() -> guiPlayer.minigameSelected(minigame));
        });
        return view;
    }

    private StartMenuView getStartMenuView() {
        var view = new StartMenuView();
        view.onQuitClicked().addListener(__ -> stage.close());
        view.onStartClicked().addListener(__ -> stage.setScene(this.nameInputView.getScene()));
        return view;
    }

    private NameInputView getNameInputView() {
        var view = new NameInputView();
        view.onSubmit().addListener(name -> {
            username = name;
            startGame();
        });
        return view;
    }

    private GuiPlayer getGuiPlayer() {
        var player = new GuiPlayer(
                Position.NORTH, username
        );
        player.cardRequired().addListener(data -> {
            trickPane.update();
            cardPane.update(data.cards(), data.playableCards());
        });
        player.minigameRequired().addListener(minigameList -> {
            minigameChoicePane.setAvailableMinigames(new HashSet<>(minigameList));
            gameView.setMinigameChoiceVisible(true);
            cardPane.update(guiPlayer.getCardHand(), null);
        });
        return player;
    }

    private void startGame() {

        Player[] players = new Player[4];
        guiPlayer = getGuiPlayer();
        players[0] = guiPlayer;


        for (int i = 1; i < 4; i++) {
            players[i] = new RandomPlayerBot(Position.values()[i]);
        }

        game = new Game(players, Platform::runLater);

        stage.setScene(gameView.getScene());
        trickPane.setGame(game);
        endGameView.setGame(game);
        game.startGame();
    }


}
