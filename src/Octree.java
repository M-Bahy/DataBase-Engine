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
    
   public void delete(Tuple tuple){
        this.root.delete(tuple);
    }
    
    
   
    public List<Tuple> search(String op1, Object val1, String op2, Object val2, String op3, Object val3) {
    List<Tuple> result = new ArrayList<>();
    Range range1 = null;
    Range range2 = null;
    Range range3 = null;
    Range range4 = null;//min <= 54564 <  8    456945945    max
    Range range5 = null;
    Range range6 = null;
    if(op1.equals("!=")){
        range1 = getRange("<", val1, root.getX(), (Comparable) val1);
        range4 = getRange(">", val1, val1, (Comparable) root.getWidth());
    }
    else{
         range1 = getRange(op1, val1, root.getX(), (Comparable) root.getWidth());
    }
    if(op2.equals("!=")){
        range2 = getRange("<", val2, root.getY(), (Comparable) val2);
        range5 = getRange(">", val2, val2, (Comparable) root.getHeight());
    }
    else{
         range2 = getRange(op2, val2, root.getY(), (Comparable) root.getHeight());
    }
    if(op3.equals("!=")){
        range3 = getRange("<", val3, root.getZ(), (Comparable) val3);
        range6 = getRange(">", val3, val3, (Comparable) root.getDepth());
    }
    else{
         range3 = getRange(op3, val3, root.getZ(), (Comparable) root.getDepth());
    }
    int count = 0;
    if(range1 != null){
        count++;
    }
    if(range2 != null){
        count++;
    }
    if(range3 != null){
        count++;
    }
    if(range4 != null){
        count++;
    }
    if(range5 != null){
        count++;
    }
    if(range6 != null){
        count++;
    }

    
    
    Range[] ranges = new Range[count];
    int index = 0;
    if(range1 != null){
        ranges[index] = range1;
        index++;
    }
    if(range2 != null){
        ranges[index] = range2;
        index++;
    }
    if(range3 != null){
        ranges[index] = range3;
        index++;
    }
    if(range4 != null){
        ranges[index] = range4;
        index++;
    }
    if(range5 != null){
        ranges[index] = range5;
        index++;
    }
    if(range6 != null){
        ranges[index] = range6;
        index++;
    }




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
        case "!=": // 1111233144 5 4844984418174
            return new Range((Comparable) value, (Comparable) value);
        case "=":
            return new Range((Comparable) value, (Comparable) value,true);
        default:
            return null;
    }
}



    
}