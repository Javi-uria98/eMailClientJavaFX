package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.EmailCuenta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class AnswerWindowController {

    @FXML
    private TextArea ta_contenido;

    @FXML
    private TextField tf_asunto;

    @FXML
    private Button btn_enviar;

    @FXML
    private Label RE;

    @FXML
    public void sendMail(ActionEvent actionEvent) {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        EmailCuenta emailCuenta = Logica.getInstance().getListaEmail().get(0);


        //System.out.println("Introduzca su cuenta de email");
        String cuentaEmail = emailCuenta.getDireccion();

        //System.out.println("Introduzca su contraseña");
        String contrasena = emailCuenta.getContrasena();

        System.out.println("Preparando el envío del correo...");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(cuentaEmail, contrasena);
            }
        });

        Message message = prepareMessage(session, cuentaEmail);


        try {
            Transport.send(message);
            System.out.println("El correo se ha enviado correctamente");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.close();
    }

    private Message prepareMessage(Session session, String cuentaEmail) {
        String cuentaAresponder=MainWindowController.columnaremitente.getCellObservableValue(MainWindowController.mresponder).getValue().toString();
        System.out.println(cuentaAresponder);
        RE.setText(cuentaAresponder);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(cuentaEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(cuentaAresponder));
            message.setSubject(tf_asunto.getText());
            message.setText(ta_contenido.getText());
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
