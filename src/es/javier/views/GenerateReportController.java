package es.javier.views;

import com.sun.mail.util.MailConnectException;
import es.javier.logica.Logica;
import es.javier.models.EmailCuenta;
import es.javier.models.Mensaje;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BackgroundImage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.mail.*;
import java.net.URL;
import java.util.*;

public class GenerateReportController implements Initializable {

    private Store store;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfContrasena;

    @FXML
    private Button btnGenerar;

    @FXML
    private Button btnSalir;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String usuario = getDireccionCuenta();
            String contra = getContrasenaCuenta();
            EmailCuenta cuenta = new EmailCuenta(usuario, contra);
            Logica.getInstance().cargarCuentaGmail(cuenta, "INBOX");

            /*
            ArrayList<Mensaje> listaMensajes = new ArrayList<Mensaje>();
            generarUnMensaje(cuenta, "[Gmail]/Importantes", listaMensajes);
            */

            btnGenerar.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(Logica.getInstance().getListaMensajes());
                    Map<String, Object> parametros = new HashMap<>();

                    try {
                        JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/javier/jasper/Mensaje.jasper"), parametros, jr);
                        JasperExportManager.exportReportToPdfFile(print, "informes/Mensaje1.pdf");
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }
            });

            btnSalir.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    private String getDireccionCuenta() {
        return tfDireccion.getText();
    }

    private String getContrasenaCuenta() {
        return tfContrasena.getText();
    }

    private void generarUnMensaje(EmailCuenta email, String s, ArrayList<Mensaje> mensajes) {
        try {
            String imap = "imaps";
            Properties properties = new Properties();
            properties.setProperty("mail.store.protocol", imap);
            Session session = Session.getInstance(properties);
            store = session.getStore(imap);
            store.connect("smtp.gmail.com", email.getDireccion(), email.getContrasena());
            Folder folder = store.getFolder(s);
            folder.open(Folder.READ_ONLY);
            Message[] message = folder.getMessages();

            for (int i = 0; i < message.length; i++) {
                Mensaje m = new Mensaje(message[i]);
                mensajes.add(m);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
