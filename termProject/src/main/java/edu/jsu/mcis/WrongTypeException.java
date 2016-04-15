package edu.jsu.mcis;

	/**	
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class WrongTypeException extends RuntimeException{
	String wrongTypeArg;
	
	/**
	*
	* Sets the message if wrong data type is found
	* @param wrongTypeArg Wrong type argument 
	* @param type The shape of the object being parsed
	* @param programName The name of the demonstration program
	* @param argList The list containing the arguments being used
	* @param argName The name of the argument being parsed
	*/
	
	public WrongTypeException(String wrongTypeArg, String type, String programName, String argList, String argName){
		super("usage: java " + programName + " " + argList + "\n" + programName + ".java: error: argument" + argName + ": invalid " + type + " value: " + wrongTypeArg);
		this.wrongTypeArg = wrongTypeArg;
	}
	
	/**
	* Gets and returns the data type if it is entered incorrectly
	* @return wrongTypeArg Wrong type argument
	*/
	
	public String getWrongTypeArg(){
		return wrongTypeArg;
	}
}