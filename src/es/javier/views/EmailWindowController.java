package es.javier.views;

import es.javier.models.eMail;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class EmailWindowController {

    private eMail email;

    @FXML
    private Button aceptar;

    @FXML
    private TextField remitenteTF;

    @FXML
    private TextField asuntoTF;

    @FXML
    private TextField fechaTF;

}
