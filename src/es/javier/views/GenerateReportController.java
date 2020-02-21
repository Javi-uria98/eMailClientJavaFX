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

    private EmailCuenta cuenta;
    private List<Mensaje> listaMensajes;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfContrasena;

    @FXML
    private Button btnGenerar;

    @FXML
    private Button btnSalir;

    @FXML
    public void GenerarInforme(ActionEvent actionEvent) {
        try {
            String usuario = getDireccionCuenta();
            String contra = getContrasenaCuenta();
            cuenta = new EmailCuenta(usuario, contra);
            Logica.getInstance().cargarCuentaGmailInformes(cuenta, "[Gmail]/Importantes");

            listaMensajes = Logica.getInstance().getListaMensajesInformes();

            JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(listaMensajes);
            Map<String, Object> parametros = new HashMap<>();

            try {
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream("/es/javier/jasper/Mensaje.jasper"), parametros, jr);
                JasperExportManager.exportReportToPdfFile(print, "informes/Mensaje3.pdf");
            } catch (JRException e) {
                e.printStackTrace();
            }
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    private String getDireccionCuenta() {
        return tfDireccion.getText();
    }

    private String getContrasenaCuenta() {
        return tfContrasena.getText();
    }

}
