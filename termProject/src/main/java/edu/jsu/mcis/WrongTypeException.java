package edu.jsu.mcis;

public class WrongTypeException extends RuntimeException{
	String wrongTypeArg;
	
	public WrongTypeException(String wrongTypeArg, String Type){
		super("usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid " + Type + " value: " + wrongTypeArg);
		this.wrongTypeArg = wrongTypeArg;
	}
	
	public String getWrongTypeArg(){
		return wrongTypeArg;
	}
}