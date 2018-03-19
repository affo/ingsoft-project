import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by affo on 20/03/18.
 */
public class WarehouseImpl extends UnicastRemoteObject implements Warehouse {
    private Map<String, Product> products;
    private Product backup;

    protected WarehouseImpl(Product backup) throws RemoteException {
        products = new HashMap<>();
        this.backup = backup;
    }

    @Override
    public double getPrice(String description) throws RemoteException {
        for (Product p : products.values()) {
            if (p.getDescription().equals(description)) {
                return p.getPrice();
            }
        }

        return backup == null ? 0.0 : backup.getPrice();
    }

    public void add(String keyword, Product product) {
        product.setLocation(this);
        products.put(keyword, product);
    }

    @Override
    public Product getProduct(List<String> keywords) throws RemoteException {
        for (String keyword : keywords) {
            Product p = products.get(keyword);
            if (p != null) {
                return p;
            }
        }

        return backup;
    }
}
