package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.eMail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowController {

    @FXML
    private Parent root;

    @FXML
    public TextField Iduser;

    @FXML
    public TextField Idcontra;

    static String usuario;
    static String contra;

    @FXML
    public void Login(ActionEvent actionEvent) throws IOException {
        usuario = getIduser();
        contra = getIdcontra();
        eMail email=new eMail(usuario, contra);
        Logica.getInstance().addCuenta(email);

        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.close();
            /*Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Cuenta de gmail de "+titulo);
            stage.setScene(new Scene(root, 1200, 600));
            stage.show();*/
    }

    private String getIduser() {
        return Iduser.getText();
    }

    private String getIdcontra() {
        return Idcontra.getText();
    }
}

