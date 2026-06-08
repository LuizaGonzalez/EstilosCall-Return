package Punto5;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class ServidorWeb {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(6565);
        }catch(IOException e){
            System.err.println("could not listen on port: 6565");
            System.exit(1);
        }
        while (true) {
            Socket clientSocket = null;
            try{
                System.out.println("listo para recibir ...");
                clientSocket = serverSocket.accept();
                System.out.println("Conexión recibida de: " + clientSocket.getInetAddress());
                atenderSolicitud(clientSocket);
            }catch (IOException e){
                System.err.println("Error atendiendo solicitud: " + e.getMessage());
            } finally {
                if (clientSocket != null){
                    clientSocket.close();
                }
            }
        }
    }

    private static void atenderSolicitud(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //lee en binario
        OutputStream out = clientSocket.getOutputStream();

        String ruta = leerRuta(in);
        if (ruta == null) return;

        File archivo = new File("www" + ruta);
        System.out.println("Buscando: " + archivo.getAbsolutePath());

        if (!archivo.exists() || archivo.isDirectory()) {
            responder404(out);
        } else {
            responder200(out, archivo, ruta);
        }
        out.flush();
    }
    private static String leerRuta(BufferedReader in) throws IOException {
        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) return null;

        System.out.println("Request: " + requestLine);

        String[] partes = requestLine.split(" ");
        if (partes.length < 2) return null;

        String ruta = partes[1];
        return ruta.equals("/") ? "/index.html" : ruta;

    }
    private static void responder200(OutputStream out, File archivo, String ruta) throws IOException {
        byte[] contenido = Files.readAllBytes(archivo.toPath());
        String encabezado = construirEncabezado("HTTP/1.1 200 OK", obtenerContentType(ruta), contenido.length);
        out.write(encabezado.getBytes());
        out.write(contenido);
    }
    private static void responder404(OutputStream out) throws IOException {
        String cuerpo = "<html><body><h1>404 - Archivo no encontrado</h1></body></html>";
        byte[] cuerpoBytes = cuerpo.getBytes();
        String encabezado = construirEncabezado("HTTP/1.1 404 Not Found", "text/html", cuerpoBytes.length);
        out.write(encabezado.getBytes());
        out.write(cuerpoBytes);
    }
    // Construye el encabezado HTTP
    private static String construirEncabezado(String status, String contentType, int length) {
        return status + "\r\n"
                + "Content-Type: " + contentType + "\r\n"
                + "Content-Length: " + length + "\r\n"
                + "\r\n";
    }
    private static String obtenerContentType(String ruta) {
        if (ruta.endsWith(".html") || ruta.endsWith(".htm")) return "text/html";
        if (ruta.endsWith(".css"))  return "text/css";
        if (ruta.endsWith(".js"))   return "application/javascript";
        if (ruta.endsWith(".png"))  return "image/png";
        if (ruta.endsWith(".jpg") || ruta.endsWith(".jpeg")) return "image/jpeg";
        if (ruta.endsWith(".gif"))  return "image/gif";
        if (ruta.endsWith(".ico"))  return "image/x-icon";
        return "application/octet-stream";
    }

}
