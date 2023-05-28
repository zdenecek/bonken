package com.bonken.console;

import com.bonken.model.*;
import com.bonken.model.minigames.Minigame;
import com.bonken.utils.Strings;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Printer {


    public static void print(ScoreBoard scoreBoard) {
        print(scoreBoard, System.out);
    }

    public static void print(ScoreBoard scoreBoard, PrintStream out) {

        String separator = " | ";

        var minigameNames = Minigame.all().stream().map(m -> m.getName()).toArray(String[]::new);
        var maxMinigameLength = Arrays.stream(minigameNames).mapToInt(String::length).max().getAsInt();

        var names = Arrays.stream(scoreBoard.game.getPlayers()).map(p -> p.getUsername()).toArray(String[]::new);
        var lengths = Arrays.stream(names).mapToInt(String::length).toArray();
        var lineLength = maxMinigameLength + Arrays.stream(lengths).sum() + (separator.length() * names.length);
        var _______ = "-".repeat(lineLength);


        out.println("Scoreboard:");
        out.println(_______);
        out.println(" ".repeat(maxMinigameLength) + separator + String.join(separator, names));
        out.println(_______);
        for (int i = 0; i <  Minigame.all().size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(Strings.padRight(minigameNames[i], maxMinigameLength));
            sb.append(separator);
            for (int j = 0; j < Game.NUM_PLAYERS; j++) {

                String score = !scoreBoard.played[i] ?  "-" : Integer.toString(scoreBoard.scores[i][j]);
                sb.append(Strings.padLeft( score , lengths[j]));
                if (j < Game.NUM_PLAYERS - 1) {
                    sb.append(separator);
                }
            }
            out.println(sb.toString());
        }
        out.println(_______);
        var sums = scoreBoard.getSums();
        var sb = new StringBuilder();
        sb.append(Strings.padRight("Sum", maxMinigameLength));
        for (int j = 0; j < Game.NUM_PLAYERS; j++) {
            sb.append(separator);
            sb.append(Strings.padLeft( Integer.toString(sums[j]), lengths[j]));
        }
        out.println(sb.toString());
    }

    static void print(List<Card> cardHand, PrintStream out) {
        out.println(cardHand.stream().map(c -> c.toString()).collect(Collectors.joining(", ")));
    }

    static void print(List<Card>  cardHand) {
        print(cardHand, System.out);
    }

    static void print(Trick trick) {
        print(trick, System.out);
    }

    static void print(Trick trick, PrintStream out) {

        var cards = trick.cards;
        var lengths = cards.stream().mapToInt(c -> c.toString().length()).toArray();

        out.println(trick.firstPlayer.name + " started:");
        out.println(" ".repeat(2) + String.join("  ", cards.stream().map(c -> c.toString()).collect(Collectors.toList())));

    }
}
