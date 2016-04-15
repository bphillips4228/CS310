package edu.jsu.mcis;

	/**
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class HelpException extends RuntimeException{
	
	/**
	* Sets the help message
	* @param programName The name of the demonstration programName
	* @param helpMessage The name of the message being thrown
	*/
	
	public HelpException(String programName, String helpMessage){
		super("usage: java " + programName + " " + helpMessage);
	}
}