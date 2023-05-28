package com.bonken.console;

import com.bonken.model.Game;
import com.bonken.model.Player;
import com.bonken.model.Position;
import com.bonken.model.RandomPlayerBot;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        int numPlayers = 0;

        Player[] players = new Player[Game.NUM_PLAYERS];
        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            if (i < numPlayers)
            {
                players[i] = new ConsolePlayer(Position.values()[i]);
            } else {
                players[i] = new RandomPlayerBot(Position.values()[i]);
            }
        }

        Game game = GameFactory.createGame(players);

        Platform.runLater(() -> game.startGame());
    }


    public static void main(String[] args) {
        launch();
    }
}
