package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.eMail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController  implements Initializable {

    @FXML
    private TableView tableViewEmail;

    @FXML
    private TreeView treeViewEmail;

    @FXML
    private Label Idlabel;

    @FXML
    void altaEmail(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableViewEmail.setItems(Logica.getInstance().getListaEmail());


    }
}
