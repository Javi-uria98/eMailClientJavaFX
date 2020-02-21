package es.javier.views;

import com.sun.mail.iap.BadCommandException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportsWindowController implements Initializable {

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
                                try {
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("generatereport.fxml"));
                                    Parent root = fxmlLoader.load();
                                    GenerateReportController controller = fxmlLoader.getController();
                                    Stage stage = new Stage();
                                    stage.initModality(Modality.APPLICATION_MODAL);
                                    stage.setScene(new Scene(root, 600, 400));
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    break;
                case "Informe de todos los correos de una carpeta":
                    generarInforme.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {

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
}
