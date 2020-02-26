package es.javier.logica;

import com.javier.componente.Tarea;
import es.javier.models.EmailCuenta;
import es.javier.models.Mensaje;
import es.javier.models.EmailTreeItem;
import es.javier.models.MensajeInforme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

import javax.mail.*;
import java.util.ArrayList;
import java.util.Properties;

public class Logica {

    private static Logica INSTANCE = null;

    private ObservableList<EmailCuenta> listaEmail;
    private ObservableList<Mensaje> listaMensajes;
    private ArrayList<Tarea> listaTareas;
    private ArrayList<MensajeInforme> listaMensajesInformesv2;

    private ArrayList<Mensaje> listaMensajesInformes;

    private Store store;

    private Logica() {
        listaEmail = FXCollections.observableArrayList();
        listaMensajes = FXCollections.observableArrayList();
        listaTareas = new ArrayList<Tarea>();
        listaMensajesInformes = new ArrayList<Mensaje>();
        listaMensajesInformesv2 = new ArrayList<MensajeInforme>();
    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Mensaje> getListaMensajes() {
        return listaMensajes;
    }

    public ObservableList<EmailCuenta> getListaEmail() {
        return listaEmail;
    }

    public ArrayList<Tarea> getListaTareas() {
        return listaTareas;
    }

    public void addCuenta(EmailCuenta email) {
        listaEmail.add(email);
    }

    public void addTarea(Tarea tarea) {
        listaTareas.add(tarea);
    }

    public ArrayList<Mensaje> getListaMensajesInformes() {
        return listaMensajesInformes;
    }

    public ArrayList<MensajeInforme> getListaMensajesInformesv2() {
        return listaMensajesInformesv2;
    }

    public void addMensajeInformev2(MensajeInforme mensajeInforme) {
        listaMensajesInformesv2.add(mensajeInforme);
    }


    /**
     * @param email cuenta que introduzco (usuario y contraseña) para que me la cargue el programa
     * @param s     nombre de la carpeta que quiero visualizar
     * @throws MessagingException captura la excepción
     */
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

    /**
     * Mismo método que el de arriba, pero para cargar los mensajes del informe (v1)
     *
     * @param email
     * @param s
     * @throws MessagingException
     */
    public void cargarCuentaGmailInformes(EmailCuenta email, String s, boolean parInforme) throws MessagingException {
        String imap = "imaps";
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", imap);
        Session session = Session.getInstance(properties);
        store = session.getStore(imap);
        store.connect("smtp.gmail.com", email.getDireccion(), email.getContrasena());
        Folder folder = store.getFolder(s);
        folder.open(Folder.READ_ONLY);
        Message[] message = folder.getMessages();

        if (parInforme) {
            for (int i = 0; i < message.length; i++) {
                Mensaje m = new Mensaje(message[i]);
                listaMensajesInformes.add(m);
            }
        } else {
            for (int i = 0; i < 1; i++) {
                Mensaje m = new Mensaje(message[i]);
                listaMensajesInformes.add(m);
            }
        }
    }

    public void cargarCuentaGmailInformesv2(EmailCuenta email, String s) throws MessagingException {
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
            MensajeInforme mi = new MensajeInforme(m.getFecha(), m.getAsunto(), m.getRemitente(), m.getDestinatario()[0], m.getMessageContent());
            listaMensajesInformesv2.add(mi);
        }
    }

    public Folder getFolder() throws MessagingException {
        return store.getDefaultFolder();
    }

    /**
     * @param folders las carpetas que tiene la cuenta de email y que cargaré en el treeview
     * @param e1      cualquier treeitem que tenga hijos (la cuenta, INBOX y [Gmail]
     * @throws MessagingException captura la excepción
     */
    public void llenarTreeView(Folder[] folders, EmailTreeItem e1) throws MessagingException {
        for (Folder f : folders) {
            EmailTreeItem e2 = new EmailTreeItem(f.getName(), e1.getEmail(), f);
            e1.getChildren().add(e2);
            if (f.getType() == Folder.HOLDS_FOLDERS) {
                llenarTreeView(f.list(), e2);
            }
        }
    }

    /*public void borrarMensaje(Message message) throws MessagingException {
        Folder folder = Logica.getInstance().getFolder();
        if (folder.getName() != "[Gmail]/Papelera") {
            Logica.getInstance().copiarMensaje(message.getFolder().getMessages());
        } else {
            message.setFlag(Flags.Flag.DELETED, true);
            folder.close(true);
        }
    }

    public void copiarMensaje(Message[] messages) throws MessagingException {
        messages = folder.getMessages();
        for (int i = 0; i < messages.length; i++) {
            m = new Mensaje(messages[i]);
            listaMensajes.add(m);
        }
        Folder papelera = store.getFolder("[Gmail]/Papelera");
        papelera.open(Folder.READ_ONLY);
        folder.copyMessages(messages, papelera);
    }*/


    /**
     * @param table tabla que le paso para que me haga el autoResize (metodo para que las columnas tengan el tamaño de su contenido)
     */
    public static void autoResizeColumns(TableView<?> table) {
        //Set the right policy
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().forEach((column) ->
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

}
