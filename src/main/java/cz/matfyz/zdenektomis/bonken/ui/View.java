package cz.matfyz.zdenektomis.bonken.ui;

import javafx.scene.Scene;

/**
 * Abstract class for all GUI scenes.
 */
public abstract class View {
    private final String css = this.getClass().getResource(Resources.getCss()).toExternalForm();

    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    protected final void setScene(Scene scene) {

        scene.getStylesheets().add(css);
        this.scene = scene;
    }

}
