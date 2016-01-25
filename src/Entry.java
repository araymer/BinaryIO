/**
 * 
 * @author Aaron Raymer
 * @class Entry represents a single entry (one complete line) in the index.
 * @method toString returns a stringified version of the Entry (the complete line)
 * @variables { public: fields - the field values read for the line }
 */
public class Entry implements Comparable<Entry> {
	public Field[] fields = new Field[27];
	
	public Entry(String line) {
		splitLineAndStore(line);
	}
	
	/**
	 * 
	 * @param line the string representing a single record to be split and stored
	 */
	private void splitLineAndStore(String line) {
		String[] strFields = line.replaceAll(" +", " ").trim().split(" ");	//Strip consecutive spaces and split
		strFields = stripSpaces(strFields);		//Still getting whitespace for some reason. This guarantees we don't get it.
		Double tempFloat = null;
		Integer tempInteger = null;
		for(int i = 0; i<fields.length; i++) {
			if(strFields[i] == null)
				continue;
			if(isAlpha(strFields[i])) { //If we don't match digits we know it's alpha
				fields[i] = new Field(strFields[i]);
				continue;
			}	
			tempFloat = Double.parseDouble(strFields[i]);
			if(Math.ceil(tempFloat) == tempFloat) {	//If the ceiling is the same number, it's an int. Use autoboxing to make Integer
				tempInteger = (int) (tempFloat/1);
				fields[i] = new Field(tempInteger);
			} else {	//Otherwise, it's a float.
				fields[i] = new Field(tempFloat);
			}
		}
	}

	/**
	 * @param string String to check for numeric digits
	 * @return if no numeric digits exist, return true. Otherwise, false.
	 */
	private boolean isAlpha(String string) {
		
		return !string.matches("[0-9]+");
		
	}

	/**
	 * @param strFields Array of strings which will be stripped
	 * @return array of strings with all elements that were only whitespace
	 * removed.
	 */
	private String[] stripSpaces(String[] strFields) {
		String[] result = new String[27];
		int count = 0;
		for(int i = 0; i<strFields.length; i++) {
			if(strFields[i].trim().compareTo("") == 0)
				continue;
			result[count++] = strFields[i];
		}
		return result;
	}
	
	/**
	 * toString()
	 */
	public String toString() {
		String result = "";
		result = "" + fields[0].getValue();
		for(int i = 1; i<27; i++) {
			if( !(fields[i] == null) ) 
				result+=" " + fields[i].getValue();
		}
		return result;
	}
	
	/**
	 * This overridden method assumes that the ObjectID field is present and first
	 * in both objects.
	 */
	@Override
	public int compareTo(Entry o) {
		Integer a = (Integer) this.fields[0].getValue();
		Integer b = (Integer) o.fields[0].getValue();
		return (a-b);
	}

	
}
