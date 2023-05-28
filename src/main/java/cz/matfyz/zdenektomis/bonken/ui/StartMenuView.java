package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class StartMenuView extends View {
    
    private SimpleEvent<Void> onStartClicked = new SimpleEvent<>();
    private SimpleEvent<Void> onQuitClicked = new SimpleEvent<>();
    private Button exitBtn, startBtn;
    private Label mainLabel;
    private VBox menuButtons, menu;
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

    public Event<Void> onStartClicked() {
        return onStartClicked;
    }

    public Event<Void> onQuitClicked() {
        return onQuitClicked;
    }

}
