package es.javier.models;

import javafx.scene.control.TreeItem;

import javax.mail.Folder;
import javax.mail.MessagingException;

public class eMailTreeView extends TreeItem<String> {

    private Folder folder;
    private eMail email;
    private String name;


    public eMailTreeView (String name, eMail email, Folder folder) throws MessagingException {
        super(name);
        this.name=name;
        this.email=email;
        this.folder=folder;
    }

    public eMail getEmail(){
        return email;
    }
}
