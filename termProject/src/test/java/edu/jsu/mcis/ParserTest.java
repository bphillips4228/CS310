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
		assertEquals("", p.getValue("length"));
	}
	
	@Test
	public void testThatUserArgumentIsAdded(){
		String[] args = {"type", "box"};
		String[] values = {"--type", "box"};
		p.addOptionalArgument(args);
		p.setShortForm("type", "t");
		p.parseValues(values);
		assertEquals("box", p.getOptionalValue("type"));
	}
	
	@Test(expected = TooManyArgsException.class)
	public void testForTooManyArgumentsException(){
		String[] values = {"5", "5", "5", "5"};
		String[] args = {"length", "width", "height"};
		p.addArguments(args);
		p.parseValues(values);
	}
	
	@Test(expected = HelpException.class)
	public void testThatHelpExceptionIsThrown(){
		p.setProgramName("program");
		String[] arg = {"help", "false"};
		String[] values = {"-h"};
		p.addOptionalArgument(arg);
		p.setOptionalArgumentType("help", Argument.dataType.BOOLEAN);
		p.setArgumentMessage("help", "poop");
		p.setShortForm("help", "h");
		p.parseValues(values);
	}
	
	@Test
	public void acceptanceTestFive(){
		String[] names = {"length", "width", "height"};
		String[] type = {"type", "box"};
		String[] digits = {"digits", "4"};
		String[] values = {"--type", "ellipsoid", "7", "3", "--digits", "1", "2"};
		p.addArguments(names);
		p.addOptionalArgument(type);
		p.addOptionalArgument(digits);
		p.parseValues(values);
		assertEquals("7", p.getValue("length"));
		assertEquals("3", p.getValue("width"));
		assertEquals("2", p.getValue("height"));
		assertEquals("ellipsoid", p.getOptionalValue("type"));
		assertEquals("1", p.getOptionalValue("digits"));
	}
	
	@Test
	public void acceptanceTestSix(){
		String[] args = {"-td"};
		String[] type = {"type", "false"};
		String[] digits = {"digits", "false"};
		p.addOptionalArgument(type);
		p.addOptionalArgument(digits);
		p.setShortForm("type", "t");
		p.setShortForm("digits", "d");
		p.setOptionalArgumentType("type", Argument.dataType.BOOLEAN);
		p.setOptionalArgumentType("digits", Argument.dataType.BOOLEAN);
		p.parseValues(args);
		assertEquals("true", p.getOptionalValue("type"));
		assertEquals("true", p.getOptionalValue("digits"));
	}
	
	@Test
	public void acceptanceTestOne(){
		String[] args = {"7", "5", "2"};
		p.addArgument("length", Argument.dataType.FLOAT);
		p.addArgument("width", Argument.dataType.FLOAT);
		p.addArgument("height", Argument.dataType.FLOAT);
		String[] type = {"type", "box"};
		String[] digits = {"digits", "4"};
		p.addOptionalArgument(type);
		p.addOptionalArgument(digits);
		p.parseValues(args);
		float volume;
		float h = Float.parseFloat(p.getValue("height"));
		float w = Float.parseFloat(p.getValue("width"));
		float l = Float.parseFloat(p.getValue("length"));
		volume = l*w*h;
		String asdf = Float.toString(volume);
		assertEquals("70.0", asdf);
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
		p.addArgument(arg, Argument.dataType.FLOAT);
		p.parseValues(values);
	}
	
	@Test(expected = WrongTypeException.class)
	public void testForWrongTypeExceptionWithInt(){
		String[] values = {"something"};
		String arg = "wrongtype";
		p.addArgument(arg, Argument.dataType.INT);
		p.parseValues(values);
	}
	
	@Test(expected = WrongTypeException.class)
	public void testForWrongTypeExceptionWithBoolean(){
		String[] values = {"something"};
		String arg = "wrongtype";
		p.addArgument(arg, Argument.dataType.BOOLEAN);
		p.parseValues(values);
	}
	
	public void testForWrongTypeExceptionWithArgumentWithNoAcceptedType(){
		String[] values = {"24354984"};
		String arg = "wrongtype";
		p.addArgument(arg);
		p.parseValues(values);
	}
	
	@Test
	public void testIfRightTypeIsParsed(){
		Boolean exceptionCatch = false;
		try{
			String[] values = {"17.5", "6", "true", "something"};
			p.addArgument("float", Argument.dataType.FLOAT);
			p.addArgument("int", Argument.dataType.INT);
			p.addArgument("boolean", Argument.dataType.BOOLEAN);
			p.addArgument("String", Argument.dataType.STRING);
			p.parseValues(values);
			exceptionCatch = true;
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