module eMailClientJavaFX {
    requires javafx.graphics;
    requires javafx.controls;
    requires org.controlsfx.controls;
    requires javafx.web;
    requires javafx.fxml;
    requires java.mail;
    requires javafx.base;
    requires RelojDigitalv2;
    requires jasperreports;
    requires java.sql;
    exports es.javier.views;
    exports es.javier;
    exports es.javier.models;
    opens es.javier.views.resources to javafx.fxml;
    opens es.javier.views to javafx.fxml;
    opens es.javier.models to javafx.base;
}