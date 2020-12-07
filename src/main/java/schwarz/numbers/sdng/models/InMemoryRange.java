package schwarz.numbers.sdng.models;

public class InMemoryRange {


    public InMemoryRange(NumberCounter numberCounter) {
        this.numberCounter = numberCounter;
    }

    private long max;
    private long start;
    private long current;
    private NumberCounter numberCounter;


    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public NumberCounter getNumberCounter() {
        return numberCounter;
    }

    public void setNumberCounter(NumberCounter numberCounter) {
        this.numberCounter = numberCounter;
    }
}
