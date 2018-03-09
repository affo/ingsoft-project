package socket.serde;

import java.io.Serializable;

/**
 * Created by affo on 08/03/18.
 */
public class Person implements Serializable {
    private final String name;
    private Person child;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Person getChild() {
        return child;
    }

    public void setChild(Person child) {
        this.child = child;
    }
}
