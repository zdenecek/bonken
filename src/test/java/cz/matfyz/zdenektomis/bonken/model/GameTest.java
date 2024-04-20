package cz.matfyz.zdenektomis.bonken.model;

import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Action;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void gameFinishes() throws InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CountDownLatch latch = new CountDownLatch(1);

        var players = new Player[Game.NUM_PLAYERS];
        var seed = 1234;

        Random random = new Random(seed);

        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            players[i] = new Player(Position.values()[i]) {

                @Override
                public void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback) {
                    callback.call(minigames.get(random.nextInt(minigames.size())));
                }

                @Override
                protected void playCard(Trick toTrick, Action<Card> callback) {
                    var options = this.getPlayableCards(toTrick);
                    callback.call(options.get(random.nextInt(options.size())));
                }

                @Override
                public String getUsername() {
                    return "test player";
                }
            };
        }

        Deck deck = new Deck() {
            @Override
            public void shuffle() {
                Collections.shuffle(this, random);
            }
        };

        Game game = new Game(players, deck, executorService::submit);

        game.onGameEnded().addListener(
                event -> {
                    latch.countDown();
                }
        );

        executorService.submit(game::startGame);

        var completed = latch.await(1, TimeUnit.SECONDS);
        assertTrue(completed, "Game did not finish in time");

    }
}