package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.utils.Event;
import cz.matfyz.zdenektomis.bonken.utils.SimpleEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * Class that shows username input screen.
 */
public class NameInputView extends View {

    private final SimpleEvent<String> onSubmit = new SimpleEvent<>();
    private final TextField textField;
    private final Label label;
    private final Label label1;
    private final BorderPane borderPane;
    private final Button submitButton;
    private final VBox vb;
    public NameInputView() {

        label1 = new Label("Enter your username:");

        textField = new TextField();
        textField.setMaxWidth(400);

        submitButton = new Button("OK");
        submitButton.getStyleClass().add("ok-button");
        submitButton.setOnAction(e -> submit());
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) submit();
        });

        label = new Label();
        label.getStyleClass().add("small-label");

        vb = new VBox();
        vb.getChildren().addAll(label1, textField, submitButton, label);
        vb.setSpacing(30);
        vb.setAlignment(Pos.CENTER);

        borderPane = new BorderPane();
        borderPane.setCenter(vb);

        setScene(new Scene(borderPane, 1080, 720));
    }

    public Event<String> onSubmit() {
        return onSubmit;
    }

    private void submit() {
        var name = textField.getText();
        if (name.length() > 8) {
            label.setText("Username is too long.");
        } else if (name == null || name.length() == 0) {
            label.setText("Username cannot be empty.");
        } else {
            onSubmit.fire(name);
        }
    }
}
