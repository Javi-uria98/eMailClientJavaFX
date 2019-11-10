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
    private static TextField Iduser;

    @FXML
    private static TextField Idcontra;

    @FXML
    public void Login(ActionEvent actionEvent) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 300, 275));
            stage.show();
    }

    public static String getIduser (){
        return Iduser.getText();
    }

    public static String getIdcontra() {
        return Idcontra.getText();
    }
}

