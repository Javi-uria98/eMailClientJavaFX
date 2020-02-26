package es.javier.extra;

import es.javier.logica.Logica;

import javax.mail.Address;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

public class FactoryMensaje {

    public static List<Mensaje> createListMensajes() {
        try {
            EmailCuenta cuentaPrueba = new EmailCuenta("pruebajavi98v2@gmail.com", "Holahola1");
            Logica.getInstance().cargarCuentaGmail(cuentaPrueba, "INBOX");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Logica.getInstance().getListaMensajes();
    }

}
