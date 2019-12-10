package es.javier.logica;

import es.javier.models.EmailCuenta;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Servicios extends Service<String> {

    private String nombreMensaje;

    public Servicios(String nombreMensaje){
        this.nombreMensaje=nombreMensaje;
    }

    @Override
    protected Task<String> createTask() {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                return nombreMensaje;
            }
        };
    }
}
