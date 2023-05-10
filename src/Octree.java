import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Octree {
    private OctreeNode root;
   // occurrences number ? 
    
    public Octree(double x, double y, double z, double width, double height, double depth) {
        this.root = new OctreeNode(x, y, z, width, height, depth);
    }
   
    
    public void insert(double x, double y, double z, Object data) {
        this.root.insert(x, y, z, data);
    }
    
   
    
    
   


    
}