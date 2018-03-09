package socket.serde;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by affo on 08/03/18.
 */
public class Session implements Serializable {
    private int data;
    private long activationTime;

    public Session(int data) {
        this.data = data;
        this.activationTime = System.currentTimeMillis();
    }

    public int getData() {
        return data;
    }

    public long getActivationTime() {
        return activationTime;
    }

    // --------------------- Serde
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        System.out.println(">>> Serialized");
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        // Override activation time
        activationTime = System.currentTimeMillis();
        System.out.println(">>> Deserialized");
    }
}
