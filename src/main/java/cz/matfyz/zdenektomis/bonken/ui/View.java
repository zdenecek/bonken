package cz.matfyz.zdenektomis.bonken.ui;

import javafx.scene.Scene;

/**
 * Abstract class for all GUI scenes.
 */
public abstract class View {
    private final String css = this.getClass().getResource(Resources.getCss()).toExternalForm();

    private Scene scene;

    /**
     * Creates a new view.
     */
    public View() {
    }

    /**
     * Returns the scene of the view.
     *
     * @return the scene of the view
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets the scene of the view.
     *
     * @param scene the scene to set
     */
    protected final void setScene(Scene scene) {

        scene.getStylesheets().add(css);
        this.scene = scene;
    }

}
