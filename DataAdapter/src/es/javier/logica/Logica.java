package es.javier.logica;

import es.javier.extra.EmailCuenta;
import es.javier.extra.Mensaje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Properties;

public class Logica {
    private static Logica INSTANCE = null;

    private ObservableList<EmailCuenta> listaEmail;
    private ArrayList<Mensaje> listaMensajes;

    private Store store;

    private Logica() {
        listaEmail = FXCollections.observableArrayList();
        listaMensajes = new ArrayList<Mensaje>();
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ArrayList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public ObservableList<EmailCuenta> getListaEmail() {
        return listaEmail;
    }

    public void addCuenta(EmailCuenta email) {
        listaEmail.add(email);
    }

    public void cargarCuentaGmail(EmailCuenta email, String s) throws MessagingException {
        String imap = "imaps";
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", imap);
        Session session = Session.getInstance(properties);
        store = session.getStore(imap);
        store.connect("smtp.gmail.com", email.getDireccion(), email.getContrasena());
        Folder folder = store.getFolder(s);
        folder.open(Folder.READ_ONLY);
        Message[] message = folder.getMessages();

        for (int i = 0; i < message.length; i++) {
            Mensaje m = new Mensaje(message[i]);
            listaMensajes.add(m);
        }
    }


    public Folder getFolder() throws MessagingException {
        return store.getDefaultFolder();
    }
}
