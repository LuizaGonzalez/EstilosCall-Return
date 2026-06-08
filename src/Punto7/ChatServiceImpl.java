package Punto7;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {

    private final String myName;

    public ChatServiceImpl(String myName) throws RemoteException {
        super();
        this.myName = myName;
    }

    @Override
    public void receiveMessage(String from, String message) throws RemoteException {
        System.out.println("\n[" + from + "]: " + message);
        System.out.print("[Tú]: ");
    }

    @Override
    public String getName() throws RemoteException {
        return myName;
    }
}