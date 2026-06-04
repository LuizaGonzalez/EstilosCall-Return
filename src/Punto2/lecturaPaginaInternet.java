package Punto2;

import javax.security.sasl.SaslClient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.io.*;

public class lecturaPaginaInternet {
    public static void main(String[] args) throws MalformedURLException {

        Scanner enlace = new Scanner(System.in);
        System.out.print("Ingrese la URL: ");
        String direccion  = enlace.nextLine();

        try {
            URL url = new URL(direccion);
            BufferedReader lecturaEnlace = new BufferedReader(new InputStreamReader(url.openStream()));

            BufferedWriter archivoAlmacena = new BufferedWriter(new FileWriter("lectura.html"));
            String linea;
            while ((linea = lecturaEnlace.readLine()) != null){
                archivoAlmacena.write(linea);
                archivoAlmacena.newLine();
            }
            lecturaEnlace.close();
            archivoAlmacena.close();
        }catch (IOException e){
            System.out.println("Se produjo el error : " + e.getMessage());
        }
    }
}
