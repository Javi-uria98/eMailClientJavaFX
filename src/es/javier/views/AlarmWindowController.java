package es.javier.views;

import com.javier.componente.ComponenteReloj;
import com.javier.componente.Tarea;
import es.javier.logica.Logica;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AlarmWindowController implements Initializable {

    private final int MAXLENGTH=2;

    @FXML
    private TableView tablaAlarmas;

    @FXML
    private TextField tfhoras;

    @FXML
    private TextField tfminutos;

    @FXML
    private TextField tfsegundos;

    @FXML
    private TextField textoAlarma;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfhoras.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, String oldValue, String newValue) {
                if (tfhoras.getText().length() > MAXLENGTH) {
                    String s = tfhoras.getText().substring(0, MAXLENGTH);
                    tfhoras.setText(s);
                }
            }
        });

        tfminutos.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, String oldValue, String newValue) {
                if (tfminutos.getText().length() > MAXLENGTH) {
                    String s = tfminutos.getText().substring(0, MAXLENGTH);
                    tfminutos.setText(s);
                }
            }
        });

        tfsegundos.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, String oldValue, String newValue) {
                if (tfsegundos.getText().length() > MAXLENGTH) {
                    String s = tfsegundos.getText().substring(0, MAXLENGTH);
                    tfsegundos.setText(s);
                }
            }
        });

        tablaAlarmas.setItems(Logica.getInstance().getListaTareas());
    }

    @FXML
    public void fijarTarea(ActionEvent actionEvent) {
        int horas = getHoras();
        int minutos = getMinutos();
        int segundos = getSegundos();
        String texto = getTexto();
        Tarea tarea = new Tarea(horas, minutos, segundos, texto);
        Logica.getInstance().addTarea(tarea);
    }

    @FXML
    public void salir(ActionEvent actionEvent){
        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.close();
    }

    @FXML
    public void borrarTarea(ActionEvent actionEvent){
        Tarea tarea = (Tarea) tablaAlarmas.getSelectionModel().getSelectedItem();
        if (tarea != null) {
            Logica.getInstance().borrarTarea(tarea);
        } else {
            Alert alerterror = new Alert(Alert.AlertType.ERROR);
            alerterror.setTitle("Ventana de error");
            alerterror.setHeaderText("Error al borrar el partido");
            alerterror.setContentText("No ha seleccionado ningún partido para ser borrado");
            alerterror.show();
        }

    }

    private int getHoras() {
        return Integer.parseInt(tfhoras.getText());
    }

    private int getMinutos() {
        return Integer.parseInt(tfminutos.getText());
    }

    private int getSegundos() {
        return Integer.parseInt(tfsegundos.getText());
    }

    private String getTexto() {
        return textoAlarma.getText();
    }


}
