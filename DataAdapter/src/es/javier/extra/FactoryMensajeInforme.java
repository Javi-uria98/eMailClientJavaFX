package es.javier.extra;

import java.util.ArrayList;
import java.util.List;

public class FactoryMensajeInforme {

    public static List<MensajeInforme> createListMensajeInforme(){
        ArrayList<MensajeInforme> lista=new ArrayList<>();
        lista.add(new MensajeInforme("10/12/19","Prueba de factory","correoremitente@correo.com","correodestinatario@correo.com","Holaaaa"));
        return lista;
    }
}
