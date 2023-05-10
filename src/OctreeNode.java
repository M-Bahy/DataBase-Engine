import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public  class OctreeNode {
    private double x, y, z, width, height, depth;
    private List<Data> data;
    private OctreeNode[] children;
    private int maxObjects;
    // { age : 5  , fn : mo  , ln : mo  , references : [  {}  ,  {}  ] }
    public OctreeNode(double x, double y, double z, double width, double height, double depth) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.data = new ArrayList<>();
        this.children = new OctreeNode[8];
    }
    public int getMaxObjects()  {
        Properties props = new Properties();
    FileInputStream fis = null;
    int m = -1;
    try {
        fis = new FileInputStream("src/resources/DBApp.config");
        props.load(fis);

        // Access properties by key
        
       // int n = Integer.parseInt(props.getProperty("MaximumRowsCountinTablePage"));
         m = Integer.parseInt(props.getProperty("MaximumEntriesinOctreeNode"));
        // Do something with properties
      // setM(m);
      // setN(n);
       System.out.println("Config file read successfully.");
       // print m and n 
       //System.out.println("M : " + getM());
      // System.out.println("N : " + n);
    } catch (IOException e) {
        System.out.println("Error reading config file.");
    } finally {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println("Error reading config file.");
            }
        }
    }
    fis=null;
    return m;
    }
    
    public void insert(double x, double y, double z, Object data) {
        if (this.children[0] != null) {
            int index = getIndex(x, y, z);
            if (index != -1) {
                this.children[index].insert(x, y, z, data);
                return;
            }
        }
        this.data.add(data);
        if (this.data.size() > this.getMaxObjects()) {
            if (this.children[0] == null) {
                split();
            }
            int i = 0;
            while (i < this.data.size()) {
                Object obj = this.data.get(i);
                int index = getIndex(x, y, z);
                if (index != -1) {
                    this.children[index].insert(x, y, z, obj);
                    this.data.remove(i);
                } else {
                    i++;
                }
            }
        }
    }
    
    public void query(double x, double y, double z, double radius, List<Object> result) {
        double dx = x - this.x;
        double dy = y - this.y;
        double dz = z - this.z;
        double distanceSquared = dx * dx + dy * dy + dz * dz;
        if (distanceSquared <= radius * radius) {
            result.addAll(this.data);
        }
        if (this.children[0] != null) {
            for (OctreeNode child : this.children) {
                child.query(x, y, z, radius, result);
            }
        }
    }
    
    private void split() {
        double subWidth = (this.width-this.x) / 2;
        double subHeight = (this.height-this.y) / 2;
        double subDepth = (this.depth-this.z) / 2;
        double width = this.width;
        double height = this.height;
        double depth = this.depth;
        
        this.children[0] = new OctreeNode(this.x, this.y, this.z, subWidth, subHeight, subDepth);

        this.children[1] = new OctreeNode( subWidth, this.y, this.z, width, subHeight, subDepth);

        this.children[2] = new OctreeNode(this.x, subHeight, this.z, subWidth, height, subDepth);

        this.children[3] = new OctreeNode(subWidth,  subHeight, this.z, width, height, subDepth);

        this.children[4] = new OctreeNode(this.x, this.y,  subDepth, subWidth, subHeight, depth);

        this.children[5] = new OctreeNode( subWidth, this.y,  subDepth, width, subHeight, depth);

        this.children[6] = new OctreeNode(this.x,  subHeight,  subDepth, subWidth, height, depth);

        this.children[7] = new OctreeNode( subWidth,  subHeight, subDepth, width, height, depth);
    }
    
    private int getIndex(double x, double y, double z) {
        int index = -1;
        double verticalMidpoint = this.x + (this.width / 2);
        double horizontalMidpoint = this.y + (this.height / 2);
        double depthMidpoint = this.z + (this.depth / 2);
        
        boolean topQuadrant = (y < horizontalMidpoint);
        boolean bottomQuadrant = (y >= horizontalMidpoint);
        boolean leftQuadrant = (x < verticalMidpoint);
        boolean rightQuadrant = (x >= verticalMidpoint);
        boolean frontQuadrant = (z < depthMidpoint);
        boolean backQuadrant = (z >= depthMidpoint);
        
        if (leftQuadrant) {
            if (topQuadrant) {
                if (frontQuadrant) {
                    index = 0;
                } else if (backQuadrant) {
                    index = 4;
                }
            } else if (bottomQuadrant) {
                if (frontQuadrant) {
                    index = 2;
                } else if (backQuadrant) {
                    index = 6;
                }
            }
        } else if (rightQuadrant) {
            if (topQuadrant) {
                if (frontQuadrant) {
                    index = 1;
                } else if (backQuadrant) {
                    index = 5;
                }
            } else if (bottomQuadrant) {
                if (frontQuadrant) {
                    index = 3;
                } else if (backQuadrant) {
                    index = 7;
                }
            }
        }
        return index;
    }
}