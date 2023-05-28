module cz.matfyz.zdenektomis.bonken.bonken {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens cz.matfyz.zdenektomis.bonken.ui to javafx.fxml;
    exports cz.matfyz.zdenektomis.bonken.ui;
    exports cz.matfyz.zdenektomis.bonken.console;

}