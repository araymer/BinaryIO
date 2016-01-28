import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.TreeMap;

/**
 * 
 * @author Aaron Raymer
 * @class Index a simple class to contain the index and maintain sort order<br>
 * <b>Constructor</b> takes a single integer argument representing the number of records
 */
public class Index {
	private TreeMap<Integer, Entry> index;
	private FileOutputStream bufferOut;
	private BufferedReader bufferIn;
	private String filepath;
	private String outpath;
	private RandomAccessFile access;
	
	public Index(String path) {
		index = new TreeMap<Integer, Entry>();
		readAndParse(path);
	}
	
	
	/**
	 * @param entry Entry to be added to Index
	 * Appends entry to end of Index. Does not maintain sort order.
	 */
	public void addEntry(Entry entry) {
		index.put((Integer) entry.objectId, entry);
	}
	
	public void prepareToReadBinary() {
		try {
			access = new RandomAccessFile(new File(outpath), "r");
			
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public TreeMap<Integer, Entry> getIndex() {
		return index;
	}
	
	/**
	 * @return void
	 * @param path The path to the file to be read.
	 * Reads in the file that is opened in the constructor
	 */
	private void readAndParse(String path) {
		int readErrorCount = 0;
		//open the file
		try {
			bufferIn = new BufferedReader(new FileReader(path));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String stringToBeParsed = null;
		Entry newEntry = null;
		
		try {
			while((stringToBeParsed = bufferIn.readLine()) != null) {
				if(stringToBeParsed != null) {
					newEntry = new Entry(stringToBeParsed);
					addEntry(newEntry);
				}
				//reset values for thoroughness (and because we check the string's)
				stringToBeParsed = null;
				newEntry = null;
			}
		} catch (IOException e) {
			readErrorCount++;
			System.out.println("READ ERROR! total read errors so far: " + readErrorCount);
		}	
		
		try {
			bufferIn.close();
		} catch (IOException e) {
			System.out.println("MISC ERROR! Error on reader close");
		}
	}
	
	
	/**
	 * @param path The path to the file that's to be written.
	 * Writes the entire Index to a Binary File from beginning to end.
	 * Guarantees sort order by default.
	 * 
	 */
	public void writeBinaryFile(String path) {
		try {
			bufferOut = new FileOutputStream(new File(path));
			outpath = path;
			//start writing our index.
			for(Entry e : index.values()) {
//				for(Field field : e.fields) {
//					//always write as string with a space so it's literally the binary version of our file, but sorted.
//					//This will make conversion easier (but less efficient)
//					if(field == null || field.getValue() == null)
//						continue;
//					bufferOut.write( (field.getValue() + " " ).getBytes());
//				}
				bufferOut.write(e.bytes);
			}
			
			bufferOut.close();

		} catch(IOException e) {
			e.printStackTrace();
		}
			
	}


	public String find(String query) {
		return "";
	}
}
