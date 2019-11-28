package es.javier.views;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Scanner;

public class SendWindowController {

    private static Scanner teclado = new Scanner(System.in);

    @FXML
    private static TextArea ta_contenido;

    @FXML
    private static TextField tf_para;

    @FXML
    private static TextField tf_asunto;

    @FXML
    private static Button btn_enviar;

    public static void sendMail(String recepient, String subject, String content) {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        System.out.println("Introduzca su cuenta de email");
        final String cuentaEmail = teclado.nextLine();

        System.out.println("Introduzca su contraseña");
        final String contrasena = teclado.nextLine();

        System.out.println("Preparando el envío del correo...");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(cuentaEmail, contrasena);
            }
        });

        Message message = prepareMessage(session, cuentaEmail, recepient, subject, content);


        try {
            Transport.send(message);
            System.out.println("El correo se ha enviado correctamente");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        EventHandler<ActionEvent> goHandler = new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                SendWindowController.sendMail(tf_para.getText(), tf_asunto.getText(), ta_contenido.getText());
            }

        };

        tf_asunto.setOnAction(goHandler);
        tf_para.setOnAction(goHandler);
        btn_enviar.setOnAction(goHandler);
    }

    private static Message prepareMessage(Session session, String cuentaEmail, String recepient, String subject, String content) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(cuentaEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject(subject);
            message.setText(content);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
