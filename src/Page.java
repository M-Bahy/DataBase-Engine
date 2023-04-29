import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
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
	
	
	public void insertHashTableINT( Hashtable<String, Object> newHashtable,String pk) throws DBAppException{
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
				System.out.println("THE MID KEY IS : "+midKey+" WHILE THE KEY IS : "+key);
                // Key already exists, do not insert new hashtable
                throw new DBAppException("Record with same primary key already exists");
            }
        }
        
        // Key does not exist, insert new hashtable in correct position
        int insertIndex = low;
        this.getData().add(insertIndex, newHashtable);
        
    }
	public void insertHashTableDOUBLE( Hashtable<String, Object> newHashtable,String pk) throws DBAppException{
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
					throw new DBAppException("Record with same primary key already exists");
	            }
	        }
	        
	        // Key does not exist, insert new hashtable in correct position
	        int insertIndex = low;
	        this.getData().add(insertIndex, newHashtable);
	    }
	public void insertHashTableString( Hashtable<String, Object> newHashtable,String pk) throws DBAppException{
	      //  Hashtable<String, Object> newHashtable = new Hashtable<Integer, String>();
	        //newHashtable.put(key, value);
	        
	        int low = 0;
	        int high = this.getData().size() - 1;
	        
	        while (low <= high) {
	            int mid = (low + high) / 2;
	            Hashtable<String, Object> midHashtable = this.getData().get(mid);
	            String midKey = (String) midHashtable.get(pk);
	            String key = (String) newHashtable.get(pk);
	            if (midKey.toLowerCase().compareTo(key.toLowerCase()) < 0) {  // midKey < key
	                low = mid + 1;
	            } else if (midKey.toLowerCase().compareTo(key.toLowerCase()) > 0) {   // midKey > key
	                high = mid - 1;
	            } else {
	                // Key already exists, do not insert new hashtable
					System.out.println(midKey.toLowerCase().compareTo(key.toLowerCase()));
					throw new DBAppException("Record with same primary key already exists");
	            }
	        }
	        
	        // Key does not exist, insert new hashtable in correct position
	        int insertIndex = low;
	        this.getData().add(insertIndex, newHashtable);
	    }
		private String fixTheDate(Date test) {
			String year = test.getYear()+1900+"";
			String month = test.getMonth()+1+"";
			if (month.length() == 1) {
				month = "0"+month;
			}
			
			String day = test.getDate()+"";
			if (day.length() == 1) {
				day = "0"+day;
			}
			String input = year+"-"+month+"-"+day;
			return input;
		}
	
	public void insertHashTableDate( Hashtable<String, Object> newHashtable,String pk) throws DBAppException{
	      //  Hashtable<String, Object> newHashtable = new Hashtable<Integer, String>();
	        //newHashtable.put(key, value);
	        
	        int low = 0;
	        int high = this.getData().size() - 1;
	        
	        while (low <= high) {
	            int mid = (low + high) / 2;
	            Hashtable<String, Object> midHashtable = this.getData().get(mid);
	            String midKey =  fixTheDate((Date)midHashtable.get(pk));
	            String key =  fixTheDate((Date)newHashtable.get(pk));
				
	          //  YYYY-MM-DD
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
					throw new DBAppException("Record with same primary key already exists");
	            }
	        }
	        
	        // Key does not exist, insert new hashtable in correct position
	        int insertIndex = low;
	        this.getData().add(insertIndex, newHashtable);
	    }
	public Hashtable<String,Object> binarySearchDouble (Double key,String pk){

		int low = 0;
	        int high = this.getData().size() - 1;
	        
	        while (low <= high) {
	            int mid = (low + high) / 2;
	            Hashtable<String, Object> midHashtable = this.getData().get(mid);
	            double midKey = (double) midHashtable.get(pk);
	            //double key = (double) newHashtable.get(pk);
	            if (midKey < key) {
	                low = mid + 1;
	            } else if (midKey > key) {
	                high = mid - 1;
	            } else {
	                // Key already exists, do not insert new hashtable
					return midHashtable;
	            }
	        }

		return null;
	}
	public Hashtable<String,Object> binarySearchInteger (int key,String pk){
		int low = 0;
        int high = this.getData().size() - 1;
        
        while (low <= high) {
            int mid = (low + high) / 2;
            Hashtable<String, Object> midHashtable = this.getData().get(mid);
			System.out.println(pk);
            int midKey = (int) midHashtable.get(pk);
           
            if (midKey < key) {
                low = mid + 1;
            } else if (midKey > key) {
                high = mid - 1;
            } else {
                // Key already exists, do not insert new hashtable
                return midHashtable;
            }
        }
		return null ;
	}
	public Hashtable<String,Object> binarySearchString (String key,String pk){
		int low = 0;
		int high = this.getData().size() - 1;
		
		while (low <= high) {
			int mid = (low + high) / 2;
			Hashtable<String, Object> midHashtable = this.getData().get(mid);
			String midKey = (String) midHashtable.get(pk);
			//String key = (String) newHashtable.get(pk);
			if (midKey.toLowerCase().compareTo(key.toLowerCase()) < 0) {  // midKey < key
				low = mid + 1;
			} else if (midKey.toLowerCase().compareTo(key.toLowerCase()) > 0) {   // midKey > key
				high = mid - 1;
			} else {
				// Key already exists, do not insert new hashtable
				return midHashtable;
			}
		}
		if(this.getData().get(low).get(pk).equals(key))
		  return this.getData().get(low);
		
		return null ;
	}
	public Hashtable<String,Object> binarySearchDate (String key,String pk){
		int low = 0;
		int high = this.getData().size() - 1;
		
		while (low <= high) {
			int mid = (low + high) / 2;
			Hashtable<String, Object> midHashtable = this.getData().get(mid);
			String midKey = fixTheDate((Date) midHashtable.get(pk));
			//String key = (String) newHashtable.get(pk);
			
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
				return midHashtable;
			}
		}
		return null;
	}
}

