package edu.jsu.mcis;
import java.util.*;
import java.lang.*;

public class Parser{
	
	private List<String> nameList;
	private List<String> valueList;
	
	
	public Parser(){
		nameList = new ArrayList<String>();
		valueList = new ArrayList<String>();
	}
	
	public void insertNames(String[] args){
		for(int i = 0; i < args.length; i++){
			nameList.add(args[i]);
		}
	}
	
	public void insertName(String arg){
		nameList.add(arg);
	}
	
	public boolean containsName(String arg){
		return nameList.contains(arg);
	}
	
	public void parseValues(String[] args){
		for(int i = 0; i < args.length; i++){
			valueList.add(args[i]);
		}

		if(valueList.size() > nameList.size()){
				String extraArgs = "";
				for(int i = nameList.size(); i < valueList.size(); i++) {
					extraArgs += args[i];
				}
				throw new TooManyArgsException(extraArgs);
		}
		
		else if(valueList.size() < nameList.size()){
				String extraArgs = "";
				for(int i = valueList.size(); i < nameList.size(); i++) {
					extraArgs += nameList.get(i);
				}
				throw new TooFewArgsException(extraArgs);
		}
	}
	
	public String getValue(String arg){
		int index = nameList.indexOf(arg);
		return valueList.get(index);
	}
	
	
	
	
	
}