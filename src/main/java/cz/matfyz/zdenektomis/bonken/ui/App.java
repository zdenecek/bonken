

package cz.matfyz.zdenektomis.bonken.ui;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class for the GUI game.
 */
public class App extends Application {

    private App() {
    }

    /**
     * Main method.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setResizable(false);
        Controller controller = new Controller(stage);
        stage.setOnCloseRequest(event -> {
            stage.close();
            controller.close();
        });
        controller.start();
    }

}