package es.javier.models;

import javax.mail.*;
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

}
