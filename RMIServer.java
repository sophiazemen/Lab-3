import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;

public class RMIServer {
    public static void main(String[] args) {
        try {
            MutexManager mutexManager = new MutexManagerImpl();
            Naming.rebind("rmi://localhost/MutexManager", mutexManager);
            System.out.println("MutexManager is running...");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.err.println("URL format is incorrect for RMI binding: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
