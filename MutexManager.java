import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MutexManager extends Remote {
    void requestEntry(int processId, VectorClock clock) throws RemoteException;
    void releaseEntry(int processId) throws RemoteException;
}
