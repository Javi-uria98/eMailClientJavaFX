package es.javier.models;

import java.util.Properties;

public class eMail {

    private String address;
    private String password;
    private Properties properties;

    public eMail(String address, String password){
        setAddress(address);
        setPassword(password);
        properties=new Properties();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
