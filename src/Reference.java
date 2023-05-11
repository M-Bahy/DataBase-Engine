import java.io.Serializable;

public class Reference   implements Serializable {
    int pageNumber;
    int rowNumber;
    public Reference(int pageNumber, int rowNumber) {
        this.pageNumber = pageNumber;
        this.rowNumber = rowNumber;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public int getRowNumber() {
        return rowNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
    public String toString() {
        return "Page Number: " + pageNumber + " Row Number: " + rowNumber;
    }

}
