module eMailClientJavaFX {
    requires javafx.graphics;
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires javafx.web;
    requires javafx.fxml;
    requires java.mail;
    requires RelojDigitalv2;
    exports es.javier.views;
    exports es.javier;
    opens es.javier.views.resources to javafx.fxml;
    opens es.javier.views to javafx.fxml;
    opens es.javier.models to javafx.base;
}