package edu.jsu.mcis;

public class RestrictedValueException extends RuntimeException{
	
	public RestrictedValueException(String value, String arg){
		super(value + " is not an allowed value for " + arg + ".");
	}
	
}