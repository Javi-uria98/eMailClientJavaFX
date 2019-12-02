package es.javier.logica;

import es.javier.models.Mensaje;
import es.javier.models.eMail;
import es.javier.models.eMailTreeItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.text.Text;

import javax.mail.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Logica {

    private static Logica INSTANCE = null;

    private ArrayList<eMail> listaEmail;
    private ObservableList<Mensaje> listaMensajes;
    private List<TreeItem<String>> treeItemsContainer;

    private Mensaje m;
    private Properties properties;
    private Session session;
    private Store store;
    private Folder folder;

    private Logica() {
        listaEmail = new ArrayList<>();
        listaMensajes = FXCollections.observableArrayList();
        treeItemsContainer = new ArrayList<>();
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public ArrayList<eMail> getListaEmail() {
        return listaEmail;
    }

    public List<TreeItem<String>> getTreeItemsContainer() {
        return treeItemsContainer;
    }

    /**
     * @param email cuenta que introduzco (usuario y contraseña) para que me la cargue el programa
     * @param s     nombre de la carpeta que quiero visualizar
     * @throws MessagingException
     */
    public void cargarCuentaGmail(eMail email, String s) throws MessagingException {
        String imap = "imaps";
        properties = new Properties();
        properties.setProperty("mail.store.protocol", imap);
        session = Session.getInstance(properties);
        store = session.getStore(imap);

        store.connect("smtp.gmail.com", email.getDireccion(), email.getContrasena());
        folder = store.getFolder(s);
        folder.open(Folder.READ_ONLY);
        Message[] message = folder.getMessages();

        for (int i = 0; i < message.length; i++) {
            m = new Mensaje(message[i]);
            listaMensajes.add(m);
        }
    }

    public Folder getFolder() throws MessagingException {
        return store.getDefaultFolder();
    }

    //otro metodo getFolders (eMail email)
    //crea un nuevo treeItem con email.getDireccion, el parametro email y como carpteta null
    //crea un array de carpetas con email.getStore.getDefaultFolder().list();
    //llama a llenarTreeView
    //retorna el treeItem

    /**
     * @param folders las carpetas que tiene la cuenta de email y que cargaré en el treeview
     * @param e1      cualquier treeitem que tenga hijos (la cuenta, INBOX y [Gmail]
     * @param email   cuenta de email que indico, junto con el nombre de la carpeta y el propio objeto carpeta para crear el treeitem hijo
     * @throws MessagingException
     */
    public void llenarTreeView(Folder[] folders, eMailTreeItem e1, eMail email) throws MessagingException {
        for (Folder f : folders) {
            eMailTreeItem e2 = new eMailTreeItem(f.getName(), email, f);
            e1.getChildren().add(e2);
            if (f.getType() == Folder.HOLDS_FOLDERS)
                llenarTreeView(f.list(), e2, email);
        }
    }

    public void borrarMensaje(Message message) throws MessagingException {
        Folder folder=Logica.getInstance().getFolder();
        if (folder.getName() != "[Gmail]/Papelera") {
            Logica.getInstance().copiarMensaje(message.getFolder().getMessages());
        } else {
            message.setFlag(Flags.Flag.DELETED, true);
            folder.close(true);
        }
    }

    public void copiarMensaje(Message[] messages) throws MessagingException {
        messages=folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            m = new Mensaje(messages[i]);
            listaMensajes.add(m);
        }
        Folder papelera=store.getFolder("[Gmail]/Papelera");
        papelera.open(Folder.READ_ONLY);
        folder.copyMessages(messages, papelera);
    }

    /**
     * @param table tabla que le paso para que me haga el autoResize (metodo para que las columnas tengan el tamaño de su contenido)
     */
    public static void autoResizeColumns(TableView<?> table) {
        //Set the right policy
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach((column) ->
        {
            //Minimal width = columnheader
            Text t = new Text(column.getText());
            double max = t.getLayoutBounds().getWidth();
            for (int i = 0; i < table.getItems().size(); i++) {
                //cell must not be empty
                if (column.getCellData(i) != null) {
                    t = new Text(column.getCellData(i).toString());
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if (calcwidth > max) {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth(max + 10.0d);
        });
    }

    //TODO implementar hilos en alguna parte de la aplicación (por ejemplo a la hora de cargar el contenido de un mensaje en el webvview)
    //TODO implementar un test (pensar de qué clase o métdo hacerlo)
    //TODO implementar algo de validación (por ejemplo en la pantalla de login, que no deje darle el botón si no escribiste nada en los textfields)
    //TODO alguna Alert



}
