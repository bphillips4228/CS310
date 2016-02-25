package edu.jsu.mcis;
import java.util.*;
import java.lang.*;

public class Parser{
	
	private List<Argument> argumentList;
	
	
	public Parser(){
		argumentList = new ArrayList<Argument>();
	}
	
	public void addArguments(String[] args){
		for(int i = 0; i < args.length; i++){
			if(args[i] == "-h")
				throw new HelpException();
			else
				argumentList.add(new Argument(args[i], "String"));
		}
	}
	
	public void addArgument(String arg){
		if(arg == "-h")
			throw new HelpException();
		else
			argumentList.add(new Argument(arg, "String"));
	}
	
	public void addArgument(String name, String valueType){
		if(name == "-h")
			throw new HelpException();
		else
			argumentList.add(new Argument(name, valueType));
	}
	
	public boolean containsName(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName() == arg)
				return true;
		}
		return false;
	}
	
	public void parseValues(String[] args){
		if(args.length > argumentList.size()){
				String extraArgs = "";
				for(int i = argumentList.size(); i < args.length; i++) {
					extraArgs += args[i];
				}
				throw new TooManyArgsException(extraArgs);
		}
		
		else if(args.length < argumentList.size()){
				String extraArgs = "";
				for(int i = args.length; i < argumentList.size(); i++){
					extraArgs += argumentList.get(i).getName();
				}
				throw new TooFewArgsException(extraArgs);
		}
		
		for(int i = 0; i < argumentList.size(); i++){
			argumentList.get(i).setValue(args[i]);
			if(!checkValueType(argumentList.get(i).getName())){
				throw new WrongTypeException(argumentList.get(i).getValue(), argumentList.get(i).getValueType());
			}
		}

	}
	
	public String getValue(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName() == arg)
				return argumentList.get(i).getValue();
		}
		
		return "poop";
		
	}
	
	private boolean checkValueType(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName() == arg){
				String argType = argumentList.get(i).getValueType();
				int tempInt;
				float tempFloat;
				if(argType == "boolean"){
					if(argumentList.get(i).getValue() == "true" || argumentList.get(i).getValue() == "false")
						return true;
					else 
						return false;
				}
				
				else if(argType == "int"){
					try{
						tempInt = Integer.parseInt(argumentList.get(i).getValue());
					} catch(NumberFormatException ex){
						return false;
					}
					return true;
				}
				
				else if(argType == "float"){
					try{
						tempFloat = Float.parseFloat(argumentList.get(i).getValue());
					} catch(NumberFormatException ex){
						return false;
					}
					return true;
				}
				
				else if(argType == "String"){
					return true;
				}
				
				else 
					return false;
				
			}
		}
		return false;
	}
}
	