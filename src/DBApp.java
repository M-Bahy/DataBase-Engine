import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;



public class DBApp {
	private  boolean firstTable = false;

	public static void main(String[] args) throws Exception{
		
		  Hashtable<String,String> htblColNameType = new Hashtable<>();
		  Hashtable<String,String> htblColNameMin = new Hashtable<>();
		  Hashtable<String,String> htblColNameMax = new Hashtable<>();

		  htblColNameType.put("id","java.lang.String");
		  htblColNameType.put("name","java.lang.String");
		  htblColNameMin.put("id", "1");
		  htblColNameMin.put("name", "a");
		  htblColNameMax.put("id", "4");
		  htblColNameMax.put("name", "z");
		  DBApp db = new DBApp();
		  
		 db.createTable("nour","id",htblColNameType,htblColNameMin,htblColNameMax);
		  
	
		Vector<String> tableNames;
		
		try {
			
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tableNames.bin"));
			 tableNames = (Vector<String>) in.readObject();
			in.close();
			
			}
			catch(Exception i) {
				throw new DBAppException();
			}
		
		Table out;
	try {
			
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("nour.bin"));
			 out = (Table) in.readObject();
			in.close();
			
			}
			catch(Exception i) {
				throw new DBAppException();
			}
	System.out.println(out.toString());
	System.out.println(tableNames);

	}
	


	public void init() {

		
		
	} 
	public void createTable(String strTableName, String strClusteringKeyColumn,
			Hashtable<String, String> htblColNameType, Hashtable<String, String> htblColNameMin,
			Hashtable<String, String> htblColNameMax) throws DBAppException {
	    boolean found = false;
		Vector<String>colNames = new Vector<String>();
		Vector<String>tableNames = new Vector<String>();
		Set<String> Names = htblColNameType.keySet();
		for(String name:Names) {
			
			if(name ==strClusteringKeyColumn )
				found = true;
			if(htblColNameMin.containsKey(name) != true || htblColNameMax.containsKey(name) != true) {
				throw new DBAppException("There is no min and max specified for Column: "+name);
			}
			if(!(htblColNameType.get(name) == "java.lang.Integer" || htblColNameType.get(name) == "java.lang.String" || htblColNameType.get(name) == "java.lang.Double" || htblColNameType.get(name) == "java.util.Date" ))
				throw new DBAppException("This is data type is not supported");
			if(htblColNameMin.get(name).compareTo(htblColNameMax.get(name))  > 0)
				throw new DBAppException("Min greater than max");
			if(strClusteringKeyColumn == null || strClusteringKeyColumn == "")
				throw new DBAppException("Primary key equals null or Empty");
			
			colNames.add(name);
			
			
			
		}
		if(!found)
			throw new DBAppException("The Specified primary key is not in the table");
		
      if(firstTable == false) {
			this.createTableNamesVector(tableNames);
			firstTable = true;
		}
		
		try {
		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("tableNames.bin"));
		 tableNames = (Vector<String>) in.readObject();
		in.close();
		
		}
		catch(Exception i) {
			throw new DBAppException();
		}
		
		if(tableNames.contains(strTableName)) {
			throw new DBAppException("Table already exists");
		}
		tableNames.add(strTableName);
		
		
		
		
		
		// Serialize table containing names of tables again
		try {
			  FileOutputStream fileOut = new FileOutputStream("tableNames.bin");
		  
		  ObjectOutputStream out = new ObjectOutputStream(fileOut);
		  out.writeObject(tableNames);
		  out.close(); 
		  fileOut.close();
		  } catch (Exception i) {
			 throw new DBAppException("moshkela fe table names");
		  }
		
		
		
		Table table = new Table(strTableName,strClusteringKeyColumn);

		
		//Serialize table again
		try {
			  FileOutputStream fileOut = new FileOutputStream(strTableName+".bin");
		  
		  ObjectOutputStream out = new ObjectOutputStream(fileOut);
		  out.writeObject(table);
		  out.close(); 
		  fileOut.close();
		  } catch (Exception i) {
			 throw new DBAppException("moshkela fe table object");
		  }
		
		
		String ans = "";
		
		for(String s:htblColNameType.keySet()) {
			ans += strTableName+","+s+","+htblColNameType.get(s)+","+(s== strClusteringKeyColumn ? "True" :"False")+","+"null"+","+"null"
		+","  +htblColNameMin.get(s)+","+htblColNameMax.get(s)+"\n";
					
					
			
			
		}
		
		
		try {
		FileWriter csvWriter = new FileWriter("metadata.csv", true);
        csvWriter.write(ans);
        csvWriter.flush();
        csvWriter.close();
		}
		catch(Exception e) {
			throw new DBAppException("Moshkela fe csv");
		}
		
		
		
		
	}
		
	
	public void createTableNamesVector(Vector<String>tableNames) throws DBAppException {
		tableNames = new Vector<String>();
		
		try {
			  FileOutputStream fileOut = new FileOutputStream("tableNames.bin");
		  
		  ObjectOutputStream out = new ObjectOutputStream(fileOut);
		  out.writeObject(tableNames);
		  out.close(); 
		  fileOut.close();
		  } catch (Exception i) {
			 throw new DBAppException("Moshkela fe table names method");
		  }
	}
	
	public void createIndex(String strTableName, String[] strarrColName) throws DBAppException {
	}

	
	public void insertIntoTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
	}

	public void updateTable(String strTableName, String strClusteringKeyValue,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {
	}

	
	public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
	}

	public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) throws DBAppException {
		return null;
	}
	
	/*{ valeu , key   }
	{ Integer , id   }
	{ id , 50002   },{ name , a   }
	{ "name" , DFFDZFDF   }
	
	
	
	{ "name" , DFFDZFDF    }
	{ "age" ,12   }
	
	
	{ "name" , DFFDZFDF    }  AND	{ "age" ,12   }*/
	
	
	
	

}
