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
	
	@Test
	public void testThatNewInstanceHasNoArgumentValue(){
		assertEquals("poop", p.getValue("length"));
	}
	
	@Test(expected = TooManyArgsException.class)
	public void testForTooManyArgumentsException(){
		String[] values = {"5", "5", "5", "5"};
		String[] args = {"length", "width", "height"};
		p.addArguments(args);
		p.parseValues(values);
	}
	
	@Test
	public void testForTooFewArgumentsException1(){
		try{
			String[] values = {"5", "5"};
			String[] args = {"length", "width", "height"};
			p.addArguments(args);
			p.parseValues(values);
		} catch(TooFewArgsException ex){
			output = ex.getExtraArgs();
		}
		assertEquals("the following arguments are required: height", "the following arguments are required: " + output);
	}
	
	@Test(expected = HelpException.class)
	public void testThatHelpExceptionIsThrown(){
		String[] args = {"-h"};
		p = new Parser();
		p.addArguments(args);
	}
	
	@Test(expected = TooFewArgsException.class)
	public void testForTooFewArgumentsException(){
		String[] values = {"5", "5"};
		String[] args = {"length", "width", "height"};
		p.addArguments(args);
		p.parseValues(values);
	}
	
	@Test(expected = WrongTypeException.class)
	public void testForWrongTypeExceptionWithFloat(){
		String[] values = {"something"};
		String arg = "wrongtype";
		p.addArgument(arg, "float");
		p.parseValues(values);
	}
	
	@Test(expected = WrongTypeException.class)
	public void testForWrongTypeExceptionWithInt(){
		String[] values = {"something"};
		String arg = "wrongtype";
		p.addArgument(arg, "int");
		p.parseValues(values);
	}
	
	@Test(expected = WrongTypeException.class)
	public void testForWrongTypeExceptionWithBoolean(){
		String[] values = {"something"};
		String arg = "wrongtype";
		p.addArgument(arg, "boolean");
		p.parseValues(values);
	}
	
	@Test
	public void testIfRightTypeIsParsed(){
		Boolean exceptionCatch = false;
		try{
			String[] values = {"17.5", "6", "true", "something"};
			p.addArgument("float", "float");
			p.addArgument("int", "int");
			p.addArgument("boolean", "boolean");
			p.addArgument("String", "String");
			p.parseValues(values);
			exceptionCatch = true;
		}catch(WrongTypeException ex){
			exceptionCatch = false;
		}
		assertTrue(exceptionCatch);
	}
	
	@Test
	public void testIfArgumentNameIsParsed(){
		String arg = "length";
		p.addArgument(arg);
		assertTrue(p.containsName(arg));
	}
	
	
	@Test
	public void testIfValueIsParsedCorrectly(){
		String[] values = {"5"};
		p.addArgument("length");
		p.parseValues(values);
		assertEquals("5", p.getValue("length"));
	}
	
	@Test
	public void testIfMultipleArgumentsAreParsed(){
		String[] args = {"length", "width", "height"};
		boolean containsArgs = false;
		p.addArguments(args);
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
		p.addArguments(args);
		p.parseValues(values);
		if(p.getValue(args[0]) == "5" && p.getValue(args[1]) == "5" && p.getValue(args[2]) == "5"){
			containsValues = true;
		}
		assertTrue(containsValues);
	}
	
	
	
}