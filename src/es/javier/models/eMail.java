package es.javier.models;

import java.util.Date;
import java.util.Properties;

public class eMail {

    private String remitente;
    private String asunto;
    private Date fecha;

    public eMail(String remitente, String asunto, Date fecha){
        this.remitente=remitente;
        this.asunto=asunto;
        this.fecha=fecha;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
