package Punto6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;

/**
 * Servidor UDP que responde con la hora actual cada vez que recibe una solicitud.
 */
public class ServidorHora {

    private DatagramSocket socket;
    private volatile boolean running = true;

    public ServidorHora(){
        try{
            socket = new DatagramSocket(9696);

        } catch (SocketException e) {
            Logger.getLogger(ServidorHora.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    public void startServer(){
        while (running) {
            byte[] buf = new byte[256];
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                //obtengo hora
                String timeString = new Date().toString();
                byte[] responseData = timeString.getBytes();

                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();

                DatagramPacket response = new DatagramPacket(
                        responseData, responseData.length, clientAddress, clientPort
                );
                socket.send(response);


            } catch (IOException e) {
                if (running) {
                    Logger.getLogger(ServidorHora.class.getName()).log(Level.SEVERE, null, e);
                }

            }
        }
    }
    public static void main(String[] args) {
        ServidorHora server = new ServidorHora();
        server.startServer();
    }
}
