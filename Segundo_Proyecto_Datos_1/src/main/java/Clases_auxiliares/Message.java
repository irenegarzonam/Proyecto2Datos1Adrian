package Clases_auxiliares;

import java.io.Serializable;

public class Message implements Serializable {



    public Message(String nombreMetodo, String usuario, String password) {
        this.nombreMetodo = nombreMetodo;
        this.usuario = usuario;
        this.password = password;
    }

    public Message(String nombreMetodo){
        this.nombreMetodo = nombreMetodo;
    }

    public Message(String nombreMetodo,String usuario){
        this.nombreMetodo = nombreMetodo;
        this.usuario = usuario;

    }

    public Message(String nombreMetodo,String user,String newUser,String newPassword){
        this.nombreMetodo = nombreMetodo;
        this.usuario = user;
        this.newUsuario = newUser;
        this.newPassword = newPassword;
    }

    //Definir atributos
    private String nombreMetodo,usuario,password,newUsuario,newPassword;
    private Nodo nodo;
    private ListaEnlazada listaEnlazada;

    public String getNombreMetodo() {
        return nombreMetodo;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }

    public Nodo getNodo() {
        return nodo;
    }

    public ListaEnlazada getListaEnlazada() {
        return listaEnlazada;
    }

    public void setListaEnlazada(ListaEnlazada listaEnlazada) {
        this.listaEnlazada = listaEnlazada;
    }

    public String getNewUsuario() {
        return newUsuario;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
