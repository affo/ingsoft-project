import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by affo on 20/03/18.
 */
public interface Warehouse extends Remote {
    double getPrice(String description) throws RemoteException;
    Product getProduct(List<String> keywords) throws RemoteException;
}
