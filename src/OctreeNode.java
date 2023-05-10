import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public  class OctreeNode {
    private Object x, y, z, width, height, depth;
    private List<Tuple> data;
    private OctreeNode[] children;
    private int maxObjects;
    // { age : 5  , fn : mo  , ln : mo  , references : [  {}  ,  {}  ] }
    public OctreeNode(Object x, Object y, Object z, Object width, Object height, Object depth) {
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
    
 
    // public void insertInt(int x, int y, int z, Tuple tuple) {
    //     if (this.children[0] != null) {
    //         int index = getIndex(x, y, z);
    //         if (index != -1) {
    //             this.children[index].insertInt(x, y, z, tuple);
    //             return;
    //         }
    //     }
    //     this.data.add(tuple);
    //     if (this.data.size() > this.getMaxObjects()) {
    //         if (this.children[0] == null) {
    //             split();
    //         }
    //         int i = 0;
    //         while (i < this.data.size()) {
    //             Tuple obj = this.data.get(i);
    //             int index = getIndex(x, y, z);
    //             if (index != -1) {
    //                 this.children[index].insertInt(x, y, z, obj);
    //                 this.data.remove(i);
    //             } else {
    //                 i++;
    //             }
    //         }
    //     }
    // }


    public void insertDouble(double x, double y, double z, Tuple tuple) {
        if (this.children[0] != null) {
            int index = getIndexDouble(x, y, z);
            if (index != -1) {
                this.children[index].insertDouble(x, y, z, tuple);
                return;
            }
        }
        this.data.add(tuple);
        if (this.data.size() > this.getMaxObjects()) {
            if (this.children[0] == null) {
                split();
            }
            int i = 0;
            while (i < this.data.size()) {
                Tuple obj = this.data.get(i);
                int index = getIndexDouble(x, y, z);
                if (index != -1) {
                    this.children[index].insertDouble(x, y, z, obj);
                    this.data.remove(i);
                } else {
                    i++;
                }
            }
        }
    }



    
    // public void insertString(String x, String y, String z, Tuple tuple) {
    //     if (this.children[0] != null) {
    //         int index = getIndex(x, y, z);
    //         if (index != -1) {
    //             this.children[index].insertString(x, y, z, tuple);
    //             return;
    //         }
    //     }
    //     this.data.add(tuple);
    //     if (this.data.size() > this.getMaxObjects()) {
    //         if (this.children[0] == null) {
    //             split();
    //         }
    //         int i = 0;
    //         while (i < this.data.size()) {
    //             Tuple obj = this.data.get(i);
    //             int index = getIndex(x, y, z);
    //             if (index != -1) {
    //                 this.children[index].insertDouble(x, y, z, obj);
    //                 this.data.remove(i);
    //             } else {
    //                 i++;
    //             }
    //         }
    //     }
    // }






















    
    // public void query(double x, double y, double z, double radius, List<Object> result) {
    //     double dx = x - this.x;
    //     double dy = y - this.y;
    //     double dz = z - this.z;
    //     double distanceSquared = dx * dx + dy * dy + dz * dz;
    //     if (distanceSquared <= radius * radius) {
    //         result.addAll(this.data);
    //     }
    //     if (this.children[0] != null) {
    //         for (OctreeNode child : this.children) {
    //             child.query(x, y, z, radius, result);
    //         }
    //     }
    // }
    
    private void split() {


        Object subWidth =null;
        Object subHeight=null;
        Object subDepth=null;



        if(x instanceof Integer){
            int widthI =(int)width;
            int xI = (int) x;
             subWidth = widthI-xI/2;
        }
     
        if(x instanceof Double){
            double widthI =(double)width;
            double xI = (double) x;
             subWidth = widthI-xI/2;
        }

        if(x instanceof String){
            String widthI =(String)width;
            String xI = (String) x;
           if(widthI.length() > xI.length()){
            xI = equateString(xI, widthI);
           }
           else {
            
            if(widthI.length() < xI.length()){
                xI = equateString(xI, widthI);
               }
           }
            subWidth = middleString(xI, widthI, xI.length());

        }


        if(x instanceof Date){
            Date widthI =(Date)width;
            Date xI = (Date) x;
            Date startdate = xI;
            Date enddate = widthI;
            var middate = new Date(((startdate.getTime() + enddate.getTime()) / 2));

            subWidth = middate;
        }


        this.children[0] = new OctreeNode(this.x, this.y, this.z, subWidth, subHeight, subDepth);

        this.children[1] = new OctreeNode( subWidth, this.y, this.z, width, subHeight, subDepth);

        this.children[2] = new OctreeNode(this.x, subHeight, this.z, subWidth, height, subDepth);

        this.children[3] = new OctreeNode(subWidth,  subHeight, this.z, width, height, subDepth);

        this.children[4] = new OctreeNode(this.x, this.y,  subDepth, subWidth, subHeight, depth);

        this.children[5] = new OctreeNode( subWidth, this.y,  subDepth, width, subHeight, depth);

        this.children[6] = new OctreeNode(this.x,  subHeight,  subDepth, subWidth, height, depth);

        this.children[7] = new OctreeNode( subWidth,  subHeight, subDepth, width, height, depth);
    }
    
    private int getIndexDouble(double x, double y, double z) {
        int index = -1;

        double width = ((double)this.width);
        double height = ((double)this.height);
        double depth = ((double)this.depth);
        double x1 = ((double)this.x);
        double y1 = ((double)this.y);
        double z1 = ((double)this.z);
        double verticalMidpoint =x1 + (width / 2);
        double horizontalMidpoint = y1 + (height / 2);
        double depthMidpoint = z1 + (depth / 2);
        
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


    private int getIndex(Object x, Object y, Object z) {



        boolean r = false;
        boolean s =  false;
        boolean v = false;
        if(x instanceof Integer ){
            r = compareInt((int)x);
        }
        if(x instanceof String){
           r =  compareString((String)x);
        }

        if(x instanceof Date){
           r =  compareDate((Date)x);
        }
        

        if(x instanceof Double){
           r =  compareDouble((double)x);
        }
// ////

if(y instanceof Integer ){
    s = compareInt((int)y);
}
if(y instanceof String){
   s =  compareString((String)y);
}

if(y instanceof Date){
   s =  compareDate((Date)y);
}


if(y instanceof Double){
   s =  compareDouble((double)y);
}


/////
if(z instanceof Integer ){
    v = compareInt((int)z);
}
if(z instanceof String){
   v =  compareString((String)z);
}

if(z instanceof Date){
   v =  compareDate((Date)z);
}


if(z instanceof Double){
   v =  compareDouble((double)z);
}



int index =-1;








        boolean topQuadrant = s;
        boolean bottomQuadrant =!s;
        boolean leftQuadrant = r ;            //(x < verticalMidpoint);
        boolean rightQuadrant =  !r ;          //(x >= verticalMidpoint);
        boolean frontQuadrant =v;
        boolean backQuadrant = !v;
        
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


private boolean compareDouble(double x2) {

    double x1 = (double)this.x;
    double width =  (double)this.width;
    double verticalMidpoint =x1 + ( width/ 2);
    return x2 < verticalMidpoint;

    }

private boolean compareDate(Date x2) {

    Date startdate = (Date)this.x;

    Date enddate = (Date)this.width;

    var middate = new Date(((startdate.getTime() + enddate.getTime()) / 2));

    return x2.compareTo(middate) < 0;
    }

private boolean compareString(String x2) {

    String x1 = (String)this.x;

    String width  = (String)this.width;
   if(x1.length() > width.length())
    width = equateString(width, x1);
    else if(x1.length() < width.length()){
        x1 = equateString(width, x1);
    }


  String mid =   middleString(x1, width, x1.length());

   return (x2.compareTo(mid) <0);
    }

private boolean compareInt(int x2) {


    int x1 = (int)this.x;
    int width =  (int)this.width;
    int verticalMidpoint =x1 + ( width/ 2);
 return x2 < verticalMidpoint;

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






static String middleString(String S, String T, int N)
{

    String r ="";
        // Stores the base 26 digits after addition
    int[] a1 = new int[N + 1];

    for (int i = 0; i < N; i++) {
        a1[i + 1] = (int)S.charAt(i) - 97
                    + (int)T.charAt(i) - 97;
    }

    // Iterate from right to left
    // and add carry to next position
    for (int i = N; i >= 1; i--) {
        a1[i - 1] += (int)a1[i] / 26;
        a1[i] %= 26;
    }

    // Reduce the number to find the middle
    // string by dividing each position by 2
    for (int i = 0; i <= N; i++) {

        // If current value is odd,
        // carry 26 to the next index value
        if ((a1[i] & 1) != 0) {

            if (i + 1 <= N) {
                a1[i + 1] += 26;
            }
        }

        a1[i] = (int)a1[i] / 2;
    }

    for (int i = 1; i <= N; i++) {
     r+=(char)(a1[i] + 97);
    }
    return r;
}











    // private int getIndexString(String x, String y, String z) {

    //     String width = ((String)this.width);
    //     String height = ((String)this.height);
    //     String depth = ((String)this.depth);
    //     String x1 = ((String)this.x);
    //     String y1 = ((String)this.y);
    //     String z1 = ((String)this.z);
    //     int index = -1;
    //     String verticalMidpoint = x1 + ( width / 2);
    //     int horizontalMidpoint = y1 + (height / 2);
    //     int depthMidpoint = z1 + (depth / 2);
        
    //     boolean topQuadrant = (y < horizontalMidpoint);
    //     boolean bottomQuadrant = (y >= horizontalMidpoint);
    //     boolean leftQuadrant = (x < verticalMidpoint);
    //     boolean rightQuadrant = (x >= verticalMidpoint);
    //     boolean frontQuadrant = (z < depthMidpoint);
    //     boolean backQuadrant = (z >= depthMidpoint);
        
    //     if (leftQuadrant) {
    //         if (topQuadrant) {
    //             if (frontQuadrant) {
    //                 index = 0;
    //             } else if (backQuadrant) {
    //                 index = 4;
    //             }
    //         } else if (bottomQuadrant) {
    //             if (frontQuadrant) {
    //                 index = 2;
    //             } else if (backQuadrant) {
    //                 index = 6;
    //             }
    //         }
    //     } else if (rightQuadrant) {
    //         if (topQuadrant) {
    //             if (frontQuadrant) {
    //                 index = 1;
    //             } else if (backQuadrant) {
    //                 index = 5;
    //             }
    //         } else if (bottomQuadrant) {
    //             if (frontQuadrant) {
    //                 index = 3;
    //             } else if (backQuadrant) {
    //                 index = 7;
    //             }
    //         }
    //     }
    //     return index;
    // }























}