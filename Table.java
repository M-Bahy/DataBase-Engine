import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Vector;




public class Table implements Serializable{
	private Vector<String> ids ;
	private Vector<Pair> range ;
	private String name; 
	 
	 public Table(String name) {
		 	this.name = name;
			ids = new Vector<String>();
			range = new Vector<Pair>();
			
			
		}

	public Vector<String> getIds() {
		return ids;
	}

	public void setIds(Vector<String> ids) {
		this.ids = ids;
	}

	public Vector<Pair> getRange() {
		return range;
	}

	public void setRange(Vector<Pair> range) {
		this.range = range;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
	
	public int search (Object o, String dataType) {
		
		
		switch (dataType) {
    	case "java.lang.Integer" :
    	 int data = (int) o;
    	 for (int i = 0;i<this.getRange().size();i++) {
    		 Pair p = this.getRange().get(i);
			 System.out.println(p.getMin());
    		 int min = (int) p.getMin();
    		 int max = (int) p.getMax();
    		 if (data<= max && data >= min) {
    			return i;
    		
    			 
    		 }
    		 
    	 }
    		
    		
    		break;
    		    
    	
    	
    	case "java.lang.String" :   
    		String dataa = (String) o;
        	 for (int i = 0;i<this.getRange().size();i++) {
        		 Pair p = this.getRange().get(i);
        		 String min = (String) p.getMin();
        		 String max = (String) p.getMax();
        		 if (max.compareTo(dataa) != -1 && min.compareTo(dataa) != 1 ) {
        			return i;
        		
        			 
        		 }
        		 
        	 }
    		
    		break;
    	case "java.lang.Double" : 
    		double dataaa = (double) o;
        	 for (int i = 0;i<this.getRange().size();i++) {
        		 Pair p = this.getRange().get(i);
        		 double min = (double) p.getMin();
        		 double max = (double) p.getMax();
        		 if (dataaa<= max && dataaa >= min) {
        			return i;
        		
        			 
        		 }
        		 
        	 }
    		
    		
    		break;
    	case "java.util.Date" :   
    		LocalDate theInput = LocalDate.parse(( (String) o   )) ;
       	 for (int i = 0;i<this.getRange().size();i++) {
       		 Pair p = this.getRange().get(i);
       		String x = (String) p.getMin();  // min
			String y = (String) p.getMax(); //max
			//Object i = htblColNameValue.get(values[1]);
			LocalDate dMIN = LocalDate.parse(x) ;
			LocalDate dMAX = LocalDate.parse(y) ;
			int notPos = dMIN.compareTo(theInput)  ;  // not +ve
			int notNeg =  dMAX.compareTo(theInput)  ;  // not -ve
			if(notPos<0 && notNeg>-1)
				return i;
       		 
       		 
       	 }
    		
    		
    		
    		break;
    	
    	}
		return -1;
		
		
	}

	public int searchPageAccordingToMin(Object o, String dataType) {
		
		if(this.getRange().size() == 1){
			return 0;
		}

		switch (dataType) {
    	case "java.lang.Integer" :
    	 int data = (int) o;
		 
    	 for ( int i = 0;i<this.getRange().size();i++) {
    		 Pair p = this.getRange().get(i);
    		 int min = (int) p.getMin();
    		 int max = (int) p.getMax();
    		 if (data < min) {
    			return i-1;
    		
    			 
    		 }
    		 
    	 }
		 
		 	if(true)
				return this.getRange().size()-1;
		 
    		
    		
    		break;
    		    
    	
    	
    	case "java.lang.String" :   
    		String dataa = (String) o;
        	 for (int i = 0;i<this.getRange().size();i++) {
        		 Pair p = this.getRange().get(i);
        		 String min = (String) p.getMin();
        		 String max = (String) p.getMax();
        		 if (min.compareTo(dataa) == 1 ) {
        			return i - 1;
        		
        			 
        		 }
        		 
        	 }
			 if(true)
				return this.getRange().size()-1;
    		
    		break;
    	case "java.lang.Double" : 
    		double dataaa = (double) o;
        	 for (int i = 0;i<this.getRange().size();i++) {
        		 Pair p = this.getRange().get(i);
        		 double min = (double) p.getMin();
        		 double max = (double) p.getMax();
        		 if (dataaa < min) {
        			return i - 1;
        		
        			 
        		 }
        		 
        	 }
			 if(true)
			 return this.getRange().size()-1;
    		
    		break;
    	case "java.util.Date" :   
    		LocalDate theInput = LocalDate.parse(( (String) o   )) ;
       	 for (int i = 0;i<this.getRange().size();i++) {
       		 Pair p = this.getRange().get(i);
       		String x = (String) p.getMin();  // min
			String y = (String) p.getMax(); //max
			//Object i = htblColNameValue.get(values[1]);
			LocalDate dMIN = LocalDate.parse(x) ;
			LocalDate dMAX = LocalDate.parse(y) ;
			int notPos = dMIN.compareTo(theInput)  ;  // not +ve
			int notNeg =  dMAX.compareTo(theInput)  ;  // not -ve
			if(notPos > 0)
				return i - 1;
       		 
       		 
       	 }
    		if(true)
				return this.getRange().size()-1;
    		
    		
    		break;
    	
    	}
		return -1;
		
		
	}
	 
	 
	 
	 

	
}
