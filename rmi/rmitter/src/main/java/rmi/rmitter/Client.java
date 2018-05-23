package rmi.rmitter;

import rmi.rmitter.control.RemoteController;
import rmi.rmitter.view.TextView;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by affo on 19/03/18.
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry();

        /* Use this if you want to list the bound objects
        for (String name : registry.list()) {
            System.out.println(name);
        }
        */

        // gets a reference for the remote controller
        RemoteController controller = (RemoteController) registry.lookup("controller");
        //Per avere l'istanza di chi implementa i metodi

        // creates and launches the view
        new TextView(controller).run();
    }
}
