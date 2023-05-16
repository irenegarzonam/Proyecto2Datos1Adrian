package Servidor;

import java.io.*;
import java.net.ServerSocket;

//CLass server
public class ServerApp {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);

        DetectarConexion arrancarServer = new DetectarConexion(serverSocket);

        Thread hilo = new Thread(arrancarServer);
        hilo.start();

        System.out.println("*Server noises turning on*");
    }

}





