package edu.jsu.mcis;
import java.util.*;

/**
	* This class holds information about the argument.
	* It also contains the enumerated type that defines the data type for each argument. <br>
	* Supported data types include String, boolean, float, and int.
	*
	*
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class Argument{
	
	private String name;
	private String value;
	public enum dataType {STRING, BOOLEAN, FLOAT, INT};
	private dataType valueType;
	private String shortForm;
	private String description;
	private List<String> restrictedValues;
	private boolean bRequired;
	
	/**
	* Initializes the name, value type, short form, description, and required type of the argument.
	* @param name The name of the argument
	*/
	
	public Argument(String name){
		this.name = name;
		this.valueType = dataType.STRING;
		this.shortForm = "";
		this.description = "";
		this.bRequired = false;
		restrictedValues = new ArrayList<String>();
	}
	
	/**
	* Initializes the name and value type for each data type in the argument.
	* @param name The name of the argument
	* @param dataType The data type of the argument
	*/
	
	public Argument(String name, dataType dataType){
		this.name = name;
		this.valueType = dataType;
	}
	
	/**
	* Sets the value of the argument 
	* @param value The value of the argument 
	*/
	
	public void setValue(String value){
		this.value = value;
	}
	
	/**
	* Takes in the optional short form of the arugument
	* @param shortForm The short form of the argument
	*/
	
	public void setShortForm(String shortForm){
		this.shortForm = shortForm;
	}
	
	/**
	* Sets the restricted values into a String array named values.
	* @param values The array holding the restricted values.
	*
	*/
	
	public void setRestrictedValues(String[] values){
		for(int i = 0; i < values.length; i++){
			restrictedValues.add(values[i]);
		}
	}
	
	/**
	* Takes in the restricted values from the command line
	* @return restrictedValues 
	*/
	
	
	public List<String> getRestrictedValues(){
		return restrictedValues;
	}
	
	/**
	* Takes in an argument from the command line and returns the short form of the argument
	* @return shortForm the short form of the argument
	*/
	
	public String getShortForm(){
		return shortForm;
	}
	
	/**
	* Sets the description of the named argument
	* 
	* @param description The description of the argument <br>
	* For example, an argument named "height" might have the description "The height of the box."
	*/
	
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	* Gets the description of the named argument from the command line.
	* @return description The description of the argument
	*/
	
	public String getDescription(){
		return description;
	}
	
	/**
	* Gets the name of the argument
	* @return name The name of the argument
	*/
	
	public String getName(){
		return name;
	}
	
	/**
	* Gets and returns the data type of the argument
	* @return dataType The data type of the argument
	*/
	
	public dataType getDataType(){
		return valueType;
	}
	
	/**
	* Sets the data type for the given argument
	* @param dataType The data type of the argument
	*/
	
	public void setDataType(dataType dataType){
		this.valueType = dataType;
	}
	
	/**
	* Gets the value of the argument
	* @return value The value of the argument
	*/
	
	public String getValue(){
		return value;
	}
	
	/**
	* Requires for the user to enter the argument 
	*/
	
	public void makeRequired(){
		bRequired = true;
	}
	
	/**
	* Returns true or false if the argument is required
	* @return bRequired
	*
	*/
	
	public boolean isRequired(){
		return bRequired;
	}
	
	/**
	* Returns determines type fo argument then creates a string containing the argument's name and type in xml format
	* @return formatted
	*
	*/
	
	public String getPositionalXMLFormat() {
		String stringType = "";
		
		Argument.dataType argType = getDataType();
		if(argType == (Argument.dataType.BOOLEAN))
			stringType = "Boolean";
		else if(argType == (Argument.dataType.INT))
			stringType = "int";
		else if(argType == (Argument.dataType.FLOAT))
			stringType = "float";
		else
			stringType = "String";
		String formatted = "\t\t<name>" + getName() + "</name>\n" + "\t\t<type>" + stringType + "</type>";
		
		return formatted;
		
	}
	
	/**
	* Returns determines type fo argument then creates a string containing the argument's name and type in xml format
	* @return formatted
	*
	*/
	
	public String getNamedXMLFormat() {
		String stringType = "";
		Argument.dataType argType = getDataType();
		if(argType == (Argument.dataType.BOOLEAN))
			stringType = "Boolean";
		else if(argType == (Argument.dataType.INT))
			stringType = "int";
		else if(argType == (Argument.dataType.FLOAT))
			stringType = "float";
		else
			stringType = "String";
		String formatted = "\t\t<name>" + getName() + "</name>\n" + "\t\t<shortform>" + getShortForm() + "</shortform>\n" + "\t\t<type>" + stringType + "</type>\n" + "\t\t<default>" + getValue() + "</default>";
		return formatted;
	}
}