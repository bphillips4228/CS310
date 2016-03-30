package edu.jsu.mcis;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

public class XMLTest {
	private Parser p;
	private XmlTool x;
	
	@Before
	public void setUp() {
		p = new Parser();
		x = new XmlTool();
		p = x.load("./src/test/java/edu/jsu/mcis/loadFile.xml");
	}
	
	@Test(expected = XMLException.class)
	public void testWithFileNotFound(){
		x.load("thisfiledoesnotexist.xml");
	}
	
	@Test(expected = XMLException.class)
	public void testFileNotXMLExtension(){
		x.load("thisfiledoesnotexist");
	}
	
	/*@Test
	public void testLoadFile() {
		String value = n.getValue("length");
		assertEquals("TestLoad", n.getProgramName());
	}*/
}