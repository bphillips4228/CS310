package edu.jsu.mcis;
import java.util.*;

	/**	
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class RequiredArgumentException extends RuntimeException{

	/**
	* Sets the message if the required arguments are not found
	*
	* @param args The required arguments that were not found.
	*/

	public RequiredArgumentException(String args){
		super("The following arguments are required: " + args);
	}
	
}