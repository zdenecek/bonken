package com.bonken.console;

import com.bonken.model.Game;
import com.bonken.model.Player;

public class GameFactory {


    public static Game createGame(Player[] players) {
        Game game = new Game(players);

        game.onTrickEnded().addListener((eventData) -> {
           // System.out.println("Trick ended");
            Printer.print(eventData.trick());
        });

        game.onRoundEnded().addListener(r -> {
               // Printer.print(game.scoreBoard);
        });

        game.onRoundStarted().addListener(r -> {
            System.out.println(r.round().startingPlayer.next().name + " chose this game");
            System.out.println("Starting round with minigame " + r.round().minigame.getName());
        });

        game.onGameEnded().addListener(g -> {
            System.out.println("Game ended");
            System.out.println("Final score:");
            Printer.print(game.scoreBoard);
        });

        return game;
    }
}
