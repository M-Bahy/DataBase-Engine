import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Octree implements Serializable {
    private OctreeNode root;
   // occurrences number ? 
    private String name;
    public Octree(Object x, Object y, Object z, Object width, Object height, Object depth, String name) {
        this.name = name;
        this.root = new OctreeNode(x, y, z, width, height, depth);
    }
   
    
    public void insert(Tuple tuple) {
        this.root.insert(tuple);
    }


    @Override
    public String toString() {
        return "Octree [root=" + root.toString() + "]";
    }
    
   
    
    
   


    
}