package cz.matfyz.zdenektomis.bonken.console;

import cz.matfyz.zdenektomis.bonken.model.Game;
import cz.matfyz.zdenektomis.bonken.model.Player;
import cz.matfyz.zdenektomis.bonken.model.Position;
import cz.matfyz.zdenektomis.bonken.model.RandomPlayerBot;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Main class for the console application.
 */
public class Main extends Application {


    /**
     * Launches the console application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    private Main() {
    }

    @Override
    public void start(Stage stage) throws IOException {
        int numPlayers = 1;

        Player[] players = new Player[Game.NUM_PLAYERS];
        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            if (i < numPlayers) {
                players[i] = new ConsolePlayer(Position.values()[i]);
            } else {
                players[i] = new RandomPlayerBot(Position.values()[i]);
            }
        }

        Game game = ConsoleGameFactory.createGame(players);

        Platform.runLater(() -> game.startGame());
    }
}
