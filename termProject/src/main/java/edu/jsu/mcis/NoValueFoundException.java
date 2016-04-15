package edu.jsu.mcis;

	/**	
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class NoValueFoundException extends RuntimeException{
	
	/**
	* Sets the message if no value is found for the argument
	* @param arg The argument being parsed
	*/
	
	
	public NoValueFoundException(String arg){
		super("No value found for the argument: " + arg);
	}
	
}