package es.javier.models;

import javafx.scene.control.TreeItem;

import javax.mail.MessagingException;

public class eMailTreeView {

    private final TreeItem<String> rootItem = new TreeItem();

    public eMailTreeView (eMail email) throws MessagingException {
        rootItem.setValue("e-mail");

        rootItem.getChildren().add(new TreeItem("Cuenta: "+email.getDireccion()));
        rootItem.getChildren().add(new TreeItem("Contraseña: "+email.getContrasena()));
    }

    public TreeItem<String>getRootItem(){
        return rootItem;
    }
}
