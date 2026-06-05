package Punto3;

import java.io.*;
import java.net.ServerSocket;
import java.net.*;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(4848);
        }catch (IOException e){
            System.out.println("Error al escuchar el puerto 4848: " + e.getMessage());
            System.exit(1);
        }
        Socket puertoCliente = null;
        try{
            puertoCliente = serverSocket.accept();
        } catch( IOException e){
            System.out.println("Error  al conectarse" + e.getMessage());
            System.exit(1);
        }
        PrintWriter numeroCuadrado = new PrintWriter(puertoCliente.getOutputStream(), true);
        BufferedReader numero = new BufferedReader(new InputStreamReader(puertoCliente.getInputStream()));
        String entrada;
        while ((entrada = numero.readLine()) != null) {
            int num = Integer.parseInt(entrada);
            int salidaServer = num* num;
            System.out.println("Número al cuadrado: "+ salidaServer );
            numeroCuadrado.println(salidaServer);
        }
        numeroCuadrado.close();
        numero.close();
        puertoCliente.close();
        serverSocket.close();
    }
}
