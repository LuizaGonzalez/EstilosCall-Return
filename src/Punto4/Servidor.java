package Punto4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main() throws IOException {
        ServerSocket serverSocket = null;
        try{
          serverSocket = new ServerSocket(20213);
        }catch (IOException e){
            System.err.println("Could not listen on port: 20213");
            System.exit(1);
        }
        Socket clientSocket = null;
        try{
            clientSocket = serverSocket.accept();
        }catch (IOException e){
            System.err.println("Accept failed");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine, outputLine;

        while ((inputLine = in.readline()) != null){
            if (in.startsWith("fun:")) {
                String funcion = in.substring(4);
            }
        }

    }
}
