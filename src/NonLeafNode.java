public class NonLeafNode implements Node {
    Node [] children;
    Object min;
    Object max;
    public NonLeafNode(Object min , Object max) {
        children = new Node[8];
        this.min = min;
        this.max = max;
    }

    public Node[] getChildren() {
        return children;
    }

    public void setChildren(Node[] children) {
        this.children = children;
    }
    
    /*public void insert(Object data, String dataType, Reference reference) {
     if(children == null){
        insertRoot( data,  dataType,  reference );
     }   
    }

    public void insertRoot(Object data, String dataType, Reference reference ) {
        children = new Node[8];
        LeafNode leafNode = new LeafNode(data, dataType, reference);

    }*/

}
