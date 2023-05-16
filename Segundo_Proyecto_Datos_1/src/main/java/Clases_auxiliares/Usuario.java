package Clases_auxiliares;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String username;
    private String password;

    public Usuario(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString(){
        return username;
    }

    /*@Override
    public String toString() {
        return "Usuario{" + "username=" + username + ", password=" + password + '}';
    }*/
}
