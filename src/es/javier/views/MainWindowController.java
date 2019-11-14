package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.Mensaje;
import es.javier.models.eMail;
import es.javier.models.eMailTreeItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import javax.mail.*;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import static es.javier.logica.Logica.autoResizeColumns;

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
    void pantallaEstilos(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("styleswindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 300, 300));
            stage.show();
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

    @FXML
    private eMailTreeItem generateTreeView() throws MessagingException {

        eMail eMail = new eMail(LoginWindowController.getIduser(), LoginWindowController.getIdcontra());
        String nombre = LoginWindowController.getIduser().substring(0, 12);
        Folder folder = Logica.getInstance().getFolder();

        eMailTreeItem eMailTreeItem = new eMailTreeItem(nombre, eMail, folder);

        Logica.getInstance().llenarTreeView(eMailTreeItem.getFolder().list(), eMailTreeItem, eMail);
        return eMailTreeItem;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        email = new eMail(LoginWindowController.getIduser(), LoginWindowController.getIdcontra());
        try {
            Logica.getInstance().cargarCuentaGmail(email, "INBOX");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        listaMensajes = Logica.getInstance().getListaMensajes();
        tableMessages.setItems(listaMensajes);
        autoResizeColumns(tableMessages);
        try {
            eMailTreeItem e = generateTreeView();
            treeViewEmail.setRoot(e);
            treeViewEmail.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue observable, Object oldValue,
                                    Object newValue) {
                    try {
                        eMailTreeItem selectedItem = (eMailTreeItem) newValue;
                        tableMessages.getItems().clear();
                        System.out.println("Selected Text : " + selectedItem.getValue());
                        Logica.getInstance().cargarCuentaGmail(email, selectedItem.getValue());
                    } catch (Exception e) {
                    }
                    tableMessages.setItems(listaMensajes);
                }

            });
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

}
