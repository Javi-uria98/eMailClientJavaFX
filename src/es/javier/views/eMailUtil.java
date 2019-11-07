package es.javier.views;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Scanner;

public class eMailUtil {

    private static Scanner teclado=new Scanner(System.in);

    public static void sendMail(String recepient) {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        //String cuentaEmail = "pruebajavi98@gmail.com";
        System.out.println("Introduzca su cuenta de email");
        String cuentaEmail = teclado.nextLine();
        //String contraseña = "Holahola1";
        System.out.println("Introduzca su contraseña");
        String contraseña = teclado.nextLine();

        System.out.println("Preparando el envío del correo...");

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
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
