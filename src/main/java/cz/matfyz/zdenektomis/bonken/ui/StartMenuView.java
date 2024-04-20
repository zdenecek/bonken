package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


/**
 * Class for the start menu of the game.
 */
public class StartMenuView extends View {

    private final SimpleEvent<Void> onStartClicked = new SimpleEvent<>();
    private final SimpleEvent<Void> onQuitClicked = new SimpleEvent<>();
    private final Button exitBtn;
    private final Button startBtn;
    private final Label mainLabel;
    private final VBox menuButtons;
    private final VBox menu;

    /**
     * Creates a new start menu view.
     */
    public StartMenuView() {

        mainLabel = new Label("BONKEN");
        mainLabel.getStyleClass().add("name-label");

        startBtn = new Button("START");
        startBtn.getStyleClass().add("menu-button");
        startBtn.setOnAction(event -> onStartClicked.fire(null));

        exitBtn = new Button("EXIT");
        exitBtn.getStyleClass().add("menu-button");
        exitBtn.setOnAction(event -> onQuitClicked.fire(null));

        menuButtons = new VBox(20, startBtn, exitBtn);
        menuButtons.setPadding(new Insets(5));
        menuButtons.setAlignment(Pos.CENTER);

        menu = new VBox(mainLabel, menuButtons);
        menu.setPadding(new Insets(10));
        menu.setAlignment(Pos.CENTER);

        this.setScene(new Scene(menu, 1080, 720));
    }


    /**
     * Get the event that is fired when the start button is clicked
     * @return event that is fired when the start button is clicked
     */
    public Event<Void> onStartClicked() {
        return onStartClicked;
    }

    /**
     * Get the event that is fired when the quit button is clicked
     * @return event that is fired when the quit button is clicked
     */
    public Event<Void> onQuitClicked() {
        return onQuitClicked;
    }

}
