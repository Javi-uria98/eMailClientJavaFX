package es.javier.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

public class EmailCuenta {

    private String direccion;
    private String contrasena;

    public EmailCuenta(String direccion, String contrasena){
        this.direccion=direccion;
        this.contrasena=contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
