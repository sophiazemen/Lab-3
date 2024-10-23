import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.net.MalformedURLException;

public class ProcessImpl extends UnicastRemoteObject implements Process {

    private int processId;
    private VectorClock clock;
    private MutexManager mutexManager;
    private boolean inCriticalSection;

    public ProcessImpl(int processId, VectorClock clock, MutexManager mutexManager) throws RemoteException {
        super();
        this.processId = processId;
        this.clock = clock;
        this.mutexManager = mutexManager;
        this.inCriticalSection = false;
        try {
            Naming.rebind("rmi://localhost/Process" + processId, this);
            System.out.println("Process " + processId + " is ready.");
        } catch (MalformedURLException e) {
            System.err.println("Error in binding Process: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void requestCriticalSection() throws RemoteException {
        clock.increment();
        mutexManager.requestEntry(processId, clock);
        inCriticalSection = true;
        System.out.println("Process " + processId + " is in the critical section.");
    }

    @Override
    public void releaseCriticalSection() throws RemoteException {
        mutexManager.releaseEntry(processId);
        inCriticalSection = false;
        System.out.println("Process " + processId + " has left the critical section.");
    }

    @Override
    public VectorClock getClock() throws RemoteException {
        return clock;
    }
}
