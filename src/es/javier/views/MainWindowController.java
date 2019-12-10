package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.EmailCuenta;
import es.javier.models.Mensaje;
import es.javier.models.EmailTreeItem;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.mail.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static es.javier.logica.Logica.autoResizeColumns;

public class MainWindowController implements Initializable {

    private ObservableList<Mensaje> listaMensajes;
    private ObservableList<EmailCuenta> listaCuentas;
    private EmailCuenta email;
    private Mensaje mensaje;
    private int contLogin = 0;

    @FXML
    SplitPane root;

    @FXML
    public TableView<Mensaje> tableMessages;

    @FXML
    public TableColumn columnaremitente;

    @FXML
    private TreeView treeViewEmail;

    @FXML
    private WebView webView;


    @FXML
    void pantallaLogin(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginwindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 300, 300));
            stage.setResizable(false);
            stage.setTitle("Pantalla de Login");
            stage.showAndWait();

            try {
                contLogin++;
                email = Logica.getInstance().getListaEmail().get(contLogin);
                Logica.getInstance().cargarCuentaGmail(email, "INBOX");
            } catch (MessagingException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Usuario o contraseña errónea. Vuelva a intentarlo");
                alert.showAndWait();
            } catch (IndexOutOfBoundsException ignored) {

            }
            listaMensajes = Logica.getInstance().getListaMensajes();
            tableMessages.setItems(listaMensajes);
            tableMessages.setRowFactory(new Callback<TableView<Mensaje>, TableRow<Mensaje>>() {
                @Override
                public TableRow<Mensaje> call(TableView<Mensaje> mensajeTableView) {
                    return new TableRow<Mensaje>() {
                        @Override
                        protected void updateItem(Mensaje mensaje, boolean b) {
                            super.updateItem(mensaje, b);
                            if (mensaje != null) {
                                try {
                                    if (!mensaje.estadoLeido())
                                        setStyle("-fx-font-weight:bold");
                                    else
                                        setStyle("");
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }
            });
            autoResizeColumns(tableMessages);
            try {
                generateTreeView();
            } catch (MessagingException ignored) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pantallaEnviar(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sendwindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pantallaResponder(ActionEvent actionEvent) {
        EmailTreeItem treeItem = (EmailTreeItem) treeViewEmail.getSelectionModel().getSelectedItem();
        if (treeItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("");
            alert.setContentText("Tiene que seleccionar un mensaje para poder responder");
            alert.showAndWait();
            return;
        }
        EmailCuenta cuenta = treeItem.getEmail();
        Mensaje mensaje = tableMessages.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sendwindow.fxml"));
            Parent root = fxmlLoader.load();
            SendWindowController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 600, 400));
            try {
                controller.responder(mensaje, cuenta);
            } catch (NullPointerException n) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("");
                alert.setContentText("Tiene que seleccionar un mensaje para poder responder");
                alert.showAndWait();
                return;
            }
            stage.show();
        } catch (IOException | MessagingException a) {
            a.printStackTrace();
        }

    }

    @FXML
    void pantallaReenviar(ActionEvent actionEvent) {
        EmailTreeItem treeItem = (EmailTreeItem) treeViewEmail.getSelectionModel().getSelectedItem();
        if (treeItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aviso");
            alert.setHeaderText("");
            alert.setContentText("Tiene que seleccionar un mensaje para poder reenviar");
            alert.showAndWait();
            return;
        }
        EmailCuenta cuenta = treeItem.getEmail();
        Mensaje mensaje = tableMessages.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sendwindow.fxml"));
            Parent root = fxmlLoader.load();
            SendWindowController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 600, 400));
            try {
                controller.reenviar(mensaje, cuenta);
            } catch (NullPointerException n) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Aviso");
                alert.setHeaderText("");
                alert.setContentText("Tiene que seleccionar un mensaje para poder responder");
                alert.showAndWait();
                return;
            }
            stage.show();
        } catch (IOException | MessagingException a) {
            a.printStackTrace();
        }
    }

    @FXML
    void borrarMensaje(ActionEvent event) {
        mensaje = tableMessages.getSelectionModel().getSelectedItem();
        int index = tableMessages.getSelectionModel().getSelectedIndex();

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
    private void generateTreeView() throws MessagingException {
        listaCuentas = Logica.getInstance().getListaEmail();
        EmailTreeItem root = new EmailTreeItem("Cuentas"); //elemento raíz de todoo el tableview
        root.setExpanded(true);
        for (EmailCuenta e : listaCuentas) {
            Logica.getInstance().cargarCuentaGmail(e, "INBOX");
            String nombre = e.getDireccion();
            Folder folder = Logica.getInstance().getFolder();
            EmailTreeItem eMailTreeItem = new EmailTreeItem(nombre, e, folder);
            Logica.getInstance().llenarTreeView(eMailTreeItem.getFolder().list(), eMailTreeItem);
            root.getChildren().add(eMailTreeItem);
        }
        treeViewEmail.setRoot(root);
        treeViewEmail.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue,
                                Object newValue) {
                try {
                    EmailTreeItem selectedItem = (EmailTreeItem) newValue;
                    tableMessages.getItems().clear();
                    System.out.println("Selected Text : " + selectedItem.getFolder().toString());
                    Logica.getInstance().cargarCuentaGmail(email, selectedItem.getFolder().toString()); //Importante el getFolder.toString(), pues devuelve el String de la ruta completa de la carpeta
                } catch (Exception e) {                                                                 //no como el getValue, que solo devuelve el String de la carpeta en sí y por eso no cargaba los mensajes
                }
                tableMessages.setItems(listaMensajes);
            }

        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginwindow.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(root, 300, 300));
        stage.setTitle("Pantalla de Login");
        stage.setResizable(false);
        stage.showAndWait();
        try {
            email = Logica.getInstance().getListaEmail().get(contLogin);
            listaMensajes = Logica.getInstance().getListaMensajes();
            tableMessages.setItems(listaMensajes);

            tableMessages.setRowFactory(new Callback<TableView<Mensaje>, TableRow<Mensaje>>() {
                @Override
                public TableRow<Mensaje> call(TableView<Mensaje> mensajeTableView) {
                    return new TableRow<Mensaje>() {
                        @Override
                        protected void updateItem(Mensaje mensaje, boolean b) {
                            super.updateItem(mensaje, b);
                            if (mensaje != null) {
                                try {
                                    if (!mensaje.estadoLeido())
                                        setStyle("-fx-font-weight:bold");
                                    else
                                        setStyle("");
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                }
            });

            tableMessages.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Mensaje>() {
                @Override
                public void changed(ObservableValue<? extends Mensaje> observableValue, Mensaje mensaje, Mensaje t1) {
                    try {
                        String m = t1.getMessageContent();
                        WebEngine webEngine = webView.getEngine();
                        webEngine.loadContent(m);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    } catch (NullPointerException ignored) {
                    }
                }
            });
            try {
                generateTreeView();
            } catch (AuthenticationFailedException auth) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("");
                alert.setContentText("Usuario o contraseña errónea. Vuelva a intentarlo");
                alert.showAndWait();
                System.exit(0);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        } catch (IndexOutOfBoundsException | NullPointerException ignored) {

        }
        try {
            autoResizeColumns(tableMessages);
        } catch (NullPointerException ignored) {

        }

    }

}
