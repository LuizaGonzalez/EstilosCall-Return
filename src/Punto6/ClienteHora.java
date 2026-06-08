package Punto6;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteHora {

    public static void main(String[] args) {
        String lastKnownTime = "(sin hora aún)";
        try {

            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(2000);
            InetAddress address = InetAddress.getByName("127.0.0.1");

            while (true) {
                byte[] buf = new byte[256];
                DatagramPacket request = new DatagramPacket(buf, buf.length, address, 9696);
                try {
                    socket.send(request);
                    DatagramPacket response = new DatagramPacket(buf, buf.length);
                    socket.receive(response);

                    lastKnownTime = new String(response.getData(), 0, response.getLength());
                    System.out.println("[ACTUALIZADO]  " + lastKnownTime);

                } catch (java.net.SocketTimeoutException e) {
                    System.out.println("[SIN RESPUESTA] \n Última hora: " + lastKnownTime);
                }
            }
        }catch (Exception e){
            Logger.getLogger(ClienteHora.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
