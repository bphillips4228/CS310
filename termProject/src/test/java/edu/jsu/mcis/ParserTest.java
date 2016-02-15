package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;

public class ParserTest{
	
	private Parser p;
	private String output;
	
	@Before
	public void setup(){
		p = new Parser();
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testThatNewInstanceHasNoArgumentValue(){
		p.getValue("length");
	}
	
	@Test(expected = TooManyArgsException.class)
	public void testForTooManyArgumentsException(){
		String[] values = {"5", "5", "5", "5"};
		String[] args = {"length", "width", "height"};
		p.insertNames(args);
		p.parseValues(values);
	}
	
	@Test
	public void testForTooFewArgumentsException1(){
		try{
			String[] values = {"5", "5"};
			String[] args = {"length", "width", "height"};
			p.insertNames(args);
			p.parseValues(values);
		} catch(TooFewArgsException ex){
			output = ex.getExtraArgs();
		}
		assertEquals("the following arguments are required: height", "the following arguments are required: " + output);
	}
	
	@Test(expected = TooFewArgsException.class)
	public void testForTooFewArgumentsException(){
		String[] values = {"5", "5"};
		String[] args = {"length", "width", "height"};
		p.insertNames(args);
		p.parseValues(values);
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
		p.parseValues(values);
		assertEquals("5", p.getValue("length"));
	}
	
	@Test
	public void testIfMultipleArgumentsAreParsed(){
		String[] args = {"length", "width", "height"};
		boolean containsArgs = false;
		p.insertNames(args);
		if(p.containsName(args[0]) && p.containsName(args[1]) && p.containsName(args[2])){
			containsArgs = true;
		}
		assertTrue(containsArgs);
	}
	
	@Test
	public void testIfMultipleValuesAreParsed(){
		String[] values = {"5", "5", "5"};
		String[] args = {"length", "width", "height"};
		boolean containsValues = false;
		p.insertNames(args);
		p.parseValues(values);
		if(p.getValue(args[0]) == "5" && p.getValue(args[1]) == "5" && p.getValue(args[2]) == "5"){
			containsValues = true;
		}
		assertTrue(containsValues);
	}
	
	
	
}