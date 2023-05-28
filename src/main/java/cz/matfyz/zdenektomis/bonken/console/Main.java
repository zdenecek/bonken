package cz.matfyz.zdenektomis.bonken.console;

import cz.matfyz.zdenektomis.bonken.model.Game;
import cz.matfyz.zdenektomis.bonken.model.Player;
import cz.matfyz.zdenektomis.bonken.model.Position;
import cz.matfyz.zdenektomis.bonken.model.RandomPlayerBot;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        int numPlayers = 0;

        Player[] players = new Player[Game.NUM_PLAYERS];
        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            if (i < numPlayers) {
                players[i] = new ConsolePlayer(Position.values()[i]);
            } else {
                players[i] = new RandomPlayerBot(Position.values()[i]);
            }
        }

        Game game = GameFactory.createGame(players);

        Platform.runLater(() -> game.startGame());
    }
}
