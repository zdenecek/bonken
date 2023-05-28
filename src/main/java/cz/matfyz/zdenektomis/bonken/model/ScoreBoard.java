package com.bonken.model;

import com.bonken.model.minigames.Minigame;

import java.util.Arrays;

public class ScoreBoard {

    public final Game game;

    public final int scores[][] = new int[Minigame.all().size()][Game.NUM_PLAYERS];
    public final boolean played[] = new boolean[Minigame.all().size()];


    public ScoreBoard(Game game) {
        this.game = game;
    }

    public void updateScoreBoard(Round round) {
        var score = round.minigame.score(round);
        var minigameIndex = Minigame.indexOf(round.minigame);
        for (int i = 0; i < Game.NUM_PLAYERS; i++) {
            scores[minigameIndex][i] = score[i];
        }
        played[minigameIndex] = true;
    }

    public int[] getSums() {
        return Arrays.stream(scores).reduce(new int[Game.NUM_PLAYERS], (a, b) -> {
            for (int i = 0; i < Game.NUM_PLAYERS; i++) {
                a[i] += b[i];
            }
            return a;
        });
    }

}
