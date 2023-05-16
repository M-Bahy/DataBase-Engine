public class Range {
    private Comparable lower;
    private Comparable upper;
    private boolean inclusive;

    public Range(Comparable lower, Comparable upper) {
        this(lower, upper, false);
    }

    public Range(Comparable lower, Comparable upper, boolean inclusive) {
        this.lower = lower;
        this.upper = upper;
        this.inclusive = inclusive;
    }
    

    @Override
    public String toString() {
        return "Range [lower=" + lower + ", upper=" + upper + ", inclusive=" + inclusive + "]";
    }
    
    public Comparable getLower() {
        return lower;
    }

    public Comparable getUpper() {
        return upper;
    }

    public boolean isInclusive() {
        return inclusive;
    }

    public boolean contains(Comparable value) {
        if (inclusive) {
            return value.compareTo(lower) >= 0 && value.compareTo(upper) <= 0;
        } else {
            return value.compareTo(lower) > 0 && value.compareTo(upper) < 0;
        }
    }
}