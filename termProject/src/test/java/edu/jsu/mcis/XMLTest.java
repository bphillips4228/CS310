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
	}
	
	@Test
	public void testThatFileLoads(){
		p = x.load("./src/test/java/edu/jsu/mcis/testLoadFile.xml");
	}
	
	@Test(expected = XMLException.class)
	public void testWithFileNotFound(){
		p = x.load("thisfiledoesnotexist.xml");
	}
	
	@Test(expected = XMLException.class)
	public void testFileNoXMLExtension(){
		p = x.load("thisfiledoesnothavexmlextension");
	}
}