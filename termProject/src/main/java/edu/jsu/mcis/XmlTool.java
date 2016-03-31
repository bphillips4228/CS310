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
			throw new NoValueFoundException("poop");
		}
		
	}
	

	private class XmlHandler extends DefaultHandler{
		Map<String, Boolean> xmlMap;
		private String name, value, shortName, description;
		private Argument.dataType dataType;
		private int position; 
		private Parser p;
		private final String[] XmlTags = {"arguments", "positional", "named", "name", "type", "shortName", "default", "position"};
		
		public XmlHandler(){
			p = new Parser();
			xmlMap = new HashMap<String, Boolean>();
			name = "";
			description = "";
			value = "";
			shortName = "";
			for(String s: XmlTags)
					xmlMap.put(s, false);
		}
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			String currentTag = qName.toLowerCase();
			if(xmlMap.containsKey(currentTag))
				xmlMap.put(currentTag, true);
		}
		
		public void endElement(String uri, String localName, String qName){
			String tag = qName.toLowerCase();
			
			if(tag.equals("named")){
				if(shortName != "")
					p.addOptionalArgument(name, value, dataType, shortName);
				else
					p.addOptionalArgument(name, value, dataType);
				name = "";
				description = "";
				shortName = "";
				value = "";
			}
			else if(tag.equals("positional")){
				p.addArgument(name, dataType);
				name = "";
				dataType = Argument.dataType.STRING;
			}
			
			if(xmlMap.get(tag))
				xmlMap.put(tag, false);
		}
		
		public void characters(char ch[], int start, int length){
			String s = "";
			for(int i = start; i < start + length; i++){
				s += String.valueOf(ch[i]);
			}
			if(xmlMap.get("arguments")){
				if(xmlMap.get("positional")){
					if(xmlMap.get("name"))
						name = s;
					else if(xmlMap.get("type"))
						dataType = dataTypeConversion(s);
					else if(xmlMap.get("description"))
						description = s;
					else if(xmlMap.get("position"))
						position = Integer.parseInt(s);
				}
				else if(xmlMap.get("named")){
					if(xmlMap.get("name"))
						name = s;
					else if(xmlMap.get("type"))
						dataType = dataTypeConversion(s);
					else if(xmlMap.get("description"))
						description = s;
					else if(xmlMap.get("position"))
						position = Integer.parseInt(s);
					else if(xmlMap.get("shortName"))
						shortName = s;
					else if(xmlMap.get("default"))
						value = s;
				}
			}
		}
		
		public Parser getParser(){
			return p;
		}
		
		private Argument.dataType dataTypeConversion(String type) {
			if(type.equals("integer"))
				return Argument.dataType.INT;
			else if(type.equals("float"))
				return Argument.dataType.FLOAT;
			else if(type.equals("boolean"))
				return Argument.dataType.BOOLEAN;
			else
				return Argument.dataType.STRING;
		
		}
	}
}