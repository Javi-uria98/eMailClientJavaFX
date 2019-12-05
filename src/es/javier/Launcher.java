package es.javier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/mainwindow.fxml"));
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root, 1250, 600));
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

}