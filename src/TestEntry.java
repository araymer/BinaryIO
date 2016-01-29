import static org.junit.Assert.*;

import org.junit.Test;


public class TestEntry {
	/**
	 * An entry should: contain a number of fields equal to the number of fields
	 * Be able to return the fields in order as a string.
	 * Be able to handle missing values
	 * 
	 */

	@Test
	public void testEntryWithTrim() {
		String str = "  26301173  0  0  0.095 -45 57 15.35   0.000396 -45.954263 104  77   -19.2    -4.2  7.7  7.7 16.74 18.28  1.54 39 22   1  1  1  G  ";
		Entry test = new Entry(str, null);
		assertFalse(test == null);
		System.out.println(test);
		assertEquals(test.toString(), "26301173 0 0 0.095 -45 57 15.35 0.000396 -45.954263 104 77 -19.2 -4.2 7.7 7.7 16.74 18.28 1.54 39 22 1 1 1 G" );
		
	}
	
	@Test
	public void testEntryWithMissingFields() {
		String str = "  26301173  0  0  0.095 -45 57 15.35   0.000396 -45.954263 104  77   -19.2    -4.2  7.7  7.7 ";
		Entry test = new Entry(str);
		
		assertEquals(test.toString(), "26301173 0 0 0.095 -45 57 15.35 0.000396 -45.954263 104 77 -19.2 -4.2 7.7 7.7" );
		for(int i = 12; i != 0; i-- ) {
			assertEquals(null, test.fields[test.fields.length - i]);
		}
		System.out.println(test.toString());
		for(int i = 0; i<15; i++ ) {
			assertTrue( test.fields[i] != null );
		}
		
	}
	
	

}
