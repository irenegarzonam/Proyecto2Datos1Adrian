package Clases_auxiliares;

public class Platillo {

    private String nombrePlatillo;
    private int calorias,precio,minutos,segundos;

    public Platillo(String nombrePlatillo, int calorias, int precio) {
        this.nombrePlatillo = nombrePlatillo;
        this.calorias = calorias;
        this.precio = precio;
    }

    @Override
    public String toString(){
        return nombrePlatillo;
    }


    public String getNombrePlatillo() {
        return nombrePlatillo;
    }

    public void setNombrePlatillo(String nombrePlatillo) {
        this.nombrePlatillo = nombrePlatillo;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
