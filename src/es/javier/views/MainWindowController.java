package es.javier.views;

import com.javier.componente.ComponenteReloj;
import com.javier.componente.EnHoraQueCoincide;
import com.javier.componente.Tarea;
import es.javier.logica.Logica;
import es.javier.logica.Servicios;
import es.javier.models.EmailCuenta;
import es.javier.models.Mensaje;
import es.javier.models.EmailTreeItem;
import es.javier.models.MensajeInforme;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.docgene.help.JavaHelpFactory;
import org.docgene.help.gui.jfx.JFXHelpContentViewer;

import javax.mail.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static es.javier.logica.Logica.autoResizeColumns;

public class MainWindowController implements Initializable {

    private ObservableList<Mensaje> listaMensajes;
    private ArrayList<MensajeInforme> listaMensajesInforme;
    private ArrayList<MensajeInforme> listaMensajesInforme_carpeta;
    private ArrayList<MensajeInforme> listaMensajesInforme_cuenta;
    private EmailCuenta email;
    private ObservableList<Tarea> listaTareas;
    private int contLogin = 0;
    private MensajeInforme mensajeInforme;

    @FXML
    private Button btnUnInforme;

    @FXML
    private ComponenteReloj cp;

    @FXML
    SplitPane root;

    @FXML
    private Label label;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public TableView<Mensaje> tableMessages;

    @FXML
    public TableColumn columnaremitente;

    @FXML
    private TreeView treeViewEmail;

    @FXML
    private WebView webView;

    @FXML
    private Button btnNuevo;

    @FXML
    public Button btnEstilos;

    @FXML
    private Button btnAlarma;

    @FXML
    private Button btnAyuda;

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
        Mensaje mensaje = tableMessages.getSelectionModel().getSelectedItem();
        int index = tableMessages.getSelectionModel().getSelectedIndex();

    }

    @FXML
    void pantallaEstilos(ActionEvent event) {
        try {
            btnEstilos.setText("Nuevo estilo");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("styleswindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 300, 300));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pantallaAlarma(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("alarmwindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root, 500, 500));
            stage.setResizable(false);
            stage.showAndWait();

            listaTareas = Logica.getInstance().getListaTareas();

            if (!listaTareas.isEmpty()) {
                for (int i = 0; i < listaTareas.size(); i++) {
                    cp.registarTarea(Logica.getInstance().getListaTareas().get(i));
                }
                cp.addEnHoraQueCoincide(new EnHoraQueCoincide() {
                    @Override
                    public void ejecuta(Tarea tarea) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Aviso");
                        alert.setHeaderText("");
                        alert.setContentText(tarea.getTextoAlarma());
                        alert.showAndWait();
                    }
                });
            }
        } catch (IOException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void pantallaAyuda(ActionEvent actionEvent) {
        try {
            URL url = this.getClass().getResource("/help/articles.zip");
            JavaHelpFactory factory = new JavaHelpFactory(url);
            factory.create();
            JFXHelpContentViewer viewer = new JFXHelpContentViewer();
            factory.install(viewer);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("helpwindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            viewer.getHelpWindow(stage, "Help Content", 600, 700);
            viewer.showHelpDialog(root);
            //stage.setScene(new Scene(root, 300, 300));
            //stage.showAndWait();

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void generateTreeView() throws MessagingException {
        ObservableList<EmailCuenta> listaCuentas = Logica.getInstance().getListaEmail();
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
        progressIndicator.setVisible(false);
        treeViewEmail.setRoot(root);
        treeViewEmail.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Servicios testService = new Servicios("Por fin!");
                testService.start();
                testService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        //Recuperamos el valor de retorno
                        String saludo = testService.getValue();
                        label.setText(saludo);
                        progressIndicator.setVisible(false);
                    }
                });
                testService.setOnRunning(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        progressIndicator.setVisible(true);
                    }
                });
                try {
                    EmailTreeItem selectedItem = (EmailTreeItem) newValue;
                    tableMessages.getItems().clear();
                    Logica.getInstance().cargarCuentaGmail(email, selectedItem.getFolder().toString()); //Importante el getFolder.toString(), pues devuelve el String de la ruta completa de la carpeta no como el getValue, que solo devuelve el String de la carpeta en sí y por eso no cargaba los mensajes
                    Logica.getInstance().cargarCuentaGmailInformesv2(email, selectedItem.getFolder().toString());
                    tableMessages.setItems(listaMensajes);
                    listaMensajesInforme = Logica.getInstance().getListaMensajesInformesv2();

                    if (selectedItem.getValue().equals("Todos")) {
                        btnUnInforme.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                for (int i = 1; i < treeViewEmail.getExpandedItemCount(); i++) {
                                    if (treeViewEmail.getTreeItem(i).getValue().equals("INBOX")) {
                                        System.out.println(treeViewEmail.getTreeItem(i).getValue());
                                        try {
                                            Logica.getInstance().cargarCuentaGmailInformesv2_cuentas(email, "INBOX");
                                        } catch (MessagingException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        if (!treeViewEmail.getTreeItem(i).getValue().toString().contains("@")) {
                                            String carpeta = "[Gmail]/" + treeViewEmail.getTreeItem(i).getValue();
                                            System.out.println(carpeta);
                                            if (!carpeta.equals("[Gmail]/[Gmail]")) {
                                                try {
                                                    Logica.getInstance().cargarCuentaGmailInformesv2_cuentas(email, carpeta);
                                                } catch (MessagingException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                                listaMensajesInforme_cuenta = Logica.getInstance().getListaMensajesInformesv2_cuenta();
                                if (!listaMensajesInforme_cuenta.isEmpty()) {
                                    try {
                                        JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(listaMensajesInforme_cuenta);
                                        Map<String, Object> parametros = new HashMap<>();
                                        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/javier/jasper/Cherry_Landscape.jasper"), parametros, jr);
                                        JasperExportManager.exportReportToPdfFile(print, "informes/InformeMensajesCuenta.pdf");
                                    } catch (JRException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    } else {
                        btnUnInforme.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    Logica.getInstance().cargarCuentaGmailInformesv2_carpetas(email, selectedItem.getFolder().toString());
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                                listaMensajesInforme_carpeta = Logica.getInstance().getListaMensajesInformesv2_carpeta();
                                JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(listaMensajesInforme_carpeta);
                                Map<String, Object> parametros = new HashMap<>();
                                try {
                                    JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/javier/jasper/Cherry.jasper"), parametros, jr);
                                    JasperExportManager.exportReportToPdfFile(print, "informes/InformeMensajesCarpeta.pdf");
                                } catch (JRException e) {
                                    e.printStackTrace();
                                } catch (NullPointerException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("¡Error!");
                                    alert.setHeaderText("");
                                    alert.setContentText("Para generar el informe, seleccione un mensaje!");
                                    alert.showAndWait();
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                }
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
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        cp.iniciar();
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
                        btnUnInforme.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                try {
                                    String[] destinatarioInforme = t1.getDestinatario();
                                    String destinatarioFinal = destinatarioInforme[0];
                                    mensajeInforme = new MensajeInforme(t1.getFecha(), t1.getAsunto(), t1.getRemitente(), destinatarioFinal, t1.getMessageContent());
                                    if (listaMensajesInforme == null) {
                                        listaMensajesInforme = Logica.getInstance().getListaMensajesInformesv2();
                                        Logica.getInstance().addMensajeInformev2(mensajeInforme);
                                    } else {
                                        listaMensajesInforme = new ArrayList<MensajeInforme>();
                                        listaMensajesInforme.add(mensajeInforme);
                                    }
                                    JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(listaMensajesInforme);
                                    Map<String, Object> parametros = new HashMap<>();
                                    try {
                                        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/javier/jasper/Silhouette_Landscape.jasper"), parametros, jr);
                                        JasperExportManager.exportReportToPdfFile(print, "informes/InformeMensajeUnico.pdf");
                                    } catch (JRException e) {
                                        e.printStackTrace();
                                    }
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                } catch (NullPointerException e) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("¡Error!");
                                    alert.setHeaderText("");
                                    alert.setContentText("¡Para generar el informe, seleccione un mensaje!");
                                    alert.showAndWait();
                                }

                            }
                        });
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
