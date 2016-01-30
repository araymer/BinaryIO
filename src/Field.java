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
	private char charField;
	Integer fieldWidth = 1;
	public boolean charFlag = false;
	public Field(int x, int width) {
		intField = x;
		fieldWidth = width;
	}
	public Field(Double x, int width) {
		floatField = x;
		fieldWidth = width;

	}
	public Field(char s, int width) {
		charField = s;
		charFlag = true;
		fieldWidth = width;

	}
	
	public Object getValue() {
		if(intField == null && floatField == null)
			return charField;
		if(intField == null && charField == 0)
			return floatField;
		if(floatField == null && charField == 0)
			return intField;
		return null;
	}
	public String toString() {
		return this.getValue() + "";
	}
}