package Clases_auxiliares;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.Scanner;


public class ColaPedidos {

    public static void main(String[] args) {


        MyList<JsonObject> listaPlatillos = new MyList<>();
        int sumaTiempos = 0;

        // Leer el archivo JSON existente en un objeto JsonArray
        JsonArray jsonArray = null;
        try (Reader reader = new FileReader("C:\\Users\\XPC}\\OneDrive\\Documents\\GitHub\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new Gson();
            jsonArray = gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Imprimir los platillos del archivo JSON
        System.out.println("Platillos disponibles:");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject platillo = jsonArray.get(i).getAsJsonObject();
            String nombre = platillo.get("nombre").getAsString();
            int calorias = platillo.get("calorias").getAsInt();
            int tiempoPreparacion = platillo.get("tiempoPreparacion").getAsInt();
            float precio = platillo.get("precio").getAsFloat();

            System.out.println(nombre + ": " + calorias + " calorías, " + tiempoPreparacion + " minutos de preparación, ₡" + precio);
        }

        // Añadir platillos a la lista y sumar sus tiempos de preparación
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los nombres de los platillos que desea agregar a la lista (escriba 'fin' para terminar):");
        String nombrePlatillo = scanner.nextLine();
        while (!nombrePlatillo.equals("fin")) {
            // Buscar el platillo en el archivo JSON
            JsonObject platillo = null;
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject platilloActual = jsonArray.get(i).getAsJsonObject();
                if (platilloActual.get("nombre").getAsString().equals(nombrePlatillo)) {
                    platillo = platilloActual;
                    break;
                }
            }
            if (platillo != null) {
                listaPlatillos.add(platillo);
                sumaTiempos += platillo.get("tiempoPreparacion").getAsInt();
            } else {
                System.out.println("El platillo ingresado no se encuentra en la lista.");
            }

            nombrePlatillo = scanner.nextLine();
        }

        // Imprimir la lista de platillos y la suma de tiempos
        System.out.println("Lista de platillos agregados:");
        for (int i = 0; i < listaPlatillos.size(); i++) {
            JsonObject platillo = listaPlatillos.get(i);
            String nombre = platillo.get("nombre").getAsString();
            int tiempoPreparacion = platillo.get("tiempoPreparacion").getAsInt();
            System.out.println(nombre + ": " + tiempoPreparacion + " minutos de preparación");
        }

        System.out.println("Tiempo total de preparación de los platillos agregados: " + sumaTiempos + " minutos");

        int tiempoTotal = 0;
        for (int i = 0; i < listaPlatillos.size(); i++) {
            JsonObject platillo = listaPlatillos.get(i);
            tiempoTotal += platillo.get("tiempoPreparacion").getAsInt();
        }

        System.out.println("Tiempo estimado de preparacion del pedido: " + tiempoTotal + " minutos");
        int tiempoRestante = tiempoTotal;
        while (tiempoRestante > 0) {
            System.out.println("Tiempo restante: " + tiempoRestante + " segundos");
            try {
                Thread.sleep(1000); // Esperar un segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tiempoRestante--;
        }
        System.out.println("El pedido esta listo");
    }


}



