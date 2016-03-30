package edu.jsu.mcis;

import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlTool{
	public Parser load(String fileName){
		Parser p = new Parser();
		XmlHandler handler = new XmlHandler();
		try{
			if(fileName.contains(".xml")){
				File xmlFile = new File(fileName);
				SAXParserFactory parserFactory = SAXParserFactory.newInstance();
				SAXParser saxParser = parserFactory.newSAXParser();
				saxParser.parse(xmlFile, handler);
				p = handler.getParser();
				return p;
			}
			else
				throw new XMLException("The File must contain a .xml extension.");
		} catch (Exception ex){
			throw new XMLException("poop");
		}
	}
	

	private class XmlHandler extends DefaultHandler{
		private String programName, name, value, shortForm, description;
		private Argument.dataType dataType;
		private int position; 
		private Parser p;
		private final String[] XmlTags = {"arguments", "positional", "named", "name", "type"};
		public XmlHandler(){
			p = new Parser();
			name = "";
			description = "";
			value = "";
		}
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			String currentTag = qName.toLowerCase();
		}
		
		public void endElement(String uri, String localName, String qName){
			if(qName.equals("named")){
				if(shortForm != "\0")
					p.addOptionalArgument(name, value, dataType, shortForm);
				else
					p.addOptionalArgument(name, value, dataType);
				name = "";
				description = "";
				value = "";
			}
			else if(qName.equals("positional")){
				p.addArgument(name, dataType);
				name = "";
				dataType = Argument.dataType.STRING;
			}
			
			String currentTag = qName.toLowerCase();
		}
		
		public void characters(char ch[], int start, int length){
			String s = "";
			for(int i = start; i < start + length; i++){
				s += String.valueOf(ch[i]);
			}
		}
		
		public Parser getParser(){
			return p;
		}
	}
}