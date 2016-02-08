package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ParserTest{
	
	private Parser p;
	
	@Before
	public void setup(){
		p = new Parser();
	}
	
	@Test
	public void testIfArgumentNameIsParsed(){
		String arg = "length";
		p.insertName(arg);
		assertTrue(p.containsName(arg));
	}
	
	
	@Test
	public void testIfValueIsParsedCorrectly(){
		String[] values = {"5"};
		p.insertName("length");
		p.insertValues(values);
		assertEquals("5", p.getValues("length"));
	}
	
	@Test
	public void testIfMultipleArgumentsAreParsed(){
		
	}
}