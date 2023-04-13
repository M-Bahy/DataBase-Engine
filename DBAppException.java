
public class DBAppException extends Exception {
	
	public DBAppException () {
		super();
	}
	public DBAppException (String s) {
		super(s);
	}
// in creating table : 
	
	// entering duplicated table name
	// non suported data type
	// primary key name not found in the hash table
	// min > max values
	// coloum without min or max 
	 // dataType not the same of that of the min , maximum 
	// primary key not null
	// min and max data types not the same as coulmn data types
	
// in inserting table:
	
	// pk must not exist 
	// data  types must match
	// must be only a single row
	
	
	
	// note :  we will compare tthe pk to determine which row will go the new page (in case of inserting in a full page)
	
}
