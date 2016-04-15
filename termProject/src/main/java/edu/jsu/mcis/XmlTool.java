package edu.jsu.mcis;

import java.util.*;
import java.io.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

	/**	
	* This class allows argument information to be loaded from an XML file. Also allows argument information to be saved to an XML file.
	* 
	*
	*
	*
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class XmlTool{
	
	/**
	* Load an xml file to populate the parser class with objects
	* @param filename
	*/
	
	public Parser load(String fileName){
		Parser p = new Parser();
		try{
			InputStream xmlFile = new FileInputStream(fileName);
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			SAXParser saxParser = parserFactory.newSAXParser();
			XmlHandler handler = new XmlHandler();
			saxParser.parse(xmlFile, handler);
			p = handler.getParser();
			return p;
		} catch (Exception ex){
			throw new XMLException("Something went wrong.");
		}
		
	}
	
	/**
	* Creates a save file in xml format that contains all the information from a parser object
	* @param a filename, and a parser object
	*/
	
	public void saveToXML(String fileName, Parser p) {
		BufferedWriter writer = null;
		String toBePrinted = "<arguments>\n";
		
		for (int i = 0; i < p.getNumOfPositionalArgs(); i++){
			toBePrinted += "\t<positional>\n";
			Argument a = p.getPositionalArg(i);
			toBePrinted += a.getPositionalXMLFormat() + "\n";
			toBePrinted += "\t\t<position>" + (i + 1) + "</position>\n";
			toBePrinted += "\t</positional>\n";
		}
		
		for (int i = 0; i < p.getNumOfNamedArgs(); i++){
			toBePrinted += "\t<named>\n";
			Argument a = p.getNamedArg(i);
			toBePrinted += a.getNamedXMLFormat() + "\n";
			toBePrinted += "\t</named>\n";
		}
		
		toBePrinted += "</arguments>";
		
		try
			{
				writer = new BufferedWriter( new FileWriter(fileName));
				writer.write(toBePrinted);

			}
		catch ( IOException e){
				System.out.println("IOException while trying to write file.");
			}
		finally
			{
				try
					{
						if ( writer != null)
						writer.close( );
					}
				catch ( IOException e){
					System.out.println("IOException while trying to close writer.");
					}
			}
		
	}
	

	private class XmlHandler extends DefaultHandler{
		private String name, value, shortName, description, programName;
		private Argument.dataType dataType;
		private int positionValue; 
		private List<String> restrictedValues;
		private Parser p;
		private boolean required;
		private boolean argumentsBool, positionalBool, namedBool, nameBool, typeBool, shortNameBool, defaultValueBool, positionBool, descriptionBool, restrictedBool, requiredBool, programNameBool ;
		
		public XmlHandler(){
			p = new Parser();
			name = "";
			description = "";
			value = "";
			shortName = "";
			restrictedValues = new ArrayList<String>();
			required = false;
			descriptionBool = false;
			argumentsBool = false;
			positionalBool = false;
			namedBool = false;
			nameBool = false;
			typeBool = false;
			shortNameBool = false;
			defaultValueBool = false;
			positionBool = false;
			restrictedBool = false;
			requiredBool = false;
			programNameBool = false;
		}
		
		public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
			String currentTag = qName.toLowerCase();
			if(currentTag.equals("arguments"))
				argumentsBool = true;
			else if(currentTag.equals("positional"))
				positionalBool = true;
			else if(currentTag.equals("named"))
				namedBool = true;
			else if(currentTag.equals("name"))
				nameBool = true;
			else if(currentTag.equals("type"))
				typeBool = true;
			else if(currentTag.equals("shortname"))
				shortNameBool = true;
			else if(currentTag.equals("default"))
				defaultValueBool = true;
			else if(currentTag.equals("position"))
				positionBool = true;
			else if(currentTag.equals("description"))
				descriptionBool = true;
			else if(currentTag.equals("restricted"))
				restrictedBool = true;
			else if(currentTag.equals("required"))
				requiredBool = true;
			else if(currentTag.equals("programname"))
				programNameBool = true;
		}
		
		public void endElement(String uri, String localName, String qName){
			String currentTag = qName.toLowerCase();
			
			if(currentTag.equals("programname"))
				p.setProgramName(programName);
			
			if(currentTag.equals("named")){
				String[] temp = new String[restrictedValues.size()];
				restrictedValues.toArray(temp);
				if(shortName != ""){
					if(!restrictedValues.isEmpty()){
						p.addOptionalArgument(name, value, dataType, shortName, temp);
					}
					else
						p.addOptionalArgument(name, value, dataType, shortName);
				}
				else
					if(!restrictedValues.isEmpty()){
						p.addOptionalArgument(name, value, dataType);
						p.addRestrictedValues(name, temp);
					}
					else
						p.addOptionalArgument(name, value, dataType);
				name = "";
				shortName = "";
				value = "";
			}
			else if(currentTag.equals("positional")){
				p.addArgument(name, dataType);
				name = "";
				dataType = Argument.dataType.STRING;
			}
			
			if(currentTag.equals("arguments"))
				argumentsBool = false;
			else if(currentTag.equals("positional"))
				positionalBool = false;
			else if(currentTag.equals("named"))
				namedBool = false;
			else if(currentTag.equals("name"))
				nameBool = false;
			else if(currentTag.equals("type"))
				typeBool = false;
			else if(currentTag.equals("shortname"))
				shortNameBool = false;
			else if(currentTag.equals("default"))
				defaultValueBool = false;
			else if(currentTag.equals("position"))
				positionBool = false;
			else if(currentTag.equals("description"))
				descriptionBool = true;
			else if(currentTag.equals("restricted"))
				restrictedBool = false;
			else if(currentTag.equals("required"))
				requiredBool = false;
			else if(currentTag.equals("programname"))
				requiredBool = false;
		}
		
		public void characters(char ch[], int start, int length) throws SAXException{
			if(programNameBool)
				programName = new String(ch, start, length);
			
			if(argumentsBool){
				if(positionalBool){
					if(nameBool)
						name = new String(ch, start, length);
					else if(typeBool)
						dataType = dataTypeConversion(new String(ch, start, length));
					else if(descriptionBool)
						description = new String(ch, start, length);
					else if(positionBool)
						positionValue = Integer.parseInt(new String(ch, start, length));
				}
				else if(namedBool){
					if(nameBool)
						name = new String(ch, start, length);
					else if(typeBool)
						dataType = dataTypeConversion(new String(ch, start, length));
					else if(descriptionBool)
						description = new String(ch, start, length);
					else if(positionBool)
						positionValue = Integer.parseInt(new String(ch, start, length));
					else if(shortNameBool)
						shortName = new String(ch, start, length);
					else if(defaultValueBool)
						value = new String(ch, start, length);
					else if(requiredBool)
						required = true;
					else if(restrictedBool)
						restrictedValues = new ArrayList<String>(Arrays.asList((new String(ch, start, length).split(", "))));
				}
			}
		}
		
		/**
		* Returns a parser object containing the data from the load file
		* @returns p a parser object
		*/
		
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