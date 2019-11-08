package es.javier.models;

import javafx.scene.control.TreeItem;

public class eMailTreeView {

    private final TreeItem<String> rootItem = new TreeItem();

    public eMailTreeView (eMail  email){
        rootItem.setValue("e-mail");

        rootItem.getChildren().add(new TreeItem("Remitente: "+email.getRemitente()));
        rootItem.getChildren().add(new TreeItem("Asunto: "+email.getAsunto()));
        rootItem.getChildren().add(new TreeItem("Fecha: "+email.getFecha()));
    }

    public TreeItem<String>getRootItem(){
        return rootItem;
    }
}
