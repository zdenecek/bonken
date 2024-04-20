package cz.matfyz.zdenektomis.bonken.console;

import cz.matfyz.zdenektomis.bonken.model.Game;
import cz.matfyz.zdenektomis.bonken.model.Player;
import cz.matfyz.zdenektomis.bonken.utils.Action;

/**
 * Factory class for creating a game for console UI version.
 */
public class ConsoleGameFactory {

    private ConsoleGameFactory() {
    }

    /**
     * Creates a game with the given players for console play.
     * @param players Players to play the game.
     * @return Game instance.
     */
    public static Game createGame(Player[] players, Action<Runnable> executeLater) {
        Game game = new Game(players, executeLater);

        game.onTrickEnded().addListener((eventData) -> {
            // System.out.println("Trick ended");
            Printer.print(eventData.trick());
        });

        game.onRoundEnded().addListener(r -> {
            Printer.print(game.scoreBoard);
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
