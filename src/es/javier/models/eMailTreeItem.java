package es.javier.models;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class eMailTreeItem extends TreeItem<String> {

    private Folder folder;
    private eMail email;
    private String name;

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public eMailTreeItem(String name, eMail email, Folder folder){
        super(name);
        this.name=name;
        this.email=email;
        this.folder=folder;
    }

    public eMail getEmail(){
        return email;
    }
}
