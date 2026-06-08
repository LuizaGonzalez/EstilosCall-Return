package Punto7;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interfaz remota del servicio de chat.
 * Define los métodos que pueden ser invocados remotamente.
 */
public interface ChatService extends Remote {

    /**
     * Recibe un mensaje de texto del peer remoto.
     * @param from  nombre/identificador del emisor
     * @param message contenido del mensaje
     * @throws RemoteException si hay un error de comunicación
     */
    void receiveMessage(String from, String message) throws RemoteException;

    /**
     * Retorna el nombre del peer (para que el otro lado sepa con quién habla).
     * @return nombre del peer
     * @throws RemoteException si hay un error de comunicación
     */
    String getName() throws RemoteException;

}
