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

public class MenuJSON {

    public static void main(String[] args) {

        // Leer el archivo JSON existente en un objeto JsonArray
        JsonArray jsonArray = null;
        try (Reader reader = new FileReader("C:\\Users\\XPC}\\OneDrive\\Documents\\GitHub\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new Gson();
            jsonArray = gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crear un objeto JsonObject que represente el nuevo platillo y agregarlo al JsonArray
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del platillo: ");
        String nombre = scanner.nextLine();
        System.out.print("Calorías: ");
        int calorias = scanner.nextInt();
        System.out.print("Tiempo de preparación (en minutos): ");
        int tiempoPreparacion = scanner.nextInt();
        System.out.print("Precio: ");
        float precio = scanner.nextFloat();

        JsonObject platillo = new JsonObject();
        platillo.addProperty("nombre", nombre);
        platillo.addProperty("calorias", calorias);
        platillo.addProperty("tiempoPreparacion", tiempoPreparacion);
        platillo.addProperty("precio", precio);

        jsonArray.add(platillo);

        // Escribir el JsonArray modificado en el archivo JSON
        try (Writer writer = new FileWriter("C:\\Users\\XPC}\\OneDrive\\Documents\\GitHub\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

