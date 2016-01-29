import java.io.BufferedReader;
import java.io.DataOutputStream;
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
	private DataOutputStream bufferOut;
	private BufferedReader bufferIn;
	private String filepath;
	private String outpath;
	private RandomAccessFile access;
	private Width[] widths;
	private int entryCount = 0;
	
	public Index(String path) {
		index = new TreeMap<Integer, Entry>();
		widths = new Width[27];
		setWidths();
		readAndParse(path);
		
	}
	
	
	private void setWidths() {
		for(int i = 0; i<widths.length; i++) {
			switch(i) {
			
			case 0: widths[0] = new Width(1, 8); break;
			case 1: widths[1] = new Width(10, 11); break;
			case 2: widths[2] = new Width(13, 14); break;
			case 3: widths[3] = new Width(16, 21); break;
			case 4: widths[4] = new Width(23, 23); break;
			case 5: widths[5] = new Width(24, 25); break;
			case 6: widths[6] = new Width(27, 28); break;
			case 7: widths[7] = new Width(30, 34); break;
			case 8: widths[8] = new Width(36, 45); break;
			case 9: widths[9] = new Width(47, 56); break;
			
			case 10: widths[10] = new Width(57, 60); break;
			case 11: widths[11] = new Width(61, 64); break;
			case 12: widths[12] = new Width(66, 72); break;
			case 13: widths[13] = new Width(74, 80); break;
			case 14: widths[14] = new Width(81, 85); break;
			case 15: widths[15] = new Width(86, 90); break;
			case 16: widths[16] = new Width(92, 96); break;
			case 17: widths[17] = new Width(98, 102); break;
			case 18: widths[18] = new Width(104, 108); break;
			case 19: widths[19] = new Width(109, 111); break;
			
			case 20: widths[20] = new Width(112, 114); break;
			case 21: widths[21] = new Width(118, 118); break;
			case 22: widths[22] = new Width(120, 121); break;
			case 23: widths[23] = new Width(123, 124); break;
			case 24: widths[24] = new Width(126, 126); break;
			case 25: widths[25] = new Width(127, 127); break;
			case 26: widths[26] = new Width(128, 128); break;
			
			}
		}
	}



	
	public void prepareToReadBinary(String path) {
		try {
			access = new RandomAccessFile(new File(path), "r");
			System.out.println("File sorted: " + verifyOrder());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
					newEntry = new Entry(stringToBeParsed, widths);
					index.put((Integer) newEntry.objectId, newEntry);
					entryCount++;
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
			bufferOut = new DataOutputStream(new FileOutputStream(path));
			outpath = path;
			//start writing our index.
			for(Entry e : index.values()) {
				for(Field field : e.fields) {
					//always write as string with a space so it's literally the binary version of our file, but sorted.
					//This will make conversion easier (but less efficient)
					if(field == null || field.getValue() == null)
						continue;
					bufferOut.writeBytes( (field.getValue() + "" ));
				}
//				bufferOut.write(e.bytes);
			}
			
			bufferOut.close();

		} catch(IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public boolean verifyOrder() throws IOException {
		int last = access.readInt();
		int current;

		for(int i = 1; i<entryCount; i++) {
			access.seek(i*128);
			current = access.readInt();
			System.out.println("Checking " + last + " < " + current );
			if(current <= last) {
				System.out.println("false");
				return false;
			}
			last = current;
			System.out.println("true");
		}
		return true;
	}

	/**
	 * 
	 * @param query The object ID to find in the file
	 * @return String The relevant entries written in ascii encoding, separated by commas.
	 * Uses interpolation search algorithm (derivative of binary search) to find the relevant entry
	 * by using seek(n). 
	 */
	public String find(String query) {
		
		
		
		return "";
	}
	
	public String toString() {
		String result = "";
		
		for(Entry e : index.values()) {
			result+=e.toString() + ", ";
		}
		return result;
	}
}
