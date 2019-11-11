package es.javier.models;

import javax.mail.*;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Mensaje {

    private Message message;

    public Mensaje(Message message) {
        this.message = message;
    }

    public String getRemitente() throws MessagingException {
        return message.getFrom()[0].toString();
    }

    public String getAsunto() throws MessagingException {
        return message.getSubject();
    }

    public String getFecha() throws MessagingException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        return sdf.format(message.getReceivedDate());
    }

    public String getMessageContent() throws MessagingException {
        try {
            Object content = message.getContent();
            if (content instanceof Multipart) {
                StringBuffer messageContent = new StringBuffer();
                Multipart multipart = (Multipart) content;
                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    if (part.isMimeType("text/plain")) {
                        messageContent.append(part.getContent().toString());
                    }
                }
                return messageContent.toString();
            }
            return content.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
