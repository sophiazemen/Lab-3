public class Request {
    private int processId;
    private int[] clock;

    public Request(int processId, int[] clock) {
        this.processId = processId;
        this.clock = clock.clone();
    }

    public int getProcessId() {
        return processId;
    }

    public int getClockTime() {
        return clock[processId]; // We use the requesting process's timestamp
    }
}
