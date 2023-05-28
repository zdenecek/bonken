package com.bonken.console;

import com.bonken.model.*;
import com.bonken.model.minigames.Minigame;
import com.bonken.utils.Action;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ConsolePlayer extends Player {
    private final Scanner scanner = new Scanner(System.in);
    private String username;

    private <T> T get ( List<T> from) {
        while (true) {
            try {
                var i  = scanner.nextInt();
                return from.get(i);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, try again");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice, try again");
            }
        }
    }

    @Override
    public void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback) {
        System.out.println("Your hand: ");
        Printer.print(cardHand);
        System.out.println("Select a minigame: ");
        for (int i = 0; i < minigames.size(); i++) {
            System.out.println(i + ": " + minigames.get(i).getName());
        }
        callback.call(get(minigames));
    }

    @Override
    protected void playCard(Trick toTrick, Action<Card> callback) {
        var playableCards = getPlayableCards(toTrick);

        System.out.println("Your hand: ");
        Printer.print(cardHand);

        System.out.println("Select a card to play: (type in a zero based index)");
        Printer.print(playableCards);

        callback.call(get(playableCards));
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public ConsolePlayer(Position position) {
        super(position);
        chooseUsername();
    }

    private void chooseUsername() {
        System.out.println("Choose your username");
        username = scanner.next();
    }



}
