package edu.jsu.mcis;
import java.util.*;
import java.lang.*;

public class Parser{
	
	private List<Argument> argumentList;
	private List<Argument> optionalArgumentsList;
	private String helpMessage;
	private String programName;
	
	public Parser(){
		argumentList = new ArrayList<Argument>();
		optionalArgumentsList = new ArrayList<Argument>();
		}
	
	public void addArguments(String[] args){
		for(int i = 0; i < args.length; i++){
			argumentList.add(new Argument(args[i], Argument.dataType.STRING));
		}
	}
	
	public void addArgument(String name){
		argumentList.add(new Argument(name));
	}
	
	public void addArgument(String name, Argument.dataType dataType){
		argumentList.add(new Argument(name, dataType));
	}
	
	public void addOptionalArgument(String[] arg){
		int index = 0;
		optionalArgumentsList.add(new Argument(arg[0]));
		index = getIndex(arg[0]);
		optionalArgumentsList.get(index).setValue(arg[1]);
	}
	
	public void setOptionalArgumentType(String name, Argument.dataType dataType){
		int index = getIndex(name);
		optionalArgumentsList.get(index).setDataType(dataType);
		
	}
	
	public boolean containsName(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName().equals(arg))
				return true;
		}
		return false;
	}
	
	public void setShortForm(String arg, String shortForm){
		int index = getIndex(arg);
		optionalArgumentsList.get(index).setShortForm(shortForm);
	}
	
	public String shortForm(String arg){
		int index = getIndex(arg);
		return optionalArgumentsList.get(index).getShortForm();
	}
	
	public void parseValues(String[] args){
		int count = 0;
		List<String> newArgsList = new ArrayList<String>(Arrays.asList(args));
		
		for(int i = 0; i < args.length; i++){
			int k = 0;
			if((args[i].charAt(0) == '-')){
				count++;
				newArgsList.remove(args[i]);
				String argument = args[i].replace("-", "");
				if(args[i].length() > 2 && args[i].charAt(1) != '-'){
					String[] charArray = argument.split("(?!^)");
					for(int j = 0; j < charArray.length; j++){
						k = getIndex(charArray[j]);
						if(k > -1){
							if(optionalArgumentsList.get(k).getDataType() == Argument.dataType.BOOLEAN){
								optionalArgumentsList.get(k).setValue("true");
							}
						}
					}
					newArgsList.remove(args[i]);
			    }
				else{
					k = getIndex(argument);
					if(k > -1){
						if(optionalArgumentsList.get(k).getDataType() == Argument.dataType.BOOLEAN){
							optionalArgumentsList.get(k).setValue("true");
							break;
						}
						optionalArgumentsList.get(k).setValue(args[i+1]);
						newArgsList.remove(args[i+1]);
						count++;
					}
				}
			}
		}
		
		if(getOptionalValue("help").equals("true")){
			int k = getIndex("help");
			throw new HelpException(programName, optionalArgumentsList.get(k).getMessage());
		}
			
		/*for(int i = 0; i < optionalArgumentsList.size(); i++){
			if(optionalArgumentsList.get(i).getDataType() ==  Argument.dataType.BOOLEAN)
				if(optionalArgumentsList.get(i).getValue() == "true")
					throw new HelpException(programName, optionalArgumentsList.get(i).getMessage());
		}*/
		
		if(args.length > argumentList.size() + count){
				String extraArgs = "";
				for(int i = argumentList.size(); i < args.length; i++) {
					extraArgs += args[i];
				}
				throw new TooManyArgsException(extraArgs);
		}
		
		else if(args.length < argumentList.size() + count){
				String extraArgs = "";
				for(int i = args.length; i < argumentList.size(); i++){
					extraArgs += argumentList.get(i).getName() + " ";
				}
				throw new TooFewArgsException(extraArgs);
		}
		
		for(int i = 0; i < argumentList.size(); i++){
			String argList = "";
			argumentList.get(i).setValue(newArgsList.get(i));
			if(!checkdataType(argumentList.get(i).getName())){
				for(int j = 0; j < argumentList.size(); j++){
					String temp = argumentList.get(j).getName();
					argList += temp + " ";
				}
				throw new WrongTypeException(argumentList.get(i).getValue(), dataTypeToString(argumentList.get(i)), programName, argList, argumentList.get(i).getName());
			}
		}

	}
	
	public String getValue(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName().equals(arg))
				return argumentList.get(i).getValue();
		}
		
		return "";
	}
	
	public String getOptionalValue(String arg){
		for(int i = 0; i < optionalArgumentsList.size(); i++){
			if(optionalArgumentsList.get(i).getName().equals(arg))
				return optionalArgumentsList.get(i).getValue();
		}
		
		return "";
	}
	
	private int getIndex(String arg){
		for(int i = 0; i < optionalArgumentsList.size(); i++){
			if((optionalArgumentsList.get(i).getName().equals(arg)) || (optionalArgumentsList.get(i).getShortForm().equals(arg)))
				return i;
		}
		return -1;
	}
	
	private boolean checkdataType(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName().equals(arg)){
				Argument.dataType argType = argumentList.get(i).getDataType();
				int tempInt;
				float tempFloat;
				if(argType.equals(Argument.dataType.BOOLEAN)){
					if(argumentList.get(i).getValue().equals("true") || argumentList.get(i).getValue().equals("false"))
						return true;
					else 
						return false;
				}
				
				else if(argType.equals(Argument.dataType.INT)){
					try{
						tempInt = Integer.parseInt(argumentList.get(i).getValue());
					} catch(NumberFormatException ex){
						return false;
					}
					return true;
				}
				
				else if(argType.equals(Argument.dataType.FLOAT)){
					try{
						tempFloat = Float.parseFloat(argumentList.get(i).getValue());
					} catch(NumberFormatException ex){
						return false;
					}
					return true;
				}
				
				else if(argType.equals(Argument.dataType.STRING)){
					return true;
				}
				
				else 
					return false;
				
			}
		}
		return false;
	}
	
	public void setArgumentMessage(String arg, String message){
		int index = getIndex(arg);
		optionalArgumentsList.get(index).setMessage(message);
	}
	
	public void setProgramName(String name){
		this.programName = name;
	}
	
	private String dataTypeToString(Argument arg){
		Argument.dataType type = arg.getDataType();
		if(type == (Argument.dataType.BOOLEAN))
			return "Boolean";
		else if(type == (Argument.dataType.INT))
			return "int";
		else if(type == (Argument.dataType.FLOAT))
			return "float";
		else
			return "String";
	}
}
	