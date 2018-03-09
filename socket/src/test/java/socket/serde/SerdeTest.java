package socket.serde;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by affo on 07/03/18.
 */
public class SerdeTest {
    // We use them to put/show stuff in object streams
    private ByteArrayInputStream bais;
    private ByteArrayOutputStream baos;

    // We are testing their behavior
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    @Before
    public void before() throws IOException {
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
    }

    private void setBytes(byte[] bytes) throws IOException {
        bais = new ByteArrayInputStream(bytes);
        ois = new ObjectInputStream(bais);
    }

    private Object read() throws IOException, ClassNotFoundException {
        return ois.readObject();
    }

    @After
    public void after() throws IOException {
        if (oos != null) {
            oos.close();
        }

        if (ois != null) {
            ois.close();
        }
    }

    // -------------------------------------------- Tests

    @Test
    public void basicTest() throws IOException, ClassNotFoundException {
        Message msg = new Message("Serialization keeps me awake at night");
        oos.writeObject(msg);

        System.out.println(baos);

        byte[] serializedBytes = baos.toByteArray();
        setBytes(serializedBytes);
        Message deserMsg = (Message) read();

        assertNotEquals(msg, deserMsg);
        assertEquals(msg.content, deserMsg.content);
    }

    @Test
    public void rwObjectTest() throws IOException, ClassNotFoundException {
        Session session = new Session(42);
        oos.writeObject(session);

        setBytes(baos.toByteArray());
        Session deserialized = (Session) read();
        assertEquals(session.getData(), deserialized.getData());
        assertTrue(session.getActivationTime() < deserialized.getActivationTime());
    }

    @Test
    public void referenceIntegrity() throws IOException, ClassNotFoundException {
        Person mother = new Person("Joan");
        Person father = new Person("Jhon");
        Person child = new Person("Jhonny");

        mother.setChild(child);
        father.setChild(child);

        // TODO try to put `oos.reset();` in the middle of this block of operations
        oos.writeObject(child);
        oos.writeObject(mother);
        oos.writeObject(father);

        setBytes(baos.toByteArray());

        Person dChild = (Person) read();
        Person dMother = (Person) read();
        Person dFather = (Person) read();

        assertEquals(dMother.getChild(), dChild);
        assertEquals(dFather.getChild(), dChild);
        assertEquals(dMother.getChild(), dFather.getChild());
    }

    /**
     * Credits to
     * https://stackoverflow.com/questions/8141440/how-are-constructors-called-during-serialization-and-deserialization
     */
    @Test
    public void inheritanceTest() throws IOException, ClassNotFoundException {
        System.out.println(">>> Creating...");
        Child child = new Child(1);
        child.setField(10);

        System.out.println("\n\n>>> Serializing...");
        oos.writeObject(child);

        System.out.println("\n\n>>> Deserializing...");
        setBytes(baos.toByteArray());
        Child deserialized = (Child) read();

        assertEquals(10, child.getField());
        assertEquals(5, deserialized.getField());
        assertEquals(1, deserialized.getI());
    }

    // TODO 1: try to add `implements Serializable` and see what happens running `inheritanceTest`
    // TODO 2: try to remove the default constructor and see what happens
    private static class Parent {
        private int field;

        protected Parent() {
            field = 5;
            System.out.println("Parent::DefaultConstructor");
        }

        public Parent(int field) {
            this.field = field;
            System.out.println("Parent::ParamConstructor");
        }

        public int getField() {
            return field;
        }

        public void setField(int field) {
            this.field = field;
        }
    }

    private static class Child extends Parent implements Serializable {
        private int i;

        public Child(int i) {
            super(i);
            this.i = i;
            System.out.println("Child::Constructor");
        }

        public int getI() {
            return i;
        }
    }
}
