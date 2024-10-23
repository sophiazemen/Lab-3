import java.io.Serializable;

public class VectorClock implements Serializable {
    private int[] clock;
    
    public VectorClock(int numProcesses) {
        clock = new int[numProcesses];
    }

    public void increment() {
        for (int i = 0; i < clock.length; i++) {
            clock[i]++;
        }
    }

    public int[] getClock() {
        return clock;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i : clock) {
            sb.append(i).append(" ");
        }
        return sb.toString().trim() + "]";
    }
}
