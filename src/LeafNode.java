import java.util.ArrayList;

public class LeafNode implements Node {
    Object data;
    String dataType;
    ArrayList<Reference> references = new ArrayList<Reference>();
    Object min;
    Object max;
    public LeafNode(Object data, String dataType , Reference reference , Object min , Object max) {
        this.data = data;
        this.dataType = dataType;
        this.max = max;
        this.min = min;
        references.add(reference);
    }
    // create a leaf node from a leaf node
    public LeafNode(LeafNode leafNode) {
        this.data = leafNode.getData();
        this.dataType = leafNode.getDataType();
        this.references = leafNode.getReferences();
        this.max = leafNode.getMax();
        this.min = leafNode.getMin();
    }
    public Object getData() {
        return data;
    }
    public String getDataType() {
        return dataType;
    }
    public ArrayList<Reference> getReferences() {
        return references;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public void addReference(Reference reference) {
        references.add(reference);
    }
    //delete a reference
    public void deleteReference(Reference reference) {
        references.remove(reference);
    }
    public Object getMax() {
        return max;
    }
    public Object getMin() {
        return min;
    }
    public void setMax(Object max) {
        this.max = max;
    }
    public void setMin(Object min) {
        this.min = min;
    }
    public String toString() {
        return "Data: " + data + " Data Type: " + dataType + " References: " + references;
    }
    // write a method to return the references size
    public int numberOfOccurrences() {
        return references.size();
    }
    public boolean isEmpty() {
        return references.size() == 0;
    }
   
}
