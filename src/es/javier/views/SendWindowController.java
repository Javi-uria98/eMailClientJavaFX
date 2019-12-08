package es.javier.views;

import es.javier.logica.Logica;
import es.javier.models.EmailCuenta;
import es.javier.models.Mensaje;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class SendWindowController implements Initializable {

    @FXML
    private ComboBox<EmailCuenta> cb_remitente;

    @FXML
    private TextArea ta_contenido;

    @FXML
    private TextField tf_para;

    @FXML
    private TextField tf_asunto;

    @FXML
    private Button btn_enviar;

    @FXML
    public void sendMail(ActionEvent actionEvent) {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        //System.out.println("Introduzca su cuenta de email");
        EmailCuenta emailCuenta = Logica.getInstance().getListaEmail().get(0);
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

        Message message = prepareMessage(session);


        try {
            Transport.send(message);
            System.out.println("El correo se ha enviado correctamente");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        Stage stage = ((Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
        stage.close();
    }

    private Message prepareMessage(Session session) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(cb_remitente.getSelectionModel().getSelectedItem().getDireccion()));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(tf_para.getText()));
            message.setSubject(tf_asunto.getText());
            message.setText(ta_contenido.getText());
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    void responder(Mensaje mensaje, EmailCuenta cuenta) throws MessagingException {
        cb_remitente.getSelectionModel().select(cuenta);
        tf_para.setText(mensaje.getDestinatario()[0]);
        tf_asunto.setText("RE: " + mensaje.getAsunto());
    }

    void reenviar(Mensaje mensaje, EmailCuenta cuenta) throws MessagingException {
        cb_remitente.getSelectionModel().select(cuenta);
        String mensajereenviado = "Mensaje procedente de: " + mensaje.getRemitente() + "\nPara: " + mensaje.getDestinatario() + "\n Con fecha: " + mensaje.getFecha()
                + "\n Y asunto: " + mensaje.getAsunto() + "\n Dice: " + mensaje.getMessageContent();
        ta_contenido.setText(mensajereenviado);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < Logica.getInstance().getListaEmail().size(); i++) {
            cb_remitente.getItems().add(Logica.getInstance().getListaEmail().get(i));
        }
    }
}
