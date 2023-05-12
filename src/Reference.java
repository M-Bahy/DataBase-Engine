import java.io.Serializable;

public class Reference   implements Serializable {
    private int pageNumber;
    
    public Reference(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public String toString() {
        return "Page Number: " + pageNumber + "\n";
    }

}
