import java.io.Serializable;
import java.util.ArrayList;

public class Tuple  implements Serializable  {
    Object x;
    Object y;
    Object z;
    ArrayList<Reference> references= new ArrayList<>();
    public Tuple(Object x, Object y, Object z , Reference references) {
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




public static void  main(String[]args){
    
}



public static String equateString(String x,String y){
 
    if(x.length() > y.length()){
        for(int i = y.length();i<x.length();i++ ){
            y+=x.charAt(i);
        }
        return y;
    }
  
    else{
    for(int i = x.length();i<y.length();i++ ){
        x+=y.charAt(i);
    }
    return x;
    }
}

}