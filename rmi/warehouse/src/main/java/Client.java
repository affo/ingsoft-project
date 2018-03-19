import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;

/**
 * Created by affo on 20/03/18.
 */
public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.setProperty("java.security.policy", "stupid.policy");
        System.setSecurityManager(new SecurityManager());

        Registry registry = LocateRegistry.getRegistry();

        System.out.println("RMI registry bindings:");
        for (String binding: registry.list()) {
            System.out.println(">>> " + binding);
        }


        Warehouse warehouse = (Warehouse) registry.lookup("warehouse");
        double price = warehouse.getPrice("Toaster");
        System.out.println(">>> " + price);

        Product toaster = warehouse.getProduct(Collections.singletonList("toaster"));
        System.out.println(">>> " + toaster.getDescription());
        Product book = warehouse.getProduct(Collections.singletonList("let's see what happens when we get a book"));
        System.out.println(">>> " + book.getDescription());
    }
}
