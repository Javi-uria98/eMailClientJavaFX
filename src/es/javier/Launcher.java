package es.javier;

import es.javier.views.eMailUtil;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        String destinarario="javi.uria98@gmail.com";
        HBox root = new HBox(5);
        TextField textField = new TextField("correo@ejemplo.com");
        Button button = new Button("Enviar");
        EventHandler<ActionEvent> goHandler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                eMailUtil.sendMail(destinarario);
            }

        };

        textField.setOnAction(goHandler);
        button.setOnAction(goHandler);

        root.getChildren().addAll(textField, button);
        Scene scene = new Scene(root, 250, 150);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[])
    {
        launch(args);
    }

}