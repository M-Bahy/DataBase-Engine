import java.io.Serializable;
import java.util.Vector;

public class Table implements Serializable{
	 String name;
	 String clusteringKey;
	 Vector<String> colName;
	 Vector<String>colType;
	 Vector<String> min;
	 Vector<String> max;
	 
	 
	 public Table(String name, String clusteringKey) {
			
			this.name = name;
			this.clusteringKey = clusteringKey;
			this.colName = new Vector<String>();
			this.colType =new Vector<String>();
			this.min = new Vector<String>();
			this.max = new Vector<String>();
		}
	 
	 
	 
	 

	 @Override
	public String toString() {
		return "Table [name=" + name + ", clusteringKey=" + clusteringKey + ", colName=" + colName + ", colType="
				+ colType + ", min=" + min + ", max=" + max + "]";
	}
}
