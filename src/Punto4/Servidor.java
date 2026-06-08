package Punto4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try{
          serverSocket = new ServerSocket(9797);
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

        String funcionActual = "cos";
        String inputLine, outputLine;

        while ((inputLine = in.readLine()) != null){

            if (inputLine.startsWith("fun:")) {
                String nuevaFuncion  = inputLine.substring(4);
                if (nuevaFuncion.equals("sin") || nuevaFuncion.equals("cos") || nuevaFuncion.equals("tan")){
                    funcionActual = nuevaFuncion;
                    out.println("Función cambiada a: " + funcionActual);
                }else{
                    System.out.println("Función no reconocida");
                }

            }else {
                double numero  = Double.parseDouble(inputLine);
                double resultado = 0;
                switch (funcionActual){
                    case "sin":
                        resultado = Math.sin(numero);
                        break;

                    case "cos":
                        resultado = Math.cos(numero);
                        break;

                    case "tan":
                        resultado = Math.tan(numero);
                        break;
                    default:
                        System.out.println("Funcion no disponible");
                        break;
                }
                out.println(resultado);
            }

        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
