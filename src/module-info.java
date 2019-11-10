module eMailClientJavaFX {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.mail;
    requires javafx.fxml;
    requires javafx.web;
    exports es.javier.views;
    exports es.javier;
    opens es.javier.views to javafx.fxml;
    opens es.javier.models to javafx.base;
}