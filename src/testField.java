import static org.junit.Assert.*;

import org.junit.Test;

public class testField {

	@Test
	public void testField() {
		
		//A field should assign to the proper class-type and should return
		//the proper value type as well. 
		Field field = new Field(100);
		assertEquals(100, field.getValue());
		assertTrue(field.getValue() instanceof java.lang.Integer);
		
		field = new Field(100.00);
		assertEquals(100.00, field.getValue());
		assertTrue(field.getValue() instanceof java.lang.Double);
		
		field = new Field(-100);
		assertEquals(-100, field.getValue());
		assertTrue(field.getValue() instanceof java.lang.Integer);
		
		field = new Field(-100.00);
		assertEquals(-100.00, field.getValue());
		assertTrue(field.getValue() instanceof java.lang.Double);
		
		field = new Field(0);
		assertEquals(0, field.getValue());
		assertTrue(field.getValue() instanceof java.lang.Integer);
		
		field = new Field(0.00);
		assertEquals(0.00, field.getValue());
		assertTrue(field.getValue() instanceof java.lang.Double);
		
		field = new Field(0);
		assertEquals(0, field.getValue());
		assertTrue(field.getValue() instanceof java.lang.Integer);
		
		field = new Field(0.00);
		assertEquals(0.00, field.getValue());
		assertTrue(field.getValue() instanceof java.lang.Double);
		
		field = new Field(0);
		assertEquals(0, field.getValue());
		assertFalse(field.getValue() instanceof java.lang.Double);
		
		field = new Field(0.00);
		assertEquals(0.00, field.getValue());
		assertFalse(field.getValue() instanceof java.lang.Integer);
	}

}
