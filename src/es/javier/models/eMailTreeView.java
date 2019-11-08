package es.javier.models;

import javafx.scene.control.TreeItem;

public class eMailTreeView {

    private final TreeItem<String> rootItem = new TreeItem();

    public eMailTreeView (eMail  email){
        rootItem.setValue("e-mail");

        rootItem.getChildren().add(new TreeItem("Correo: "+email.getAddress()));
        rootItem.getChildren().add(new TreeItem("Contraseña: "+email.getPassword()));
    }

    public TreeItem<String>getRootItem(){
        return rootItem;
    }
}
