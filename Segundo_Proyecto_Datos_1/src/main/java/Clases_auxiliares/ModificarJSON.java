package Clases_auxiliares;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class ModificarJSON {

    public static void main(String[] args) {

        // Leer el archivo JSON existente en un objeto JsonArray
        JsonArray jsonArray = null;
        try (Reader reader = new FileReader("C:\\Users\\XPC}\\OneDrive\\Documents\\GitHub\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new Gson();
            jsonArray = gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione el número del platillo que desea modificar:");

        // Mostrar la lista de platillos disponibles y pedirle al usuario que seleccione uno
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject platillo = jsonArray.get(i).getAsJsonObject();
            String nombre = platillo.get("nombre").getAsString();
            System.out.println((i + 1) + ". " + nombre);
        }
        int seleccion = scanner.nextInt();

        // Obtener el objeto JsonObject del platillo seleccionado
        JsonObject platilloSeleccionado = jsonArray.get(seleccion - 1).getAsJsonObject();

        // Pedirle al usuario que ingrese los nuevos valores para cada uno de los atributos del platillo
        System.out.println("Ingrese los nuevos valores para el platillo:");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        if (nombre.equals("")) {
            nombre = scanner.nextLine();
        }
        System.out.print("Calorías: ");
        int calorias = scanner.nextInt();
        System.out.print("Tiempo de preparación (en minutos): ");
        int tiempoPreparacion = scanner.nextInt();
        System.out.print("Precio: ");
        float precio = scanner.nextFloat();

        // Modificar el objeto JsonObject del platillo seleccionado con los nuevos valores ingresados
        platilloSeleccionado.addProperty("nombre", nombre);
        platilloSeleccionado.addProperty("calorias", calorias);
        platilloSeleccionado.addProperty("tiempoPreparacion", tiempoPreparacion);
        platilloSeleccionado.addProperty("precio", precio);

        // Escribir el JsonArray modificado en el archivo JSON
        try (Writer writer = new FileWriter("C:\\Users\\XPC}\\OneDrive\\Documents\\GitHub\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

