import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Octree {
    Node root;
    private final static String theString = "java.lang.String";
	private final static String theDouble = "java.lang.Double";
	private final static String theDate = "java.util.Date";
	private final static String theInt = "java.lang.Integer";
    public Octree(Object min , Object max) {
        root = new NonLeafNode(min , max);
    }
    public void insert(Object data, String dataType, Reference reference , Object min , Object max) {
        if(root == null) {
            root = new LeafNode(data, dataType, reference , min , max);
        }
        else {
            insert(data, dataType, reference, root , min , max);
        }
    }
    public void insert(Object data, String dataType, Reference reference, Node node , Object min , Object max ) {
        if(node instanceof LeafNode) {
            LeafNode leafNode = (LeafNode) node;
            if(leafNode.getDataType().equals(dataType)) {
                if(leafNode.getData().equals(data)) {
                    leafNode.addReference(reference);
                }
                else {
                    //split
                    split(leafNode, data, dataType, reference,node , min , max);
                }
            }
            else {
                //split
                split(leafNode, data, dataType, reference,node , min , max);
            }
        }
        else {
            NonLeafNode nonLeafNode = (NonLeafNode) node;
            int index = getIndex(data, dataType , node );
            if(nonLeafNode.getChildren()[index] == null) {
                nonLeafNode.getChildren()[index] = new LeafNode(data, dataType, reference , min , max );
            }
            else {
                insert(data, dataType, reference, nonLeafNode.getChildren()[index] , min , max);
            }
        }
    }
    public void split(LeafNode leafNode, Object data, String dataType, Reference reference, Node node , Object min , Object max ) {
        //create a new non leaf node
        NonLeafNode nonLeafNode = new NonLeafNode(leafNode.getMin() , leafNode.getMax());
        //create a new leaf node
        LeafNode newLeafNode = new LeafNode(data, dataType, reference  , min , max );
        //get the index of the leaf node
        int index = getIndex(leafNode.getData(), leafNode.getDataType(),node);
        //set the leaf node to the index of the non leaf node
        nonLeafNode.getChildren()[index] = new LeafNode(leafNode);
        //get the index of the new leaf node
        index = getIndex(newLeafNode.getData(), newLeafNode.getDataType(),node);
        //set the new leaf node to the index of the non leaf node
        nonLeafNode.getChildren()[index] = newLeafNode;
        //set the root to the non leaf node
        node = nonLeafNode;
    }

    public int getIndex(Object data, String dataType , Node node) {
        int index = -1;
        if(dataType.equals(theString)) {
          String tuple = (String) data;


        }
        if(dataType.equals(theInt)) {
            int tuple = (int) data;
            int min = (int) node.getMin();
            int max = (int) node.getMax();
            List<Range> x = calculateRanges(min, max, 8);
            for (int i = 0; i < x.size(); i++) {
                if (tuple >= x.get(i).getStart() && tuple < x.get(i).getEnd()) {
                    index = i;
                }
            }
        }
        if(dataType.equals(theDouble)) {
            Double tuple = (Double) data;
        }
        if(dataType.equals(theDate)) {
            Date tuple = (Date) data;
        }
        return index;
    }

    public static List<int[]> divideRange(int min, int max) {
        int range = max - min;
        int partSize = range / 8;
        List<int[]> ranges = new ArrayList<>();
        int lowerBound = min;
        for (int i = 0; i < 8; i++) {
            int upperBound = lowerBound + partSize;
            ranges.add(new int[]{lowerBound, upperBound});
            lowerBound = upperBound;
        }
        // Adjust the last range to include any leftover range
        ranges.get(7)[1] = max;
        return ranges;
    }


    public static List<Range> calculateRanges(int start, int end, int n) {
        List<Range> ranges = new ArrayList<>();
        int rangeSize = (end - start) / n;
        int remainder = (end - start) % n;
        int currentStart = start;
        for (int i = 0; i < n; i++) {
            int currentEnd = currentStart + rangeSize;
            if (remainder > 0) {
                currentEnd++;
                remainder--;
            }
            ranges.add(new Range(currentStart, currentEnd));
            currentStart = currentEnd;
        }
        return ranges;
    }
    


    public static List<RangeD> calculateRanges(double start, double end, int n) {
        List<RangeD> ranges = new ArrayList<>();
        double rangeSize = (end - start) / n;
        double remainder = (end - start) % n;
        double currentStart = start;
        for (int i = 0; i < n; i++) {
            double currentEnd = currentStart + rangeSize;
            if (remainder > 0) {
                currentEnd += 1.0;
                remainder -= 1.0;
            }
            ranges.add(new RangeD(currentStart, currentEnd));
            currentStart = currentEnd;
        }
        return ranges;
    }
    
    
    

    


    public static void main (String[]args){
        //List<int[]> x = divideRange(1, 16);
        //System.out.println(Arrays.deepToString(x.toArray()));
        List<Range> x = calculateRanges(0, 16, 8);
        System.out.println(Arrays.deepToString(x.toArray()));
        List<RangeD> y = calculateRanges(1.0, 2.0, 8);
        System.out.println(Arrays.deepToString(y.toArray()));
    }

   // return -1;
    }

    // create a method to get the index of the data
   /*  public int getIndex(Object data, String dataType) {
        int index = 0;
        if(dataType.equals("String")) {
            String string = (String) data;
            if(string.length() > 0) {
                index = string.charAt(0) - 'a';
            }
        }
        else if(dataType.equals("Integer")) {
            Integer integer = (Integer) data;
            index = integer % 8;
        }
        else if(dataType.equals("Double")) {
            Double double1 = (Double) data;
            index = (int) (double1 % 8);
        }
        else if(dataType.equals("Float")) {
            Float float1 = (Float) data;
            index = (int) (float1 % 8);
        }
        else if(dataType.equals("Long")) {
            Long long1 = (Long) data;
            index = (int) (long1 % 8);
        }
        else if(dataType.equals("Short")) {
            Short short1 = (Short) data;
            index = (int) (short1 % 8);
        }
        else if(dataType.equals("Byte")) {
            Byte byte1 = (Byte) data;
            index = (int) (byte1 % 8);
        }
        else if(dataType.equals("Character")) {
            Character character = (Character) data;
            index = character % 8;
        }
        return index;
    }

*/


    // continue here
    // continue your code here
    // continue your octree code here co pilot
    // continue your octree code here co pilot
    // continue your octree code here co pilot
    
