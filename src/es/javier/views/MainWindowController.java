package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.Mensaje;
import es.javier.models.eMail;
import es.javier.models.eMailTreeItem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.scene.web.WebEngine;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private ObservableList<Mensaje> listaMensajes;
    private eMail email;
    private WebEngine webEngine;
    private Mensaje mensaje;

    @FXML
    private TableView<Mensaje> tableMessages;

    @FXML
    private TreeView treeViewEmail;

    @FXML
    private WebView webView;

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

    @FXML
    void seleccionarFila(MouseEvent event) {
        mensaje = tableMessages.getSelectionModel().getSelectedItem();
        try {
            int index = tableMessages.getSelectionModel().getSelectedIndex();
            webView.getEngine().loadContent(mensaje.getMessageContent(Logica.getInstance().getListaMensajes().get(index)));
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException ignored) {

        }

    }

    /*private eMailTreeItem getFolders (eMail email) throws MessagingException {
        eMailTreeItem treeItem = new eMailTreeItem(email.getDireccion(), email.getContrasena(), null);
        Folder[] folders
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email = new eMail(LoginWindowController.getIduser(), LoginWindowController.getIdcontra());
        try {
            Logica.getInstance().cargarCuentaGmail(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        listaMensajes = Logica.getInstance().getListaMensajes();
        tableMessages.setItems(listaMensajes);
    }

    //crear una pantalla de opciones donde se pueda cambiar el diseño de la app (darle a un botón llamado estilos y que me abra una nueva ventana donde seleccionar que estilo poner)

    //los botones que hay que poner (nuevo, responder, reenviar y eliminar) tienen que ir en un componente llamado toolbar

    //buscar como ajustar automáticamente las columnas de una tabla

    //debería cambiar el diseño de la app y que en vez de usar un borderpane use un splitpane (dos en realidad, uno horizontal general y otro vertical en la parte derecha)

}
