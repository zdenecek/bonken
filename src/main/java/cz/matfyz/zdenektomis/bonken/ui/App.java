package cz.matfyz.zdenektomis.bonken.ui;


import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

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