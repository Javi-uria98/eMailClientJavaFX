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
    private ArrayList<EmailCuenta> listaCuentas;
    private EmailCuenta email;
    private Mensaje mensaje;
    static Mensaje mresponder;


    @FXML
    SplitPane root;

    @FXML
    private TableView<Mensaje> tableMessages;


    @FXML
    public static TableColumn columnaremitente;

    @FXML
    private TreeView treeViewEmail;

    @FXML
    private WebView webView;


    @FXML
    void pantallaLogin(ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginwindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 600, 400));
            stage.showAndWait();
            //email = new EmailCuenta(LoginWindowController.usuario, LoginWindowController.contra);
            try {
                Logica.getInstance().cargarCuentaGmail(email, "INBOX");
            } catch (MessagingException e) {
                e.printStackTrace();
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
            } catch (MessagingException e) {
                e.printStackTrace();
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
        TablePosition pos = tableMessages.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        mresponder = tableMessages.getItems().get(row);
        columnaremitente = pos.getTableColumn();
        //System.out.println((String)columnaremitente.getCellObservableValue(mresponder).getValue());
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("answerwindow.fxml"));
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
    void seleccionarFila(MouseEvent event) {
        mensaje = tableMessages.getSelectionModel().getSelectedItem();
        try {
            int index = tableMessages.getSelectionModel().getSelectedIndex();
            webView.getEngine().loadContent(mensaje.getMessageContent(Logica.getInstance().getListaMensajes().get(index)));
            tableMessages.refresh();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException ignored) {

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
        stage.showAndWait();

        email = Logica.getInstance().getListaEmail().get(0);

        try {
            Logica.getInstance().cargarCuentaGmail(email, "INBOX");
        } catch (MessagingException e) {
            e.printStackTrace();
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
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
