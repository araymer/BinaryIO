import java.util.TreeMap;

/**
 * 
 * @author Aaron Raymer
 * @class Index a simple class to contain the index and maintain sort order<br>
 * <b>Constructor</b> takes a single integer argument representing the number of records
 */
public class Index {
	private TreeMap<Integer, Entry> records;
	
	public Index() {
		records = new TreeMap<Integer, Entry>();
	}
	
	
	/**
	 * @param entry Entry to be added to Index
	 * Appends entry to end of Index. Does not maintain sort order.
	 */
	public void addEntry(Entry entry) {
		records.put((Integer) entry.fields[0].getValue(), entry);
	}
	
	/**
	 * @return boolean Verifies whether the Index is sorted properly,
	 * if not, sorts and returns false. If sorted, returns true.
	 */
	public boolean checkSort() {
		return false;
	}
	
	public TreeMap<Integer, Entry> getIndex() {
		return records;
	}
	
	/**
	 * @param path The path to the file that's to be written.
	 * @param sort Flag to determine whether or not to verify sort order
	 * Writes the entire Index to a Binary File from beginning to end.
	 * 
	 */
	public void writeBinaryFile(String path, boolean sort) {
		if(sort)
			checkSort();
		writeBinaryFile(path);
	}
	
	/**
	 * @param path The path to the file that's to be written.
	 * Writes the entire Index to a Binary File from beginning to end.
	 * Guarantees sort order by default.
	 * 
	 */
	public void writeBinaryFile(String path) {
		
	}
}
