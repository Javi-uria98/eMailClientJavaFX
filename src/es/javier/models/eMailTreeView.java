package es.javier.models;

import javafx.scene.control.TreeItem;

import javax.mail.MessagingException;

public class eMailTreeView {

    private final TreeItem<String> rootItem = new TreeItem();

    public eMailTreeView (Mensaje  mensaje) throws MessagingException {
        rootItem.setValue("e-mail");

        rootItem.getChildren().add(new TreeItem("Remitente: "+mensaje.getRemitente()));
        rootItem.getChildren().add(new TreeItem("Asunto: "+mensaje.getAsunto()));
        rootItem.getChildren().add(new TreeItem("Fecha: "+mensaje.getFecha()));
    }

    public TreeItem<String>getRootItem(){
        return rootItem;
    }
}
