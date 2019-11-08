package es.javier.logica;

import es.javier.models.eMail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

public class Logica {

    private static Logica INSTANCE = null;
    private ObservableList<Message> listaEmail;
    private List<TreeItem<String>> treeItemsContainer;

    private Logica() {
        listaEmail = FXCollections.observableArrayList();
        treeItemsContainer = new ArrayList<>();

    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Message> getListaEmail() {
        return listaEmail;
    }

    public List<TreeItem<String>> getTreeItemsContainer() {
        return treeItemsContainer;
    }
}
