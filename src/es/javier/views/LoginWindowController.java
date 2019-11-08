package es.javier.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController {

    @FXML
    private Label Idlabel;

    @FXML
    private TextField Iduser;

    @FXML
    private TextField Idcontra;

    @FXML
    public void Login(ActionEvent actionEvent) throws IOException {
        if (Iduser.getText().equalsIgnoreCase("Usuario") && Idcontra.getText().equalsIgnoreCase("contra")) {
            Idlabel.setText("Logueado correctamente");
            Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Pantalla principal");
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
        } else {
            Idlabel.setText("Introduzca el usuario y contraseña correctos");
        }
    }

}

