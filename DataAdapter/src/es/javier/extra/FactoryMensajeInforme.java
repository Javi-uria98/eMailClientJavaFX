package es.javier.extra;

import java.util.ArrayList;
import java.util.List;

public class FactoryMensajeInforme {

    public static List<MensajeInforme> createListMensajeInforme(){
        ArrayList<MensajeInforme> lista=new ArrayList<>();
        lista.add(new MensajeInforme("10/12/19","Prueba de factory","correoremitente@correo.com","correodestinatario@correo.com","Holaaaa"));
        lista.add(new MensajeInforme("INBOX", "12/06/17","Prueba de factory con carpeta","correoremitente2@gmail.com","correodestinatario2@correo.com","Adiooos"));
        return lista;
    }
}
