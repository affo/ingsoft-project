import java.io.Serializable;

/**
 * Created by affo on 21/03/18.
 */
public class Product implements Serializable {
    private final String description;
    private final double price;
    private Warehouse location;

    public Product(String description, double price) {
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public Warehouse getLocation() {
        return location;
    }

    public void setLocation(Warehouse location) {
        this.location = location;
    }
}
