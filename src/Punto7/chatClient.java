package Punto7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class chatClient {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese su nombre: ");
        String name = scanner.nextLine().trim();

        // Puerto propio para recibir mensajes
        System.out.print("Ingrese su puerto para recibir mensajes: ");
        int myPort = Integer.parseInt(scanner.nextLine().trim());

        // Datos del peer
        System.out.print("Ingrese la IP del peer remoto: ");
        String remoteIp = scanner.nextLine().trim();

        System.out.print("Ingrese el puerto del peer remoto: ");
        int remotePort = Integer.parseInt(scanner.nextLine().trim());

        // 1. Publicar su propio ChatServiceImpl para RECIBIR mensajes
        ChatServiceImpl myService = new ChatServiceImpl(name);
        Registry myRegistry = LocateRegistry.createRegistry(myPort);
        myRegistry.rebind("ChatService", myService);
        System.out.println("Listo para recibir en puerto " + myPort);

        // 2. Conectarse al peer para ENVIAR mensajes
        ChatService remotePeer = null;
        System.out.println("Conectando al peer...");
        while (remotePeer == null) {
            try {
                Registry remoteRegistry = LocateRegistry.getRegistry(remoteIp, remotePort);
                remotePeer = (ChatService) remoteRegistry.lookup("ChatService");
                System.out.println("Conectado a: " + remotePeer.getName());
            } catch (Exception e) {
                System.out.print(".");
                Thread.sleep(2000);
            }
        }

        // 3. Bucle de chat
        System.out.println("\n Chat LULOGO. \n Escriba 'salir' para terminar ===\n");
        System.out.print("[Tú]: ");

        String line;
        while ((line = scanner.nextLine()) != null) {
            if (line.equalsIgnoreCase("salir")) break;
            if (!line.trim().isEmpty()) {
                remotePeer.receiveMessage(name, line);
            }
            System.out.print("[Tú]: ");
        }

        System.out.println("Chat cerrado.");
        scanner.close();
        System.exit(0);
    }
}