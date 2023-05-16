import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;
import java.time.LocalDate;
import java.lang.reflect.Array;
import java.util.Properties;

public class DBApp {

	// We worked on creating an index for a populated table.

	// Problem: all Objects used when creating the Octree where Strings since they came 
	// from the serialized data (Doubles were considered String causing the split() method to malfunction (instance of))

	// Solution: added method checkStringType to return the right type of Object to use in the Octree Creation.

	// To Be Fixed: the checkStringType method MISSING Date checking 

	// Check OctreeNode Class for more changes

	private boolean firstTable = false;
	private static int n;
	private static int m;
	private static String thePK = "";
	private final static String theString = "java.lang.String";
	private final static String theDouble = "java.lang.Double";
	private final static String theDate = "java.util.Date";
	private final static String theInt = "java.lang.Integer";
	private static Vector<Octree> ocs = new Vector<Octree>();
	public DBApp () {
		try{
		ocs = (Vector<Octree>)	deserialize("Octrees");
		
		}
		catch(Exception e) {
			
		}
	}

	public static void main(String[] args) throws Exception {
		DBApp dbApp = new DBApp();
		// dbApp.init();
		// createTheTables(dbApp);
		// dbApp.insertStudentRecords(dbApp, 6);
		// printData();
		// String[] index = new String[3];
		// index[0] = "id";
		// index[1] = "gpa";
		// index[2] = "first_name";
		// dbApp.createIndex("students", index);
	//	printData();
		//System.out.println(ocs.get(0));

		// Vector<Octree> ocs = (Vector<Octree>)	deserialize("Octrees");
	//	System.out.println(ocs.get(0));
		// String tableName, String columnName, String operator, Object value
		// X: 81-8976 Y: 3.19 Z: hWknCP
		//System.out.println(ocs.size());

		//X: 50-7952 Y: 3.12 Z: ZiDDlx
		SQLTerm s1 = new SQLTerm("students", "id", "=", "82-8772");
		SQLTerm s2 = new SQLTerm("students", "gpa", "=", 4.32);
		SQLTerm s3 = new SQLTerm("students", "first_name", "=", "pzSMNq");
		SQLTerm s4 = new SQLTerm("students", "last_name", "=", "EGpfuC");
		//82-8772,pzSMNq,NfdxAL,1992-09-28,4.32
		SQLTerm[] arrSQLTerms = new SQLTerm[4];
		arrSQLTerms[0] = s1;
		arrSQLTerms[1] = s2;
		arrSQLTerms[2] = s3;
		arrSQLTerms[3] = s4;
		String[] strarrOperators = new String[3];
		strarrOperators[0] = "OR";
		strarrOperators[1] = "AND";
		strarrOperators[2] = "AND";
		Iterator resultSet = dbApp.selectFromTable(arrSQLTerms, strarrOperators);
		//System.out.println(resultSet.);
		System.out.println("ANA HENA 555555555555555555555555555555555");
		while (resultSet.hasNext()) {
			
			System.out.println(resultSet.next());
			//System.out.println("ANA HENA 555555555555555555555555555555555");
		}
		// System.out.println("ANA HENA 555555555555555555555555555555555");

		//printData();

			//System.out.println(ocs.size());



		//dbApp.selectFromTable(null, args)
		
		

	}
	private static void createTheTables(DBApp dbApp) throws Exception {
		createStudentTable(dbApp);
        createCoursesTable(dbApp);
        createTranscriptsTable(dbApp);
        createPCsTable(dbApp);
	}
	private void insertStudentRecords(DBApp dbApp, int limit) throws Exception {
        BufferedReader studentsTable = new BufferedReader(new FileReader("students_table.csv"));
        String record;
        int c = limit;
        if (limit == -1) {
            c = 1;
        }

        Hashtable<String, Object> row = new Hashtable<>();
        while ((record = studentsTable.readLine()) != null && c > 0) {
            String[] fields = record.split(",");

            row.put("id", fields[0]);
            row.put("first_name", fields[1]);
            row.put("last_name", fields[2]);

            int year = Integer.parseInt(fields[3].trim().substring(0, 4));
            int month = Integer.parseInt(fields[3].trim().substring(5, 7));
            int day = Integer.parseInt(fields[3].trim().substring(8));

            Date dob = new Date(year - 1900, month - 1, day);
            row.put("dob", dob);

            double gpa = Double.parseDouble(fields[4].trim());

            row.put("gpa", gpa);

			if(fields[0].equals("48-8527")){
				System.out.println("here");
			}


            dbApp.insertIntoTable("students", row);
            row.clear();
            if (limit != -1) {
                c--;
            }
        }
        studentsTable.close();
    }

    private void insertCoursesRecords(DBApp dbApp, int limit) throws Exception {
        BufferedReader coursesTable = new BufferedReader(new FileReader("courses_table.csv"));
        String record;
        Hashtable<String, Object> row = new Hashtable<>();
        int c = limit;
        if (limit == -1) {
            c = 1;
        }
        while ((record = coursesTable.readLine()) != null && c > 0) {
            String[] fields = record.split(",");


            int year = Integer.parseInt(fields[0].trim().substring(0, 4));
            int month = Integer.parseInt(fields[0].trim().substring(5, 7));
            int day = Integer.parseInt(fields[0].trim().substring(8));

            Date dateAdded = new Date(year - 1900, month - 1, day);

            row.put("date_added", dateAdded);

            row.put("course_id", fields[1]);
            row.put("course_name", fields[2]);
            row.put("hours", Integer.parseInt(fields[3]));

            dbApp.insertIntoTable("courses", row);
            row.clear();

            if (limit != -1) {
                c--;
            }
        }

        coursesTable.close();
    }

    private void insertTranscriptsRecords(DBApp dbApp, int limit) throws Exception {
        BufferedReader transcriptsTable = new BufferedReader(new FileReader("transcripts_table.csv"));
        String record;
        Hashtable<String, Object> row = new Hashtable<>();
        int c = limit;
        if (limit == -1) {
            c = 1;
        }
        while ((record = transcriptsTable.readLine()) != null && c > 0) {
            String[] fields = record.split(",");

            row.put("gpa", Double.parseDouble(fields[0].trim()));
            row.put("student_id", fields[1].trim());
            row.put("course_name", fields[2].trim());

            String date = fields[3].trim();
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8));

            Date dateUsed = new Date(year - 1900, month - 1, day);
            row.put("date_passed", dateUsed);

            dbApp.insertIntoTable("transcripts", row);
            row.clear();

            if (limit != -1) {
                c--;
            }
        }

        transcriptsTable.close();
    }

    private void insertPCsRecords(DBApp dbApp, int limit) throws Exception {
        BufferedReader pcsTable = new BufferedReader(new FileReader("pcs_table.csv"));
        String record;
        Hashtable<String, Object> row = new Hashtable<>();
        int c = limit;
        if (limit == -1) {
            c = 1;
        }
        while ((record = pcsTable.readLine()) != null && c > 0) {
            String[] fields = record.split(",");

            row.put("pc_id", Integer.parseInt(fields[0].trim()));
            row.put("student_id", fields[1].trim());

            dbApp.insertIntoTable("pcs", row);
            row.clear();

            if (limit != -1) {
                c--;
            }
        }

        pcsTable.close();
    }

    private static void createStudentTable(DBApp dbApp) throws Exception {
        // String CK
        String tableName = "students";

        Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
        htblColNameType.put("id", "java.lang.String");
        htblColNameType.put("first_name", "java.lang.String");
        htblColNameType.put("last_name", "java.lang.String");
        htblColNameType.put("dob", "java.util.Date");
        htblColNameType.put("gpa", "java.lang.Double");

        Hashtable<String, String> minValues = new Hashtable<>();
        minValues.put("id", "43-0000");
        minValues.put("first_name", "AAAAAA");
        minValues.put("last_name", "AAAAAA");
        minValues.put("dob", "1990-01-01");
        minValues.put("gpa", "0.7");

        Hashtable<String, String> maxValues = new Hashtable<>();
        maxValues.put("id", "99-9999");
        maxValues.put("first_name", "zzzzzz");
        maxValues.put("last_name", "zzzzzz");
        maxValues.put("dob", "2000-12-31");
        maxValues.put("gpa", "5.0");

        dbApp.createTable(tableName, "id", htblColNameType, minValues, maxValues);
    }


    private static void createCoursesTable(DBApp dbApp) throws Exception {
        // Date CK
        String tableName = "courses";

        Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
        htblColNameType.put("date_added", "java.util.Date");
        htblColNameType.put("course_id", "java.lang.String");
        htblColNameType.put("course_name", "java.lang.String");
        htblColNameType.put("hours", "java.lang.Integer");


        Hashtable<String, String> minValues = new Hashtable<>();
        minValues.put("date_added", "1901-01-01");
        minValues.put("course_id", "0000");
        minValues.put("course_name", "AAAAAA");
        minValues.put("hours", "1");

        Hashtable<String, String> maxValues = new Hashtable<>();
        maxValues.put("date_added", "2020-12-31");
        maxValues.put("course_id", "9999");
        maxValues.put("course_name", "zzzzzz");
        maxValues.put("hours", "24");

        dbApp.createTable(tableName, "date_added", htblColNameType, minValues, maxValues);

    }

    private static void createTranscriptsTable(DBApp dbApp) throws Exception {
        // Double CK
        String tableName = "transcripts";

        Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
        htblColNameType.put("gpa", "java.lang.Double");
        htblColNameType.put("student_id", "java.lang.String");
        htblColNameType.put("course_name", "java.lang.String");
        htblColNameType.put("date_passed", "java.util.Date");

        Hashtable<String, String> minValues = new Hashtable<>();
        minValues.put("gpa", "0.7");
        minValues.put("student_id", "43-0000");
        minValues.put("course_name", "AAAAAA");
        minValues.put("date_passed", "1990-01-01");

        Hashtable<String, String> maxValues = new Hashtable<>();
        maxValues.put("gpa", "5.0");
        maxValues.put("student_id", "99-9999");
        maxValues.put("course_name", "zzzzzz");
        maxValues.put("date_passed", "2020-12-31");

        dbApp.createTable(tableName, "gpa", htblColNameType, minValues, maxValues);
    }


    private static void createPCsTable(DBApp dbApp) throws Exception {
        // Integer CK
        String tableName = "pcs";

        Hashtable<String, String> htblColNameType = new Hashtable<String, String>();
        htblColNameType.put("pc_id", "java.lang.Integer");
        htblColNameType.put("student_id", "java.lang.String");


        Hashtable<String, String> minValues = new Hashtable<>();
        minValues.put("pc_id", "0");
        minValues.put("student_id", "43-0000");

        Hashtable<String, String> maxValues = new Hashtable<>();
        maxValues.put("pc_id", "20000");
        maxValues.put("student_id", "99-9999");

        dbApp.createTable(tableName, "pc_id", htblColNameType, minValues, maxValues);
    }
	public static void fixTheRanges (String tableName,String pk) throws DBAppException{
		Vector<Table> tables = (Vector<Table>) deserialize(tableName);
		Table t = tables.get(0);
		Vector<Page> pp;
		Page p;
		for (int i =0 ;i<t.getIds().size();i++){
			 pp = (Vector<Page>) deserialize(tableName+"Page"+t.getIds().get(i));
			 p = pp.get(0);
			t.getRange().get(i).setMin(p.getData().get(0).get(pk));
			t.getRange().get(i).setMax(p.getData().get(p.getData().size()-1).get(pk));
		}
		serializeTable(tables, tableName);
		tables=null;
		t=null;
		pp=null;
	
		p=null;
		//System.gc();
	}

	private static void printData() throws DBAppException {
		Vector<Table> tt = (Vector<Table>) deserialize("students");
		Table t = tt.get(0);
		System.out.println( "Total number of Pages : " + t.getIds().size());
		System.out.println();
		for(String s:t.getIds()){
			System.out.println("The ID: "+ s);
			Vector<Page> pages = (Vector<Page>)  deserialize("studentsPage"+s);
			Page p1 = pages.get(0);
			System.out.println("Page "+s+" Data : ");
			System.out.println(p1.getData());
			System.out.println();
		}
		//System.out.println("n5las");
	}

	private static void displayThe2Pages() throws DBAppException {
		Vector<Page> pages = (Vector<Page>)  deserialize("dumbTablePage1");
		Page p1 = pages.get(0);
		System.out.println(p1.getData());
		System.out.println();
		Vector<Page> pagess = (Vector<Page>)  deserialize("dumbTablePage2");
		Page p11 = pagess.get(0);
		System.out.println(p11.getData());
	}

	private static void insertDummyData(DBApp db, int id, int number, String string, String date , Double d)
			throws ParseException, DBAppException {
		Hashtable<String,Object> h4 = new Hashtable<String,Object>();
		h4.put("id", id);
		h4.put("testInteger", number);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date elDate = dateFormat.parse(date);
		h4.put("testDouble", d);
		h4.put("testString", string);
		h4.put("testDate", elDate);

		db.insertIntoTable("dumbTable", h4);
	}

	private static void createDummyData(DBApp db) throws DBAppException {
		Hashtable<String,String> h1 = new Hashtable<String,String>();
		h1.put("id", theInt);
		h1.put("testInteger", theInt);
		h1.put("testDouble", theDouble);
		h1.put("testString", theString);
		h1.put("testDate", theDate);

		Hashtable<String,String> h2 = new Hashtable<String,String>();
		h2.put("id", "1");
		h2.put("testInteger", "0");
		h2.put("testDouble", "0.0");
		h2.put("testString", "A");
		h2.put("testDate", "2002-04-29");

		Hashtable<String,String> h3 = new Hashtable<String,String>();
		h3.put("id", "20");
		h3.put("testInteger", "1000");
		h3.put("testDouble", "1000.0");
		h3.put("testString", "ZZZZZZZZZZZZ");
		h3.put("testDate", "2023-04-29");

		db.createTable("dumbTable", "id", h1, h2, h3);
	}

	public static int getN() throws DBAppException {
		//return n;

		Properties props = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("src/resources/DBApp.config");
            props.load(fis);

            // Access properties by key
            
            int n = Integer.parseInt(props.getProperty("MaximumRowsCountinTablePage"));
			int m = Integer.parseInt(props.getProperty("MaximumEntriesinOctreeNode"));
            // Do something with properties
           setM(m);
		   setN(n);
		   System.out.println("Config file read successfully.");
		   // print m and n 
		   //System.out.println("M : " + getM());
		  // System.out.println("N : " + n);
        } catch (IOException e) {
			throw new DBAppException();
		} finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new DBAppException();
                }
            }
        }
		fis=null;
		return n;

	}

	public static void setN(int k) {
		n = k;
	}

	public static int getM() throws DBAppException {
		Properties props = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("src/resources/DBApp.config");
            props.load(fis);

            // Access properties by key
            
            int n = Integer.parseInt(props.getProperty("MaximumRowsCountinTablePage"));
			int m = Integer.parseInt(props.getProperty("MaximumEntriesinOctreeNode"));
            // Do something with properties
           setM(m);
		   setN(n);
		   System.out.println("Config file read successfully.");
		   // print m and n 
		   //System.out.println("M : " + getM());
		  // System.out.println("N : " + n);
        } catch (IOException e) {
			throw new DBAppException();
		} finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new DBAppException();
                }
            }
        }
		fis=null;
		return m;
	}

	public static void setM(int k) {
		m = k;
	}

	public void init() throws DBAppException {
		//this.setN(2);
		//this.setM(2);
		Properties prop = new Properties();
        OutputStream output = null;
		try {
		output = new FileOutputStream("src/resources/DBApp.config");

            // set the properties value
            prop.setProperty("MaximumRowsCountinTablePage", "5");
            prop.setProperty("MaximumEntriesinOctreeNode", "2");
            

            // save properties to project root folder
            prop.store(output, null);

            System.out.println("Config file created successfully.");

        } catch (IOException io) {
            throw new DBAppException();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    throw new DBAppException();
                }
            }
        }
		output=null;
		Properties props = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("src/resources/DBApp.config");
            props.load(fis);

            // Access properties by key
            
            int n = Integer.parseInt(props.getProperty("MaximumRowsCountinTablePage"));
			int m = Integer.parseInt(props.getProperty("MaximumEntriesinOctreeNode"));
            // Do something with properties
           this.setM(m);
		   this.setN(n);
		   System.out.println("Config file read successfully.");
		   // print m and n 
		   System.out.println("M : " + this.getM());
		   System.out.println("N : " + this.getN());
        } catch (IOException e) {
            throw new DBAppException();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new DBAppException();
                }
            }
        }
		fis=null;



		
		FileWriter csvWriter;
		try {
			 csvWriter = new FileWriter("metadata.csv");
			// Close the writer to save the changes
			csvWriter.close();
			System.out.println("metadata.csv created successfully!");
		} catch (IOException e) {
			//System.out.println("An error occurred: " + e.getMessage());
			throw new DBAppException();
		}
		csvWriter=null;
		
		try {
			this.createTableNamesVector();
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			throw new DBAppException();
		}
		
		
		// write n in csv
		try {
			 csvWriter = new FileWriter("metadata.csv", true);
			csvWriter.write(Integer.toString(this.getN()) + "\n");
			csvWriter.flush();
			csvWriter.close();
		} catch (Exception e) {
			throw new DBAppException();
		}
		
		
		
	
		
		
		
		
		
		
		
		
		
		csvWriter=null;
		System.gc();

	}

	public void createTable(String strTableName, String strClusteringKeyColumn,
		Hashtable<String, String> htblColNameType, Hashtable<String, String> htblColNameMin,
		Hashtable<String, String> htblColNameMax) throws DBAppException {
		boolean found = false;
		boolean isGood = false;
		boolean isNew = false;
		Vector<String> tableNames = new Vector<String>();
		ObjectInputStream in;
		try {

			 in = new ObjectInputStream(new FileInputStream("tableNames.bin"));
			tableNames = (Vector<String>) in.readObject();
			in.close();

		} catch (Exception i) {
			
			throw new DBAppException();
		}
		in=null;

		if (tableNames.contains(strTableName)) {
			throw new DBAppException("Table already exists");
		}
		isNew=true;
		

		//Table table = new Table(strTableName, strClusteringKeyColumn);

		
		

		String ans = "";

		for (String s : htblColNameType.keySet()) {

			if (s == strClusteringKeyColumn)
				found = true;
			if (htblColNameMin.containsKey(s) != true || htblColNameMax.containsKey(s) != true) {
				throw new DBAppException("There is no min and max specified for Column: " + s);
			}
			if ((htblColNameType.get(s).toLowerCase()).compareTo(theInt.toLowerCase()) != 0 && (htblColNameType.get(s).toLowerCase()).compareTo(theString.toLowerCase()) != 0
					&& (htblColNameType.get(s).toLowerCase()).compareTo(theDouble.toLowerCase()) != 0 && (htblColNameType.get(s).toLowerCase()).compareTo(theDate.toLowerCase()) != 0){
						System.out.println(htblColNameType.get(s));
				throw new DBAppException("This is data type is not supported");
					}
			if (htblColNameMin.get(s).toLowerCase().compareTo(htblColNameMax.get(s).toLowerCase()) > 0)
				throw new DBAppException("Min greater than max");
			if (strClusteringKeyColumn == null || strClusteringKeyColumn.compareTo("") == 0)
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
			FileOutputStream fileOut;
			ObjectOutputStream out;
			// Serialize table containing names of tables again
			try {
				 fileOut = new FileOutputStream("tableNames.bin");

				 out = new ObjectOutputStream(fileOut);
				out.writeObject(tableNames);
				out.close();
				fileOut.close();
			} catch (Exception i) {
				throw new DBAppException("moshkela fe table names");
			}
			tableNames=null;
			fileOut=null;
			out=null;
			
			
			
			// 2 : create {tableName}.bin file
			Vector<Table> p;
			Table t;
			// Serialize table again
			try {
				//Vector<String> p = new Vector<String>();
				//  {1,2,3,4,8}  { () , () }
				 p = new Vector<Table>();
				 t = new Table (strTableName);
				p.add(t);
				 fileOut = new FileOutputStream(strTableName + ".bin");
				 out = new ObjectOutputStream(fileOut);
				out.writeObject(p);
				out.close();
				fileOut.close();
				
				
			} catch (Exception i) {
				throw new DBAppException("moshkela fe table object");
			}
			p=null;
			t=null;
			fileOut=null;
			out=null;
			
			
			
			// 3 : final stage , writing data is csv
			FileWriter csvWriter;
		try {
			 csvWriter = new FileWriter("metadata.csv", true);
			csvWriter.write(ans);
			csvWriter.flush();
			csvWriter.close();
		} catch (Exception e) {
			throw new DBAppException("Moshkela fe csv");
		}
		
		csvWriter=null;
		
		
		
		}
		
		
		System.gc();
	}

	public void createTableNamesVector() throws DBAppException {
		Vector<String> tableNames = new Vector<String>();
		FileOutputStream fileOut;
		ObjectOutputStream out;
		try {
			 fileOut = new FileOutputStream("tableNames.bin");

			 out = new ObjectOutputStream(fileOut);
			out.writeObject(tableNames);
			out.close();
			fileOut.close();
		} catch (Exception i) {
			throw new DBAppException("Moshkela fe table names method");
		}
		fileOut=null;
		out=null;
		System.gc();
	}

	public static void updateMetaData( int rowIndexToEdit , String indexName) {
        String csvFile = "metadata.csv";
        String line = "";
        String delimiter = ",";
        List<String[]> rows = new ArrayList<>();

        // Step 1: Read the CSV file and store its contents in a list
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] row = line.split(delimiter);
                rows.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Step 2: Locate the row that needs to be edited
       /* int rowIndexToEdit = -1;
        for (int i = 0; i < rows.size(); i++) {
            String[] row = rows.get(i);
            if (row[0].equals("id123")) {
                rowIndexToEdit = i;
                break;
            }
        }

        if (rowIndexToEdit == -1) {
            System.out.println("Row not found");
            return;
        }*/

        // Step 3: Edit the row's values
        String[] rowToEdit = rows.get(rowIndexToEdit);
        /*rowToEdit[1] = "new value 1";
        rowToEdit[2] = "new value 2";*/
		rowToEdit[4] = indexName;
		rowToEdit[5]= "Octree"; 
        // ...

        // Step 4: Write the updated data structure back to the CSV file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            for (String[] row : rows) {
                String rowString = String.join(delimiter, row);
                bw.write(rowString);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




	public void createIndex(String strTableName, String[] strarrColName) throws DBAppException {
		if(strarrColName.length != 3) {
			throw new DBAppException("You can only index 3 columns");
		}

		String [] col1 = new String[4];
		String [] col2 = new String[4];
		String [] col3 = new String[4];
		col1[0]=strarrColName[0];
		col2[0]=strarrColName[1];
		col3[0]=strarrColName[2];
		String indexName = col1[0] +  col2[0]  + col3[0]+"Index";
		//BufferedReader br;
		try {
			BufferedReader br;
			String line;
			checkIfValidOctree(strTableName, strarrColName, col1, col2, col3, indexName);
			writeToMetaData(strTableName, col1, col2, col3, indexName);
			// Name , Type , Min , Max
		}
		catch(Exception e) {
			throw new DBAppException(e.getMessage());
		}
		// create the Octree
		//Octree octree = new Octree(col1[2], col2[2], col3[2], col1[3], col2[3],col3[3]);
		//Octree octree = new Octree(1, 1, 1, 3, 3,3);
		String [] colNames = new String [3];
		colNames[0] = col1[0];
		colNames[1] = col2[0];
		colNames[2] = col3[0];
		
		// Object x = checkStringType(col1[2]);
		// Object width = checkStringType(col1[3]);
		// ("id",5)
		// Object y = checkStringType(col2[2]);
		// Object height = checkStringType(col2[3]);

		// Object z = checkStringType(col3[2]);
		// Object depth = checkStringType(col3[3]);
		Object x = "";
		Object width = "";
		Object y = "";
		Object height = "";
		Object z = "";
		Object depth = "";
		switch(col1[1]){
			case "java.lang.Integer":
				x = Integer.parseInt(col1[2]);
				width = Integer.parseInt(col1[3]);
				break;
			case "java.lang.Double":
				x = Double.parseDouble(col1[2]);
				width = Double.parseDouble(col1[3]);
				break;
			case "java.util.Date":
				x = new Date(col1[2]);
				width = new Date(col1[3]);
				break;
			case "java.lang.String":
				x = col1[2];
				width = col1[3];
				break;
			
		}
		switch(col2[1]){
			case "java.lang.Integer":
				y = Integer.parseInt(col2[2]);
				height = Integer.parseInt(col2[3]);
				break;
			case "java.lang.Double":
				y = Double.parseDouble(col2[2]);
				height = Double.parseDouble(col2[3]);
				break;
			case "java.util.Date":
				y = new Date(col2[2]);
				height = new Date(col2[3]);
				break;
			case "java.lang.String":
				y = col2[2];
				height = col2[3];
				break;
			
		}
		switch(col3[1]){
			case "java.lang.Integer":
				z = Integer.parseInt(col3[2]);
				depth = Integer.parseInt(col3[3]);
				break;
			case "java.lang.Double":
				z = Double.parseDouble(col3[2]);
				depth = Double.parseDouble(col3[3]);
				break;
			case "java.util.Date":
				z = new Date(col3[2]);
				depth = new Date(col3[3]);
				break;
			case "java.lang.String":
				z = col3[2];
				depth = col3[3];
				break;
			
		}



		
		Octree octree = new Octree(x, y, z, width, height, depth, indexName, colNames, strTableName);
		
		
		// Sadwat, Ibrahim, Nour code starts here
		Vector<Table> tables = (Vector<Table>) deserialize(strTableName);
		if(tables.get(0).getIds().isEmpty()) {
			serializeOctree(octree);
			ocs = (Vector<Octree>) deserialize("Octrees");
			return;
		};

		Table table = tables.get(0);
		Vector<String> ids = table.getIds();
		
		for(int i = 0; i < ids.size(); i++){
		String pageId = ids.get(i);
		Vector<Page> pages =  (Vector<Page>) deserialize(strTableName+"Page"+ pageId);
		Page page = pages.get(0);
		Vector<   Hashtable<String,Object>   > data = page.getData();
		for(int j = 0; j < data.size(); j++){
			Object x1 = data.get(j).get(col1[0]);
			Object y1 = data.get(j).get(col2[0]);
			Object z1 = data.get(j).get(col3[0]);
			String pkName = "";
			Object pkValue = "";

			try {
				BufferedReader	br = new BufferedReader(new FileReader("metadata.csv"));
				String line = br.readLine();

				while ((line = br.readLine()) != null) {
					String[] values = line.split(",");
					if (values[0].equals(strTableName)) {
						// Table Name, Column Name, Column Type, ClusteringKey, IndexName,IndexType, min, max
						if (values[3].equals("True")) {
							pkName = values[1];
							
						}
					}
				}
			} catch (Exception e) {
				
			}

			pkValue = data.get(j).get(pkName);


			Reference reference = new Reference(Integer.parseInt(pageId), pkName, pkValue);

			Tuple OctTuple = new Tuple(x1, y1, z1, reference);
			octree.insert(OctTuple);
		}
	}
	serializeOctree(octree);
	ocs = (Vector<Octree>) deserialize("Octrees");

	}

	private Object checkStringType(String theString){

		// Check Date is missing
		
		
		Object x = theString;
		try {
			x = Integer.parseInt(theString);
		} catch (NumberFormatException e) {
			try{
			x = Double.parseDouble(theString);
			return x;
			}
			catch(NumberFormatException e1){
				return x;
			}
		}

		return x;
	}


	public void writeToMetaData(String strTableName, String[] col1, String[] col2, String[] col3, String indexName)
			throws FileNotFoundException, IOException {
		BufferedReader br;
		String line;
		br = new BufferedReader(new FileReader("metadata.csv"));
		line = br.readLine();
		int i = 0 ;
		while ((line = br.readLine()) != null) {
			//Table Name, Column Name, Column Type, ClusteringKey, IndexName,IndexType, min, max
			String[] values = line.split(",");
			i++;
			if(values[0].equals(strTableName)){
				if(values[1].equals(col1[0]) || values[1].equals(col2[0]) || values[1].equals(col3[0])){
					
				updateMetaData(i, indexName);

				}
			}
		
		}
	}
	public void checkIfValidOctree(String strTableName, String[] strarrColName, String[] col1, String[] col2, String[] col3,
			String indexName) throws FileNotFoundException, IOException, DBAppException {
		BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
		String line = br.readLine();
		int numberOfCorrectData = 0;

		if(!exists(strTableName))
		     throw new DBAppException();
		    
		while ((line = br.readLine()) != null) {
			//Table Name, Column Name, Column Type, ClusteringKey, IndexName,IndexType, min, max
			String[] values = line.split(",");
			if(values[0].equals(strTableName)){
				
				String colName = values[1];
				for(int i = 0; i < strarrColName.length; i++) {
					if(strarrColName[i].equals(colName)) {
						if(colName.equals(col1[0])){
							col1[1] = values[2];
							col1[2]=values[6];
							col1[3]=values[7];
							if(!values[4].equals("null")){
								throw new DBAppException("Index already exists");
							}
						}
						if(colName.equals(col2[0])){
							col2[1] = values[2];
							col2[2]=values[6];
							col2[3]=values[7];
							if(!values[4].equals("null")){
								throw new DBAppException("Index already exists");
							}
						}
						if(colName.equals(col3[0])){
							col3[1] = values[2];
							col3[2]=values[6];
							col3[3]=values[7];
							if(!values[4].equals("null")){
								throw new DBAppException("Index already exists");
							}
						}
						numberOfCorrectData++;
					}
				}
			}

		}
		if (numberOfCorrectData != 3){
			throw new DBAppException("Columns");
		}
		br.close();
	}

	public void insertIntoTable2(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {     
		// check if table exit
		Vector<String> tableNames = new Vector<String>();
		String pkBahy = "";
		Object pkValue = "";
		int pageNumberForIndex = 0;
		boolean octreeExists = false;
		ArrayList<String> octreeName = new ArrayList<String>();
		//String [] cols = new String[3];
		boolean [] condition = new boolean [htblColNameValue.keySet().size()];
		int count = 0;
		ObjectInputStream in;
		try {

			 in = new ObjectInputStream(new FileInputStream("tableNames.bin"));
			tableNames = (Vector<String>) in.readObject();
			in.close();

		} catch (Exception i) {
			throw new DBAppException();
		}
		in=null;
		if(!tableNames.contains(strTableName)) {
			System.out.println(strTableName);
			throw new DBAppException("Table doesn't exit");
		}
		tableNames=null;
		BufferedReader br;
		try {
			 br = new BufferedReader(new FileReader("metadata.csv"));
			String line = br.readLine();
			int N = getN();
			String pk = "";
			String dataType = "";
			boolean isClustering = false;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(values[0].equals(strTableName)) {
					if(!values[4].equals("null")){
						octreeExists = true;
						octreeName.add( values[4]);
						
					}
                	// we got the table
                if (htblColNameValue.containsKey(values[1]))	{
                	condition[count] = true;
                	count ++;
                	
                	
                	
                	
                	// we checked the coloumn exists
                	int variable = 1;
                	switch (values[2]) {
                	case theInt :
                	
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Integer ) {
                			variable = 0;
                			// 6 min      7  max
                        	if(    (Integer.parseInt(values[6]))   >   ((int)  (htblColNameValue.get(values[1])))           
                        			||   (Integer.parseInt(values[7]))   <   ((int)  (htblColNameValue.get(values[1])))    )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                		}
                		
                		break;
                		    
                	
                	
                	case theString :   
                		if(  (htblColNameValue.get(values[1]) ) instanceof  String ) {
                			variable = 0;
                			
                			if(    (  values[6].toLowerCase().compareTo( ( (String)     htblColNameValue.get(values[1])       ).toLowerCase() )     == 1         
                        			||   values[7].toLowerCase().compareTo(((String)htblColNameValue.get(values[1])).toLowerCase())   == -1     )  )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                			
                		}
                		
                		break;
                	case theDouble : 
                		
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Double ) {
                			variable = 0;
                			
                			
                			if(    (Double.parseDouble(values[6]))   >   ((Double)  (htblColNameValue.get(values[1])))           
                        			||   (Double.parseDouble(values[7]))   <   ((Double)  (htblColNameValue.get(values[1])))    )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                			
                		}
                		
                		break;
                	case theDate :   
                		
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Date ) {
                			variable = 0;
                 			// law rg3t -1 then ely bara is the earlier one
                			// law rag3t 1 then ely bara is later 
                			
                			String x = values[6];  // min
                			String y = values[7]; //max
                			Object i = htblColNameValue.get(values[1]);
                			LocalDate dMIN = LocalDate.parse(x) ;
                			LocalDate dMAX = LocalDate.parse(y) ;
							Date test = (Date) i;
							
							//System.out.println("The test : " + test);
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String testingDate = dateFormat.format(test);
							//System.out.println("The String : " + testingDate);

                			LocalDate theInput = LocalDate.parse(( testingDate   )) ;
                			
                			int notPos = dMIN.compareTo(theInput)  ;  // not +ve
                			int notNeg =  dMAX.compareTo(theInput)  ;  // not -ve
                			if(notPos>0 || notNeg<0)
                				throw new DBAppException("Data is not within the domain of the coloumn !");
                			
                		}
                		
                		
                		break;
                	
                	}
                	
                	if(variable==1) {
                		throw new DBAppException("Data entered doenst match data type in the table");
                	}
                	// we made sure data type matches   and all is good 
					//change2
                	if(values[3].compareTo("True") == 0 && htblColNameValue.get(values[1]) != null ) { 
						// instead of == "True", use compareto(String)
                		isClustering = true;
                	    pk = values[1];
						pkBahy=values[1];
						thePK = pk;
                	    dataType = values[2];
                	}
                
                	
                	
                   
                	
                	
                	
                }
                  }    
                
            }
            if (!isClustering)
            	throw new DBAppException("No value for the Primary key was found !");
            
            br.close();
            
            if(!condition[condition.length-1]) {
            	throw new DBAppException("An extra invalid column was entered");
            }


			addTheNulls(htblColNameValue,strTableName);
			
			//insertIntoIndex(htblColNameValue, octreeExists, octreeName ,);


            
			// change1
        	Vector<Table> p = (Vector<Table>) deserialize(strTableName); // without + ".bin
        	Table t = p.get(0);
			p.remove(t);
        	if(t.getIds().isEmpty()) {
        		Page p1 = new Page();
				

				// put it here




        		p1.getData().add(htblColNameValue);
        		p1.setSize(p1.getSize()+1);
        		Vector<Page> v = new Vector<Page>();
        		v.add(p1);
        		// serialize p1
        		serialize(v, (strTableName+"Page"+1));
        		t.getIds().add("1");
				// System.out.println(t.getIds().get(0));
        		t.getRange().add(new Pair (htblColNameValue.get(pk),htblColNameValue.get(pk)));
				System.out.println("The min is : "+t.getRange().get(0).getMin()+" The max is : "+t.getRange().get(0).getMax());
				FileOutputStream fileOut;
				ObjectOutputStream out;
				try {
					//Vector<String> p = new Vector<String>();
					//  {1,2,3,4,8}  { () , () }
					p = new Vector<Table>();
					//Table t = new Table (strTableName);
					p.add(t);
					 fileOut = new FileOutputStream(strTableName + ".bin");
					 out = new ObjectOutputStream(fileOut);
					out.writeObject(p);
					out.close();
					fileOut.close();
					updateOctreeAfterInserting(htblColNameValue, pkBahy, octreeExists, octreeName,1);
					
					
				} catch (Exception i) {
					//i.printStackTrace();
					throw new DBAppException("moshkela fe table object");
				}
				p=null;
        	    p1=null;
				v=null;
				fileOut=null;
				out=null;
				pageNumberForIndex = 1;
        		
        	}
        	else {
				//System.out.println(htblColNameValue.get(pk));
				

        		int index = t.search(htblColNameValue.get(pk), dataType); //kill
				System.out.println("00000000000000000000000000000000");
				System.out.println("THE PK : "+htblColNameValue.get(pk)+" THE INDEX FOUND : "+index);
				System.out.println();
				for(int g = 0;g<t.getRange().size();g++){
					Pair f = t.getRange().get(g);
					System.out.println("In the "+g+" Page , The minimum is : "+f.getMin()+" While the max is : "+f.getMax());
				}
				System.out.println("00000000000000000000000000000000");
        		if( index != -1) {  // We found the page (using the range) and we inserted 
        			                // , we increased the size of the page too 
        			String pageID = t.getIds().get(index);
        			Vector <Page> v = (Vector <Page>) deserialize(strTableName+"Page"+pageID);
        			Page pp = v.get(0);
        			
        				
					if(dataType.toLowerCase().compareTo(theInt.toLowerCase()) == 0) {
						pp.insertHashTableINT(htblColNameValue, pk);
						t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
						t.getRange().get(index).setMin(pp.getData().get(0).get(pk));

						System.out.println("*******************************************************************");
						System.out.println(pp.getData());
						System.out.println("*******************************************************************");
						// if((int) pp.getData().get(0).get("id") == 13){
						// 	System.out.println("Aywa ana 13");
						// }
						}
					else {
						if(dataType.toLowerCase().compareTo(theString.toLowerCase()) == 0) {
							System.out.println("OMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR");
							System.out.println(pp.getData());
							System.out.println("OMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR");
							pp.insertHashTableString(htblColNameValue, pk);
							t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
							t.getRange().get(index).setMin(pp.getData().get(0).get(pk));
						}
						else {
							if(dataType.toLowerCase().compareTo(theDouble.toLowerCase()) == 0) {
								if(htblColNameValue.get(pk) .equals( 3.7164)){
									System.out.println("Ana hna ya rab n5las");

								}
								pp.insertHashTableDOUBLE(htblColNameValue, pk);
								t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
								t.getRange().get(index).setMin(pp.getData().get(0).get(pk));
							}
							else {
								if(dataType.toLowerCase().compareTo(theDate.toLowerCase()) == 0) {
									pp.insertHashTableDate(htblColNameValue, pk);
									t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
									t.getRange().get(index).setMin(pp.getData().get(0).get(pk));
								}
									
							}
								
						}
							
							
					}
            			pp.setSize(pp.getSize()+1);

        				// I here must shift the last row in the curr page to the next page
						if(pp.getSize() > N) {
							updateOctreeAfterInserting(htblColNameValue, pkBahy, octreeExists, octreeName
							,Integer.parseInt(pageID));
							System.out.println("AYWAAAAAAAA >N");
							int shiftedindex = pp.getData().size() - 1;
							Hashtable<String, Object> shiftedRow = pp.getData().get(shiftedindex);
							 // last entry to shift 
							pp.getData().remove(shiftedindex);
							// we stoped here
							//Object primaryKey = shiftedRow.get(pkBahy);
							//Reference ref = new Reference(Integer.parseInt(pageID), pkBahy, primaryKey);	
							deleteExtraRowFromOctree(pkBahy, octreeName, pageID, shiftedRow); 
							
							pp.setSize(pp.getSize() - 1);
							t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
							System.out.println("++++++++++++++++++++++++++++++++++++++");
							System.out.println("THE SHIFTED ROW : "+shiftedRow);
							System.out.println("++++++++++++++++++++++++++++++++++++++");
							//serialize pp 
							//Date gg = (Date) shiftedRow.get("date_added");
							
							
							String oldPID = pageID;
						
							try {
								int i = 0;
								Object oldMAX = null;
								Object oldMIN = null;
								//updateOctreeAfterInserting(htblColNameValue, pkBahy, octreeExists, octreeName);
								while(true){
										index = t.search(shiftedRow.get(pk), dataType);
										pageID = t.getIds().get( index);
        								Vector <Page> v1 = (Vector <Page>) deserialize(strTableName+"Page"+pageID);
        							Page pp1 = v1.get(0);
								
								if(dataType.toLowerCase().compareTo(theInt.toLowerCase()) == 0) {
									if(!pp1.getData().contains(shiftedRow)){
										pp1.insertHashTableINT(shiftedRow, pk);
										System.out.println("4444444444444444444444444444444444444");
										System.out.println("The new page (PP1) is : "+pp1.getData());
										System.out.println("4444444444444444444444444444444444444");
									 oldMIN = t.getRange().get(index).getMin();
									 oldMAX =  t.getRange().get(index).getMax();
									t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1).get(pk));
									t.getRange().get(index).setMin(pp1.getData().get(0).get(pk));
									}
									
									}
								else {
									if(dataType.toLowerCase().compareTo(theString.toLowerCase()) == 0) {
										if(!pp1.getData().contains(shiftedRow)){
											
										pp1.insertHashTableString(shiftedRow, pk);
										System.out.println(t.getRange().size());
										index = t.search(shiftedRow.get(pk), dataType);
										t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1).get(pk));
										t.getRange().get(index).setMin(pp1.getData().get(0).get(pk));
										}
									}
									else {
										if(dataType.toLowerCase().compareTo(theDouble.toLowerCase()) == 0) {
											
											pp1.insertHashTableDOUBLE(shiftedRow, pk);
											index = t.search(shiftedRow.get(pk), dataType);
											t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1).get(pk));
											t.getRange().get(index).setMin(pp1.getData().get(0).get(pk));
										}
										else {
											if(dataType.toLowerCase().compareTo(theDate.toLowerCase()) == 0) {
												pp1.insertHashTableDate(shiftedRow, pk);
												index = t.search(shiftedRow.get(pk), dataType);
												
												t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1).get(pk));
												t.getRange().get(index).setMin(pp1.getData().get(0).get(pk));
											}
												
										}
											
									}
										
										
								}
								pp1.setSize(pp1.getSize() + 1);

								if(pp1.getSize() <= N){
								v1 = new Vector<>();
								v1.add(pp1);
								System.out.println(pp1.getData());
								serialize(v1, strTableName+"Page"+pageID);

								Vector<Page> ser = new Vector<Page>();
								ser.add(pp);
								System.out.println("The old PID : "+oldPID);
								serialize(ser, strTableName+"Page"+oldPID);
								FileOutputStream fileOut;
								ObjectOutputStream out;
								// 2. serialize the table
								try {
									Vector<Table> tableV = new Vector<Table>();
									tableV.add(t);
									 fileOut = new FileOutputStream(strTableName + ".bin");
									 out = new ObjectOutputStream(fileOut);
									out.writeObject(tableV);
									out.close();
									fileOut.close();
									updateOctreeAfterInserting(htblColNameValue, pkBahy, octreeExists, octreeName
									,Integer.parseInt(pageID));
									
									
								} catch (Exception e) {
									//e.printStackTrace();
									throw new DBAppException("moshkela fe table object");
								}
								fileOut = null;
								out = null;

								ser=null;
									return;
								} 
								
								//else 
								//shiftedRow = pp1.getData().get(shiftedindex);
								Hashtable<String, Object> temp = shiftedRow;
								shiftedRow = pp1.getData().remove(pp1.getData().size()-1);
									
								if(!temp.equals(shiftedRow)){
									// insert temp in the Octree
									updateOctreeAfterInserting(temp, pkBahy, octreeExists, octreeName,
								Integer.parseInt(pageID));

									// remove shiftedRow from the Octree
									deleteExtraRowFromOctree(pkBahy, octreeName, pageID, shiftedRow); 


								}
								//pp1.setSize(pp.getSize() - 1);
								t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1).get(pk));
								t.getRange().get(index).setMin(pp1.getData().get(0).get(pk));
								i++;

								// we should serialize any change in any page before entering the loop again

								// 1. serialize the page
								
								Vector<Page> ser = new Vector<Page>();
								ser.add(pp);
								System.out.println("The old PID : "+oldPID);
								serialize(ser, strTableName+"Page"+oldPID);


								v1 = new Vector<>();
								v1.add(pp1);
								serialize(v1, strTableName+"Page"+pageID);
								
								// 2. serialize the table
								FileOutputStream fileOut;
								ObjectOutputStream out;
								Vector<Table> tableV;
								try {
									 tableV = new Vector<Table>();
									tableV.add(t);
									 fileOut = new FileOutputStream(strTableName + ".bin");
									 out = new ObjectOutputStream(fileOut);
									out.writeObject(tableV);
									out.close();
									fileOut.close();
									
									
								} catch (Exception e) {
									//e.printStackTrace();
									throw new DBAppException("moshkela fe table object");
								}

								tableV=null;
								//hena aho
								v1=null;
								pp1=null;
								ser=null;
									}

								//pp1=null;
							} 

							catch (ArrayIndexOutOfBoundsException e) { 
							// did not find next page
							// so we create a new page
							Page newPage = new Page();
							newPage.getData().add(shiftedRow);
							newPage.setSize(1);
							System.out.println(newPage.getData().get(0).get(pk));
							//serialize and add to table

							// 1. serialize the page
							pageID = Integer.parseInt(pageID) + 1+"";
							Vector<Page> v1 = new Vector<>();
							v1.add(newPage);
							serialize(v1, strTableName+"Page"+pageID);
							
							// serialize the old page
							Vector<Page> ser = new Vector<Page>();
							ser.add(pp);
							System.out.println("The old PID : "+oldPID);
							serialize(ser, strTableName+"Page"+oldPID);

								updateOctreeAfterInserting(shiftedRow, pkBahy, octreeExists, octreeName,
								Integer.parseInt(pageID));

							// 2. serialize the table
							t.getIds().add(pageID);
							t.getRange().add(new Pair(shiftedRow.get(pk), shiftedRow.get(pk)));
							Vector<Table> tableV;
							FileOutputStream fileOut;
							ObjectOutputStream out;
							try {
								 tableV = new Vector<Table>();
								tableV.add(t);
								 fileOut = new FileOutputStream(strTableName + ".bin");
								 out = new ObjectOutputStream(fileOut);
								out.writeObject(tableV);
								out.close();
								fileOut.close();
								
								
							} catch (Exception i) {
								//i.printStackTrace();
								throw new DBAppException("moshkela fe table object");
							}
							
							//hena aho
							newPage=null;
							v1=null;
							ser=null;
							tableV=null;
							fileOut=null;
							out=null;
							}
							return;
							
						}
						Vector<Page> sol = new Vector<Page>();
						sol.add(pp);
						serialize(sol, strTableName+"Page"+pageID);
						// 2. serialize the table
						try {
							Vector<Table> tableV = new Vector<Table>();
							tableV.add(t);
							FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
							ObjectOutputStream out = new ObjectOutputStream(fileOut);
							updateOctreeAfterInserting(htblColNameValue, pkBahy, octreeExists, octreeName
							,Integer.parseInt(pageID));
							out.writeObject(tableV);
							out.close();
							fileOut.close();
							tableV=null;
							fileOut=null;
							out=null;
							
						} catch (Exception e) {
							//e.printStackTrace();
							throw new DBAppException("moshkela fe table object");
						}

        			v=null;
					pp=null;
					sol=null;
					pageNumberForIndex=Integer.parseInt(pageID);
        		}
        		else {
        			// WE didnt find the page using the range :( 
        			// what to do ? 
        			// check the min of the 2nd page , if > my value then insert my value in the 1st page
        			// keep checking the min of the next pages similary to find a place to insert it. 
					
					boolean addPage = false;
					
					int ind = t.searchPageAccordingToMin(htblColNameValue.get(pk), dataType); 
					System.out.println("THE IND : "+ind);
					String pageID = t.getIds().get(ind);
					Vector <Page> v = (Vector <Page>) deserialize(strTableName+"Page"+pageID);
        			Page pp = v.get(0);
					v=null;
					// if index is != -1 then page is found
					if(ind != -1){
						
						// check page < N
						for (int i = ind; i < t.getRange().size(); i++) { // get first "empty" page
							pageID = t.getIds().get(i);
        					v = (Vector <Page>) deserialize(strTableName+"Page"+pageID);
        					pp = v.get(0);
							if(pp.getSize() >= N){
								ind = i + 1;
								if(ind == t.getRange().size()){
									addPage = true;
									break;
								}
							}
							else break;
						}


					}
					
					// change 3: compareto instead of ==
					if(!addPage){
						if(dataType.toLowerCase().compareTo(theInt.toLowerCase()) == 0) {
							pp.insertHashTableINT(htblColNameValue, pk);
							t.getRange().get(ind).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
							t.getRange().get(ind).setMin(pp.getData().get(0).get(pk));
							}
						else {
							if(dataType.toLowerCase().compareTo(theString.toLowerCase()) == 0) {
								pp.insertHashTableString(htblColNameValue, pk);
								t.getRange().get(ind).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
								t.getRange().get(ind).setMin(pp.getData().get(0).get(pk));
							}
							else {
								if(dataType.toLowerCase().compareTo(theDouble.toLowerCase()) == 0) {
									pp.insertHashTableDOUBLE(htblColNameValue, pk);
									t.getRange().get(ind).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
									t.getRange().get(ind).setMin(pp.getData().get(0).get(pk));
								}
								else {
									if(dataType.toLowerCase().compareTo(theDate.toLowerCase()) == 0) {
										pp.insertHashTableDate(htblColNameValue, pk);
										t.getRange().get(ind).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
										t.getRange().get(ind).setMin(pp.getData().get(0).get(pk));
									}
										
								}
									
							}
								
								
						}
							pp.setSize(pp.getSize()+1);
							System.out.println(pp.getSize());
							//1. serialize page
							Vector<Page> v1 = new Vector<>();
							v1.add(pp);
							pp=null;
							serialize(v1, (strTableName+"Page"+pageID));
							v1=null;
							// 2. serialize the table
							try {
								Vector<Table> tableV = new Vector<Table>();
								tableV.add(t);
								FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
								ObjectOutputStream out = new ObjectOutputStream(fileOut);
								out.writeObject(tableV);
								out.close();
								fileOut.close();
								tableV=null;
								fileOut=null;
								out=null;	
								updateOctreeAfterInserting(htblColNameValue, pkBahy, octreeExists, octreeName,
								Integer.parseInt(pageID));
									
							} catch (Exception e) {
								//e.printStackTrace();
								throw new DBAppException("moshkela fe table object");
							}
					}
					// add a new page
					else{
						Page newPage = new Page();
						newPage.getData().add(htblColNameValue);
						newPage.setSize(1);

						//serialize and add to table

						// 1. serialize the page
						int pid = Integer.parseInt(pageID);
						pageID = (pid + 1)+"";
						Vector<Page> v1 = new Vector<>();
						v1.add(newPage);
						serialize(v1, strTableName+"Page"+pageID);
						
						// 2. serialize the table
						t.getIds().add(pageID);
						t.getRange().add(new Pair(htblColNameValue.get(pk), htblColNameValue.get(pk)));
						try {
							Vector<Table> tableV = new Vector<Table>();
							tableV.add(t);
							FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
							ObjectOutputStream out = new ObjectOutputStream(fileOut);
							out.writeObject(tableV);
							out.close();
							fileOut.close();
							tableV=null;
								fileOut=null;
								out=null;	
								updateOctreeAfterInserting(htblColNameValue, pkBahy, octreeExists, octreeName
								, Integer.parseInt(pageID));
								
						} catch (Exception i) {
							//i.printStackTrace();
							throw new DBAppException("moshkela fe table object");
						}
						newPage=null;
						v1=null;
					}
        			
        		}
        		
     
        	}
            p=null;
            t=null;
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DBAppException();
		}
		br=null;
		
		
		// here we should serialize the "insert 13" probllem
	}

	private static void deleteExtraRowFromOctree(String pkBahy, ArrayList<String> octreeName, String pageID,
			Hashtable<String, Object> shiftedRow) throws DBAppException {
		if(octreeName.isEmpty())
		{
			return;
		}		
		Object pkValue;
		for(int i = 0 ;i<octreeName.size();i++){
			String name = octreeName.get(i);
			Octree octree = null;
			int toBeDeleted = -1;
			for(int j = 0 ;j<ocs.size();j++){
				if(ocs.get(j).getName().equals(name)){
					octree = ocs.get(j);
					toBeDeleted = j;
					break;
				}
			}
			String [] colNames =  octree.getColNames();
			String col1 = colNames[0];
			String col2 = colNames[1];
			String col3 = colNames[2];
			pkValue = shiftedRow.get(pkBahy);
			Reference r = new Reference(Integer.parseInt(pageID),pkBahy,pkValue);
			//ArrayList<Reference> refs = new ArrayList<>();
			//refs.add(r);
			Tuple t1 = new Tuple(shiftedRow.get(col1),
			shiftedRow.get(col2),
			shiftedRow.get(col3),
			r);
			octree.delete(t1);

			ocs.remove(toBeDeleted);
			serializeOctree(octree);
			ocs = (Vector<Octree>) deserialize("Octrees");
		
		}
	}

	private static void updateOctreeAfterInserting(Hashtable<String, Object> htblColNameValue, String pkBahy, boolean octreeExists,
			ArrayList<String> octreeName,int p) throws DBAppException {
		Object pkValue;
		if(octreeExists){
			for(int i = 0 ;i<octreeName.size();i++){
				String name = octreeName.get(i);
				Octree octree = null;
				int toBeDeleted = -1;
				for(int j = 0 ;j<ocs.size();j++){
					if(ocs.get(j).getName().equals(name)){
						octree = ocs.get(j);
						toBeDeleted = j;
						break;
					}
				}
				String [] colNames =  octree.getColNames();
				String col1 = colNames[0];
				String col2 = colNames[1];
				String col3 = colNames[2];
				pkValue = htblColNameValue.get(pkBahy);
				Reference r = new Reference(p,pkBahy,pkValue);
				//ArrayList<Reference> refs = new ArrayList<>();
				//refs.add(r);
				Tuple t1 = new Tuple(htblColNameValue.get(col1),
				htblColNameValue.get(col2),
				htblColNameValue.get(col3),
				r);
				octree.insert(t1);

				ocs.remove(toBeDeleted);
				serializeOctree(octree);
				ocs = (Vector<Octree>) deserialize("Octrees");
			
			}
			




			
		}
	}
	
	
	
	
	
	
	
	
	// private void insertIntoIndex(Hashtable<String, Object> htblColNameValue, boolean octreeExists, String octreeName,int pageNumber) {
	// 	if(octreeExists){
	// 		Octree oct = null;
	// 		for(Octree o : this.ocs){
	// 			if(o.getName().equals(octreeName)){
	// 				oct = o;
	// 				break;
	// 			}
	// 		}
	// 		String [] colNames = oct.getColNames();
	// 		Reference r = new Reference(pageNumber);
	// 		Tuple t = new Tuple(htblColNameValue.get(colNames[0]),htblColNameValue.get(colNames[1]),htblColNameValue.get(colNames[2]),r);
			

	// 	}
	// }

	private static void addTheNulls(Hashtable<String, Object> htblColNameValue, String strTableName) throws DBAppException {
		Set x = htblColNameValue.keySet();
		ArrayList<String> htblNames = new ArrayList<String>();
		ArrayList<String> allNames = new ArrayList<String>();
		for (Object o :x.toArray()){
			htblNames.add((String) o);
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
			String line = br.readLine();
			int N = getN() ;
			String pk = "";
			String dataType = "";
			boolean isClustering = false;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
				if(values[0].equals(strTableName)){
					allNames.add(values[1]);
				}
			}

			br=null;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new DBAppException();
		}
		for (String n : allNames){
			if(!htblNames.contains(n)){
				throw new DBAppException("Missing column");
			}

		}
		
	}
	public void updateTable(String strTableName, String strClusteringKeyValue,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {
				// following method updates one row only
				// htblColNameValue holds the key and new value
				// htblColNameValue will not include clustering key as column name
				// strClusteringKeyValue is the value to look for to find the row to update.
				if(!found(strTableName, strClusteringKeyValue, htblColNameValue))
					throw new DBAppException();
				System.gc();
	}

	public static boolean found(String tableName,String keyValue,Hashtable<String, Object> htblColNameValue) throws DBAppException{
		if(!exists(tableName)) // check if the table even exists
			throw new DBAppException("Table doesn't exist");
		String key = "";
		String keyDataType = "";
		int isDataCorrect = 0;
		try {
		BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
		String line = br.readLine();
		
		
		while ((line = br.readLine()) != null) {
			String[] values = line.split(",");
			if(values[0].equals(tableName)) {
			// we got the table
			if(values[3].equals("True")) {
				// we got the PK
				key = values[1];
				keyDataType = 	values[2];
				// Up till here we knew the following : 
				// 1) The table exists
				// 2) what is the table's Primary key
				// 3) what is the table's Primary Key data type
				}
				for (int j = 0;j<htblColNameValue.keySet().size();j++) {//check if data is valid
					String tmp = htblColNameValue.keySet().iterator().next();
					if(values[1].equals(tmp)){ 
					// we got the cloumn now we check if it is correct data type and within the range 
						int variable = 1;
                	switch (values[2]) {
                	case theInt :
                	
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Integer ) {
                			variable = 0;
                			// 6 min      7  max
                        	if(    (Integer.parseInt(values[6]))   >   ((int)  (htblColNameValue.get(values[1])))           
                        			||   (Integer.parseInt(values[7]))   <   ((int)  (htblColNameValue.get(values[1])))    )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                		}
                		
                		break;
                		    
                	
                	
                	case theString :   
                		if(  (htblColNameValue.get(values[1]) ) instanceof  String ) {
                			variable = 0;
                			
                			if(    (  values[6].toLowerCase().compareTo( ( (String)     htblColNameValue.get(values[1])       ).toLowerCase() )     == 1         
                        			||   values[7].toLowerCase().compareTo(((String)htblColNameValue.get(values[1])).toLowerCase())   == -1     )  )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                			
                		}
                		
                		break;
                	case theDouble : 
                		
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Double ) {
                			variable = 0;
                			
                			
                			if(    (Double.parseDouble(values[6]))   >   ((Double)  (htblColNameValue.get(values[1])))           
                        			||   (Double.parseDouble(values[7]))   <   ((Double)  (htblColNameValue.get(values[1])))    )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                			
                		}
                		
                		break;
                	case theDate :   
                		System.out.println("ana gowa el date");
						System.out.println(htblColNameValue.get(values[1]));
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Date ) {
                			variable = 0;
							System.out.println("ana gowa el if");
                 			// law rg3t -1 then ely bara is the earlier one
                			// law rag3t 1 then ely bara is later 
                			
                			String x = values[6];  // min
                			String y = values[7]; //max
                			Object i = htblColNameValue.get(values[1]);
                			LocalDate dMIN = LocalDate.parse(x) ;
                			LocalDate dMAX = LocalDate.parse(y) ;
							Date test = (Date) i;
							
							//System.out.println("The test : " + test);
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
							String testingDate = dateFormat.format(test);
                			LocalDate theInput = LocalDate.parse(( testingDate   )) ;
                			
                			int notPos = dMIN.compareTo(theInput)  ;  // not +ve
                			int notNeg =  dMAX.compareTo(theInput)  ;  // not -ve
                			if(notPos>0 || notNeg<0)
                				throw new DBAppException("Data is not within the domain of the coloumn !");
                			
                		}
                		
                		
                		break;
                	
                	}
                	
                	if(variable==1) {
                		throw new DBAppException("Data entered doenst match data type in the table");
                	}
					isDataCorrect++;
					}
				}
			}
			//System.out.println(line);
		}
		br=null;
		} catch (Exception e) {
			//System.out.println(e.getCause());
			throw new DBAppException();
			//e.getCause()
		}


		if (isDataCorrect==htblColNameValue.size()){
			// table exists and data is valid (in the correct data type and within the min & the max)
			// now lets go check if the row (the hashtable) exists or not
			//System.out.println("The rest of code");
			return rowExists(tableName, keyValue, key, keyDataType,htblColNameValue);
		}
		else {
			return false;
		}
		//returnValue =  
		//return returnValue;
	}
	public static boolean rowExists(String tableName,String keyValue,String key ,
	 String keyDataType,Hashtable<String, Object> htblColNameValue) throws DBAppException{
		Vector<Table> tables = (Vector<Table>) deserialize(tableName);
		Table table = tables.get(0);
		// Note : we won't edit sth. in the table so no need to remove it from the vector and re add it and reserialize it again
		Object o = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//Date date = dateFormat.parse(dateString);
		switch(keyDataType){
			case theInt : o = Integer.parseInt(keyValue);break;
			case theString : o = keyValue; break ;
			case theDouble : o = new Double(keyValue);break;
			case theDate : try {
					o = dateFormat.parse(keyValue);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					throw new DBAppException();
				} break;

		}

		int pageIndex = table.search(o, keyDataType);
		if(pageIndex==-1){ // row does't exist
			return true;
		}
		else{ // if the row exists it will be in this page , bec. the row is within its range
			System.out.println(table.getIds());
			System.out.println(pageIndex);
			Vector<Page> pages =  (Vector<Page>) deserialize(tableName+"Page"+table.getIds().get(pageIndex));
			Page page = pages.get(0);
			pages.remove(page);
			Hashtable<String,Object> doesExist = null;
			switch(keyDataType){
				case theInt : 
					Integer iTMP = Integer.parseInt(keyValue) ;
					int iValue = iTMP.intValue();
					System.out.println("The ivalue : "+iValue+" While the key is : "+key);
					System.out.println();
					System.out.println(  "THAAAAAAAAAA DATA IN PAGE" + page.getData());
					doesExist = page.binarySearchInteger(iValue, key);
					break;
				case theString : doesExist = page.binarySearchString(keyValue, key); break ;
				case theDouble : doesExist = page.binarySearchDouble(new Double(keyValue), key) ;break;
				case theDate : doesExist = page.binarySearchDate(keyValue, key); break;
	
			}
			if (doesExist != null) { // we now have the row that will be updated
				// وصلنا بالسلامه الحمد الله
				update(tableName, htblColNameValue, pageIndex, pages, page, doesExist,key);
				tables=null;
				table=null;
				pages=null;
				page=null;
				return true; // تم عمل ابديت بنجاح   :)
			}
			else {
				tables=null;
				table=null;
				pages=null;
				page=null;
				return true;
			}
			
			
		}



		//tables=null;

		//return false ;
	}

	public static void update(String tableName, Hashtable<String, Object> htblColNameValue, int pageIndex,
			Vector<Page> pages, Page page, Hashtable<String, Object> doesExist,String key) throws DBAppException {
		//System.out.println(htblColNameValue.keySet().size());
		ArrayList<String> octreeName = new ArrayList<String>();
		String pass = pageIndex+"";
				for(int i = 0 ;i<ocs.size();i++){
			Octree o = ocs.get(i);
			if(o.getTableName().equals(tableName)){
				octreeName.add(o.getName());
			}

			//octreeName.add(t2.getOcs().get(i).getName());
		}
		deleteExtraRowFromOctree(key,octreeName,pass,doesExist);
		Iterator<String> it = htblColNameValue.keySet().iterator();
		for (int k = 0;k<htblColNameValue.keySet().size();k++){
			String updatedColumn = it.next();
			//System.out.println(" here  :  up col  "+updatedColumn+"  here  get :   "+htblColNameValue.get(updatedColumn));
			//System.out.println(htblColNameValue.get(updatedColumn) instanceof String);
			String x = htblColNameValue.get(updatedColumn) instanceof Date ? theDate
			: ( htblColNameValue.get(updatedColumn) instanceof Integer ? theInt  
			:  ( htblColNameValue.get(updatedColumn) instanceof Double ? theDouble 
			:  theString ) );
			//System.out.println(x);
			switch(x){
				case theInt : doesExist.put(updatedColumn, (Integer) htblColNameValue.get(updatedColumn));break;
				case theString : doesExist.put(updatedColumn, (String) htblColNameValue.get(updatedColumn)); break;
				//System.out.println(updatedColumn+"here");break ;
				case theDouble : doesExist.put(updatedColumn, (Double) htblColNameValue.get(updatedColumn));break;
				case theDate : 
					doesExist.put(updatedColumn,(Date) htblColNameValue.get(updatedColumn) ) ; break;

			}
			System.out.println(updatedColumn);
		}
		Vector<Table> t = (Vector<Table>)deserialize(tableName);
		Table t2 = t.get(0);
		System.out.println(page.getData());
		pages.add(page); // tableName+"Page"+(pageIndex+1
		serialize(pages, tableName+"Page"+t2.getIds().get(pageIndex));
		
		boolean octreeExists = !octreeName.isEmpty();
		//String pkBahy = "";
		/*private void updateOctreeAfterInserting(Hashtable<String, Object> htblColNameValue, String pkBahy, boolean octreeExists,
			ArrayList<String> octreeName,int p) throws DBAppException { */
		updateOctreeAfterInserting(doesExist,key,octreeExists,octreeName,pageIndex);
		


		/*Object pkValue;
		if(octreeExists){
			for(int i = 0 ;i<octreeName.size();i++){
				String name = octreeName.get(i);
				Octree octree = null;
				int toBeDeleted = -1;
				for(int j = 0 ;j<ocs.size();j++){
					if(ocs.get(j).getName().equals(name)){
						octree = ocs.get(j);
						toBeDeleted = j;
						break;
					}
				}
				String [] colNames =  octree.getColNames();
				String col1 = colNames[0];
				String col2 = colNames[1];
				String col3 = colNames[2];
				pkValue = doesExist.get(pkBahy);
				Reference r = new Reference(p,pkBahy,pkValue);
				//ArrayList<Reference> refs = new ArrayList<>();
				//refs.add(r);
				Tuple t1 = new Tuple(doesExist.get(col1),
				doesExist.get(col2),
				doesExist.get(col3),
				r);
				octree.delete(t1);

				ocs.remove(toBeDeleted);
				serializeOctree(octree);
				ocs = (Vector<Octree>) deserialize("Octrees");
			
			}
			




			
		} */


	}			
	public static boolean exists (String tableName) throws DBAppException{
		Vector<String> tableNames = new Vector<String> ();
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tableNames.bin"));
			 tableNames = (Vector<String>) in.readObject();
			in.close();
			in=null;
		} catch (Exception i) {
			//i.printStackTrace();
			throw new DBAppException();
		}
		//tableNames=null;
		boolean x = tableNames.contains(tableName);
		tableNames=null;
		return x;
	}
	public static Object deserialize (String name) throws DBAppException {
		Object r =null;
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream(name+".bin"));
			 r =  in.readObject();
			in.close();

		} catch (Exception i) {
			//i.printStackTrace();
			throw new DBAppException();
		}
		return r;
		
	}
	public static void serialize (Vector<Page> p,String name) throws DBAppException {
		FileOutputStream fileOut;
		ObjectOutputStream out;
		try {
			//Vector<String> p = new Vector<String>();
			//  {1,2,3,4,8}  { () , () }
			
			 fileOut = new FileOutputStream(name + ".bin");
			 out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
			
			
		} catch (Exception i) {
			//i.printStackTrace();
			throw new DBAppException();
		}
		
		
	}
	public static void serializeOctree (Octree o) throws DBAppException {
		FileOutputStream fileOut;
		ObjectOutputStream out;
		Vector<Octree> ocs = null;
		boolean exists = false;
		try{
		ocs = (Vector<Octree>)	deserialize("Octrees");
		exists = true;
		}
		catch(Exception e) {
			exists = false;
		}
		if(exists){
			ocs.add(o);
			// serialize the ocs
			//FileOutputStream fileOut;
		//ObjectOutputStream out;
		try {
			//Vector<String> p = new Vector<String>();
			//  {1,2,3,4,8}  { () , () }
			
			 fileOut = new FileOutputStream("Octrees.bin");
			 out = new ObjectOutputStream(fileOut);
			out.writeObject(ocs);
			out.close();
			fileOut.close();
			
			
		} catch (Exception i) {
			//i.printStackTrace();
			throw new DBAppException();
		}

		}
		else {
			// create the ocs
			ocs = new Vector<Octree>();
			ocs.add(o);
			try {
				//Vector<String> p = new Vector<String>();
				//  {1,2,3,4,8}  { () , () }
				
				 fileOut = new FileOutputStream("Octrees.bin");
				 out = new ObjectOutputStream(fileOut);
				out.writeObject(ocs);
				out.close();
				fileOut.close();
				
				
			} catch (Exception i) {
				//i.printStackTrace();
				throw new DBAppException();
			}
		}


		/*try {
			//Vector<String> p = new Vector<String>();
			//  {1,2,3,4,8}  { () , () }
			
			 fileOut = new FileOutputStream(name + ".bin");
			 out = new ObjectOutputStream(fileOut);
			out.writeObject(o);
			out.close();
			fileOut.close();
			
			
		} catch (Exception i) {
			//i.printStackTrace();
			throw new DBAppException();
		}*/
		
		
	}

	
	public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) throws DBAppException {
// 3la ndfa
	 

        String fullterm = "";
		boolean isNotEqual = false;
		for(int y = 0 ;y<arrSQLTerms.length;y++){
			if(arrSQLTerms[y].getStrOperator().equals("!=")){
				isNotEqual = true;
				break;
			}
		}


























		String tableName = arrSQLTerms[0].getStrTableName();
		ArrayList<String> octreeNames = new ArrayList<String>();
		// ArrayList<String> tmpNames = new ArrayList<String>();
		// boolean linearOR = false;
		addTheNames(tableName, octreeNames);
		boolean noOctrees = octreeNames.size() == 0;
		System.out.println("arr "+arrSQLTerms.length);
		System.out.println("star "+strarrOperators.length);
		if(       !(arrSQLTerms.length == strarrOperators.length + 1)                   ) {
			throw new DBAppException();
		}
		String term = "";
		for (int  i = 0;i<arrSQLTerms.length;i++){
			System.out.println(term);
			if(i%2==0){
				if(i==0){
					term += arrSQLTerms[i].getStrColumnName();
					fullterm +=arrSQLTerms[i].getStrColumnName()+"*";
				fullterm += arrSQLTerms[i].getStrOperator()+"*";
				fullterm +=arrSQLTerms[i].getObjValue()+"*";
				}
				else{
					term += strarrOperators[i-1];
				term += arrSQLTerms[i].getStrColumnName();
				fullterm +=strarrOperators[i-1]+"*";
				fullterm +=arrSQLTerms[i].getStrColumnName()+"*";
				fullterm += arrSQLTerms[i].getStrOperator()+"*";
				fullterm +=arrSQLTerms[i].getObjValue()+"*";
				
			
				}
			
			}
			else{
				term += strarrOperators[i-1];
				term += arrSQLTerms[i].getStrColumnName();

				fullterm +=strarrOperators[i-1]+"*";
				fullterm +=arrSQLTerms[i].getStrColumnName()+"*";
				fullterm += arrSQLTerms[i].getStrOperator()+"*";
				fullterm +=arrSQLTerms[i].getObjValue()+"*";
			}

			
		}
		fullterm = fullterm.substring(0,fullterm.length()-1);

		System.out.println("Fullterm: "+fullterm);

		System.out.println("OMAAAAR"+term);
		ArrayList <Integer> toBeUsed = new ArrayList<Integer>();
		//System.out.println("THE NAME IS : "+ocs.get(0).getName());
		for (int i = 0 ;i<ocs.size();i++){
			if(ocs.get(i).getTableName().equals(tableName)){
				//AND
				String and = "AND";
				String name1 = ocs.get(i).getColNames()[0]+and+ocs.get(i).getColNames()[1]+and+ocs.get(i).getColNames()[2]; // abc
				String name2 = ocs.get(i).getColNames()[0]+and+ocs.get(i).getColNames()[2]+and+ocs.get(i).getColNames()[1];  //bca
				String name3 = ocs.get(i).getColNames()[1]+and+ocs.get(i).getColNames()[0]+and+ocs.get(i).getColNames()[2];  
				String name4 = ocs.get(i).getColNames()[1]+and+ocs.get(i).getColNames()[2]+and+ocs.get(i).getColNames()[0];
				String name5 = ocs.get(i).getColNames()[2]+and+ocs.get(i).getColNames()[0]+and+ocs.get(i).getColNames()[1];
				String name6 = ocs.get(i).getColNames()[2]+and+ocs.get(i).getColNames()[1]+and+ocs.get(i).getColNames()[0];
				if(term.contains(name1) ||term.contains(name2) ||term.contains(name3) ||term.contains(name4) ||term.contains(name5) ||term.contains(name6) ){
					toBeUsed.add(i);

					
				}
				System.out.println("TobeUsed: "+toBeUsed);
				// tmpNames.add(name1);
				// tmpNames.add(name2);
				// tmpNames.add(name3);
				// tmpNames.add(name4);
				// tmpNames.add(name5);
				// tmpNames.add(name6);
			}
		}
		Iterator it = null;
		// StringTokenizer ST = new StringTokenizer(term,"OR");
		// while(ST.hasMoreTokens()){
		// 	String s = ST.nextToken();
		// 	if(!tmpNames.contains(s)){
		// 		linearOR = true;

		// 	}
			 


		//}
			System.out.println( "Should be false : " +  toBeUsed.isEmpty());
		
		if(toBeUsed.isEmpty() || isNotEqual){
			// linear
   //56468484			1 2 3    45564654   1 4 1 2 3 
			Vector<Vector<Hashtable<String,Object>>> results = new Vector<Vector<Hashtable<String,Object>>>();
			//Vector<Hashtable<String,Object>> resultSet = new Vector<Hashtable<String,Object>>();
			
			evaluateConditionsIndividually(arrSQLTerms, tableName, results);
			//System.out.println(results.get(0));
			//System.out.println(results.get(1));
			Vector<Hashtable<String,Object>> finalResultSet = new Vector<Hashtable<String,Object>>();
			if(results.size()==1){
				finalResultSet = results.get(0);
				it = finalResultSet.iterator();
				return it;
			}
			System.out.println("results size "+results.size());
			finalResultSet = results.get(0);
			for(int i = 1 ;i<results.size()-1;i++){
				Vector<Hashtable<String,Object>> set1 = results.get(i);
				Vector<Hashtable<String,Object>> set2 =finalResultSet;
				String operator = strarrOperators[i-1];
				switch(operator){
					case "AND":
					//System.out.println("ana fe el  and");
						for(int j = 0;  j<set1.size()    ;j++){
							Hashtable<String,Object> row = set1.get(j);
							//System.out.println(row);
							//System.out.println(set2);
							//System.out.println(set2.contains(row));
							if(set2.contains(row)){
								finalResultSet.add(row);
							}
						}
						break;
					case "OR":
					//System.out.println("ana fe el  or");
						for(int j = 0;  j<set1.size()  ;j++){
							Hashtable<String,Object> row = set1.get(j);
							
								finalResultSet.add(row);
							
						}
						for(int j = 0;  j<set2.size()  ;j++){
							Hashtable<String,Object> row = set2.get(j);
							if(!finalResultSet.contains(row)){
								finalResultSet.add(row);
							}
						}
						break;
					case "XOR":
					//System.out.println("ana fe el  xor");
						// A' B + A B'
						Vector<Hashtable<String,Object>> tmp2 = new Vector<Hashtable<String,Object>>();
						for(int j = 0;  j<set1.size()  ;j++){
							Hashtable<String,Object> row = set1.get(j);
							if(!set2.contains(row)){
								tmp2.add(row);
							}
						}
						Vector<Hashtable<String,Object>> tmp1 = new Vector<Hashtable<String,Object>>();
						for(int j = 0;  j<set2.size()  ;j++){
							Hashtable<String,Object> row = set2.get(j);
							if(!set1.contains(row)){
								tmp1.add(row);
							}
						}
						for(int j = 0;  j<tmp1.size()  ;j++){
							Hashtable<String,Object> row = tmp1.get(j);
							
								finalResultSet.add(row);
							
						}
						for(int j = 0;  j<tmp2.size()  ;j++){
							Hashtable<String,Object> row = tmp2.get(j);
							if(!finalResultSet.contains(row)){
								finalResultSet.add(row);
							}
						}
						break;
						// Distinct
				}
			}
			
			it = finalResultSet.iterator();
			return it;
		}
		else{
			// Using Octree
			System.out.println("Using Octreeeeeeeeeeee");
			







			String line = "";
			String splitBy = ",";
			
			Vector<String[]> Data =new Vector<String[]>();
			try{
				BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
				while ((line = br.readLine()) != null)   //returns a Boolean value
				{
					String[] array= line.split(splitBy);
					Data.add(array);
	
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	


			String[] columns = new String[arrSQLTerms.length]; // columns that is included in the condition array(sqlTerms)
			int noOfConditions = arrSQLTerms.length;
			//getting the column names that are in the sqlterms array
			for (int i = 0; i<arrSQLTerms.length; i++)
				columns[i] = arrSQLTerms[i].getStrColumnName();
			




	
	
				Hashtable<String,SQLTerm> h1 = new Hashtable<String,SQLTerm>();
			Vector<SQLTerm>  conditionsOfIndexedColumns= new Vector<SQLTerm>();
	
			Vector<String> indexedColumns = new Vector<String>();
					Vector<String> nonIndexedColumns = new Vector<String>();
	
					//checking which columns are indexed and which are not (from the metadata)
					Vector<SQLTerm> conditionsOfNonIndexedColumns=new Vector<SQLTerm>();
					conditionsOfIndexedColumns=new Vector<SQLTerm>();
	
					ArrayList<String> arr = new ArrayList<>();

					for (int j = 0; j<columns.length; j++){
						for (int k = 0; k<Data.size();k++){
							if ((Data.get(k)[0]).equals(tableName)){
								if (Data.get(k)[1].equals(columns[j])){
									if (!(Data.get(k)[4].equals("null"))) {
										indexedColumns.add(columns[j]);
										//arr.add(Data.get(k)[4]);
										for (int h = 0; h < arrSQLTerms.length; h++) {
											
											if (arrSQLTerms[h].getStrColumnName().equals(columns[j])) {
												h1.put(columns[j], arrSQLTerms[h]);
												conditionsOfIndexedColumns.add(arrSQLTerms[h]);
												break;
											}
										}
									}
									else
								{
										nonIndexedColumns.add(columns[j]);
										//arr.add("null");
								}
									for(int h=0;h<arrSQLTerms.length;h++){
										if(arrSQLTerms[h].getStrColumnName().equals(columns[j]) && !(conditionsOfIndexedColumns.contains(arrSQLTerms[h]))){
											
											conditionsOfNonIndexedColumns.add(arrSQLTerms[h]);
											break;
										}
									}
								}
							}
						}
	}



	for(int i = 0;i<arrSQLTerms.length;i++){

		if(indexedColumns.contains(arrSQLTerms[i].getStrColumnName())){
			System.out.println("Yes it is indexed column");
			for (int k = 0; k<Data.size();k++){
				if ((Data.get(k)[0]).equals(tableName)){
					if (Data.get(k)[1].equals(arrSQLTerms[i].getStrColumnName())){
						arr.add(Data.get(k)[4]);
						break;
		}
	}
}
		}
		else
		  arr.add("null");
	}

	
	Vector<Vector<Hashtable<String,Object>>> resss = new Vector<Vector<Hashtable<String,Object>>>();
	for(int i = 0;i<arr.size();i++){
		System.out.println(Arrays.toString(arr.toArray()));
		if(arr.get(i).equals("null") || arr.size() - i < 3 ||!(arr.get(i).equals(arr.get(i+1)) && arr.get(i+1).equals(arr.get(i+2))  && strarrOperators[i].equals("AND") && strarrOperators[i+1].equals("AND") )){
			Vector<Vector<Hashtable<String,Object>>> ressstmp = new Vector<Vector<Hashtable<String,Object>>>();

			SQLTerm[] arrSqlTermsTmp = new SQLTerm[1];
			arrSqlTermsTmp[0] = arrSQLTerms[i];
			evaluateConditionsIndividually(arrSqlTermsTmp, arrSqlTermsTmp[0].getStrTableName(), ressstmp);

			resss.add(ressstmp.get(0));

			System.out.println("arrSqlTerm: "+arrSqlTermsTmp[0]);
			System.out.println("results :"+ressstmp);


			System.out.println("ana gwa el null 3ashan msh 3lya index");
			
	}
	else
	{
		// Vector<Vector<Hashtable<String,Object>>> ressstmp = new Vector<Vector<Hashtable<String,Object>>>();
		if(arr.size()-i >= 3){

			if(arr.get(i).equals(arr.get(i+1)) && arr.get(i+1).equals(arr.get(i+2))  && strarrOperators[i].equals("AND") && strarrOperators[i+1] == "AND")
			{
				for(int j = 0;j<ocs.size();j++){
					Octree o = ocs.get(j);
					if(o.getName().equals(arr.get(i)))
					{
						System.out.println(arrSQLTerms[i]);
						List<Tuple> searchRes = 
						o.search(arrSQLTerms[i].getStrOperator(), 
						arrSQLTerms[i].getObjValue(),arrSQLTerms[i+1].getStrOperator(),
						 arrSQLTerms[i+1].getObjValue(), 
						 arrSQLTerms[i+2].getStrOperator(), 
						 arrSQLTerms[i+2].getObjValue());

						Iterator<Tuple> tmpIT = searchRes.iterator();

         Vector<Hashtable<String, Object>> fr = new Vector<Hashtable<String, Object>>();
		while(tmpIT.hasNext()){
			Tuple t = (Tuple) tmpIT.next();
			Hashtable<String, Object> h = new Hashtable<String, Object>();
			ArrayList<Reference> r = t.getReferences();
			for(int x = 0;x<r.size();x++){
				Reference ref = r.get(x);
				int indexOfPage = ref.getPageNumber();
				String pk = ref.getPkName();
				Object pkValue = ref.getPkValue();
				// Vector<Table> tables = (Vector<Table>) deserialize(tableName);
				// Table table = tables.get(0);
				// Vector<String> ids = table.getIds();
				// for(int j = 0 ;j<ids.size();j++){

				// }
				Vector<Page> ps = (Vector<Page>) deserialize(tableName+"Page"+indexOfPage);
				Page p = ps.get(0);
				for(int y = 0;y<p.getData().size();y++){
					Hashtable<String, Object> h2 = p.getData().get(y);
					if(h2.get(pk).equals(pkValue)){
						h = h2;
						break;
				}

				}
		 	}

			fr.addElement(h);
		}

		resss.add(fr);









						System.out.println("Search Results: "+searchRes);
						System.out.println("Ana 3lay index");
						System.out.println("fr: "+fr);
					}

				}
				
			}
			
			strarrOperators[i] = "NO";
			strarrOperators[i+1] ="NO";
			i+=2;
		}

	}
}//end of for loop


// for(int i = 0;i<strarrOperators.length;i++){

	
// }

Vector<Hashtable<String,Object>> finalResultSet   = new Vector<Hashtable<String,Object>>(); 
System.out.println("Final Result Set before for: "+finalResultSet);
int k = 0;
for(int i = 0 ;i<resss.size()-1;i++,k++){

	System.out.println("Final Result Set after for : "+finalResultSet);
	Vector<Hashtable<String,Object>> set1 = new Vector<Hashtable<String,Object>>();
	            if(i == 0){
				 set1= resss.get(i);
				}
				else
				{
					set1 = finalResultSet;
				}
				System.out.println("Set1: "+set1);
				Vector<Hashtable<String,Object>> set2 =resss.get(i+1);
				String operator = strarrOperators[k];
				switch(operator){
					case "AND":
					    System.out.println("Ana fe el ANd");
						for(int j = 0;  j<set1.size()    ;j++){
							Hashtable<String,Object> row = set1.get(j);
							//System.out.println(row);
							//System.out.println(set2);
							//System.out.println(set2.contains(row));
							if(set2.contains(row)){
								finalResultSet.add(row);
							}
							
							 
						}
						break;
					case "OR":
					System.out.println("Ana fe el OR");
						for(int j = 0;  j<set1.size()  ;j++){
							Hashtable<String,Object> row = set1.get(j);
							
								finalResultSet.add(row);
							
						}
						for(int j = 0;  j<set2.size()  ;j++){
							Hashtable<String,Object> row = set2.get(j);
							if(!finalResultSet.contains(row)){
								finalResultSet.add(row);
							}
						}
						break;
					case "XOR":
					System.out.println("Ana fe el XOR");
						// A' B + A B'
						Vector<Hashtable<String,Object>> tmp2 = new Vector<Hashtable<String,Object>>();
						for(int j = 0;  j<set1.size()  ;j++){
							Hashtable<String,Object> row = set1.get(j);
							if(!set2.contains(row)){
								tmp2.add(row);
							}
						}
						Vector<Hashtable<String,Object>> tmp1 = new Vector<Hashtable<String,Object>>();
						for(int j = 0;  j<set2.size()  ;j++){
							Hashtable<String,Object> row = set2.get(j);
							if(!set1.contains(row)){
								tmp1.add(row);
							}
						}
						for(int j = 0;  j<tmp1.size()  ;j++){
							Hashtable<String,Object> row = tmp1.get(j);
							
								finalResultSet.add(row);
							
						}
						for(int j = 0;  j<tmp2.size()  ;j++){
							Hashtable<String,Object> row = tmp2.get(j);
							if(!finalResultSet.contains(row)){
								finalResultSet.add(row);
							}
						}
						break;
						default:i--;
						break;
						// Distinct
				}
			}
			



	System.out.println("indexedColumns: "+indexedColumns);
	System.out.println("nonIndexedColumns: "+nonIndexedColumns);
	System.out.println("conditionsOfIndexedColumns: "+conditionsOfIndexedColumns);
	System.out.println("conditionsOfNonIndexedColumns: "+conditionsOfNonIndexedColumns);
	return finalResultSet.iterator();
	//return finalsubmission.Iterator();
}
// 	List <Tuple> rs = new ArrayList<Tuple>();
// 	Vector<Octree> indexOctrees = new Vector<Octree>();
// 	for(int i = 0;i<toBeUsed.size();i++){
// 		indexOctrees.add(ocs.get(i));
// 	} 

// 	// Vector<Vector<Hashtable<String,Object>>> R = new Vector<Vector<Hashtable<String,Object>>>();

// 	// StringTokenizer ST = new StringTokenizer(fullterm,"*");
	
// 	// while(ST.hasMoreTokens()){
// 	// 	String columnName1 = ST.nextToken();
// 	// 	String columnOpr1 = ST.nextToken();
// 	// 	String columnValue1 = ST.nextToken();
// 	// 	String midOpr1="";
// 	// 	if(ST.hasMoreTokens()){
// 	// 		midOpr1 = ST.nextToken();
		
// 	// 	if(indexedColumns.contains(columnName1)){
// 	// 		String columnName2 ="";
// 	// 		String columnOpr2 = ST.nextToken();
// 	// 	     String columnValue2 = ST.nextToken();



// 	// 		 String columnName3 ="";
// 	// 		 String columnOpr3 = ST.nextToken();
// 	// 		  String columnValue3 = ST.nextToken();

// 	// 		if(ST.hasMoreTokens()){
// 	// 			columnName2 = ST.nextToken();
// 	// 			columnOpr2 = ST.nextToken();
// 	// 			columnValue2 =ST.nextToken();

// 	// 		}

// 	// 	}
// 	// }

// 	// }

// Vector<List<Tuple>>  octresults= new Vector<>();





// 	for(int i = 0;i<indexOctrees.size();i++){
// 		Octree o = indexOctrees.get(i);
// 		// SQLTerm sql1 = conditionsOfIndexedColumns.get(3*i);
// 		// String colName1 = sql1.getStrColumnName();
// 		// String operator1 = sql1.getStrOperator();
// 		// Object value1 = sql1.getObjValue();

// 		// SQLTerm sql = conditionsOfIndexedColumns.get(3*i);
// 		// String colName = sql.getStrColumnName();
// 		// String operator = sql.getStrOperator();
// 		// Object value = sql.getObjValue();


// 		// SQLTerm sql = conditionsOfIndexedColumns.get(3*i);
// 		// String colName = sql.getStrColumnName();
// 		// String operator = sql.getStrOperator();
// 		// Object value = sql.getObjValue();
// 		SQLTerm s1 = h1.get(o.getColNames()[0]);
// 		SQLTerm s2 = h1.get(o.getColNames()[1]);
// 		SQLTerm s3 = h1.get(o.getColNames()[2]);
// 		String colName1 = s1.getStrColumnName();
// 		String operator1 = s1.getStrOperator();
// 		Object value1 = s1.getObjValue();
// 		String colName2 = s2.getStrColumnName();
// 		String operator2 = s2.getStrOperator();
// 		Object value2 = s2.getObjValue();
// 		String colName3 = s3.getStrColumnName();
// 		String operator3 = s3.getStrOperator();
// 		Object value3 = s3.getObjValue();


// List<Tuple> searchRes = o.search(operator1, value1, operator2, value2, operator3, value3);
// 		 rs.addAll( searchRes);
// 		 octresults.add(searchRes);

// 		// System.out.println("rs size: "+rs.size());

// 		//System.out.println("ANA DA5ALT HENA YA RAB");

// 	}
// 	StringTokenizer ST = new StringTokenizer(fullterm,"*");
	
//     while(ST.hasMoreTokens()){
// 		String columnName1 = ST.nextToken();
// 		String columnOpr1 = ST.nextToken();
// 		String columnValue1 = ST.nextToken();
// 		String midOpr1="";
// 		if(ST.hasMoreTokens()){
// 			midOpr1 = ST.nextToken();
		
// 		if(indexedColumns.contains(columnName1)){
// 			String columnName2 ="";
// 			String columnOpr2  = "";
// 		     String columnValue2 ="" ;



// 			 String columnName3 ="";
// 			 String columnOpr3 = "";
// 			  String columnValue3 = "";

// 			if(ST.hasMoreTokens()){
// 				columnName2 = ST.nextToken();
// 				columnOpr2 = ST.nextToken();
// 				columnValue2 =ST.nextToken();

// 			}
// 			if(indexedColumns.contains(columnName2)){

// 			}

// 		}
// 	}
// 	}
	
    //  

	
	//it = rs.iterator();



	}


		//end of else
		// Vector<Hashtable<String, Object>> fr = new Vector<Hashtable<String, Object>>();
		// while(it.hasNext()){
		// 	Tuple t = (Tuple) it.next();
		// 	Hashtable<String, Object> h = new Hashtable<String, Object>();
		// 	ArrayList<Reference> r = t.getReferences();
		// 	for(int i = 0;i<r.size();i++){
		// 		Reference ref = r.get(i);
		// 		int indexOfPage = ref.getPageNumber();
		// 		String pk = ref.getPkName();
		// 		Object pkValue = ref.getPkValue();
		// 		// Vector<Table> tables = (Vector<Table>) deserialize(tableName);
		// 		// Table table = tables.get(0);
		// 		// Vector<String> ids = table.getIds();
		// 		// for(int j = 0 ;j<ids.size();j++){

		// 		// }
		// 		Vector<Page> ps = (Vector<Page>) deserialize(tableName+"Page"+indexOfPage);
		// 		Page p = ps.get(0);
		// 		for(int j = 0;j<p.getData().size();j++){
		// 			Hashtable<String, Object> h1 = p.getData().get(j);
		// 			if(h1.get(pk).equals(pkValue)){
		// 				h = h1;
		// 				break;
		// 			}
		// 		}
		//  	}

		// 	fr.addElement(h);
		// }













//Vector<String> rs = new Vector<String>();



		
	//}

	private void evaluateConditionsIndividually(SQLTerm[] arrSQLTerms, String tableName, Vector<Vector<Hashtable<String, Object>>> results) throws DBAppException {
		System.out.println("Result in Methods Start: "+results);
		for (int i = 0;i<arrSQLTerms.length;i++){
			Vector<Hashtable<String, Object>> resultSet = new Vector<Hashtable<String, Object>>();
			SQLTerm sql = arrSQLTerms[i];
			String colName = sql.getStrColumnName();
			String operator = sql.getStrOperator();
			Object value = sql.getObjValue();
			System.out.println(value);
			System.out.println(operator);
			String dataType = (value instanceof Integer) ?  "int"  :  
					(value instanceof Double) ?  "double"   :
					(value instanceof String) ?  "string"   : "Date" ;
			Vector<Table> ts = (Vector<Table>) deserialize(tableName);
			Table t = ts.get(0);
			Vector<String> ids = t.getIds();
			for(int j = 0 ;j<ids.size();j++){
				Vector<Page> pages = (Vector<Page>) deserialize(tableName+"Page"+ids.get(j));
				Page p = pages.get(0);
				Vector<Hashtable<String,Object>> tuples = p.getData();
				for(int k = 0;k<tuples.size();k++){
					Hashtable<String,Object> tuple = tuples.get(k);
					//System.out.println(tuple);
					// if(tuple.get("gpa").equals(0.85)){
					// 	System.out.println("here");
					// }
					// A'  B  or   B'  A 
					switch(operator){
						 case ">":
							if(dataType.equals("int")){
								//System.out.println("Col Name : "+colName+ " Value : "+value+" Tuple.colName : "+tuple.get(colName));
								int test = (int) tuple.get(colName);
								if(test > (int) value){
									
									resultSet.add(tuple);
								}

								}
							
							else if(dataType.equals("double")){
								double test = (double) tuple.get(colName);
								if(test > (double) value){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("string")){
								String test = (String) tuple.get(colName);
								if(test.compareTo((String) value) > 0){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("Date")){
								Date test = (Date) tuple.get(colName);
								if(test.compareTo((Date) value) > 0){
									resultSet.add(tuple);
								}

							}
							
							break;
						case ">=":
							if(dataType.equals("int")){
								int test = (int) tuple.get(colName);
								if(test >= (int) value){
									resultSet.add(tuple);
								}

								}
							
							else if(dataType.equals("double")){
								double test = (double) tuple.get(colName);
								if(test >= (double) value){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("string")){
								String test = (String) tuple.get(colName);
								if(test.compareTo((String) value) >= 0){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("Date")){
								Date test = (Date) tuple.get(colName);
								if(test.compareTo((Date) value) >= 0){
									resultSet.add(tuple);
								}

							}
							break;
						case "<":
							if(dataType.equals("int")){
								int test = (int) tuple.get(colName);
								if(test < (int) value){
									resultSet.add(tuple);
								}

								}
							
							else if(dataType.equals("double")){
								double test = (double) tuple.get(colName);
								if(test < (double) value){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("string")){
								String test = (String) tuple.get(colName);
								if(test.compareTo((String) value) < 0){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("Date")){
								Date test = (Date) tuple.get(colName);
								if(test.compareTo((Date) value) < 0){
									resultSet.add(tuple);
								}

							}
							break;
						case "<=":
							if(dataType.equals("int")){
								int test = (int) tuple.get(colName);
								if(test <= (int) value){
									resultSet.add(tuple);
								}

								}
							
							else if(dataType.equals("double")){
								double test = (double) tuple.get(colName);
								if(test <= (double) value){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("string")){
								String test = (String) tuple.get(colName);
								if(test.compareTo((String) value) <= 0){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("Date")){
								Date test = (Date) tuple.get(colName);
								if(test.compareTo((Date) value) <= 0){
									resultSet.add(tuple);
								}

							}
							break;
						case "!=":
							if(dataType.equals("int")){
								int test = (int) tuple.get(colName);
								if(test != (int) value){
									resultSet.add(tuple);
								}

								}
							
							else if(dataType.equals("double")){
								double test = (double) tuple.get(colName);
								if(test != (double) value){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("string")){
								String test = (String) tuple.get(colName);
								if(test.compareTo((String) value) != 0){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("Date")){
								Date test = (Date) tuple.get(colName);
								if(test.compareTo((Date) value) != 0){
									resultSet.add(tuple);
								}

							}
							break;
						case "=":
							if(dataType.equals("int")){
								int test = (int) tuple.get(colName);
								if(test == (int) value){
									resultSet.add(tuple);
								}

								}
							
							else if(dataType.equals("double")){
								double test = (double) tuple.get(colName);
								if(test == (double) value){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("string")){
								String test = (String) tuple.get(colName);
								if(test.compareTo((String) value) == 0){
									resultSet.add(tuple);
								}
							}
							else if(dataType.equals("Date")){
								Date test = (Date) tuple.get(colName);
								if(test.compareTo((Date) value) == 0){
									resultSet.add(tuple);
								}

							}
							break;
						
					}

				}
			}
			/**/
			System.out.println("Result in Methods endResultset: "+resultSet);
			results.add(resultSet);
		}
	}
	private void addTheNames(String tableName, ArrayList<String> octreeNames) throws DBAppException {
		try {
			
			
		
			BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				if(values[0].equals(tableName)) {
					String octName = values[4];
					if(!octName.equals("null") && !octreeNames.contains(octName)) {
						octreeNames.add(octName);
					}
				}
			 }


		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new DBAppException();
		}
	}
	
	public void insertIntoTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
		insertIntoTable2(strTableName, htblColNameValue);
		fixTheRanges(strTableName, thePK);
		System.gc();
	}
	
	public static void serializeTable (Vector<Table> p,String name) throws DBAppException {
		try {
			//Vector<String> p = new Vector<String>();
			//  {1,2,3,4,8}  { () , () }
			
			FileOutputStream fileOut = new FileOutputStream(name + ".bin");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
			
			
		} catch (Exception i) {
			//i.printStackTrace();
			throw new DBAppException();
		}
		
		
	}


	public static void serializet (Vector<Table> p,String name) throws DBAppException {
		try {
			//Vector<String> p = new Vector<String>();
			//  {1,2,3,4,8}  { () , () }
			
			FileOutputStream fileOut = new FileOutputStream(name + ".bin");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
			
			
		} catch (Exception i) {
			//i.printStackTrace();
			throw new DBAppException();
		}
		
		
	}



	

//begining  of delete
	public static boolean searchAndDeleteNBString( String strTableName, Hashtable<String, Object> htblColNameValue,String key)
	 throws DBAppException {
		System.out.println("Ana gowa el delete non primary key");
		System.out.println("The hashtable : " +htblColNameValue);
		Vector<Table> tables = (Vector<Table>) deserialize(strTableName);
		Table table = tables.remove(0);
		boolean found = true;
		boolean firstFound = false;
		Vector<Page> pages = null  ;
		Page page = null ;
		int i = 0;
		for(;i<table.getIds().size();i++) {
			pages =  (Vector<Page>) deserialize(strTableName+"Page"+table.getIds().get(i));
			 page = pages.get(0);
		
			pages.remove(page);
			Vector<Hashtable<String,Object>> searchDomain = page.getData();
			for(int j = 0 ;j<searchDomain.size();j++) {
				Hashtable<String,Object> h = searchDomain.get(j);
				System.out.println("Current hash_table: "+h);
				Iterator<String> itr = htblColNameValue.keySet().iterator();
				for(int k = 0;k < htblColNameValue.keySet().size();k++) {
					String tmp = itr.next();
					if(!(htblColNameValue.get(tmp).equals( h.get(tmp))))
						found = false;
					System.out.println("found: "+found);

					System.out.println("Htbl: "+htblColNameValue.get(tmp));
					System.out.println("h.get(tmp): "+h.get(tmp));
					System.out.println("tmp "+tmp);
					System.out.println(htblColNameValue.get(tmp).equals( h.get(tmp)));
				}
				
				System.out.println("found1: "+found);
				if(found) {
					System.out.println("Ana gowa el found");
					ArrayList<String> names = new ArrayList<String>();
					for(int k=0;k<ocs.size();k++){
						if(ocs.get(k).getTableName().equals(strTableName)){
							names.add(ocs.get(k).getTableName());
						}
					}

					deleteExtraRowFromOctree(key, names, table.getIds().get(i), page.getData().get(j));


					page.getData().remove(j);
					page.setSize(page.getSize()-1);
					firstFound = true;
				}
				else
					found = true;
			}
			if(page.getSize() != 0)
			{
				pages.add(page);
				serialize(pages,strTableName+"Page"+(table.getIds().get(i)));
				





			}
			else {
				System.out.println("i: "+i);
				System.out.println("table.getIDs.get(): "+table.getIds().get(i));
				String fileName = strTableName+"Page"+(table.getIds().get(i))+".bin";
				table.getIds().remove(i);
                   File file = new File(fileName);
        
                   if (file.delete()) {
                    System.out.println("File " + fileName + " deleted successfully.");
                  } else {
                    System.out.println("Failed to delete file " + fileName);
        }

				
			}
			

			
		}
	
		tables.add(table);
		serializet(tables,strTableName);
		tables=null;
		table=null;
		pages=null;
		page=null;
		//System.gc();
		return firstFound;
	}


	private static String fixTheDate(Date test) {
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


	public static boolean searchForDeleteB(String tableName,String keyValue,String key ,
	String keyDataType,Hashtable<String, Object> htblColNameValue) throws DBAppException{
	   Vector<Table> tables = (Vector<Table>) deserialize(tableName);
	   Table table = tables.remove(0);
	   // Note : we won't edit sth. in the table so no need to remove it from the vector and re add it and reserialize it again
	   Object o = null;
	   SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	   //Date date = dateFormat.parse(dateString);
	   switch(keyDataType){
		   case theInt : o = Integer.parseInt(keyValue);break;
		   case theString : o = keyValue; break ;
		   case theDouble : o = new Double(keyValue);break;
		   case theDate : 
				//Date x = new Date(keyValue);
				//System.out.println("IBRAHIIIIIIIIIIIIIIIM"+key);
				//Date x = (Date) htblColNameValue.get(key);
				//String in = "" ;


				   o = (Date) htblColNameValue.get(key);
			   break;

	   }

	   int pageIndex = table.search(o, keyDataType);
	   System.out.println("page index hna fe getIDs() : "+table.getIds().get(pageIndex));
	   if(pageIndex==-1){ // row does't exist
		   return false;
	   }
	   else{ // if the row exists it will be in this page , bec. the row is within its range
		   
		   Vector<Page> pages =  (Vector<Page>) deserialize(tableName+"Page"+(table.getIds().get(pageIndex)));
		   Page page = pages.get(0);
		   pages.remove(page);
		   Hashtable<String,Object> doesExist = null;
		   System.out.println(keyValue);
		   switch(keyDataType){
			   case theInt : 
				   Integer iTMP = Integer.parseInt(keyValue) ;
				   int iValue = iTMP.intValue();
				   doesExist = page.binarySearchInteger(iValue, key);
				   break;
			   case theString : doesExist = page.binarySearchString(keyValue, key); break ;
			   case theDouble : doesExist = page.binarySearchDouble(new Double(keyValue), key) ;break;
			   case theDate : 
			   	Date y = (Date) htblColNameValue.get(key);
				
			 
			 
			   doesExist = page.binarySearchDate(fixTheDate(y), key); break;
   
		   }
		
		   if (doesExist != null) { // we now have the row that will be updated
			   // وصلنا بالسلامه الحمد الله
		   System.out.println("To Be Deleted: "+ page.getData().get(page.getData().indexOf(doesExist)));
		   



		   Iterator<String> itr = htblColNameValue.keySet().iterator();
		   for(int k = 0;k < htblColNameValue.keySet().size();k++) {
			   String tmp = itr.next();
			   if(!(htblColNameValue.get(tmp).equals( doesExist.get(tmp))))
			     	return false;
			


		   }
		   String pass = pageIndex+"";
		   ArrayList <String> names = new ArrayList<String>();
		   for(int i =0;i<ocs.size();i++){
			Octree oc = ocs.get(i);
			if(oc.getTableName().equals(tableName)){
				names.add(oc.getTableName());
			}
		   }

			deleteExtraRowFromOctree(key, names, pass, doesExist);


				page.getData().remove(page.getData().indexOf(doesExist));
			   page.setSize(page.getSize()-1);
			   if(page.getSize() != 0)
			   {
				   pages.add(page);
				   serialize(pages,tableName+"Page"+(table.getIds().get(pageIndex)));

				   
			   }
			   if(page.getSize() == 0) {
				   System.out.println("Table _ID:"+ table.getIds());
				   System.out.println("page Index hna mfish.getIds() :"+pageIndex);
				 


				  


				   String fileName = tableName+"Page"+(table.getIds().get(pageIndex))+".bin";
				   table.getIds().remove(pageIndex);
                   File file = new File(fileName);
        
                   if (file.delete()) {
                    System.out.println("File " + fileName + " deleted successfully.");
                  } else {
                    System.out.println("Failed to delete file " + fileName);
        }








				   
			   }
			   System.out.println("Please esht8aly");
			   tables.add(table);
			   serializet(tables,tableName);
			   tables=null;
			   table=null;
			   pages=null;
			   page=null;
			   //System.gc();
			   return true; // تم عمل ابديت بنجاح   :)
		   }
		   else {
			tables=null;
			   table=null;
			   pages=null;
			   page=null;
			   return false;
		   }
		   
		   
	   }
}


public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
	if(!exists(strTableName)) {
		throw new DBAppException("Table doesn't exist");
	}

	if(htblColNameValue.isEmpty()){
		Vector<Table> thetables = (Vector<Table>)deserialize(strTableName);
		Table thetable = thetables.get(0);
		while(!(thetable.getIds().isEmpty())){
			String fileName = strTableName+"Page"+(thetable.getIds().get(0))+".bin";
			thetable.getIds().remove(0);
			File file = new File(fileName);
 
			if (file.delete()) {
			 System.out.println("File " + fileName + " deleted successfully.");
		   } else {
			 System.out.println("Failed to delete file " + fileName);
		}
	}

	serializeTable(thetables, strTableName);
	thetables=null;
	thetable=null;

	return;
	}
	boolean isTypeCorrect = true;
	try {
		BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
		String line = br.readLine();
		String key = "";
		String keyDataType = "";
		String keyValue ="";
		while ((line = br.readLine()) != null) {
			String[] values = line.split(",");
			if(values[0].equals(strTableName)) {
				// we got the table
				if(values[3].equals("True")) {
					// we got the PK
					key = values[1];
					keyDataType = 	values[2];
					keyValue =htblColNameValue.get(values[1])+"";
					System.out.println("mmmmmmmmmmmmmmmmmmmmmmmm"+keyValue);
					// Up till here we knew the following : 
					// 1) The table exists
					// 2) what is the table's Primary key
					// 3) what is the table's Primary Key data type
					}
				for (int j = 0;j<htblColNameValue.keySet().size();j++) {//check if data is valid
					String tmp = htblColNameValue.keySet().iterator().next();
					if(values[1].equals(tmp)){ 
					// we got the cloumn now we check if it is correct data type and within the range 
						int variable = 1;
						if(values[2] .equals(theInt)) {
							if(!(htblColNameValue.get(values[1]) instanceof Integer) )
								isTypeCorrect = false;


						}
						if(values[2].equals(theString)) {
							if(!(htblColNameValue.get(values[1]) instanceof String) )
								isTypeCorrect = false;

						}
						if(values[2].equals(theDate)) {
							if(!(htblColNameValue.get(values[1]) instanceof Date) )
								isTypeCorrect = false;

						}
						if(values[2].equals(theDouble)) {
							if(!(htblColNameValue.get(values[1]) instanceof Double) )
								isTypeCorrect = false;

						}
						
					}
				}
		}
		}
			if(!isTypeCorrect) {
				throw new DBAppException("The values of columns do not match the column datatypes");
			}
		//I updated it to be keyValue != null because the hashtable may not include value for the pk 
			if(!(keyValue .equals( "null"))) {
				boolean x = searchForDeleteB(strTableName,keyValue,key,keyDataType, htblColNameValue);
				fixTheRanges(strTableName, key);
//					if(x == false) {
//						throw new DBAppException("No row matches");
//					}
			}
			else {
				searchAndDeleteNBString(strTableName,htblColNameValue,key);
				fixTheRanges(strTableName, key);
			}




			
	
	
	
	
			br.close();

}catch(Exception e) {
	throw new DBAppException("Exception was thrown while deleting");
}
System.gc();
}





}
