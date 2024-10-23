import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

public class ProcessClient {
    public static void main(String[] args) {
        try {
            int processId = Integer.parseInt(args[0]);
            MutexManager mutexManager = (MutexManager) Naming.lookup("rmi://localhost/MutexManager");
            VectorClock clock = new VectorClock(3); // Assuming 3 processes
            Process process = new ProcessImpl(processId, clock, mutexManager);

            Naming.rebind("rmi://localhost/Process" + processId, process);

            System.out.println("Process " + processId + " is running...");

            // Example usage: Request critical section
            process.requestCriticalSection();
            Thread.sleep(5000); // Simulate some work
            process.releaseCriticalSection();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.err.println("URL format is incorrect for RMI binding: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
