import java.io.Serializable;

public class Reference   implements Serializable {
    private int pageNumber;
    private String pkName;
    private Object pkValue;

    
    public Reference(int pageNumber, String pkName, Object pkValue) {
        this.pageNumber = pageNumber;
        this.pkName = pkName;
        this.pkValue = pkValue;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    
    public String getPkName() {
        return pkName;
    }
    public Object getPkValue() {
        return pkValue;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public String toString() {
        return "Page Number: " + pageNumber + "\n";
    }

}
