import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.TreeMap;

/**
 * 
 * @author Aaron Raymer
 * @class Index a simple class to contain the index and maintain sort order<br>
 * <b>Constructor</b> takes a single integer argument representing the number of records
 */
public class Index {
	private TreeMap<Integer, Entry> index;
	private RandomAccessFile bufferOut;
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
	
	/**
	 * sets the widths for the fields in the line according to the readme.
	 * Using a switch is slow, but it's a bit more readable than initializing the entire array
	 * with values.
	 */
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



	/**
	 * Reopens the file for read access.
	 * @param path The path to the file to be read.
	 */
	public void prepareToReadBinary(String path) {
		try {
			access = new RandomAccessFile(new File(path), "r");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @return getter for the index.
	 */
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
			//Basic readLine loop.
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
	 * 
	 * 
	 */
	public void writeBinaryFile(String path) {
		try {
			bufferOut = new RandomAccessFile(new File(path), "rw");
			outpath = path;
			//start writing our index.
			for(Entry e : index.values()) {
				for(Field field : e.fields) {
					//always write as string with a space so it's literally the binary version of our file, but sorted.
					//This will make conversion easier (but less efficient)
					if(field == null || field.getValue() == null)
						continue;
					if(field.fieldWidth == 4)
						bufferOut.writeInt((Integer) field.getValue());
					if(field.fieldWidth == 8)
						bufferOut.writeDouble((Double) field.getValue());
					if( field.charFlag )
						bufferOut.writeByte((char) field.getValue());
//					bufferOut.writeByte(',');
					
				}
			}
			
			bufferOut.close();

		} catch(IOException e) {
			e.printStackTrace();
		}
			
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * 
	 * Debugging method, ignore. Just makes sure our file is sorted and our spacing is correct
	 */
	public boolean verifyOrder() throws IOException {
		access.seek(0);
		int last = access.readInt();
		int current;

		for(int i = 1; i<entryCount; i++) {
			access.seek(0);
			access.seek(i*140);
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
	 * Pre-condition: input string can be parsed as standard integer. 
	 * @throws IOException 
	 */
	public String find(String query) throws IOException {
		String result = null;
		int target = Integer.parseInt(query);
		int leftBound = 0;
		int leftKey;
		int midPoint;
		double offset;
		int probe;
		int rightBound = entryCount-1;
		int rightKey;
		boolean success = false;
		
		//Setup our keys and first probe
		access.seek(leftBound * 140);
		leftKey = access.readInt();
		access.seek(rightBound * 140);
		rightKey = access.readInt();
		offset = (target-leftKey);
		offset /= (rightKey-leftKey);
		offset *= (rightBound-leftBound) ;
		midPoint = (int) ( leftBound + offset);
		access.seek((int)midPoint * 140);
		probe = access.readInt();
		
		//Begin searching.
		while(leftBound < rightBound) {
			
			if(probe == target) {
				success = true;
				break;
			} 
			else if(probe < target) {
				leftBound = (int) (midPoint + 1);
			} else if(probe > target) {
				rightBound = (int) (midPoint - 1);
			}
			offset = (target-leftKey);
			offset /= (rightKey-leftKey);
			offset *= (rightBound-leftBound) ;
			midPoint = (int) ( leftBound + offset);
			access.seek(leftBound * 140);
			leftKey = access.readInt();
			access.seek(rightBound * 140);
			rightKey = access.readInt();
			access.seek((int)midPoint * 140);
			probe = access.readInt();
		}
		
		if(success) {
			access.seek((int)midPoint*140);
			return readFields(midPoint*140);
		}
		
		
		return null;
	}
	
	/**
	 * 
	 * @param marker The offset to the beginning of the entry.
	 * @return String of concatenated fields that have been translated from binary.
	 * @throws IOException
	 */
	private String readFields(int marker) throws IOException {
		String result = "";
		int currMarker = marker;
		Integer tempInt;
		Double tempDbl;
		char tempChar;
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
				case 23:
					tempInt = access.readInt();
					currMarker+=4;
					result+=tempInt + ", ";
					access.seek(currMarker);
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
				case 18: 
					tempDbl = access.readDouble();
					currMarker+=8;
					result+=tempDbl + ", ";
					access.seek(currMarker);
					break;
				
				case 4:
				case 24:
				case 25:
				case 26: 
					tempChar = (char) access.readByte();
					currMarker+=1;
					result+=tempChar + ", ";
					access.seek(currMarker);
					break;
			}	
		}
		
		
		result = result.trim().substring(0, result.length() - 2).trim();

		
		return result;
	}

	public String toString() {
		String result = "";
		
		for(Entry e : index.values()) {
			result+=e.toString() + ", ";
		}
		return result;
	}

	public void firstLast() throws IOException {
		
		String temp;
		System.out.println("First five entries: ");
		System.out.println(readFields(0));
		System.out.println(readFields(1*140));
		System.out.println(readFields(2*140));
		System.out.println(readFields(3*140));
		System.out.println(readFields(4*140));
		System.out.println(" \nLast five entries:");
		System.out.println(readFields( (entryCount-5)*140) );
		System.out.println(readFields( (entryCount-4)*140) );
		System.out.println(readFields( (entryCount-3)*140) );
		System.out.println(readFields( (entryCount-2)*140) );
		System.out.println(readFields( (entryCount-1)*140) );
		
		System.out.println("\nTotal entries: " + entryCount);
		
	}
}
