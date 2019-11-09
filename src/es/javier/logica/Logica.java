package es.javier.logica;

import es.javier.models.Mensaje;
import es.javier.models.eMail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Logica {

    private static Logica INSTANCE = null;

    private ArrayList<eMail>listaEmail;
    private ObservableList<Mensaje>listaMensaje;
    private List<TreeItem<String>> treeItemsContainer;

    private Mensaje m;
    private Properties properties;
    private Session session;
    private Store store;
    private Folder carpetaInbox;

    private Logica() {
        listaEmail=new ArrayList<>();
        listaMensaje = FXCollections.observableArrayList();
        treeItemsContainer = new ArrayList<>();
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ArrayList<eMail> getListaEmail() {
        return listaEmail;
    }

    public ObservableList<Mensaje> getListaMensaje() {
        return listaMensaje;
    }

    public List<TreeItem<String>> getTreeItemsContainer() {
        return treeItemsContainer;
    }

    public void cargarCuentaGmail(eMail email) throws MessagingException {
        String imap="imaps";
        properties=new Properties();
        properties.setProperty("mail.store.protocol",imap);
        session=Session.getDefaultInstance(properties);
        store=session.getStore(imap);

        store.connect("smtp.gmail.com", email.getDireccion(), email.getContrasena());
        carpetaInbox=store.getFolder("INBOX FOLDER");
        carpetaInbox.open(Folder.READ_ONLY);
        Message[] message=carpetaInbox.getMessages();

        for (int i=0;i<message.length;i++){
            m=new Mensaje(message[i]);
            listaMensaje.add(m);
        }


    }



}
