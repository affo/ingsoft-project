package rmi.rmitter;

import rmi.rmitter.control.Controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by affo on 19/03/18.
 */
public class Server {
    public static void main(String[] args) throws RemoteException {
        Controller controller = new Controller();
        System.out.println(">>> Controller exported");

        Registry registry = LocateRegistry.getRegistry();
        registry.rebind("controller", controller);
    }
}
