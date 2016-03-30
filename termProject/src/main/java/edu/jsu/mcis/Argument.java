package edu.jsu.mcis;
import java.util.*;

public class Argument{
	
	private String name;
	private String value;
	public enum dataType {STRING, BOOLEAN, FLOAT, INT};
	private dataType valueType;
	private String shortForm;
	private String description;
	
	public Argument(String name){
		this.name = name;
		this.valueType = dataType.STRING;
		this.shortForm = "";
		this.description = "";
	}
	
	public Argument(String name, dataType dataType){
		this.name = name;
		this.valueType = dataType;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public void setShortForm(String shortForm){
		this.shortForm = shortForm;
	}
	
	public String getShortForm(){
		return shortForm;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getName(){
		return name;
	}
	
	public dataType getDataType(){
		return valueType;
	}
	
	public void setDataType(dataType dataType){
		this.valueType = dataType;
	}
	
	public String getValue(){
		return value;
	}
	
}