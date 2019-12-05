package es.javier.models;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;

public class EmailTreeItem extends TreeItem<String> {

    private Folder folder;
    private EmailCuenta email;
    private String name;

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public EmailTreeItem(String name, EmailCuenta email, Folder folder){
        super(name);
        this.name=name;
        this.email=email;
        this.folder=folder;
    }

    public EmailTreeItem(String name){
        super(name);
        folder=null;
        email=null;
    }

    public EmailCuenta getEmail(){
        return email;
    }
}
