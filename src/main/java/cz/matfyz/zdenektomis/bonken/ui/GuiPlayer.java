package com.bonken.ui;

import com.bonken.model.Position;
import com.bonken.model.minigames.Minigame;
import com.bonken.utils.Action;
import com.bonken.model.Card;
import com.bonken.model.Player;
import com.bonken.model.Trick;

import java.util.ArrayList;
import java.util.List;

public class GuiPlayer extends Player {

    private Action<List<Minigame>> onMinigameRequired;
    private Action<Minigame> onMinigameSelected;
    private boolean miniGameRequired = false;
    private boolean cardRequired = false;

    String username;

    public GuiPlayer(Position position, String username, Action<List<Minigame>> onMinigameRequired ) {
        super(position);
        this.username = username;
        this.onMinigameRequired = onMinigameRequired;

    }

    public void minigameSelected(Minigame minigame) {

        if(miniGameRequired == false) return;
        miniGameRequired = false;
        onMinigameSelected.call(minigame);
    }


    @Override
    public void requestSelectMinigame(List<Minigame> minigames, Action<Minigame> callback) {
        miniGameRequired = true;
        this.onMinigameSelected = callback;
        onMinigameRequired.call(minigames);
    }


    private Action<Card> cardPlayCallback;
    @Override
    protected void playCard(Trick toTrick, Action<Card> callback) {
        cardRequired = true;
        cardPlayCallback = callback;
    }

    public void cardSelected(Card card) {
        if(cardRequired == false) return;
        cardPlayCallback.call(card);
    }


    @Override
    public String getUsername() {
        return username;
    }
}
