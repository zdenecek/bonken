package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.model.Card;
import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class for showing players hand at the bottom of the screen.
 */
public class CardPane extends HBox {

    private static final Logger LOGGER = Logger.getLogger(CardPane.class.getName());

    private final double cardWidth = 132;
    private final double cardHeight = 180;
    private final double cardHoverDistance = 25;

    private final SimpleEvent<Card> cardClicked = new SimpleEvent<>();

    public CardPane() {
        super();
        this.setAlignment(Pos.CENTER);
    }

    public Event<Card> cardClicked() {
        return cardClicked;
    }


    public void update(List<Card> cards) {
        this.update(cards, null);
    }

    /**
     * Updates CardPane before playing into trick.
     *
     * @param cards
     * @param playableCards
     */
    public void update(List<Card> cards, Set<Card> playableCards) {

        LOGGER.info("Updating card view");

        this.getChildren().clear();
        int cardCount = cards.size();

        double size = this.getWidth();
        double sizeForOverlap = size - cardWidth;
        double spacePerCard = sizeForOverlap / (cardCount - 1);
        double space = spacePerCard - cardWidth;

        this.setSpacing(space > 0 ? 0 : space);

        for (var card : cards) {

            ImageView imageView = new ImageView(new Image(getImage(card)));
            imageView.getStyleClass().add("card");
            VBox pane = new VBox(imageView);
            pane.setAlignment(Pos.BOTTOM_CENTER);
            pane.setMinHeight(cardHeight + cardHoverDistance);

            this.getChildren().add(pane);

            if (playableCards != null && playableCards.contains(card)) {
                pane.setOnMouseClicked(event -> cardClicked.fire(card));
                pane.hoverProperty().addListener((__, ___, hover) -> imageView.setTranslateY(hover ? -cardHoverDistance : 0));
            }
        }
    }

    private String getImage(Card card) {
        return this.getClass().getResource(Resources.getImage(card)).toExternalForm();
    }
}
