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
    private String [] colNames;
    private String tableName;
    public Octree(Object x, Object y, Object z, Object width, Object height, Object depth, String name, String [] colNames, String tableName) {
        this.name = name;
        this.colNames = colNames;
        this.tableName = tableName;
        this.root = new OctreeNode(x, y, z, width, height, depth);
    }
   
    
    public String getTableName() {
        return tableName;
    }


    public void insert(Tuple tuple) {
        this.root.insert(tuple);
    }


    @Override
    public String toString() {
        return "Octree [root=" + root.toString() + "]";
    }


    public String getName() {
        return name;
    }


    public String[] getColNames() {
        return colNames;
    }
    
   
    
    
   
    public List<Tuple> search(String op1, Object val1, String op2, Object val2, String op3, Object val3) {
    List<Tuple> result = new ArrayList<>();
    Range range1 = getRange(op1, val1, root.getX(), (Comparable) root.getWidth());
    Range range2 = getRange(op2, val2, root.getY(), (Comparable) root.getHeight());
    Range range3 = getRange(op3, val3, root.getZ(), (Comparable) root.getDepth());
    Range[] ranges = {range1, range2, range3};
    for (int i = 0; i < ranges.length; i++) {
        System.out.println(ranges[i]);
    }
    root.searchHelper(ranges, root, result);
    return result;
}



private Range getRange(String operator, Object value, Object min, Comparable max) {
    switch (operator) {
        case ">":
            return new Range((Comparable) value, max);
        case ">=":
            return new Range((Comparable) value, max, true);
        case "<":
            return new Range((Comparable) min, (Comparable) value);
        case "<=":
            return new Range((Comparable) min, (Comparable) value, true);
        case "!=":
            return new Range((Comparable) value, (Comparable) value);
        case "=":
            return new Range((Comparable) value, (Comparable) value);
        default:
            return null;
    }
}



    
}