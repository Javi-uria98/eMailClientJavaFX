package es.javier.logica;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Servicios extends Service<String> {

    private String name;

    public Servicios(String name){
        this.name=name;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<String>() {
            @Override
            protected String call() throws Exception {
                return null;
            }
        };
    }
}
