package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.Mensaje;
import es.javier.models.eMail;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController  implements Initializable {

    private ObservableList<Mensaje> listaMensajes;
    private eMail email;


    @FXML
    private TableView<Mensaje> tableMessages;

    @FXML
    private TreeView treeViewEmail;

    @FXML
    void altaEmail(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("emailwindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 300, 275));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String usuario=LoginWindowController.getIduser();
        String contra=LoginWindowController.getIdcontra();
        listaMensajes=Logica.getInstance().getListaMensajes();
        email=new eMail(usuario,contra);
        try {
            Logica.getInstance().cargarCuentaGmail(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        tableMessages.setItems(listaMensajes);

    }
}
