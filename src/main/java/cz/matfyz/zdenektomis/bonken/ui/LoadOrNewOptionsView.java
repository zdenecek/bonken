package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Class for choosing between a new game or saved game.
 */
public class LoadOrNewOptionsView extends View {

    private final SimpleEvent<Void> showStartMenu = new SimpleEvent<>();
    private final SimpleEvent<Void> startVsBotsNew = new SimpleEvent<>();
    private final SimpleEvent<Void> startVsBotsLoad = new SimpleEvent<>();

    /**
     * Creates a new load or new options view.
     */
    public LoadOrNewOptionsView() {
        BorderPane borderPane = new BorderPane();

        Button returnBtn = new Button("RETURN");
        returnBtn.getStyleClass().add("return-button");
        returnBtn.setOnAction(event -> showStartMenu.fire(null));
        VBox returnBox = new VBox(returnBtn);
        returnBox.setAlignment(Pos.TOP_LEFT);

        Label text = new Label("Choose game mode");

        Button newGame = new Button("NEW GAME");
        newGame.getStyleClass().add("menu-button");

        Button loadGame = new Button("LOAD GAME");
        loadGame.getStyleClass().add("menu-button");

        HBox hbox = new HBox(newGame, loadGame);
        VBox menu = new VBox(text, hbox);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(28);
        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(28);

        borderPane.setCenter(menu);
        borderPane.setTop(returnBox);

        Scene scene = new Scene(borderPane, 1080, 720);

        newGame.setOnAction(event -> startVsBotsNew.fire(null));
        loadGame.setOnAction(event -> startVsBotsLoad.fire(null));

        setScene(scene);
    }

    /**
     * Get the event that is fired when the start menu button is clicked
     * @return event that is fired when the start menu button is clicked
     */
    public Event<Void> showStartMenu() {
        return showStartMenu;
    }

    /**
     * Get the event that is fired when the new game button is clicked
     * @return event that is fired when the new game button is clicked
     */
    public Event<Void> startVsBotsNew() {
        return startVsBotsNew;
    }

    /**
     * Get the event that is fired when the load game button is clicked
     * @return event that is fired when the load game button is clicked
     */
    public Event<Void> startVsBotsLoad() {
        return startVsBotsLoad;
    }
}
