package edu.jsu.mcis;
import java.util.*;

public class Argument{
	
	private String name;
	private String value;
	private String valueType;
	
	public Argument(String name){
		this.name = name;
	}
	
	public Argument(String name, String valueType){
		this.name = name;
		this.valueType = valueType;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public String getName(){
		return name;
	}
	
	public String getValueType(){
		return valueType;
	}
	
	public String getValue(){
		return value;
	}
	
}