public class Range {
    private final int start;
    private final int end;
    
    public Range(int start, int end) {
        this.start = start;
        this.end = end;
    }
    
    public int getStart() {
        return start;
    }
    
    public int getEnd() {
        return end;
    }
    
    @Override
    public String toString() {
        return "[" + start + ", " + end + ")";
    }
}
