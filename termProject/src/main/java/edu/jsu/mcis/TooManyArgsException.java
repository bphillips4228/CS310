package edu.jsu.mcis;

	/**	
	*@author Brandon Phillips
	*@author Ryan Mullally
	*@author Cody Dempsey
	*@author Quintan Brothers
	*@author Sam Vogt
	*/

public class TooManyArgsException extends RuntimeException{
	private String extraArgs;
	
	/**
	* Sets the message and the extra arguments 
	* @param extraArgs The extra arguments being parsed
	*/
	
	public TooManyArgsException(String extraArgs){
		super("unrecognized arguments: " + extraArgs);
		this.extraArgs = extraArgs;
	}
	
	/**
	* Returns the extra arguments in a String format
	* @return extraArgs The extra arguments being parsed
	*/
		
	public String getExtraArgs() { 
		return extraArgs; 
	}
}