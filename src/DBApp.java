import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class DBApp {
	private boolean firstTable = false;
	private int n;

	public static void main(String[] args) throws Exception {

		DBApp db = new DBApp();
		if(false) {
		db.init();
		return;	
		}

		try {
			FileWriter csvWriter = new FileWriter("metadata.csv", true);
			csvWriter.write("bahy,1" + "\n");
			csvWriter.flush();
			csvWriter.close();
		} catch (Exception e) {
			throw new DBAppException("Moshkela fe csv");
		}
		try {
			FileWriter csvWriter = new FileWriter("metadata.csv", true);
			csvWriter.write("bahy,2" + "\n");
			csvWriter.flush();
			csvWriter.close();
		} catch (Exception e) {
			throw new DBAppException("Moshkela fe csv");
		}
		try {
			FileWriter csvWriter = new FileWriter("metadata.csv", true);
			csvWriter.write("bahy,3" + "\n");
			csvWriter.flush();
			csvWriter.close();
		} catch (Exception e) {
			throw new DBAppException("Moshkela fe csv");
		}

		
		  Hashtable<String,String> htblColNameType = new Hashtable<>();
		  Hashtable<String,String> htblColNameMin = new Hashtable<>();
		  Hashtable<String,String> htblColNameMax = new Hashtable<>();
		  
		  htblColNameType.put("id","java.lang.String");
		  htblColNameType.put("name","java.lang.String");
		  htblColNameMin.put("id","1");
		  htblColNameMin.put("name", "a");
		  htblColNameMax.put("id", "4");
		  htblColNameMax.put("name", "z"); 
		  
		  db.createTable("a7a","lol",htblColNameType,htblColNameMin,htblColNameMax);
		  
		  
		  Vector<String> tableNames;
		  
		  try {
		  
		  ObjectInputStream in = new ObjectInputStream(new
		  FileInputStream("tableNames.bin")); tableNames = (Vector<String>)
		  in.readObject(); in.close();
		  
		  } catch(Exception i) { throw new DBAppException(); }
		  
		  Table out; try {
		  
		  ObjectInputStream in = new ObjectInputStream(new
		  FileInputStream("nour.bin")); out = (Table) in.readObject(); in.close();
		  
		  } catch(Exception i) { throw new DBAppException(); }
		  System.out.println(out.toString()); System.out.println(tableNames);
		 

	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void init() {
		this.setN(20);
		
		
		try {
			FileWriter csvWriter = new FileWriter("metadata.csv");
			// Close the writer to save the changes
			csvWriter.close();
			System.out.println("metadata.csv created successfully!");
		} catch (IOException e) {
			System.out.println("An error occurred: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		try {
			this.createTableNamesVector();
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void createTable(String strTableName, String strClusteringKeyColumn,
		Hashtable<String, String> htblColNameType, Hashtable<String, String> htblColNameMin,
		Hashtable<String, String> htblColNameMax) throws DBAppException {
		boolean found = false;
		boolean isGood = false;
		boolean isNew = false;
		Vector<String> tableNames = new Vector<String>();

		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tableNames.bin"));
			tableNames = (Vector<String>) in.readObject();
			in.close();

		} catch (Exception i) {
			throw new DBAppException();
		}

		if (tableNames.contains(strTableName)) {
			throw new DBAppException("Table already exists");
		}
		isNew=true;
		

		Table table = new Table(strTableName, strClusteringKeyColumn);

		
		

		String ans = "";

		for (String s : htblColNameType.keySet()) {

			if (s == strClusteringKeyColumn)
				found = true;
			if (htblColNameMin.containsKey(s) != true || htblColNameMax.containsKey(s) != true) {
				throw new DBAppException("There is no min and max specified for Column: " + s);
			}
			if (!(htblColNameType.get(s) == "java.lang.Integer" || htblColNameType.get(s) == "java.lang.String"
					|| htblColNameType.get(s) == "java.lang.Double" || htblColNameType.get(s) == "java.util.Date"))
				throw new DBAppException("This is data type is not supported");
			if (htblColNameMin.get(s).compareTo(htblColNameMax.get(s)) > 0)
				throw new DBAppException("Min greater than max");
			if (strClusteringKeyColumn == null || strClusteringKeyColumn == "")
				throw new DBAppException("Primary key equals null or Empty");

			ans += strTableName + "," + s + "," + htblColNameType.get(s) + ","
					+ (s == strClusteringKeyColumn ? "True" : "False") + "," + "null" + "," + "null" + ","
					+ htblColNameMin.get(s) + "," + htblColNameMax.get(s) + "\n";

		}
		if (!found)
			throw new DBAppException("The Specified primary key is not in the table");
		
		isGood = true;
		if(isNew&&isGood){
			
			
			
			// 1 : add table name to table names list
			
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
			
			
			
			
			// 2 : create {tableName}.bin file
			
			
			// Serialize table again
			try {
				FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");

				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(table);
				out.close();
				fileOut.close();
			} catch (Exception i) {
				throw new DBAppException("moshkela fe table object");
			}
			
			
			
			
			// 3 : final stage , writing data is csv

		try {
			FileWriter csvWriter = new FileWriter("metadata.csv", true);
			csvWriter.write(ans);
			csvWriter.flush();
			csvWriter.close();
		} catch (Exception e) {
			throw new DBAppException("Moshkela fe csv");
		}
		
		
		
		
		
		}
		
		

	}

	public void createTableNamesVector() throws DBAppException {
		Vector<String> tableNames = new Vector<String>();

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

	/*
	 * { valeu , key } { Integer , id } { id , 50002 },{ name , a } { "name" ,
	 * DFFDZFDF }
	 * 
	 * 
	 * 
	 * { "name" , DFFDZFDF } { "age" ,12 }
	 * 
	 * 
	 * { "name" , DFFDZFDF } AND { "age" ,12 }
	 */

}
