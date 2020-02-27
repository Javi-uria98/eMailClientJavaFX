package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.Mensaje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import javax.mail.MessagingException;
import java.net.URL;
import java.util.ResourceBundle;

import static es.javier.logica.Logica.getInstance;

public class PantallaExamenController implements Initializable {

    private Mensaje mensaje;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    void alert(ActionEvent actionEvent) throws MessagingException {
        int indiceasunto = comboBox.getSelectionModel().getSelectedIndex();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText("Queda el asunto");
        alert.setContentText(Logica.getInstance().getListaMensajes().get(indiceasunto).getAsunto());
        alert.showAndWait();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < getInstance().getListaMensajes().size(); i++) {
            try {
                String listaRemitente = Logica.getInstance().getListaMensajes().get(i).getRemitente();
                String listaFecha = Logica.getInstance().getListaMensajes().get(i).getFecha();
                comboBox.getItems().addAll(listaRemitente + ", " + listaFecha);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

}
