import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.net.MalformedURLException;
import java.util.Queue;
import java.util.LinkedList;

public class MutexManagerImpl extends UnicastRemoteObject implements MutexManager {

    private Queue<Request> requestQueue;

    public MutexManagerImpl() throws RemoteException {
        super();
        requestQueue = new LinkedList<>();
        try {
            Naming.rebind("rmi://localhost/MutexManager", this);
            System.out.println("MutexManager is ready.");
        } catch (MalformedURLException e) {
            System.err.println("Error in binding MutexManager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // MutexManager methods
    @Override
    public synchronized void requestEntry(int processId, VectorClock clock) throws RemoteException {
        requestQueue.add(new Request(processId, clock));
        System.out.println("Request received from process: " + processId);
    }

    @Override
    public synchronized void releaseEntry(int processId) throws RemoteException {
        requestQueue.removeIf(request -> request.getProcessId() == processId);
        System.out.println("Process " + processId + " has exited the critical section.");
    }

    // Inner class to represent a request
    private static class Request {
        private int processId;
        private VectorClock clock;

        public Request(int processId, VectorClock clock) {
            this.processId = processId;
            this.clock = clock;
        }

        public int getProcessId() {
            return processId;
        }

        public VectorClock getClock() {
            return clock;
        }
    }
}
