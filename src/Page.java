import java.io.Serializable;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Vector;

public class Page implements Serializable {
	private Vector<   Hashtable<String,Object>   > data;  // ROWS
	private int size ;
	  public Page (){
		  data = new Vector<Hashtable<String,Object>>() ;
		  size = 0;
		  
	  }
	public Vector<Hashtable<String, Object>> getData() {
		return data;
	}
	public void setData(Vector<Hashtable<String, Object>> data) {
		this.data = data;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	
	public void insertHashTableINT( Hashtable<String, Object> newHashtable,String pk) {
      //  Hashtable<String, Object> newHashtable = new Hashtable<Integer, String>();
        //newHashtable.put(key, value);
        
        int low = 0;
        int high = this.getData().size() - 1;
        
        while (low <= high) {
            int mid = (low + high) / 2;
            Hashtable<String, Object> midHashtable = this.getData().get(mid);
            int midKey = (int) midHashtable.get(pk);
            int key = (int) newHashtable.get(pk);
            if (midKey < key) {
                low = mid + 1;
            } else if (midKey > key) {
                high = mid - 1;
            } else {
                // Key already exists, do not insert new hashtable
                return;
            }
        }
        
        // Key does not exist, insert new hashtable in correct position
        int insertIndex = low;
        this.getData().add(insertIndex, newHashtable);
        
    }
	public void insertHashTableDOUBLE( Hashtable<String, Object> newHashtable,String pk) {
	      //  Hashtable<String, Object> newHashtable = new Hashtable<Integer, String>();
	        //newHashtable.put(key, value);
	        
	        int low = 0;
	        int high = this.getData().size() - 1;
	        
	        while (low <= high) {
	            int mid = (low + high) / 2;
	            Hashtable<String, Object> midHashtable = this.getData().get(mid);
	            double midKey = (double) midHashtable.get(pk);
	            double key = (double) newHashtable.get(pk);
	            if (midKey < key) {
	                low = mid + 1;
	            } else if (midKey > key) {
	                high = mid - 1;
	            } else {
	                // Key already exists, do not insert new hashtable
	                return;
	            }
	        }
	        
	        // Key does not exist, insert new hashtable in correct position
	        int insertIndex = low;
	        this.getData().add(insertIndex, newHashtable);
	    }
	public void insertHashTableString( Hashtable<String, Object> newHashtable,String pk) {
	      //  Hashtable<String, Object> newHashtable = new Hashtable<Integer, String>();
	        //newHashtable.put(key, value);
	        
	        int low = 0;
	        int high = this.getData().size() - 1;
	        
	        while (low <= high) {
	            int mid = (low + high) / 2;
	            Hashtable<String, Object> midHashtable = this.getData().get(mid);
	            String midKey = (String) midHashtable.get(pk);
	            String key = (String) newHashtable.get(pk);
	            if (midKey.compareTo(key) == -1) {  // midKey < key
	                low = mid + 1;
	            } else if (midKey.compareTo(key) == 1) {   // midKey > key
	                high = mid - 1;
	            } else {
	                // Key already exists, do not insert new hashtable
	                return;
	            }
	        }
	        
	        // Key does not exist, insert new hashtable in correct position
	        int insertIndex = low;
	        this.getData().add(insertIndex, newHashtable);
	    }
	
	public void insertHashTableDate( Hashtable<String, Object> newHashtable,String pk) {
	      //  Hashtable<String, Object> newHashtable = new Hashtable<Integer, String>();
	        //newHashtable.put(key, value);
	        
	        int low = 0;
	        int high = this.getData().size() - 1;
	        
	        while (low <= high) {
	            int mid = (low + high) / 2;
	            Hashtable<String, Object> midHashtable = this.getData().get(mid);
	            String midKey = (String) midHashtable.get(pk);
	            String key = (String) newHashtable.get(pk);
	            
	            LocalDate mIDKEY = LocalDate.parse(midKey) ;
				LocalDate kEY = LocalDate.parse(key) ;
				/*a negative integer if the object being compared is less than the argument    // ely gowa > ely bara   ---> -ve
				zero if the object being compared is equal to the argument
				a positive integer if the object being compared is greater than the argument.*/
	            
	            
	            if (mIDKEY.compareTo(kEY) < 0) {  // midKey < key
	                low = mid + 1;
	            } else if (mIDKEY.compareTo(kEY) > 0) {   // midKey > key
	                high = mid - 1;
	            } else {
	                // Key already exists, do not insert new hashtable
	                return;
	            }
	        }
	        
	        // Key does not exist, insert new hashtable in correct position
	        int insertIndex = low;
	        this.getData().add(insertIndex, newHashtable);
	    }
	  


}

