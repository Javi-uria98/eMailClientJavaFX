package es.javier.views;

import com.sun.mail.iap.BadCommandException;
import es.javier.logica.Logica;
import es.javier.models.EmailCuenta;
import es.javier.models.Mensaje;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportsWindowController implements Initializable {

    private EmailCuenta cuenta;
    private List<Mensaje> listaMensajes;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfContrasena;

    @FXML
    private TextField tfCarpeta;

    @FXML
    private Button generarInforme;

    @FXML
    private ComboBox<String> comboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBox.setOnAction((e) -> {
            switch (comboBox.getSelectionModel().getSelectedItem()) {
                case "Informe de un solo correo":
                        generarInforme.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                GenerarInforme(false, getCarpetaCuenta(), "/es/javier/jasper/Mensaje.jasper", "informes/Mensaje1.pdf");
                            }
                        });
                    break;
                case "Informe de todos los correos de una carpeta":
                    generarInforme.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            GenerarInforme(true, getCarpetaCuenta(), "/es/javier/jasper/Mensaje2.jasper", "informes/Mensaje2.pdf");
                        }
                    });
                    break;
                case "Informe de todos los correos de una cuenta agrupados por carpeta":
                    generarInforme.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {

                        }
                    });
                    break;
                default:
                    break;
            }
        });
    }

    private void GenerarInforme(boolean carpeta, String nombreCarpeta, String rutaJasper, String rutaPDF) {
        try {
            String usuario = getDireccionCuenta();
            String contra = getContrasenaCuenta();
            cuenta = new EmailCuenta(usuario, contra);
            Logica.getInstance().cargarCuentaGmailInformes(cuenta, nombreCarpeta, carpeta);

            listaMensajes = Logica.getInstance().getListaMensajesInformes();

            JRBeanCollectionDataSource jr = new JRBeanCollectionDataSource(listaMensajes);
            Map<String, Object> parametros = new HashMap<>();

            try {
                JasperPrint print = JasperFillManager.fillReport(getClass().getResourceAsStream(rutaJasper), parametros, jr);
                JasperExportManager.exportReportToPdfFile(print, rutaPDF);
            } catch (JRException e) {
                e.printStackTrace();
            }
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    private String getDireccionCuenta() {
        return tfDireccion.getText();
    }

    private String getContrasenaCuenta() {
        return tfContrasena.getText();
    }

    private String getCarpetaCuenta(){
        return tfCarpeta.getText();
    }

}
