package edu.jsu.mcis;
import java.util.*;
import java.lang.*;
import javax.xml.*;

public class Parser{
	
	private List<Argument> argumentList;
	private List<Argument> optionalArgumentsList;
	private String programName;
	private int count = 0;
	
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
	
	public void addOptionalArgument(String name, String value){
		optionalArgumentsList.add(new Argument(name));
		int k = getIndex(name);
		optionalArgumentsList.get(k).setValue(value);
	}
	
	public void addOptionalArgument(String name, String value, Argument.dataType dataType){
		optionalArgumentsList.add(new Argument(name));
		int k = getIndex(name);
		optionalArgumentsList.get(k).setValue(value);
		optionalArgumentsList.get(k).setDataType(dataType);
	}
	
	public void addOptionalArgument(String name, String value, Argument.dataType dataType, String shortForm){
		optionalArgumentsList.add(new Argument(name));
		int k = getIndex(name);
		optionalArgumentsList.get(k).setValue(value);
		optionalArgumentsList.get(k).setDataType(dataType);
		optionalArgumentsList.get(k).setShortForm(shortForm);
	}
	
	public void addOptionalArgument(String name, String value, Argument.dataType dataType, String shortForm, String description){
		optionalArgumentsList.add(new Argument(name));
		int k = getIndex(name);
		optionalArgumentsList.get(k).setValue(value);
		optionalArgumentsList.get(k).setDataType(dataType);
		optionalArgumentsList.get(k).setShortForm(shortForm);
		optionalArgumentsList.get(k).setDescription(description);
	}
	
	public void setRestrictedValues(String name, String[] restrictedValues){
		int k = getIndex(name);
		optionalArgumentsList.get(k).setRestrictedValues(restrictedValues);
	}
	
	public String[] getRestrictedValues(String name){
		int k = getIndex(name);
		return optionalArgumentsList.get(k).getRestrictedValues().toArray(new String[optionalArgumentsList.get(k).getRestrictedValues().size()]);
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
	
	public String getShortForm(String arg){
		int index = getIndex(arg);
		return optionalArgumentsList.get(index).getShortForm();
	}
	
	public void parseValues(String[] args){
		checkForRequiredArgument(args);
		List<String> newArgsList = handleOptionalArguments(args);
		checkForHelpException();
		checkForTooManyArguments(args);
		checkForTooFewArguments(args);
		
		for(int i = 0; i < argumentList.size(); i++){
			argumentList.get(i).setValue(newArgsList.get(i));
			checkForWrongDataType(i);
		}
		
	}
	
	private void checkForRequiredArgument(String[] args){
		List<Argument> requiredArgs = new ArrayList<Argument>();
		int temp = 0;
		if(optionalArgumentsList.size() > 0){
			for(int i = 0; i < optionalArgumentsList.size(); i++){
				if(optionalArgumentsList.get(i).isRequired())
					requiredArgs.add(optionalArgumentsList.get(i));
			}
			
			for(int i = 0; i < requiredArgs.size(); i++){
				for(int j = 0; i < args.length; j++){
					if(requiredArgs.get(i).getName() == args[j] || requiredArgs.get(i).getShortForm() == args[j])
						temp++;
				}
			}
			
			if(temp > 0){
				String argNames = "";
				for(int i = 0; i < requiredArgs.size(); i++){
					argNames += requiredArgs.get(i).getName() + " ";
				}
				throw new RequiredArgumentException(argNames);
			}
		}
	}
	
	private List<String> handleOptionalArguments(String[] args){
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
					int restrictedCount = 0;
					k = getIndex(argument);
					if(k > -1){
						if(optionalArgumentsList.get(k).getDataType() == Argument.dataType.BOOLEAN){
							optionalArgumentsList.get(k).setValue("true");
							break;
						}
						if(optionalArgumentsList.get(k).getRestrictedValues().size() > 0){
							for(int j = 0; j < optionalArgumentsList.get(k).getRestrictedValues().size(); j++){
								if(args[i+1] == optionalArgumentsList.get(k).getRestrictedValues().get(j)){
									optionalArgumentsList.get(k).setValue(args[i+1]);
									newArgsList.remove(args[i+1]);
									count++;
									break;
								}
								else
									restrictedCount++;
							}
							if(restrictedCount == optionalArgumentsList.get(k).getRestrictedValues().size())
								throw new RestrictedValueException(args[i+1], args[i]);
						}
						else{
							optionalArgumentsList.get(k).setValue(args[i+1]);
							newArgsList.remove(args[i+1]);
							count++;
						}
					}
				}
			}
		}
		
		for(int i = 0; i < optionalArgumentsList.size(); i++){
			String argList = "";
			if (!checkDataType(optionalArgumentsList.get(i).getName())){
				for(int j = 0; j < optionalArgumentsList.size(); j++){
					String temp = optionalArgumentsList.get(j).getName();
					argList += temp + " ";
				}
				throw new WrongTypeException(optionalArgumentsList.get(i).getValue(), dataTypeToString(optionalArgumentsList.get(i)), programName, argList, optionalArgumentsList.get(i).getName());
			}
			
		}
		
		return newArgsList;
	}
	
	private void checkForHelpException(){
		for(int i = 0; i < optionalArgumentsList.size(); i++){
			if(optionalArgumentsList.get(i).getName().equals("help"))
				if(optionalArgumentsList.get(i).getValue() == "true")
					throw new HelpException(programName, optionalArgumentsList.get(i).getDescription());
		}
	}
	
	private void checkForTooManyArguments(String[] args){
		if(args.length > argumentList.size() + count){
				String extraArgs = "";
				for(int i = argumentList.size(); i < args.length; i++) {
					extraArgs += args[i];
				}
				throw new TooManyArgsException(extraArgs);
		}
	}
	
	private void checkForTooFewArguments(String[] args){
		if(args.length < argumentList.size() + count){
				String extraArgs = "";
				for(int i = args.length; i < argumentList.size(); i++){
					extraArgs += argumentList.get(i).getName() + " ";
				}
				throw new TooFewArgsException(extraArgs);
		}
	}
	
	private void checkForWrongDataType(int i){
		String argList = "";
		if(!checkDataType(argumentList.get(i).getName())){
				for(int j = 0; j < argumentList.size(); j++){
					String temp = argumentList.get(j).getName();
					argList += temp + " ";
				}
				throw new WrongTypeException(argumentList.get(i).getValue(), dataTypeToString(argumentList.get(i)), programName, argList, argumentList.get(i).getName());
		}
	}
	
	public String getValue(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName().equals(arg))
				return argumentList.get(i).getValue();
		}
		
		throw new NoValueFoundException(arg);
	}
	
	public String getOptionalValue(String arg){
		for(int i = 0; i < optionalArgumentsList.size(); i++){
			if(optionalArgumentsList.get(i).getName().equals(arg))
				return optionalArgumentsList.get(i).getValue();
		}
		
		throw new NoValueFoundException(arg);
	}
	
	private int getIndex(String arg){
		for(int i = 0; i < optionalArgumentsList.size(); i++){
			if((optionalArgumentsList.get(i).getName().equals(arg)) || (optionalArgumentsList.get(i).getShortForm().equals(arg)))
				return i;
		}
		
		throw new OptionalArgumentDoesNotExistException(arg, programName);
		
	}
	
	private boolean checkDataType(String arg){
		int k = 0;
		try{
			k = getIndex(arg);
			Argument.dataType argType = optionalArgumentsList.get(k).getDataType();
				int tempInt;
				float tempFloat;
				if(argType.equals(Argument.dataType.BOOLEAN)){
					if(optionalArgumentsList.get(k).getValue().equals("true") || optionalArgumentsList.get(k).getValue().equals("false"))
						return true;
					else 
						return false;
				}
					
				else if(argType.equals(Argument.dataType.INT)){
					try{
						tempInt = Integer.parseInt(optionalArgumentsList.get(k).getValue());
					} catch(NumberFormatException ex){
						return false;
					}
					return true;
				}
				
				else if(argType.equals(Argument.dataType.FLOAT)){
					try{
						tempFloat = Float.parseFloat(optionalArgumentsList.get(k).getValue());
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
					
		}catch(OptionalArgumentDoesNotExistException OpArgException){
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
		}
		return false;
	}
	
	public void setArgumentDescription(String arg, String description){
		int index = getIndex(arg);
		optionalArgumentsList.get(index).setDescription(description);
	}
	
	public String getArgumentDescription(String arg){
		int index = getIndex(arg);
		return optionalArgumentsList.get(index).getDescription();
	}
	
	public void setProgramName(String name){
		this.programName = name;
	}
	
	public String dataTypeToString(Argument arg){
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
	
	public Argument.dataType getDataType(String arg){
		int index = getIndex(arg);
		return optionalArgumentsList.get(index).getDataType();
	}
	
}
	