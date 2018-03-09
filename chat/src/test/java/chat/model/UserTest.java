package chat.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by affo on 05/03/18.
 */
public class UserTest {
    private User john;
    private Group group;
    private StubMessageReceiver receiver;

    @Before
    public void before() {
        john = new User("john");
        group = mock(Group.class);
        receiver = new StubMessageReceiver();
    }

    @Test
    public void usernameTest() {
        assertEquals("john", john.getUsername());
    }

    @Test
    public void receiveMessage() {
        User carl = new User("carl");

        when(group.getName()).thenReturn("mocked-group");

        john.listenToMessages(receiver);
        Message message = new Message(group, carl, "Hello everybody!");
        john.receiveMessage(message);


        Message received = receiver.get();

        assertEquals(message, received);
    }
}
