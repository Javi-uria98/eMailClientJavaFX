package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.eMail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EmailWindowController {

    private eMail email;

    @FXML
    private TextField remitenteTF;

    @FXML
    private TextField asuntoTF;

    @FXML
    private DatePicker fechaTF;

    @FXML
    void crearEmail (ActionEvent actionEvent){
        String remitente=remitenteTF.getText();
        String asunto=asuntoTF.getText();
        LocalDate fecha=fechaTF.getValue();
        eMail email = new eMail(remitente,asunto,fecha);
        Logica.getInstance().addEmail(email);
        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.close();
    }

}
