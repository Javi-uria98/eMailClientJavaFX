package es.javier.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowController {

    @FXML
    private static TextField Iduser;

    @FXML
    private static TextField Idcontra;

    @FXML
    public void Login(ActionEvent actionEvent) throws IOException {
            String titulo=getIduser().substring(0,12);
            Parent root = FXMLLoader.load(getClass().getResource("mainwindow.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Cuenta de gmail de "+titulo);
            stage.setScene(new Scene(root, 500, 500));
            stage.show();
    }

    //por ahora los dejo así aunque no tenga mucho sentido (pues ponga lo que ponga en los textfields me va a cargar esa cuenta). Mi intencion es que los metodos
    //hiciesen un return del getText() de los textfields (osea, que si yo en la pantalla login introduzco de usuario "javi_prueba77@gmail.com" y de que contraseña
    //la que tuviese, me cargase esa cuenta en concreto. No obstante, si hago que los métodos devuelvan algo tipo Iduser.getText(), me salta la NullPointerException

    public static String getIduser (){
        String usuario="pruebajavi98@gmail.com";
        return usuario;
    }

    public static String getIdcontra() {
        String contra="Holahola1";
        return contra;
    }
}

