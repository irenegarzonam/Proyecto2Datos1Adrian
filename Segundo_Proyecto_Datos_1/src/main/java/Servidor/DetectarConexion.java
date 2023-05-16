package Servidor;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DetectarConexion implements Runnable {

    ServerSocket serverSocket;

    public DetectarConexion(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (true) {
            Socket socket;

            try {
                socket = serverSocket.accept();

                System.out.println("Un cliente se ha conectado: " + socket);

                //Maneja entrada y salida de objetos
                ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outObject = new ObjectOutputStream(socket.getOutputStream());

                System.out.println("Esta entrando aqui con cada mensaje");

                Thread thread = new HiloClientes(socket, inObject, outObject);
                thread.start();

            } catch (Exception e) {
                System.out.println("entro aqui el error");
                e.printStackTrace();

            }
        }

    }

}
