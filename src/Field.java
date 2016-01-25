/**
 * 
 * @author Aaron Raymer
 * Represents a single field in a record. 
 * Pretty self-explanatory. Just an abstraction to make the field-handling
 * logic easier and cleaner.
 *
 */
public class Field {
	private Integer intField;
	private Double floatField;
	private String strField;
	
	public Field(int x) {
		intField = x;
	}
	public Field(Double x) {
		floatField = x;
	}
	public Field(String s) {
		strField = s;
	}
	
	public Object getValue() {
		if(intField == null && floatField == null)
			return strField;
		if(intField == null && strField == null)
			return floatField;
		if(floatField == null && strField == null)
			return intField;
		return null;
	}
}