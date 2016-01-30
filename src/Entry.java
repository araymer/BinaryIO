import java.util.LinkedList;

/**
 * 
 * @author Aaron Raymer
 * @class Entry represents a single entry (one complete line) in the index.
 */

public class Entry implements Comparable {
	public Field[] fields = new Field[27]; // this is the index data.
	
	public int objectId = 0;
	private Width[] widths;
	
	
	public Entry(String line, Width[] w) {
		widths = w;
		splitLineAndStore(line);
	}
	
	/**
	 * 
	 * @param line the string representing a single record to be split and stored
	 * Takes in the entire entry as a string and splits it into substrings for storage.
	 */
	private void splitLineAndStore(String line) {
		LinkedList<String> str = new LinkedList<String>();
		String temp;
		
		//Split the string into substrings based on the byte information in the readme.
		//Add into interim linkedlist
		for(int i = 0; i<widths.length; i++) {
			if(widths[i].start > line.length()) {
				break;
			}/* else if(widths[i].stop > line.length()) {
				temp = line.substring(widths[i].start-1);
			} */else {
				temp = line.substring(widths[i].start-1, widths[i].stop);
			}
			str.push(temp);
			temp = null;
		}
		//Reversing the list. Ended up in reverse due to pushing, 
		//and I fought with ordering on a tangentially related problem.
		//So, I didn't wanna change the ordering in the previous loop.
		//Will probably factor out this loop if time permits.
		LinkedList<String> reversed = new LinkedList<String>();
		for(String s : str) {
			reversed.push(s);
		}
		str = reversed;
		
		Double tempFloat = null;
		Integer tempInteger = null;
		
		//divvy up the strings into fields.
		//Again, switch statement is ugly, but it's a bit more readable, I think.
		for(int i = 0; i<27; i++) {
			int size = 0;
			switch(i) {
				case 0:
				case 1:
				case 2:	
				case 5:
				case 6:
				case 10:
				case 11:
				case 19:
				case 20:
				case 21:
				case 22:
				case 23: size = 4;
					tempInteger = Integer.parseInt(str.get(i).trim());
					if(str.get(i).trim().compareTo("") == 0)
						tempInteger = 0;
					fields[i] = new Field(tempInteger, size);
					break;
				
				case 3:
				case 7:
				case 8:
				case 9:
				case 12:
				case 13:
				case 14:
				case 15:
				case 16:
				case 17:
				case 18: size = 8;
					tempFloat = Double.parseDouble(str.get(i).trim()); 
					if(str.get(i).trim().compareTo("") == 0)
						tempFloat = 0.;
					fields[i] = new Field(tempFloat, size);
					break;
				
				case 4:
				case 24:
				case 25:
				case 26: size = 1;
					fields[i] = new Field(str.get(i).charAt(0), size); 
					break;
			}	
		}
		objectId = (int) fields[0].getValue();  //We use the objectId in the comparable override for Entry.
	}

	/**
	 * toString()
	 */
	public String toString() {
		String result = "";
		result = "" + fields[0].getValue();
		for(int i = 1; i<27; i++) {
			if( !(fields[i] == null) ) 
//				result+= ", " + fields[i].getValue();
				result+=fields[i].toString() + ", ";
		}
		return result;
	}
	
	/**
	 * This overridden method assumes that the ObjectID field is present and first
	 * in both objects.
	 */
	@Override
	public int compareTo(Object o) {
		Integer a = (Integer) this.fields[0].getValue();
		Integer b = (Integer) ((Entry)o).fields[0].getValue();
		return (a-b);
	}



	
}
