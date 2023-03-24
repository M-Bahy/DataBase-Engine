import java.io.*;
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

	public static void main(String[] args) throws Exception {
	
		/*SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
		String dateInString = "7-Jun-2013";
		Date date = formatter.parse(dateInString);
		System.out.println(date);*/
		
		// law rg3t -1 then ely bara is the earlier one
		// law rag3t 1 then ely bara is later 
		DBApp dbApp = new DBApp();
		// dbApp.init();

		// Hashtable htblColNameType = new Hashtable( );
		// htblColNameType.put("id", "java.lang.Integer");
		// htblColNameType.put("name", "java.lang.String");
		// htblColNameType.put("gpa", "java.lang.Double");

		// Hashtable htblColNameMin = new Hashtable<>();
		// htblColNameMin.put("id", "1");
		// htblColNameMin.put("name", "A");
		// htblColNameMin.put("gpa", "0");
		

		// Hashtable htblColNameMax = new Hashtable<>();
		// htblColNameMax.put("id", "20");
		// htblColNameMax.put("name", "ZZZZZZZZZZZ");
		// htblColNameMax.put("gpa", "4");

		// dbApp.createTable("Student", "id", htblColNameType, htblColNameMin, htblColNameMax);

		Hashtable htblColNameValue = new Hashtable<>();
		htblColNameValue.put("id", new Integer( 3 ));
		htblColNameValue.put("name", new String("nour" ) );
		htblColNameValue.put("gpa", new Double( 0.95 ) ); 

		dbApp.insertIntoTable("Student", htblColNameValue);
		
		// String x = "2002-04-29";  // min
		// String y = "2023-04-29"; //max
		// Object i = "2002-05-29";
		// LocalDate dMIN = LocalDate.parse(x) ;
		// LocalDate dMAX = LocalDate.parse(y) ;
		// LocalDate theInput = LocalDate.parse(( (String) i   )) ;
		
		// System.out.println(  dMIN.compareTo(theInput)  );  // not +ve
		// System.out.println(  dMAX.compareTo(theInput)  );  // not -ve
		
		
	//	int g = i  < ((  (Long)    x         ));
	//	System.out.println(g);
		
		/*Page one = new Page();
		Page two = new Page();
		one.nextPage=two;
		System.out.println(one.nextPage);
		try {
			FileOutputStream fileOut = new FileOutputStream("test.bin");

			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(one);
			out.close();
			fileOut.close();
		} catch (Exception i) {
			throw new DBAppException("moshkela fe table names");
		}
		
		Page result ;
		try {

			ObjectInputStream in = new ObjectInputStream(new FileInputStream("test.bin"));
			result = (Page) in.readObject();
			in.close();

		} catch (Exception i) {
			throw new DBAppException();
		}
		
		System.out.println(result.nextPage);*/
		
		
		
		/*
		Vector <String> i = new Vector<String>();
		i.add("zero");
		i.add("one");
		i.add("two");
		
		System.out.println(i);
		i.remove(1);
		System.out.println(i.get(1));
		System.out.println(i);*/
		
/*
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
		 
*/
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
		
		
		// write n in csv
		try {
			FileWriter csvWriter = new FileWriter("metadata.csv", true);
			csvWriter.write(this.getN());
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
			if (!((htblColNameType.get(s)) == "java.lang.Integer" || htblColNameType.get(s) == "java.lang.String"
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
			String line;
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
                	case "java.lang.Integer" :
                	
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Integer ) {
                			variable = 0;
                			// 6 min      7  max
                        	if(    (Integer.parseInt(values[6]))   >   ((int)  (htblColNameValue.get(values[1])))           
                        			||   (Integer.parseInt(values[7]))   <   ((int)  (htblColNameValue.get(values[1])))    )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                		}
                		
                		break;
                		    
                	
                	
                	case "java.lang.String" :   
                		if(  (htblColNameValue.get(values[1]) ) instanceof  String ) {
                			variable = 0;
                			
                			if(    (  values[6].compareTo( ( (String)     htblColNameValue.get(values[1])       ) )     == 1         
                        			||   values[7].compareTo(((String)htblColNameValue.get(values[1])))   == -1     )  )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                			
                		}
                		
                		break;
                	case "java.lang.Double" : 
                		
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Double ) {
                			variable = 0;
                			
                			
                			if(    (Double.parseDouble(values[6]))   >   ((Double)  (htblColNameValue.get(values[1])))           
                        			||   (Double.parseDouble(values[7]))   <   ((Double)  (htblColNameValue.get(values[1])))    )
                        		throw new DBAppException("Data is not within the domain of the coloumn !");
                			
                		}
                		
                		break;
                	case "java.util.Date" :   
                		
                		if(  (htblColNameValue.get(values[1]) ) instanceof  Date ) {
                			variable = 0;
                 			// law rg3t -1 then ely bara is the earlier one
                			// law rag3t 1 then ely bara is later 
                			
                			String x = values[6];  // min
                			String y = values[7]; //max
                			Object i = htblColNameValue.get(values[1]);
                			LocalDate dMIN = LocalDate.parse(x) ;
                			LocalDate dMAX = LocalDate.parse(y) ;
                			LocalDate theInput = LocalDate.parse(( (String) i   )) ;
                			
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
                	if(values[3].compareTo("True") == 0 && htblColNameValue.get(values[1]) != null ) { // instead of == "True", use compareto(String)
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
				System.out.println(t.getIds().get(0));
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
        		int index = t.search(htblColNameValue.get(pk), dataType);
        		if( index != -1) {  // We found the page (using the range) and we inserted 
        			                // , we increased the size of the page too 
        			String pageID = t.getIds().get(index);
        			Vector <Page> v = (Vector <Page>) deserialize(strTableName+"Page"+pageID);
        			Page pp = v.get(0);
        			
        				
        			if(dataType == "java.lang.Integer") {
    	    			pp.insertHashTableINT(htblColNameValue, pk);
                    	t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1));
        			    t.getRange().get(index).setMin(pp.getData().get(0));
            			}
            		else {
            			if(dataType == "java.lang.String") {
                			pp.insertHashTableString(htblColNameValue, pk);
                			t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1));
                			t.getRange().get(index).setMin(pp.getData().get(0));
                		}
            			else {
            				if(dataType == "java.lang.Double") {
                    			pp.insertHashTableDOUBLE(htblColNameValue, pk);
                    		    t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1));
                        		t.getRange().get(index).setMin(pp.getData().get(0));
                    		}
            				else {
            					if(dataType == "java.util.Date") {
                        			pp.insertHashTableDate(htblColNameValue, pk);
                        			t.getRange().get(index).setMax(pp.getData().get(pp.getData().size()-1));
                            		t.getRange().get(index).setMin(pp.getData().get(0));
                        		}
            						
            				}
            					
            			}
            				
            				
            		}
            			pp.setSize(pp.getSize()+1);

        				// I here must shift the last row in the curr page to the next page
						if(pp.getSize() > this.getN()) {
	
							int shiftedindex = pp.getSize() - 1;
							Hashtable<String, Object> shiftedRow = pp.getData().get(shiftedindex); // last entry to shift 
							pp.getData().remove(shiftedindex);
							pp.setSize(pp.getSize() - 1);

							//serialize pp 

						
							try {
								int i = 1;
								while(true){
								pageID = t.getIds().get(index + i);
        						Vector <Page> v1 = (Vector <Page>) deserialize(strTableName+"Page"+pageID);
        						Page pp1 = v1.get(0);
								
								if(dataType == "java.lang.Integer") {
									pp1.insertHashTableINT(htblColNameValue, pk);
									t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1));
									t.getRange().get(index).setMin(pp1.getData().get(0));
									}
								else {
									if(dataType == "java.lang.String") {
										pp1.insertHashTableString(htblColNameValue, pk);
										t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1));
										t.getRange().get(index).setMin(pp1.getData().get(0));
									}
									else {
										if(dataType == "java.lang.Double") {
											pp1.insertHashTableDOUBLE(htblColNameValue, pk);
											t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1));
											t.getRange().get(index).setMin(pp1.getData().get(0));
										}
										else {
											if(dataType == "java.util.Date") {
												pp1.insertHashTableDate(htblColNameValue, pk);
												t.getRange().get(index).setMax(pp1.getData().get(pp1.getData().size()-1));
												t.getRange().get(index).setMin(pp1.getData().get(0));
											}
												
										}
											
									}
										
										
								}
								pp1.setSize(pp1.getSize() + 1);

								if(pp1.getSize() < this.getN()) break;
								
								//else 
								shiftedRow = pp1.getData().get(shiftedindex);
								pp1.setSize(pp.getSize() - 1);
								i++;

								// we should serialize any change in any page before entering the loop again

								// 1. serialize the page
								
								v1 = new Vector<>();
								v1.add(pp1);
								serialize(v1, strTableName+"Page"+pageID);
								
								// 2. serialize the table
								try {
									Vector tableV = new Vector<Table>();
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

							catch (NullPointerException e) { 
							// did not find next page
							// so we create a new page
							Page newPage = new Page();
							newPage.getData().add(shiftedRow);
							newPage.setSize(1);

							//serialize and add to table

							// 1. serialize the page
							pageID = pageID + 1;
							Vector<Page> v1 = new Vector<>();
							v1.add(newPage);
							serialize(v1, strTableName+"Page"+pageID);
							
							// 2. serialize the table
							t.getIds().add(pageID);
							t.getRange().add(new Pair(shiftedRow.get(pk), shiftedRow.get(pk)));
							try {
								Vector tableV = new Vector<Table>();
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
        			// keep checking the min of the next pages similary to find a place to insert it . 
        			
        		}
        		
     
        	}
            
            
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void updateTable(String strTableName, String strClusteringKeyValue,
			Hashtable<String, Object> htblColNameValue) throws DBAppException {
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
	 * 
	 * 
	 * 
	 * {"id",5}
	 * 
	 * values[1]
	 * [false,false]
	 * 
	 * id {4544,65456,64565}
	 * name {fdf,dffdf,dffd}
	 * 5 , mohamed
	 * id , name
	 * 
	 *  //   insert in order
	 * 
	 * 
	 * 
	 * ibrahim.bin
	 * {
	 * 
	 * {  5   ,  mohamed                           }     hash tables   row 1
	 * 
	 * {  7  ,  mohamed                               }     hash tables   row 2
	 *  
	 *  
	 *  
	 *  }
	 *  
	 *  
	 *  
	 *  
	 *   ibrahim.bin
	 * {
	 * 
	 * {  5,  mohamed                           }     hash tables   row 1
	 * 
	 *  {  7 ,  mohamed                               }     hash tables   row 2
	 *  
	 *  
	 *  
	 *  }
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

}
