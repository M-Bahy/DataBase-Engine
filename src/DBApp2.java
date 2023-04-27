import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;
import java.time.LocalDate;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;


public class DBApp2 {
	private boolean firstTable = false;
	private int n;
	private static String thePK = "";
	private final static String theString = "java.lang.String";
	private final static String theDouble = "java.lang.Double";
	private final static String theDate = "java.util.Date";
	private final static String theInt = "java.lang.Integer";

	public static void main(String[] args) throws Exception {
	DBApp dbApp = new DBApp();
	dbApp.init();
	/*createTheTables(dbApp);
	Hashtable<String, Object> htblColNameType = new Hashtable<String, Object>();
	htblColNameType.put("pc_id", 750);
    dbApp.insertIntoTable("pcs", htblColNameType);

	Vector<Page> pp = (Vector<Page>) deserialize("pcsPage1");
	Page p = pp.get(0);
	System.out.println(p.getData());*/

	/*Hashtable<String, Object> htblColNameType3 = new Hashtable<String, Object>();
	htblColNameType3.put("student_id", "52-1096");	
	dbApp.updateTable("pcs", "750", htblColNameType3);


	Hashtable<String, Object> htblColNameType2 = new Hashtable<String, Object>();
	htblColNameType2.put("student_id", "52-1096");
	dbApp.deleteFromTable("pcs", htblColNameType2);*/

	/*Vector<Page> mm = (Vector<Page>) deserialize("pcsPage1");
	Page m = mm.get(0);
	System.out.println(m.getData());*/
	




	

	 
	}

	public static void bahy() {
        final DBApp dbApp = new DBApp();
       // dbApp.init();
		//createTheTables(dbApp);
        String table = "students";
        Hashtable<String, Object> row = new Hashtable();
        row.put("id", "31-1235");
        row.put("first_name", "foo");
        row.put("middle_name", "bateekh");
        row.put("last_name", "bar");


        Date dob = new Date(1995 - 1900, 4 - 1, 1);
        row.put("dob", dob);

        row.put("gpa", 1.1);


        Assertions.assertThrows(DBAppException.class, () -> {
                    dbApp.insertIntoTable(table, row);
                }
        );

    }


    public void testSetPageSize() throws Exception {
        String configFilePath = "src/main/resources/";

        if (Files.exists(Paths.get(configFilePath, "DBApp.config"))) {
            configFilePath = Paths.get(configFilePath, "DBApp.config").toString();
        } else if (Files.exists(Paths.get(configFilePath, "DBApp.properties"))) {
            configFilePath = Paths.get(configFilePath, "DBApp.properties").toString();
        } else {
            throw new Exception("`DBApp.config` does not exist in resources directory");
        }

        List<String> config = Files.readAllLines(Paths.get(configFilePath));
        boolean lineFound = false;
        for (int i = 0; i < config.size(); i++) {
            if (config.get(i).toLowerCase().contains("page")) {
                config.set(i, config.get(i).replaceAll("\\d+", "250"));
                lineFound = true;
                break;
            }
        }

        if (!lineFound) {
            throw new Exception("Cannot set page size, make sure that key `MaximumRowsCountinTablePage` is present in DBApp.config");
        }

//        System.out.println("CONFIG" + configFilePath);
        Files.write(Paths.get(configFilePath), config);

    }

   
    public void testClearMetaDataFile() throws Exception {

        String metaFilePath = "src/main/resources/metadata.csv";
        File metaFile = new File(metaFilePath);

        if (!metaFile.exists()) {
            throw new Exception("`metadata.csv` in Resources folder does not exist");
        }

        PrintWriter writer = new PrintWriter(metaFile);
        writer.write("");
        writer.close();
    }

    
    public void testDataDirectory() throws Exception {
        DBApp dbApp = new DBApp();
        dbApp.init();

        String dataDirPath = "src/main/resources/data";
        File dataDir = new File(dataDirPath);

        if (!dataDir.isDirectory() || !dataDir.exists()) {
            throw new Exception("`data` Directory in Resources folder does not exist");
        }

        ArrayList<String> files = new ArrayList<>();
        try {
            files = Files.walk(Paths.get(dataDirPath))
                    .map(f -> f.toAbsolutePath().toString())
                    .filter(p -> !Files.isDirectory(Paths.get(p)))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(files);
        for (String file : files) {
            Files.delete(Paths.get(file));
        }
    }

    
    public void testTableCreation() throws Exception {
        DBApp dbApp = new DBApp();
        dbApp.init();

        createStudentTable(dbApp);
        createCoursesTable(dbApp);
        createTranscriptsTable(dbApp);
        createPCsTable(dbApp);

        dbApp = null;
    }

    
    public void testRecordInsertions() throws Exception {
        DBApp dbApp = new DBApp();
        dbApp.init();
        dbApp.setN(10);
        DBApp2 dbApp2 = new DBApp2();
        createTheTables(dbApp);
       // dbApp.createTheTables(dbApp);
        int limit = 80;

        //insertStudentRecords(dbApp, limit);
       insertCoursesRecords(dbApp, limit);
      //  insertTranscriptsRecords(dbApp, limit);
        //insertPCsRecords(dbApp, limit);
     dbApp = null;
    }
	// 30-06-09

  
    public void testExtraStudentsInsertion() {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "students";
        Hashtable<String, Object> row = new Hashtable();
        row.put("id", "31-1235");
        row.put("first_name", "foo");
        row.put("middle_name", "bateekh");
        row.put("last_name", "bar");


        Date dob = new Date(1995 - 1900, 4 - 1, 1);
        row.put("dob", dob);

        row.put("gpa", 1.1);


        Assertions.assertThrows(DBAppException.class, () -> {
                    dbApp.insertIntoTable(table, row);
                }
        );

    }

    
    public void testExtraCoursesInsertion() {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "courses";
        Hashtable<String, Object> row = new Hashtable();

        Date date_added = new Date(2011 - 1900, 4 - 1, 1);
        row.put("date_added", date_added);

        row.put("course_id", "foo");
        row.put("course_name", "bar");
        row.put("hours", 13);
        row.put("semester", 5);

        Assertions.assertThrows(DBAppException.class, () -> {
                    dbApp.insertIntoTable(table, row);
                }
        );

    }

   
    public void testExtraTranscriptsInsertion() {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "transcripts";
        Hashtable<String, Object> row = new Hashtable();
        row.put("gpa", 1.5);
        row.put("student_id", "34-9874");
        row.put("course_name", "bar");
        row.put("elective", true);


        Date date_passed = new Date(2011 - 1900, 4 - 1, 1);
        row.put("date_passed", date_passed);


        Assertions.assertThrows(DBAppException.class, () -> {
                    dbApp.insertIntoTable(table, row);
                }
        );
    }


    
    public void testExtraPCsInsertion() {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "pcs";
        Hashtable<String, Object> row = new Hashtable();
        row.put("pc_id", 50);
        row.put("student_id", "31-12121");
        row.put("room", "C7.02");

        Assertions.assertThrows(DBAppException.class, () -> {
                    dbApp.insertIntoTable(table, row);
                }
        );
    }

    
    public void testUpdateStudents() throws Exception {
        DBApp dbApp = new DBApp();
        //dbApp.init();
		

        BufferedReader studentsTable = new BufferedReader(new FileReader("students_table.csv"));
        String record;
        int c = 1;

        String clusteringKey = "";
        Hashtable<String, Object> row = new Hashtable<>();
        while ((record = studentsTable.readLine()) != null && c > 0) {
            String[] fields = record.split(",");
            clusteringKey = fields[0];
            c--;
        }
        studentsTable.close();

        String table = "students";

        row.put("first_name", "fooooo");
        row.put("last_name", "baaaar");

        Date dob = new Date(1992 - 1900, 9 - 1, 8);
        row.put("dob", dob);
        row.put("gpa", 1.1);

        dbApp.updateTable(table, clusteringKey, row);
        dbApp = null;
    }

    
    public void testUpdateCourses() throws Exception {
        DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "courses";

        BufferedReader coursesTable = new BufferedReader(new FileReader("courses_table.csv"));
        String record;
        Hashtable<String, Object> row = new Hashtable<>();
        int c = 1;

        String clusteringKey = "";
        while ((record = coursesTable.readLine()) != null && c > 0) {
            String[] fields = record.split(",");
            clusteringKey += fields[0].trim().substring(0, 4);
            clusteringKey += "-";
            clusteringKey += fields[0].trim().substring(5, 7);
            clusteringKey += "-";
            clusteringKey += fields[0].trim().substring(8);

            c--;
        }

        coursesTable.close();

        row.put("course_id", "1100");
        row.put("course_name", "baaaar");
        row.put("hours", 13);


        dbApp.updateTable(table, clusteringKey, row);
        dbApp = null;
    }

    
    public void testUpdateTranscripts() throws Exception {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "transcripts";
        Hashtable<String, Object> row = new Hashtable();

        BufferedReader transcriptsTable = new BufferedReader(new FileReader("transcripts_table.csv"));
        String record;

        int c = 1;
        String clusteringKey = "";
        while ((record = transcriptsTable.readLine()) != null && c > 0) {
            String[] fields = record.split(",");
            clusteringKey = fields[0].trim();

            c--;
        }

        transcriptsTable.close();

        row.put("student_id", "43-9874");
        row.put("course_name", "baaaar");

        Date date_passed = new Date(2011 - 1900, 4 - 1, 1);
        row.put("date_passed", date_passed);

        dbApp.updateTable(table, clusteringKey, row);
    }

    
    public void testUpdatePCs() throws Exception {
        final DBApp dbApp = new DBApp();
       //dbApp.init();

        String table = "pcs";
        Hashtable<String, Object> row = new Hashtable();
        row.put("student_id", "51-3808");

        BufferedReader pcsTable = new BufferedReader(new FileReader("pcs_table.csv"));
        String record;

        int c = 1;
        String clusteringKey = "";

        while ((record = pcsTable.readLine()) != null && c > 0) {
            String[] fields = record.split(",");
            clusteringKey = fields[0].trim();
            c--;

        }

        pcsTable.close();

        dbApp.updateTable(table, clusteringKey, row);

    }

    
    public void testUpdateStudentsExtra() {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "students";
        Hashtable<String, Object> row = new Hashtable();
        row.put("first_name", "foo");
        row.put("middle_name", "hamada");
        row.put("last_name", "bar");

        Date dob = new Date(1992 - 1900, 9 - 1, 8);
        row.put("dob", dob);
        row.put("gpa", 0.1);

        try {
			dbApp.updateTable(table, "82-8772", row);
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			System.out.println("it works");
		}

    }

    
    public void testUpdateCoursesExtra() {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "courses";
        Hashtable<String, Object> row = new Hashtable();
        row.put("elective", true);
        row.put("course_id", "foo");
        row.put("course_name", "bar");
        row.put("hours", 13);
        row.put("semester", 5);
		try {
			dbApp.updateTable(table, "2000-04-01", row);
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			System.out.println("it works");
		}

        
    }

    
    public void testUpdateTranscriptsExtra() {
        final DBApp dbApp = new DBApp();
        //dbApp.init();

        String table = "transcripts";
        Hashtable<String, Object> row = new Hashtable();

        row.put("student_id", "34-9874");
        row.put("course_name", "bar");

        Date date_passed = new Date(2011 - 1900, 4 - 1, 1);
        row.put("date_passed", date_passed);

        row.put("elective", true);
		try {
			dbApp.updateTable(table, "1.57", row);
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			System.out.println("it works");
		}
        

    }

    
    public void testUpdatePCsExtra() {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        String table = "pcs";
        Hashtable<String, Object> row = new Hashtable();

        row.put("student_id", "79-0786");
        row.put("os", "linux");

        try {
			dbApp.updateTable(table, "00353", row);
		} catch (DBAppException e) {
			// TODO Auto-generated catch block
			System.out.println("it works");
		}


    }

    
    public void testStudentsDeletionComplex() throws Exception {
        final DBApp dbApp = new DBApp();
      //  dbApp.init();

        String table = "students";
        Hashtable<String, Object> row = new Hashtable();

        BufferedReader studentsTable = new BufferedReader(new FileReader("students_table.csv"));
        String record;
        int c = 0;
        int finalLine = 1;


        while ((record = studentsTable.readLine()) != null && c <= finalLine) {
            if (c == finalLine) {
                String[] fields = record.split(",");
                row.put("id", fields[0]);

                int year = Integer.parseInt(fields[3].trim().substring(0, 4));
                int month = Integer.parseInt(fields[3].trim().substring(5, 7));
                int day = Integer.parseInt(fields[3].trim().substring(8));


                Date dob = new Date(year - 1900, month - 1, day);
                row.put("dob", dob);

                double gpa = Double.parseDouble(fields[4].trim());

                row.put("gpa", gpa);

            }
            c++;
        }
        studentsTable.close();


        dbApp.deleteFromTable(table, row);

    }

    
    public void testCoursesDeleteComplex() throws Exception {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        BufferedReader coursesTable = new BufferedReader(new FileReader("courses_table.csv"));
        String record;
        Hashtable<String, Object> row = new Hashtable<>();
        int c = 0;
        int finalLine = 1;
        while ((record = coursesTable.readLine()) != null && c <= finalLine) {
            if (c == finalLine) {
                String[] fields = record.split(",");

                int year = Integer.parseInt(fields[0].trim().substring(0, 4));
                int month = Integer.parseInt(fields[0].trim().substring(5, 7));
                int day = Integer.parseInt(fields[0].trim().substring(8));

                Date dateAdded = new Date(year - 1900, month - 1, day);
                row.put("date_added", dateAdded);
                row.put("course_name", fields[2]);


            }
            c++;
        }


        String table = "courses";

        dbApp.deleteFromTable(table, row);
    }


    
    public void testTranscriptsDeleteComplex() throws Exception {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        BufferedReader transcriptsTable = new BufferedReader(new FileReader("transcripts_table.csv"));
        String record;
        Hashtable<String, Object> row = new Hashtable<>();
        int c = 0;
        int finalLimit = 1;
        while ((record = transcriptsTable.readLine()) != null && c <= finalLimit) {
            if (c == finalLimit) {
                String[] fields = record.split(",");
                row.put("gpa", Double.parseDouble(fields[0].trim()));
                row.put("course_name", fields[2].trim());
            }
            c++;
        }

        transcriptsTable.close();

        String table = "transcripts";

        dbApp.deleteFromTable(table, row);
    }

    
    public void testPCsDeleteComplex() throws Exception {
        final DBApp dbApp = new DBApp();
       // dbApp.init();

        BufferedReader pcsTable = new BufferedReader(new FileReader("pcs_table.csv"));
        String record;
        Hashtable<String, Object> row = new Hashtable<>();
        int c = 0;
        int finalLine = 1;
        while ((record = pcsTable.readLine()) != null && c <= finalLine) {
            if(c == finalLine) {
                String[] fields = record.split(",");

                row.put("pc_id", Integer.parseInt(fields[0].trim()));
                row.put("student_id", fields[1].trim());
            }
            c++;
        }


        String table = "pcs";
        dbApp.deleteFromTable(table, row);
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
            Vector<String> s = (Vector<String>) deserialize("tableNames");
            System.out.println(s);
			if(fields[0].equals("52-2476")){
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
	public static void fixTheRanges (String tableName,String pk){
		Vector<Table> tables = (Vector<Table>) deserialize(tableName);
		Table t = tables.get(0);
		for (int i =0 ;i<t.getIds().size();i++){
			Vector<Page> pp = (Vector<Page>) deserialize(tableName+"Page"+t.getIds().get(i));
			Page p = pp.get(0);
			t.getRange().get(i).setMin(p.getData().get(0).get(pk));
			t.getRange().get(i).setMax(p.getData().get(p.getData().size()-1).get(pk));
		}
		serializeTable(tables, tableName);
	}

	private static void printData() {
		Vector<Table> tt = (Vector<Table>) deserialize("students");
		Table t = tt.get(0);
		System.out.println( "Total number of Pages : " + t.getIds().size());
		System.out.println();
		System.out.println(t.getIds());
		for(String s:t.getIds()){
			Vector<Page> pages = (Vector<Page>)  deserialize("studentsPage"+s);
			Page p1 = pages.get(0);
			System.out.println("Page "+s+" Data : ");
			for(Hashtable<String,Object> h1:p1.getData())
			System.out.println(h1.get("id")+" "+h1.get("first_name")+" "+h1.get("last_name")+" "+h1.get("dob")+" "+h1.get("gpa"));
			//System.out.println(h1.get("first_name")+" "+h1.get("last_name")+" "+h1.get("dob")+" "+h1.get("gpa"));
			System.out.println();
		}
	}

	private static void displayThe2Pages() {
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

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public void init() {
		this.setN(2);
		
		
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
		
		
		// write n in csv
		try {
			FileWriter csvWriter = new FileWriter("metadata.csv", true);
			csvWriter.write(Integer.toString(this.getN()) + "\n");
			csvWriter.flush();
			csvWriter.close();
		} catch (Exception e) {
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
		

		//Table table = new Table(strTableName, strClusteringKeyColumn);

		
		

		String ans = "";

		for (String s : htblColNameType.keySet()) {

			if (s == strClusteringKeyColumn)
				found = true;
			if (htblColNameMin.containsKey(s) != true || htblColNameMax.containsKey(s) != true) {
				throw new DBAppException("There is no min and max specified for Column: " + s);
			}
			if ((htblColNameType.get(s)).compareTo(theInt) != 0 && (htblColNameType.get(s)).compareTo(theString) != 0
					&& (htblColNameType.get(s)).compareTo(theDouble) != 0 && (htblColNameType.get(s)).compareTo(theDate) != 0){
						System.out.println(htblColNameType.get(s));
				throw new DBAppException("This is data type is not supported");
					}
			if (htblColNameMin.get(s).compareTo(htblColNameMax.get(s)) > 0)
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
				//Vector<String> p = new Vector<String>();
				//  {1,2,3,4,8}  { () , () }
				Vector<Table> p = new Vector<Table>();
				Table t = new Table (strTableName);
				p.add(t);
				FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(p);
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

	public void insertIntoTable2(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {     
		// check if table exit
		Vector<String> tableNames = new Vector<String>();
		boolean [] condition = new boolean [htblColNameValue.keySet().size()];
		int count = 0;

		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tableNames.bin"));
			tableNames = (Vector<String>) in.readObject();
			in.close();

		} catch (Exception i) {
			throw new DBAppException();
		}
		if(!tableNames.contains(strTableName)) {
			throw new DBAppException("Table doesn't exit");
		}
		try {
			BufferedReader br = new BufferedReader(new FileReader("metadata.csv"));
			String line = br.readLine();
			int N = Integer.parseInt(line);
			String pk = "";
			String dataType = "";
			boolean isClustering = false;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if(values[0].equals(strTableName)) {
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
                			
                			if(    (  values[6].compareTo( ( (String)     htblColNameValue.get(values[1])       ) )     == 1         
                        			||   values[7].compareTo(((String)htblColNameValue.get(values[1])))   == -1     )  )
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
            
			// change1
        	Vector<Table> p = (Vector<Table>) deserialize(strTableName); // without + ".bin
        	Table t = p.get(0);
			p.remove(t);
        	if(t.getIds().isEmpty()) {
        		Page p1 = new Page();
        		p1.getData().add(htblColNameValue);
        		p1.setSize(p1.getSize()+1);
        		Vector<Page> v = new Vector<Page>();
        		v.add(p1);
        		// serialize p1
        		serialize(v, (strTableName+"Page"+1));
        		t.getIds().add("1");
				// System.out.println(t.getIds().get(0));
        		t.getRange().add(new Pair (htblColNameValue.get(pk),htblColNameValue.get(pk)));
				
				try {
					//Vector<String> p = new Vector<String>();
					//  {1,2,3,4,8}  { () , () }
					p = new Vector<Table>();
					//Table t = new Table (strTableName);
					p.add(t);
					FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(p);
					out.close();
					fileOut.close();
					
					
				} catch (Exception i) {
					i.printStackTrace();
					throw new DBAppException("moshkela fe table object");
				}
        	    
        		
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
        			
        				
					if(dataType.compareTo(theInt) == 0) {
						pp.insertHashTableINT(htblColNameValue, pk);
						t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
						t.getRange().get(index).setMin(pp.getData().get(0).get(pk));

						System.out.println("*******************************************************************");
						System.out.println(pp.getData());
						System.out.println("*******************************************************************");
						if((int) pp.getData().get(0).get("id") == 13){
							System.out.println("Aywa ana 13");
						}
						}
					else {
						if(dataType.compareTo(theString) == 0) {
							System.out.println("OMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR");
							System.out.println(pp.getData());
							System.out.println("OMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR");
							pp.insertHashTableString(htblColNameValue, pk);
							t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
							t.getRange().get(index).setMin(pp.getData().get(0).get(pk));
						}
						else {
							if(dataType.compareTo(theDouble) == 0) {
								pp.insertHashTableDOUBLE(htblColNameValue, pk);
								t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
								t.getRange().get(index).setMin(pp.getData().get(0).get(pk));
							}
							else {
								if(dataType.compareTo(theDate) == 0) {
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
							System.out.println("AYWAAAAAAAA >N");
							int shiftedindex = pp.getData().size() - 1;
							Hashtable<String, Object> shiftedRow = pp.getData().get(shiftedindex); // last entry to shift 
							pp.getData().remove(shiftedindex);
							
							pp.setSize(pp.getSize() - 1);
							t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
							System.out.println("++++++++++++++++++++++++++++++++++++++");
							System.out.println("THE SHIFTED ROW : "+shiftedRow);
							System.out.println("++++++++++++++++++++++++++++++++++++++");
							//serialize pp 
							String oldPID = pageID;
						
							try {
								int i = 1;
								Object oldMAX = null;
								Object oldMIN = null;
								while(true){
								pageID = t.getIds().get(index + i);
        						Vector <Page> v1 = (Vector <Page>) deserialize(strTableName+"Page"+pageID);
        						Page pp1 = v1.get(0);
								
								if(dataType.compareTo(theInt) == 0) {
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
									if(dataType.compareTo(theString) == 0) {
										if(!pp1.getData().contains(shiftedRow)){
											
										pp1.insertHashTableString(shiftedRow, pk);
										t.getRange().get(index).setMax(pp1.getData().get(pp.getData().size()-1).get(pk));
										t.getRange().get(index).setMin(pp1.getData().get(0).get(pk));
										}
									}
									else {
										if(dataType.compareTo(theDouble) == 0) {
											pp1.insertHashTableString(shiftedRow, pk);
											t.getRange().get(index).setMax(pp1.getData().get(pp.getData().size()-1).get(pk));
											t.getRange().get(index).setMin(pp1.getData().get(0).get(pk));
										}
										else {
											if(dataType.compareTo(theDate) == 0) {
												pp1.insertHashTableString(shiftedRow, pk);
												t.getRange().get(index).setMax(pp1.getData().get(pp.getData().size()-1).get(pk));
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
								
								// 2. serialize the table
								try {
									Vector<Table> tableV = new Vector<Table>();
									tableV.add(t);
									FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
									ObjectOutputStream out = new ObjectOutputStream(fileOut);
									out.writeObject(tableV);
									out.close();
									fileOut.close();
									
									
								} catch (Exception e) {
									e.printStackTrace();
									throw new DBAppException("moshkela fe table object");
								}


									return;
								} 
								
								//else 
								//shiftedRow = pp1.getData().get(shiftedindex);
								shiftedRow = pp1.getData().remove(pp1.getData().size()-1);
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
								try {
									Vector<Table> tableV = new Vector<Table>();
									tableV.add(t);
									FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
									ObjectOutputStream out = new ObjectOutputStream(fileOut);
									out.writeObject(tableV);
									out.close();
									fileOut.close();
									
									
								} catch (Exception e) {
									e.printStackTrace();
									throw new DBAppException("moshkela fe table object");
								}
									}
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



							// 2. serialize the table
							t.getIds().add(pageID);
							t.getRange().add(new Pair(shiftedRow.get(pk), shiftedRow.get(pk)));
							try {
								Vector<Table> tableV = new Vector<Table>();
								tableV.add(t);
								FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
								ObjectOutputStream out = new ObjectOutputStream(fileOut);
								out.writeObject(tableV);
								out.close();
								fileOut.close();
								
								
							} catch (Exception i) {
								i.printStackTrace();
								throw new DBAppException("moshkela fe table object");
							}
							

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
							out.writeObject(tableV);
							out.close();
							fileOut.close();
							
							
						} catch (Exception e) {
							e.printStackTrace();
							throw new DBAppException("moshkela fe table object");
						}

        			
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
						if(dataType.compareTo(theInt) == 0) {
							pp.insertHashTableINT(htblColNameValue, pk);
							t.getRange().get(ind).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
							t.getRange().get(ind).setMin(pp.getData().get(0).get(pk));
							}
						else {
							if(dataType.compareTo(theString) == 0) {
								pp.insertHashTableString(htblColNameValue, pk);
								t.getRange().get(ind).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
								t.getRange().get(ind).setMin(pp.getData().get(0).get(pk));
							}
							else {
								if(dataType.compareTo(theDouble) == 0) {
									pp.insertHashTableDOUBLE(htblColNameValue, pk);
									t.getRange().get(ind).setMax(pp.getData().get(pp.getData().size()-1).get(pk));
									t.getRange().get(ind).setMin(pp.getData().get(0).get(pk));
								}
								else {
									if(dataType.compareTo(theDate) == 0) {
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
							serialize(v1, (strTableName+"Page"+pageID));
								
							// 2. serialize the table
							try {
								Vector<Table> tableV = new Vector<Table>();
								tableV.add(t);
								FileOutputStream fileOut = new FileOutputStream(strTableName + ".bin");
								ObjectOutputStream out = new ObjectOutputStream(fileOut);
								out.writeObject(tableV);
								out.close();
								fileOut.close();
									
									
							} catch (Exception e) {
								e.printStackTrace();
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
								
								
						} catch (Exception i) {
							i.printStackTrace();
							throw new DBAppException("moshkela fe table object");
						}
					}
        			
        		}
        		
     
        	}
            
            
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// here we should serialize the "insert 13" probllem
	}

	public void updateTable(String strTableName, String strClusteringKeyValue,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {
				// following method updates one row only
				// htblColNameValue holds the key and new value
				// htblColNameValue will not include clustering key as column name
				// strClusteringKeyValue is the value to look for to find the row to update.
				if(!found(strTableName, strClusteringKeyValue, htblColNameValue))
					throw new DBAppException();
				
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
                			
                			if(    (  values[6].compareTo( ( (String)     htblColNameValue.get(values[1])       ) )     == 1         
                        			||   values[7].compareTo(((String)htblColNameValue.get(values[1])))   == -1     )  )
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
		} catch (Exception e) {
			System.out.println(e.getCause());
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
	 String keyDataType,Hashtable<String, Object> htblColNameValue){
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
					e.printStackTrace();
				} break;

		}

		int pageIndex = table.search(o, keyDataType);
		if(pageIndex==-1){ // row does't exist
			return false;
		}
		else{ // if the row exists it will be in this page , bec. the row is within its range
			
			Vector<Page> pages =  (Vector<Page>) deserialize(tableName+"Page"+(pageIndex+1));
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
				update(tableName, htblColNameValue, pageIndex, pages, page, doesExist);

				return true; // تم عمل ابديت بنجاح   :)
			}
			else {
				return false;
			}
			
			
		}





		//return false ;
	}

	public static void update(String tableName, Hashtable<String, Object> htblColNameValue, int pageIndex,
			Vector<Page> pages, Page page, Hashtable<String, Object> doesExist) {
		//System.out.println(htblColNameValue.keySet().size());
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
		System.out.println(page.getData());
		pages.add(page); // tableName+"Page"+(pageIndex+1
		serialize(pages, tableName+"Page"+(pageIndex+1));
	}			
	public static boolean exists (String tableName){
		Vector<String> tableNames = new Vector<String> ();
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream("tableNames.bin"));
			 tableNames = (Vector<String>) in.readObject();
			in.close();

		} catch (Exception i) {
			i.printStackTrace();
		}
		return tableNames.contains(tableName);
	}
	public static Object deserialize (String name) {
		Object r =null;
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream(name+".bin"));
			 r =  in.readObject();
			in.close();

		} catch (Exception i) {
			i.printStackTrace();
		}
		return r;
		
	}
	public static void serialize (Vector<Page> p,String name) {
		try {
			//Vector<String> p = new Vector<String>();
			//  {1,2,3,4,8}  { () , () }
			
			FileOutputStream fileOut = new FileOutputStream(name + ".bin");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
			
			
		} catch (Exception i) {
			i.printStackTrace();
		}
		
		
	}

	
	public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) throws DBAppException {
		return null;
	}
	
	public void insertIntoTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
		insertIntoTable2(strTableName, htblColNameValue);
		fixTheRanges(strTableName, thePK);

	}
	
	public static void serializeTable (Vector<Table> p,String name) {
		try {
			//Vector<String> p = new Vector<String>();
			//  {1,2,3,4,8}  { () , () }
			
			FileOutputStream fileOut = new FileOutputStream(name + ".bin");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
			
			
		} catch (Exception i) {
			i.printStackTrace();
		}
		
		
	}


	public static void serializet (Vector<Table> p,String name) {
		try {
			//Vector<String> p = new Vector<String>();
			//  {1,2,3,4,8}  { () , () }
			
			FileOutputStream fileOut = new FileOutputStream(name + ".bin");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
			
			
		} catch (Exception i) {
			i.printStackTrace();
		}
		
		
	}



	{
		/*//db.init();
		Vector<Page> p = (Vector<Page>) deserialize("StudentPage1");
		//System.out.println(p.toString());
		Hashtable<String,Object> htblColNameValue = new Hashtable<String,Object> ();
		htblColNameValue.put("name", "nour");
		htblColNameValue.put("gpa", new Double("2"));
		Page p1 = p.get(0);
		System.out.println();
		System.out.println("old : ");
		System.out.println();
		System.out.println(p1.getData());
		System.out.println();
		System.out.println("Output : ");
		System.out.println();
		db.updateTable("Student", "10", htblColNameValue);
		System.out.println();
		Vector<Page> p2 = (Vector<Page>) deserialize("StudentPage1");
		Page p22 = p2.get(0);
		System.out.println(p22.getData());*/
	}



//begining  of delete
	public static boolean searchAndDeleteNBString( String strTableName, Hashtable<String, Object> htblColNameValue) {
		System.out.println("Ana gowa el delete non primary key");
		Vector<Table> tables = (Vector<Table>) deserialize(strTableName);
		Table table = tables.remove(0);
		boolean found = true;
		boolean firstFound = false;
		Vector<Page> pages  = null;
		int i = 0;
		for(;i<table.getIds().size();i++) {
			pages =  (Vector<Page>) deserialize(strTableName+"Page"+(i+1));
			Page page = pages.get(0);
		
			pages.remove(page);
			Vector<Hashtable<String,Object>> searchDomain = page.getData();
			for(int j = 0 ;j<searchDomain.size();j++) {
				Hashtable<String,Object> h = searchDomain.get(j);
				Iterator<String> itr = htblColNameValue.keySet().iterator();
				for(int k = 0;k < htblColNameValue.keySet().size();k++) {
					String tmp = itr.next();
					if(!(htblColNameValue.get(tmp).equals( h.get(tmp))))
						found = false;
					System.out.println("found: "+found);

					System.out.println("Htbl: "+htblColNameValue.get(tmp));
					System.out.println("tmp: "+h.get(tmp));
					System.out.println(htblColNameValue.get(tmp).equals( h.get(tmp)));
				}
				
				System.out.println("found1: "+found);
				if(found) {
					System.out.println("Ana gowa el found");
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
				serialize(pages,strTableName+"Page"+(i+1));
				





			}
			else {
				table.getIds().remove(i);
				int y = i+1;
				System.out.println("y: "+y);
				String fileName = strTableName+"Page"+y+".bin";
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
		return firstFound;
	}

	public static boolean searchForDeleteB(String tableName,String keyValue,String key ,
	String keyDataType,Hashtable<String, Object> htblColNameValue){
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
		   case theDate : try {
				   o = dateFormat.parse(keyValue);
			   } catch (ParseException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   } break;

	   }

	   int pageIndex = table.search(o, keyDataType);
	   if(pageIndex==-1){ // row does't exist
		   return false;
	   }
	   else{ // if the row exists it will be in this page , bec. the row is within its range
		   
		   Vector<Page> pages =  (Vector<Page>) deserialize(tableName+"Page"+(pageIndex+1));
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
			   case theDate : doesExist = page.binarySearchDate(keyValue, key); break;
   
		   }
		
		   if (doesExist != null) { // we now have the row that will be updated
			   // وصلنا بالسلامه الحمد الله
		   System.out.println("To Be Deleted: "+ page.getData().get(page.getData().indexOf(doesExist)));
		   if(page.getData().get(page.getData().indexOf(doesExist)).get("id").equals("45-2080")){
		       System.out.println("Welcome to Egypt");
		   }



		   Iterator<String> itr = htblColNameValue.keySet().iterator();
		   for(int k = 0;k < htblColNameValue.keySet().size();k++) {
			   String tmp = itr.next();
			   if(!(htblColNameValue.get(tmp).equals( doesExist.get(tmp))))
			     	return false;
			


		   }
			   page.getData().remove(page.getData().indexOf(doesExist));
			   page.setSize(page.getSize()-1);
			   if(page.getSize() != 0)
			   {
				   pages.add(page);
				   serialize(pages,tableName+"Page"+(pageIndex+1));

				   
			   }
			   if(page.getSize() == 0) {
				   System.out.println("Table _ID:"+ table.getIds());
				   System.out.println("page Index :"+(pageIndex+1));
				   table.getIds().remove(pageIndex);



				   String fileName = tableName+"Page"+(pageIndex+1)+".bin";
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
			   return true; // تم عمل ابديت بنجاح   :)
		   }
		   else {
			   return false;
		   }
		   
		   
	   }
}


public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
	if(!exists(strTableName)) {
		throw new DBAppException("Table doesn't exist");
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
				searchAndDeleteNBString(strTableName,htblColNameValue);
				fixTheRanges(strTableName, key);
			}
	
	
	
	

}catch(Exception e) {
	System.out.println("Exception was thrown while deleting");
}
}





}
