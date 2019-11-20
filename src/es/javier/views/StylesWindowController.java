package es.javier.views;

import com.sun.javafx.css.StyleManager;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StylesWindowController implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private String caspian;

    @FXML
    private String modena;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setPromptText("Elija su estilo ...");
        comboBox.setOnAction((e) -> {
            Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
            switch (comboBox.getSelectionModel().getSelectedItem()) {
                case "Misterioso":
                    StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("resources/estilomisterioso.css").toExternalForm());
                    break;
                case "Impactante":
                    StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("resources/estiloimpactante.css").toExternalForm());
                    break;
                case "Increible":
                    StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("resources/estiloincreible.css").toExternalForm());
                    break;
                case "Maravilloso":
                    StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("resources/estilomaravilloso.css").toExternalForm());
                    break;
                case "Fantastico":
                    StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("resources/estilofantastico.css").toExternalForm());
                    break;
                default:
                    break;
            }
        });
    }


}



