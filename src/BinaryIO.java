/*=============================================================================
 |   Assignment:  Program #1 - Interpolation Searching a Binary File
 |       Author:  Aaron Raymer - atraymer@email.arizona.edu
 |
 |       Course:  CSc460 - Database Design
 |   Instructor:  L. McCann
 |     Due Date:  January 29th, 2016, at the beginning of class
 |
 |     Language:  JAVA
 |     Packages:  JAVA SDK Native imports only
 |  Compile/Run:  Run "make.sh" on *NIX bash
 |
 +-----------------------------------------------------------------------------
 |
 |  Description:  The goal is to read in, parse and write a binary file from a given input file.
 |					Then, close the file, open the newly created binary file and print the first 5
 |					and last 5 entries. Additionally, it will take in a user supplied search and output
 |					that line if present.
 |                
 |        Input:  Object Identifier in the form of an integer.
 |
 |       Output:  The output should include 10 printed entries (the first and last 5)
 |					and a user-supplied query result. Also, the total number of records present
 |
 |   Techniques:  [Names of standard algorithms employed, explanations
 |                 of why things were done they way they were done, etc.
 |                 This is the place for technical information that another
 |                 programmer would like to know.]
 |
 |   Required Features Not Included:
 |                [If the assignment specifies a feature that you were
 |                 unable to include in the program, mention that omission
 |                 here.  Otherwise, state that you were able to include
 |                 all of the required features in your program.]
 |
 |   Known Bugs:  [If you know of any problems with the code, provide
 |                details here, otherwise clearly state that you know
 |                of no logic errors.]
 |
 *===========================================================================*/

import java.io.BufferedReader;			//For access to the BufferedReader
import java.io.FileNotFoundException;	//For the construction of FileReader
import java.io.FileReader;				//For access to FileReader
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author Aaron Raymer
 * 
 * @class BinaryIO Application's main class, which reads in a file and prints the first and last 5 entries along with a user-directed search.
 *
 */
public class BinaryIO {
	
	private final static String origFilepath = "./spmcat.dat";		//the path of the original input file to be read
	private final static String binaryFilepath  = "./binaryspmcat.txt";		//the path of the file we want to create
	private Index index;	//Our index, everything is there. This class is a controller (so we don't clutter our main class)

	
	public BinaryIO(String readPath) {
        		index = new Index(readPath);
		index.writeBinaryFile(binaryFilepath);
		index.prepareToReadBinary(binaryFilepath);
		
	}

	
	
	/**
	 * @return void
	 * @param query string of Object ID to find in binary file
	 * Finds a record in the Binary File and returns it.
	 */
	public String find(String query) {
		return index.find(query);
	}



	public static void main(String[] args) {
		if(args[0].length() == 0) {
			System.out.println("You must supply a filepath as a command line argument. Exit status 1");
			System.exit(1);
		}
		String readInput = "";
		BufferedReader br;
		
		BinaryIO bin = new BinaryIO(args[0]);
		
		//loop to take in user-supplied arguments & find.
		while(true) {
			System.out.println("Please enter an Object Identifier to query or enter 'q' to quit:");
			br = new BufferedReader(new InputStreamReader(System.in));
			try {
				readInput = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(readInput.compareTo("q") == 0)
				break;
			System.out.println(bin.find(readInput));
		}
		
		try {
			br.close();
			System.out.println("Exit successful");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		

	}
}
