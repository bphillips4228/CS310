package edu.jsu.mcis;

import java.util.*;

public class Parser{
	
	private List<String> nameList;
	private List<String> valueList;
	
	
	public Parser(){
		nameList = new ArrayList<String>();
		valueList = new ArrayList<String>();
	}
	
	public void insertName(String arg){
		nameList.add(arg);
	}
	
	public boolean containsName(String arg){
		return nameList.contains(arg);
	}
	
	public void insertValues(String[] args){
		for(int i = 0; i < args.length; i++){
			valueList.add(args[i]);
		}
	}
	
	public String getValues(String arg){
		int index;
		if(containsName(arg)){
			index = nameList.indexOf(arg);
			return valueList.get(index);
		}
		return "";
	}
	
}