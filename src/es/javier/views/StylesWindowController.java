package es.javier.views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class StylesWindowController implements Initializable {

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private String caspian;

    @FXML
    private String modena;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setOnAction((e) -> {
            if (comboBox.getSelectionModel().getSelectedItem().equals("Caspian")) {
                Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
            } else
                Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);

        });
    }

}
