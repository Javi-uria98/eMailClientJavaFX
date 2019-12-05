package es.javier.logica;

import es.javier.models.EmailCuenta;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class Servicios extends Service<EmailCuenta> {

    private EmailCuenta email;

    public Servicios(EmailCuenta email){
        this.email=email;
    }

    @Override
    protected Task<EmailCuenta> createTask() {
        return new Task<>() {
            @Override
            protected EmailCuenta call() throws Exception {
                return email;
            }
        };
    }
}
