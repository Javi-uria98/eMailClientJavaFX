package es.javier.extra;

public class MensajeInforme {

    private String fecha;
    private String asunto;
    private String remitente;
    private String destinatario;
    private String contenido;

    public MensajeInforme(){

    }

    public MensajeInforme(String fecha, String asunto, String remitente, String destinatario, String contenido){
        this.fecha=fecha;
        this.asunto=asunto;
        this.remitente=remitente;
        this.destinatario=destinatario;
        this.contenido=contenido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
