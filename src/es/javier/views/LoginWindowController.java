package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.EmailCuenta;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LoginWindowController implements Initializable {

    @FXML
    private Parent root;

    @FXML
    public Button btnAceptar;

    @FXML
    public TextField Iduser;

    @FXML
    public TextField Idcontra;

    static String usuario;
    static String contra;

    @FXML
    public void Login(ActionEvent actionEvent) {
        usuario = getIduser();
        contra = getIdcontra();
        EmailCuenta email=new EmailCuenta(usuario, contra);
        Logica.getInstance().addCuenta(email);

        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.close();
            /*Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Cuenta de gmail de "+titulo);
            stage.setScene(new Scene(root, 1200, 600));
            stage.show();*/
    }

    private String getIduser() {
        return Iduser.getText();
    }

    private String getIdcontra() {
        return Idcontra.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ValidationSupport validationSupport = new ValidationSupport();
        validationSupport.registerValidator(Iduser, Validator.createEmptyValidator("El usuario está vacío"));
        validationSupport.registerValidator(Iduser, Validator.createRegexValidator("Solo pueden introducirse letras, números y caracteres especiales",
                Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$"), Severity.ERROR));
        validationSupport.registerValidator(Idcontra, Validator.createEmptyValidator("La contraseña está vacía"));
        validationSupport.registerValidator(Idcontra, Validator.createRegexValidator("Solo pueden introducirse letras y números", Pattern.compile("^[a-zA-Z0-9]*$"), Severity.ERROR));

        validationSupport.invalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                btnAceptar.setDisable(newValue);
            }
        });
    }
}

