package edu.jsu.mcis;

	/**	
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class RestrictedValueException extends RuntimeException{
	
	/**
	* Sets the message if one of the arguments entered is not allowed. <br>
	* For example "type" can only be a shape, such a circle, it can not be an integer or float.
	*
	* @param arg The argument being parsed
	* @param value The value entered for the argument.
	*/
	
	public RestrictedValueException(String value, String arg){
		super(value + " is not an allowed value for " + arg + ".");
	}
	
}