package es.javier.views;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.SSLSession;
import java.util.Properties;

public class JavaMail {

    public static void sendMail(String recepient) {
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
    }

    public static Message prepareMessage(Session session, String cuentaEmail, String recepient) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(cuentaEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Prueba de email");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
