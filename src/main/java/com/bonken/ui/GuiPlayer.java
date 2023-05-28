//package com.bonken.ui;
//
//import bonken.utils.Action;
//import com.bonken.model.Card;
//import com.bonken.model.Player;
//import com.bonken.model.Trick;
//
//import java.util.ArrayList;
//
//public class GuiPlayer extends Player {
//
//    private Action<ArrayList<Integer>> onMinigameRequired;
//    private Action<Integer> onMinigameSelected;
//    private boolean miniGameRequired = false;
//    private boolean cardRequired = false;
//    private Trick currentTrick;
//
//    public GuiPlayer(int id, String username, Action<ArrayList<Integer>> onMinigameRequired ) {
//        super(id);
//        this.username = username;
//        this.onMinigameRequired = onMinigameRequired;
//
//    }
//
//    public void minigameSelected(Integer minigame) {
//
//        if(miniGameRequired == false) return;
//        miniGameRequired = false;
//        onMinigameSelected.call(minigame);
//    }
//
//    public void cardSelected(Card card) {
//        if(cardRequired == false) return;
//        currentTrick.addCard(this, card);
//    }
//
//    @Override
//    public void chooseMinigame(ArrayList<Integer> minigames, Action<Integer> callback) {
//        miniGameRequired = true;
//        this.onMinigameSelected = callback;
//        onMinigameRequired.call(minigames);
//    }
//
//    @Override
//    public void play(Trick trick) {
//        cardRequired = true;
//        currentTrick = trick;
//    }
//}
