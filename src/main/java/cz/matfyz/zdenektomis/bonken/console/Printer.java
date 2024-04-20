package cz.matfyz.zdenektomis.bonken.console;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.model.Game;
import cz.matfyz.zdenektomis.bonken.model.ScoreBoard;
import cz.matfyz.zdenektomis.bonken.model.Trick;
import cz.matfyz.zdenektomis.bonken.model.minigames.Minigame;
import cz.matfyz.zdenektomis.bonken.utils.Strings;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Class for printing game state to console or other stream.
 */
public class Printer {

    private Printer() {
    }


    /**
     * Prints the score board to the console.
     * @param scoreBoard The score board to print.
     */
    public static void print(ScoreBoard scoreBoard) {
        print(scoreBoard, System.out);
    }

    /**
     * Prints the score board to the given stream.
     * @param scoreBoard The score board to print.
     * @param out The stream to print to.
     */
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
        for (int i = 0; i < Minigame.all().size(); i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(Strings.padRight(minigameNames[i], maxMinigameLength));
            sb.append(separator);
            for (int j = 0; j < Game.NUM_PLAYERS; j++) {

                String score = !scoreBoard.played[i] ? "-" : Integer.toString(scoreBoard.scores[i][j]);
                sb.append(Strings.padLeft(score, lengths[j]));
                if (j < Game.NUM_PLAYERS - 1) {
                    sb.append(separator);
                }
            }
            out.println(sb);
        }
        out.println(_______);
        var sums = scoreBoard.getSums();
        var sb = new StringBuilder();
        sb.append(Strings.padRight("Sum", maxMinigameLength));
        for (int j = 0; j < Game.NUM_PLAYERS; j++) {
            sb.append(separator);
            sb.append(Strings.padLeft(Integer.toString(sums[j]), lengths[j]));
        }
        out.println(sb);
    }


    /**
     * Prints the hand to the console.
     * @param cardHand The hand to print.
     * @param out The stream to print to.
     */
    static void print(List<Card> cardHand, PrintStream out) {
        out.println(cardHand.stream().map(c -> c.toString()).collect(Collectors.joining(", ")));
    }

    /**
     * Prints the hand to the console in a short, easily readable format.
     * @param cardHand The hand to print.
     */
    static void printShort(List<Card> cardHand, PrintStream out) {

        out.println( cardHand.stream()
                .collect(groupingBy(Card::suit))
                .entrySet()
                .stream()
                .map((entry) -> entry.getKey().toPrettyChar() +
                        entry.getValue().stream()
                                .map(Card::value)
                                .sorted()
                                .toList().reversed().stream()
                                .map(Card.Value::toChar)
                                .map(c -> c.toString())
                                .collect(Collectors.joining(""))
                ).collect(Collectors.joining(" "))
        );
    }


    /**
     * Prints the hand to the console.
     * @param cardHand The hand to print.
     */
    public static void print(List<Card> cardHand) {
        print(cardHand, System.out);
    }

    /**
     * Prints the hand to the console in a short, easily readable format.
     * @param cardHand The hand to print.
     */
    public static void printShort(List<Card> cardHand) {
        printShort(cardHand, System.out);
    }

    /**
     * Prints the trick to the console.
     * @param trick The game to print.
     */
    public static void print(Trick trick) {
        print(trick, System.out);
    }

    /**
     * Prints the trick to the given stream.
     * @param trick The trick to print.
     * @param out The stream to print to.
     */
    public static void print(Trick trick, PrintStream out) {

        var cards = trick.cards;
        var lengths = cards.stream().mapToInt(c -> c.toString().length()).toArray();

        out.println(trick.firstPlayer.name + " started:");
        out.println(" ".repeat(2) + String.join("  ", cards.stream().map(c -> c.toString()).collect(Collectors.toList())));

    }
}
