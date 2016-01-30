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
		LinkedList<String> reversed = new LinkedList<String>();
		for(String s : str) {
			reversed.push(s);
		}
		str = reversed;
		
		Double tempFloat = null;
		Integer tempInteger = null;
		
		//divvy up the strings into fields.
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
				case 23: size = 4; break;
				
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
				case 18: size = 8; break;
				
				case 4:
				case 24:
				case 25:
				case 26: size = 1; break;
			}
			
			if(str.get(i) == null)
				continue;
			if(isAlpha(str.get(i))) { //If we don't match digits we know it's alpha
				fields[i] = new Field(str.get(i).charAt(0), size);
				continue;
			}	
			tempFloat = Double.parseDouble(str.get(i));
			if(Math.ceil(tempFloat) == tempFloat) {	//If the ceiling is the same number, it's an int. Use autoboxing to make Integer
				tempInteger = (int) (tempFloat/1);
				fields[i] = new Field(tempInteger, size);
			} else {	//Otherwise, it's a float.
				fields[i] = new Field(tempFloat, size);
			}
		}
		objectId = (int) fields[0].getValue();
	}

	/**
	 * @param string String to check for numeric digits
	 * @return if no numeric digits exist, return true. Otherwise, false.
	 */
	private boolean isAlpha(String string) {
		if(string == null)
			return false;
		return !string.matches("[0-9]+");
		
	}
	
	public void checkBytes() {
		int bytecount= 0;
		for(Field f : fields) {
			if(f.getValue() instanceof java.lang.Integer) {
				bytecount+=4;
			} else if(f.getValue() instanceof java.lang.Double) {
				bytecount+=8;
			} else {
				bytecount++;
			}
		}
		System.out.println("byte count: " + bytecount);
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
