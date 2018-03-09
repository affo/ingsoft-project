package chat.model;

import chat.exceptions.UserNotInGroupException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by affo on 27/02/18.
 */
public class GroupTest {
    private Group group;
    private User john, mark, carl;

    @Before
    public void before() throws RemoteException {
        group = new Group();
        john = mock(User.class);
        mark = mock(User.class);
        carl = mock(User.class);

        when(john.getUsername()).thenReturn("john");
        when(mark.getUsername()).thenReturn("mark");
        when(carl.getUsername()).thenReturn("carl");
    }

    @Test
    public void groupSizeTest() throws RemoteException {
        group.join(john);
        Assert.assertEquals(1, group.size());
        group.join(mark);
        Assert.assertEquals(2, group.size());
        group.join(carl);
        Assert.assertEquals(3, group.size());

        group.leave(john);
        Assert.assertEquals(2, group.size());
    }

    @Test
    public void sendMessage() {
        System.out.println(john);
        group.join(john);
        group.join(carl);
        group.join(mark);

        Message m = new Message(group, john, "hello");
        group.sendMessage(m);

        List<Message> messages = group.messages();
        List<Message> expected = Collections.singletonList(m);
        assertThat(messages, is(expected));

        verify(john).receiveMessage(m);
        verify(carl).receiveMessage(m);
        verify(mark).receiveMessage(m);
    }

    @Test(expected = UserNotInGroupException.class)
    public void testIllegalMessage() throws RemoteException {
        Message message = new Message(group, john, "hello");
        group.sendMessage(message);
    }
}
