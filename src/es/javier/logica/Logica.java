package es.javier.logica;

import es.javier.models.Mensaje;
import es.javier.models.eMail;
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


    //metodo del EmailTreeView
    //EmailTreeItem cargarCarpetas (CuentaEmail c) {
    //  Paso 0, crear el EmailtreeItem raiz
    //  Primero, conectar con store, tal y como hice a la hora de recuperar los mensajes
    //  Segundo, obtener las carpetas de primer nivel
    //  Tercero, crear un EmailTreeItem por cada carpeta (con un for, por ejemplo)
    //  Cuarto, añadir cada uno como hijo del EmailTreeItem raiz
    //  ...
    //  Paso final, devolver el EmailtreeItem raiz -el del paso 0-
    // y luego, lo que devuelva este método, será puesto al setRoot del treeview (en plan Logica.getInstance.cargarCarpetas

    //metodo para que las columnas tengan el tamaño de su contenido
    public static void autoResizeColumns( TableView<?> table )
    {
        //Set the right policy
        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach( (column) ->
        {
            //Minimal width = columnheader
            Text t = new Text( column.getText() );
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getItems().size(); i++ )
            {
                //cell must not be empty
                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if ( calcwidth > max )
                    {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            column.setPrefWidth( max + 10.0d );
        } );
    }



}
