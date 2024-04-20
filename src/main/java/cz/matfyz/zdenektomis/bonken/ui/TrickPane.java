package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.model.*;
import cz.matfyz.zdenektomis.bonken.utils.Callable;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

/**
 * Pane for displaying a trick, four cards in a grid.
 */
public class TrickPane extends Pane {

    private static final Logger LOGGER = Logger.getLogger(TrickPane.class.getName());

    /**
     * Border for where the other players place their cards.
     */
    protected static Border otherPlayerBorder = new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    /**
     * Border for where the player places their cards.
     */
    protected static Border playerBorder = new Border(new BorderStroke(Color.GOLDENROD, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    /**
     * Width of a card.
     */
    protected double cardWidth = 132;
    /**
     * Height of a card.
     */
    protected double cardHeight = 180;
    /**
     * Timer for hiding the trick animation.
     */
    protected Timer timer;
    /**
     * Panes for the cards where they are to be placed.
     */
    protected Pane[] cardPanes;
    /**
     * The game that the trick pane is showing.
     */
    protected Game game;
    /**
     * Position of the player at the bottom.
     */
    protected Position bottomPlayer;
    /**
     * State variable, whether a trick end animation is being shown.
     */
    protected boolean showingTrickEnd = false;
    /**
     * State variable, whether the trick pane is showing blocking rectangle to disengage clicking.
     */
    protected boolean blocking = false;
    private boolean trickEndAlreadyDrawn = false;
    private Callable showBlock;
    private Callable hideBlock;
    private final StatusPane statusPane;

    /**
     * Create a new trick pane.
     * @param bottomPlayer position of the player, default is North
     * @param showBlock    called after playing a card
     * @param hideBlock    called when the player is supposed to play
     */
    public TrickPane(Position bottomPlayer, Callable showBlock, Callable hideBlock) {
        super();

        this.showBlock = showBlock;
        this.hideBlock = hideBlock;

        timer = new Timer();
        this.bottomPlayer = bottomPlayer;

        statusPane = new StatusPane();
        setupCardPanes();
    }

    /**
     * Recalculate the positions of the card panes.
     */
    protected void adjustPanePositions() {
        double h = (this.getHeight() - cardHeight) / 2;
        double w = (this.getWidth() - cardWidth) / 2;
        double alignment = (this.getWidth() / 4);

        Position currPos = bottomPlayer;
        for (int i = 0; i < 4; i++) {
            Pane p = cardPanes[i];
            p.setBorder(otherPlayerBorder);
            if (currPos == bottomPlayer) {
                p.setTranslateY(this.getHeight() - cardHeight - 20);
                p.setTranslateX(w);
                p.setBorder(playerBorder);
            } else if (currPos == bottomPlayer.next()) {
                p.setTranslateY(h);
                p.setTranslateX(alignment);
            } else if (currPos == (bottomPlayer.next()).next()) {
                p.setTranslateY(20);
                p.setTranslateX(w);
            } else if (currPos == ((bottomPlayer.next()).next()).next()) {
                p.setTranslateY(h);
                p.setTranslateX(this.getWidth() - cardWidth - alignment);
            }
            currPos = currPos.next();
        }
    }

    /**
     * Hide the current trick.
     * Used when ending trick.
     */
    public void packUpTrick() {
        if (!showingTrickEnd) return;
        showingTrickEnd = false;
    }

    /**
     * Clear the trick pane.
     */
    protected void clear() {
        for (Pane p : cardPanes) {
            p.getChildren().clear();
        }
    }

    /**
     * Kill the timer for trick hiding.
     */
    public void killTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }

    /**
     * Set the game for the trick pane.
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
        statusPane.setGame(game);
    }

    /**
     * Updates the trick.
     */
    public void update() {
        if (blocking) {
            hideBlock.call();
        }
        Round round = game.currentRound();

        if (round.tricks.size() != 1 && !trickEndAlreadyDrawn) {
            trickEndAlreadyDrawn = true;
            showingTrickEnd = true;
            drawTrick(round.getLastFinishedTrick());
            showBlock.call();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> update());
                    blocking = true;
                    showingTrickEnd = false;
                }
            }, 2500);

        } else if (!showingTrickEnd) {

            Trick trick = round.currentTrick();
            if (trick == null) return;
            drawTrick(trick);
            trickEndAlreadyDrawn = false;
        }
    }

    private void drawTrick(Trick trick) {
        LOGGER.info("Drawing trick.");
        this.clear();
        Position currPosition = trick.firstPlayer;
        for (Card card : trick.cards) {
            putCard(card, currPosition);
            currPosition = currPosition.next();
        }
        adjustPanePositions();
        statusPane.update();
    }

    private void setupCardPanes() {
        cardPanes = new Pane[4];

        for (int i = 0; i < 4; i++) {
            Pane p = new Pane();
            cardPanes[i] = p;
            p.setMinHeight(cardHeight);
            p.setMinWidth(cardWidth);
            this.getChildren().add(p);
        }

        this.getChildren().add(statusPane);
        statusPane.setTranslateX(10);
        statusPane.setTranslateY(10);
    }

    private void putCard(Card card, Position pos) {

        String image = this.getClass().getResource(Resources.getImage(card)).toExternalForm();
        ImageView imageView = new ImageView(new Image(image));
        imageView.getStyleClass().add("card");

        this.cardPanes[pos.order].getChildren().add(imageView);
    }

}
