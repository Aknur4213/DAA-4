package DAA.metrics;

public class Metrics {
    private long operations = 0;

    public void addOperation() {
        operations++;
    }

    public long getOperations() {
        return operations;
    }
}
