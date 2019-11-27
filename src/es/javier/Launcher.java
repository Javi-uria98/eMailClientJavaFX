package es.javier;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        /*HBox root = new HBox(5);
        TextField textField = new TextField("correo@ejemplo.com");
        Button button = new Button("Enviar");
        EventHandler<ActionEvent> goHandler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                SendWindowController.sendMail(textField.getText());
            }

        };

        textField.setOnAction(goHandler);
        button.setOnAction(goHandler);

        root.getChildren().addAll(button, textField);
        Scene scene = new Scene(root, 250, 150);
        stage.setScene(scene);
        stage.show();*/
        Parent root = FXMLLoader.load(getClass().getResource("views/mainwindow.fxml"));
        stage.setTitle("Pantalla Principal");
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

}