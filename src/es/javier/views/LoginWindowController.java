package es.javier.views;

import es.javier.logica.Logica;
import es.javier.logica.Servicios;
import es.javier.models.EmailCuenta;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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

    @FXML
    public void Login(ActionEvent actionEvent) {
        String usuario = getIduser();
        String contra = getIdcontra();
        EmailCuenta email=new EmailCuenta(usuario, contra);
        Logica.getInstance().addCuenta(email);
        Label label = new Label("");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        Servicios testService = new Servicios(email);
        testService.start();
        testService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                //Recuperamos el valor de retorno
                String saludo = testService.getValue().getDireccion();
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


        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.close();
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
        validationSupport.registerValidator(Idcontra, Validator.createRegexValidator("Solo pueden introducirse letras, números y guión", Pattern.compile("^[a-zA-Z0-9-]*$"), Severity.ERROR));

        validationSupport.invalidProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                btnAceptar.setDisable(newValue);
            }
        });
    }
}

