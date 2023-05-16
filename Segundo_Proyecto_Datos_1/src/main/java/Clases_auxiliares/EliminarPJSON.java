package Clases_auxiliares;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Scanner;

public class EliminarPJSON {

    public static void main(String[] args) {


        JsonArray jsonArray = null;
        try (Reader reader = new FileReader("C:\\Users\\XPC}\\OneDrive\\Documents\\GitHub\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new Gson();
            jsonArray = gson.fromJson(reader, JsonArray.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // MUESTRA los platillos existentes
        System.out.println("PLATILLOS EXISTENTES:");
        for (JsonElement jsonElement : jsonArray) {
            JsonObject platillo = jsonElement.getAsJsonObject();
            System.out.println("Nombre: " + platillo.get("nombre").getAsString() +
                    ", Calorías: " + platillo.get("calorias").getAsInt() +
                    ", Tiempo de preparación: " + platillo.get("tiempoPreparacion").getAsInt() +
                    ", Precio: " + platillo.get("precio").getAsFloat());
        }


        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre del platillo a eliminar: ");
        String nombreEliminar = scanner.nextLine();

        boolean eliminado = false;
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject platillo = jsonArray.get(i).getAsJsonObject();
            if (platillo.get("nombre").getAsString().equals(nombreEliminar)) {
                jsonArray.remove(i);
                eliminado = true;
                break;
            }
        }

        if (eliminado) {
            System.out.println("El platillo " + nombreEliminar + " ha sido eliminado.");
        } else {
            System.out.println("El platillo " + nombreEliminar + " no existe en el menú.");
        }

        // Escribir el JsonArray modificado en el archivo JSON
        try (Writer writer = new FileWriter("C:\\Users\\XPC}\\OneDrive\\Documents\\GitHub\\Proyecto_Datos1_2\\Segundo_Proyecto_Datos_1\\Archvos JSON\\Platillos1.JSON")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

