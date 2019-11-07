package es.javier.views;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSession;
import java.util.Properties;

public class eMailUtil {

    public static void sendMail(String recepient) {
        System.out.println("Preparando el envío del correo");

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String cuentaEmail = "xxxxx@gmail.com";
        String contraseña = "xxxx";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(cuentaEmail, contraseña);
            }
        });

        Message message = prepareMessage(session, cuentaEmail, recepient);


        try {
            Transport.send(message);
            System.out.println("El correo se ha enviado correctamente");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static Message prepareMessage(Session session, String cuentaEmail, String recepient) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(cuentaEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Prueba de email");
            message.setText("Holaaaaa \n ¿Se ha enviado bien? ");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
