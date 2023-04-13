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


public class DBApp {
	private boolean firstTable = false;
	private int n;
	private final static String theString = "java.lang.String";
	private final static String theDouble = "java.lang.Double";
	private final static String theDate = "java.util.Date";
	private final static String theInt = "java.lang.Integer";

	public static void main(String[] args) throws Exception {
		DBApp db = new DBApp();
		/*db.init();
		createDummyData(db);
		insertDummyData(db, 3, 25, "three", "2008-08-14" , 2.3);
		
		insertDummyData(db, 5, 619, "five", "2019-12-15" , 2.5);
		
		insertDummyData(db, 6, 619, "six", "2019-12-15" , 2.5);
		insertDummyData(db, 2, 55, "two", "2013-06-01" , 2.2);

		insertDummyData(db, 12, 99, "twoelf", "2014-05-23" , 2.4);
		
		insertDummyData(db, 1, 55, "one", "2013-06-01" , 2.2);
		insertDummyData(db, 4, 101, "four", "2010-05-23" , 2.4);*/
		//insertDummyData(db, 13, 25, "threeTEEN", "2008-08-14" , 2.3);
		printData();
		
		
		
		
	}

	private static void printData() {
		Vector<Table> tt = (Vector<Table>) deserialize("dumbTable");
		Table t = tt.get(0);
		System.out.println( "Total number of Pages : " + t.getIds().size());
		System.out.println();
		for(String s:t.getIds()){
			Vector<Page> pages = (Vector<Page>)  deserialize("dumbTablePage"+s);
			Page p1 = pages.get(0);
			System.out.println("Page "+s+" Data : ");
			System.out.println(p1.getData());
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
			i.printStackTrace();
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

	public void insertIntoTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {     
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
						}
					else {
						if(dataType.compareTo(theString) == 0) {
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
							int shiftedindex = pp.getSize() - 1;
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
		}
		} catch (Exception e) {
			e.printStackTrace();
		}


		if (isDataCorrect==htblColNameValue.size()){
			// table exists and data is valid (in the correct data type and within the min & the max)
			// now lets go check if the row (the hashtable) exists or not
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

	public void deleteFromTable(String strTableName, Hashtable<String, Object> htblColNameValue) throws DBAppException {
	}

	public Iterator selectFromTable(SQLTerm[] arrSQLTerms, String[] strarrOperators) throws DBAppException {
		return null;
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

}
