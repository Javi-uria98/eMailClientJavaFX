package es.javier.logica;

import es.javier.models.Mensaje;
import es.javier.models.eMail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Logica {

    private static Logica INSTANCE = null;

    private ArrayList<eMail>listaEmail;
    private ObservableList<Mensaje> listaMensajes;
    private List<TreeItem<String>> treeItemsContainer;

    private Mensaje m;
    private Properties properties;
    private Session session;
    private Store store;
    private Folder carpetaInbox;

    private Logica() {
        listaEmail=new ArrayList<>();
        listaMensajes = FXCollections.observableArrayList();
        treeItemsContainer = new ArrayList<>();
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ArrayList<eMail> getListaEmail() {
        return listaEmail;
    } // creo ya el método porque asumo que tendré que usarlo en el TreeView ¿?

    public ObservableList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public List<TreeItem<String>> getTreeItemsContainer() {
        return treeItemsContainer;
    }

    public void cargarCuentaGmail(eMail email) throws MessagingException {
        String imap="imaps";
        properties=new Properties();
        properties.setProperty("mail.store.protocol",imap);
        session=Session.getInstance(properties);
        store=session.getStore(imap);

        store.connect("smtp.gmail.com", email.getDireccion(), email.getContrasena());
        carpetaInbox=store.getFolder("INBOX");
        carpetaInbox.open(Folder.READ_ONLY);
        Message[] message=carpetaInbox.getMessages();

        for (int i=0;i<message.length;i++){
            m=new Mensaje(message[i]);
            listaMensajes.add(m);
        }


    }



}
