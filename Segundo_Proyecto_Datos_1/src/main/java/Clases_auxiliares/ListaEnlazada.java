package Clases_auxiliares;

import java.io.Serializable;

public class ListaEnlazada implements Serializable {

    private Nodo head;
    private Nodo last;
    private int size;

    /**
     * Constructor de la clase Lista Enlazada
     */
    public ListaEnlazada(){
        this.head = null;
        this.last = null;
        this.size = 0;
    }

    public void insertarNuevoNodo(Nodo nuevo_nodo){

        //Si la cabeza de la lista esta vacía, se asigna un nuevo valor
        if (this.head == null){

            this.head = nuevo_nodo;
            this.last = nuevo_nodo;
            this.size++;

        }
        else{

            //El nuevo objeto ingresado pasa a ser apuntando por el último
            this.last.setNext(nuevo_nodo);

            //Se asigna un nuevo valor final
            this.last = nuevo_nodo;

            //Se incrementa el tamaño de la lista
            this.size++;

        }

    }

    public boolean devolverElemento(String user){

        Nodo current = this.head;

        while (current != null){

            if (current.getValor().getUsername().equals(user)){
                return true;
            }

            current = current.getNext();

        }

        return false;

    }

    public void eliminarNodo(String user){

        Nodo current = this.head;
        Nodo refAnterior = this.head;

        //Si el Nodo a eliminar es el head
        if (current.getValor().getUsername().equals(user)){
            this.head = current.getNext();
        }

        while (current != null){

            if (current.getValor().getUsername().equals(user)){

                refAnterior.setNext(current.getNext());
                current.setNext(null);  //Se elimina el apuntador
                return;
            }

            refAnterior = current;
            current = current.getNext();



        }

        System.out.println("No se encontro a la persona");


    }

    public void editarElemento(String user,String newUser,String newPassword){

        Nodo current = this.head;

        while (current != null){

            if (current.getValor().getUsername().equals(user)){
                current.getValor().setUsername(newUser);
                current.getValor().setPassword(newPassword);
                System.out.println("Se encontro el Nodo en la lista enlazada");
                return;
            }

            current = current.getNext();


        }

        System.out.println("No se encontró a la persona");

    }

    public void verElementos(){

        Nodo current = this.head;

        while (current != null){
            System.out.println(current.getValor().getUsername());

            current = current.getNext();

        }
    }


    public int getSize() {
        return size;
    }

    public Nodo getHead() {
        return head;
    }
}
