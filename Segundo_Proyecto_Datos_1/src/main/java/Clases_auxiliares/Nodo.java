package Clases_auxiliares;

import java.io.Serializable;

public class Nodo implements Serializable {

    private Usuario valor;
    private Nodo derecha;
    private Nodo izquierda;


    private Nodo next;
    private Usuario usuario;
    private Platillo platillo;

    /**
     * Constructor del objeto que almacenará usuarios
     */
    public Nodo() {
        this.derecha = null;  //Empieza sin apuntarle a alguien
        this.izquierda = null;  //Empieza sin apuntarle a nadie
        this.next = null;  //Empieza sin apuntar a alguien

    }

    /**
     * Devuelve el Nodo hijo de la derecha
     *
     * @return hijo derecho
     */
    public Nodo getDerecha() {
        return derecha;
    }

    /**
     * Le asigna un valor al Nodo derecho
     *
     * @param derecha Nuevo Nodo derecho
     */
    public void setDerecha(Nodo derecha) {
        this.derecha = derecha;
    }

    /**
     * Devuelve el Nodo hijo de la izquierda
     *
     * @return hijo izquierdo
     */
    public Nodo getIzquierda() {
        return izquierda;
    }

    /**
     * Le asigna un valor al Nodo izquierdo
     *
     * @param izquierda Nuevo nodo izquierdo
     */
    public void setIzquierda(Nodo izquierda) {
        this.izquierda = izquierda;
    }

    /**
     * Devuelve el valor de tipo Usuario almacenado en el Nodo
     *
     * @return Objeto tipo Usuario
     */
    public Usuario getValor() {
        return valor;
    }

    /**
     * Le asigna al Nodo un objeto de tipo Usuario
     *
     * @param valor Nuevo objeto Usuario
     */
    public void setValor(Usuario valor) {
        this.valor = valor;
    }

    /**
     * Devuelve el Nodo al que está apuntando el Nodo actual
     *
     * @return Nodo siguiente
     */
    public Nodo getNext() {
        return next;
    }

    /**
     * Le asigna al Nodo actual un Nodo al cual apuntar
     *
     * @param next Nuevo Nodo que será apuntado
     */
    public void setNext(Nodo next) {
        this.next = next;
    }

    /**
     * Devuelve el Platillo almacenado en el Nodo
     *
     * @return Objeto tipo Platillo
     */

    public Platillo getPlatillo() {
        return platillo;
    }



    /**
     * Asigna al Nodo un objeto de tipo Platillo
     * @param platillo Nuevo platillo
    */

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }


}
