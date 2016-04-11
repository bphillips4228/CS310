package edu.jsu.mcis;
import java.util.*;

public class RequiredArgumentException extends RuntimeException{

	public RequiredArgumentException(String args){
		super("The following arguments are required: " + args + ".");
	}
	
}