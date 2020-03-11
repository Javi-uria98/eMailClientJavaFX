package es.javier;

import com.javier.componente.ComponenteReloj;
import es.javier.views.MainWindowController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("views/mainwindow.fxml"));
        Parent root = fxmlLoader.load();
        MainWindowController mwc = fxmlLoader.getController();
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root, 1250, 600));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                ComponenteReloj cp=mwc.getCp();
                cp.getTimer().cancel();
                cp.getTimer().purge();
            }
        });
    }

    public static void main(String args[]) {
        launch(args);
    }

}