package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

public class XMLTest {
	private Parser p;
	private XmlTool x;
	
	@Before
	public void setUp() {
		x = new XmlTool();
	}
	
	@Test
	public void testThatFileLoads(){
		p = x.load("./src/test/java/edu/jsu/mcis/loadFile.xml");
	}
	
	@Test(expected = XMLException.class)
	public void testWithFileNotFound(){
		p = x.load("thisfiledoesnotexist.xml");
	}
	
	@Test(expected = XMLException.class)
	public void testFileNoXMLExtension(){
		p = x.load("thisfiledoesnothavexmlextension");
	}
	
	@Test
	public void testThatFileIsStoredInParser(){
		p = x.load("./src/test/java/edu/jsu/mcis/loadFile.xml");
		assertTrue(p.containsName("length"));
		assertTrue(p.containsName("width"));
		assertTrue(p.containsName("height"));
	}
	
	@Test
	public void testThatFileIsSaved(){
		p = new Parser();
		String[] args = {"5", "5", "false", "asdf", "-t", "sphere"};
		p.addArgument("length", Argument.dataType.FLOAT);
		p.addArgument("width", Argument.dataType.INT);
		p.addArgument("height", Argument.dataType.BOOLEAN);
		p.addArgument("variable", Argument.dataType.STRING);
		p.addOptionalArgument("type", "box", Argument.dataType.STRING, "t");
		p.addOptionalArgument("bool", "false", Argument.dataType.BOOLEAN);
		p.addOptionalArgument("int", "1", Argument.dataType.INT);
		p.addOptionalArgument("float", "1.0", Argument.dataType.FLOAT);
		p.parseValues(args);
		x.saveToXML("saveFileUnitTest.xml", p);
	}
}