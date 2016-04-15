package edu.jsu.mcis;

	/**	
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class OptionalArgumentDoesNotExistException extends RuntimeException {
	
	/**
	* Sets the message if the optional argument entered is not found
	* @param argName The name of the argument being parsed
	* @param programName The name of the demonstration program
	*
	*/
	
	public OptionalArgumentDoesNotExistException(String argName, String programName) {
		super("usage: java " + programName + "\n" + programName + ".java: error: argument " + argName + " does not exist");
	}
}