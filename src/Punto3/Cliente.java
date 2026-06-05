package Punto3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try{
            echoSocket = new Socket("127.0.0.1", 4848);
            out = new PrintWriter(echoSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

        }catch (UnknownHostException e){
            System.err.println("Don't know about host!");
            System.exit(1);
        }catch (IOException e) {
            System.err.println("Error:" + e.getMessage());
            System.exit(1);
        }

        Scanner numIn = new Scanner(System.in);
        System.out.println("Dame un numero: ");
        String num = numIn.nextLine();
        out.println(num);

        System.out.println("Respuesta del servidor: " + in.readLine());

        out.close();
        echoSocket.close();
    }
}
