import java.util.ArrayList;

public class Data {
    Object x;
    Object y;
    Object z;
    ArrayList<Reference> references= new ArrayList<>();
    public Data(Object x, Object y, Object z , Reference references) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.references.add( references ) ;
    }
    public Object getX() {
        return x;
    }
    public Object getY() {
        return y;
    }
    public Object getZ() {
        return z;
    }
    public ArrayList<Reference> getReferences() {
        return references;
    }
    public void setX(Object x) {
        this.x = x;
    }
    public void setY(Object y) {
        this.y = y;
    }
    public void setZ(Object z) {
        this.z = z;
    }
    public void setReferences(ArrayList<Reference> references) {
        this.references = references;
    }
    public String toString() {
        return "X: " + x + " Y: " + y + " Z: " + z + " References: " + references;
    }
}
