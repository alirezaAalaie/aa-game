module aa {

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    exports view.Menu;

    opens view.Menu to javafx.fxml;

    exports model;
    opens model to com.google.gson, javafx.fxml;

    exports controller.control;
    opens controller.control to com.google.gson;

}
