module com.bonken.bonken {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.bonken.ui to javafx.fxml;
    exports com.bonken.ui;
}