package rmi.rmitter.view;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by affo on 20/03/18.
 */
public interface RemoteBaseView extends Remote {
    void error(String message) throws RemoteException;

    void ack(String message) throws RemoteException;
}
