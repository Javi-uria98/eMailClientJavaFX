package es.javier.logica;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.Message;

public class Logica {

    private static Logica INSTANCE = null;
    private ObservableList<Message> listaEmail;


    private Logica(){
        listaEmail= FXCollections.observableArrayList();

    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }

    public ObservableList<Message> getListaEmail() {
        return listaEmail;
    }


}
