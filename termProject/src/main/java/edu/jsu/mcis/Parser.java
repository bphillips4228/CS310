package edu.jsu.mcis;
import java.util.*;
import java.lang.*;
import javax.xml.*;

/** 
 * Allows the user to add each argument and enter data values into the command line. 
 * 
 *
 *@author Brandon Phillips 
 *@author Ryan Mullally
 *@author Cody Dempsey
 *@author Quintan Brothers
 *@author Sam Vogt
 *
 */

public class Parser{
	
	private List<Argument> argumentList;
	private List<Argument> optionalArgumentsList;
	private String programName;
	private int count = 0;
	
	/**
	* Creates the argument list and the optional arguments list as new Array Lists. <br>
	* An example of an optional argument would be <code> -help </code> which would display a help message showing help information.
	*/
	
	public Parser(){
		argumentList = new ArrayList<Argument>();
		optionalArgumentsList = new ArrayList<Argument>();
	}
	
	/**
	 * Adds the arguments entered by the user to a String array
	 * @param args The arguments entered from the command line.
	 */
	
	
	public void addArguments(String[] args){
		for(int i = 0; i < args.length; i++){
			argumentList.add(new Argument(args[i], Argument.dataType.STRING));
		}
	}
	
	/**
	 * Adds a name to an argument in the argument list.
	 * @param name The name of the arguement. 
	 */
	
	public void addArgument(String name){
		argumentList.add(new Argument(name));
	}
	
	/**
	* Adds the name and the datatype to the argument list. 
	* @param name The name of the argument.
	* @param dataType The data type of the argument.
	*/
	
	public void addArgument(String name, Argument.dataType dataType){
		argumentList.add(new Argument(name, dataType));
	}
	
	/**
	* Adds the name and value of optional arguments into the optional argument list. 
	* @param name The name of the optional argument
	* @param value The value of the optional argument.
	*/
	
	public void addOptionalArgument(String name, String value){
		optionalArgumentsList.add(new Argument(name));
		int k = getIndex(name);
		optionalArgumentsList.get(k).setValue(value);
	}
	
	/**
	* Adds the name, value, and dataType to the optional argument list and sets each value.
	* @param name the name of the optional argument.
	* @param value the value of the optional argument.
	* @param dataType the data type of the optional argument.
	*/
	
	public void addOptionalArgument(String name, String value, Argument.dataType dataType){
		optionalArgumentsList.add(new Argument(name));
		int k = getIndex(name);
		optionalArgumentsList.get(k).setValue(value);
		optionalArgumentsList.get(k).setDataType(dataType);
	}
	
	/**
	* Adds the name, value, data type, and short form value for optional argument list and sets each value.	
	* @param name the name of the optional argument.
	* @param value the value of the optional argument. 
	* @param dataType the data type of the optional argument.
	* @param shortForm the short form value of the optional argument. 
	*/
	
	public void addOptionalArgument(String name, String value, Argument.dataType dataType, String shortForm){
		optionalArgumentsList.add(new Argument(name));
		int k = getIndex(name);
		optionalArgumentsList.get(k).setValue(value);
		optionalArgumentsList.get(k).setDataType(dataType);
		optionalArgumentsList.get(k).setShortForm(shortForm);
	}
	
	/**
	* Adds the name, value, data type, short form, and description of the argument.
	* @param name the name of the optional argument.
	* @param value the value of the optional argument. 
	* @param dataType the data type of the optional argument.
	* @param shortForm the short form value of the optional argument. 
	* @param description the description of the optional argument.
	*/
	
	public void addOptionalArgument(String name, String value, Argument.dataType dataType, String shortForm, String[] restrictedValues){
		optionalArgumentsList.add(new Argument(name));
		int k = getIndex(name);
		optionalArgumentsList.get(k).setValue(value);
		optionalArgumentsList.get(k).setDataType(dataType);
		optionalArgumentsList.get(k).setShortForm(shortForm);
		optionalArgumentsList.get(k).setRestrictedValues(restrictedValues);
	}
	
	/**
	* Sets the restricted values to the optional argument Array List.
	* @param name The name of the optional argument
	* @param restrictedValues The String array containing the resctricted values.
	*/
	
	public void addRestrictedValues(String name, String[] restrictedValues){
		int k = getIndex(name);
		optionalArgumentsList.get(k).setRestrictedValues(restrictedValues);
	}
	
	/**
	* Returns the optional argument list as a String array.
	* @param name The name of the argument
	* @return optionalArgumentsList
	*/
	
	public String[] getRestrictedValues(String name){
		int k = getIndex(name);
		return optionalArgumentsList.get(k).getRestrictedValues().toArray(new String[optionalArgumentsList.get(k).getRestrictedValues().size()]);
	}
	
	/**
	* Sets the datatype of the optional argument.
	* @param name the name of the optional argument.
	* @param dataType the data type of the optional argument.
	*/
	
	public void setOptionalArgumentType(String name, Argument.dataType dataType){
		int index = getIndex(name);
		optionalArgumentsList.get(index).setDataType(dataType);
		
	}
	
	/**
	* Checks to see if each argument in argument list contains a name.
	* @param arg the argument in the argument list.
	* @return true true if a name is assigned to the argument.
	* @return false false if a name is not assigned to the argument.
	*/
	
	public boolean containsName(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName().equals(arg))
				return true;
		}
		return false;
	}
	
	/**
	* Sets the short form value of an optional argument. 
	* @param arg The optional argument.
	* @param shortForm The short form value of the optional argument.
	*/
	
	public void setShortForm(String arg, String shortForm){
		int index = getIndex(arg);
		optionalArgumentsList.get(index).setShortForm(shortForm);
	}
	
	/**
	* Returns the short form value of an optional argument.
	* @param arg The optional argument.
	* @return returns the optional argument list and the short form values for each argument in that list. 
	*/
	
	public String getShortForm(String arg){
		int index = getIndex(arg);
		return optionalArgumentsList.get(index).getShortForm();
	}
	
	/**
	* Requires the user to input a value for an argumnet in the commandline
	* @param the argument name
	* @return returns the shortform name for an argument
	*/
	
	public void makeRequired(String arg){
		int index = getIndex(arg);
		optionalArgumentsList.get(index).makeRequired();
	}
	
	/**
	* Parses the values from the command line.
	* @param args the values retrieved from the command line.
	*
	*/
	
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
				for(int j = 0; j < args.length; j++){
					String argument = args[i].replace("-", "");
					if(requiredArgs.get(i).getName().equals(argument) || requiredArgs.get(i).getShortForm().equals(argument))
						temp++;
				}
			}
			
			if(temp < requiredArgs.size()){
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
	
	/**
	* Gets the argument value from the command line and throws an exception if no value is found.
	* @param arg The argument being parsed.
	* @return Returns the argument list populated with the argument values.
	*/
	
	public String getValue(String arg){
		for(int i = 0; i < argumentList.size(); i++){
			if(argumentList.get(i).getName().equals(arg))
				return argumentList.get(i).getValue();
		}
		
		throw new NoValueFoundException(arg);
	}
	
	/**
	* Loops through the optional argument list and gets the value from each optional argument in the optional argument list. Throws an exception if no value is assigned to an optional argument.
	* @param arg the optional argument
	* @return returns the value for each optional argument.
	*/
	
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
	
	/**
	* Sets the description of the arguments in the optional argument list. 
	* @param arg the argument.
	* @param description the description of the argument. <br>
	* For example, an argument named "height" might have the description "The height of the box."
	*/
	
	public void setArgumentDescription(String arg, String description){
		int index = getIndex(arg);
		optionalArgumentsList.get(index).setDescription(description);
	}
	
	/**
	* Returns the decription of the arguments in the optional argument list. 
	* @param arg the argument
	* @return Returns the description of each argument from the optional argument list.
	*/
	
	public String getArgumentDescription(String arg){
		int index = getIndex(arg);
		return optionalArgumentsList.get(index).getDescription();
	}
	
	/**
	* Sets the name of the program.
	* @param name The name of the program.
	*/
	
	public void setProgramName(String name){
		this.programName = name;
	}
	
	/**
	* Checks to see if the datatype is an acceptable data type (boolean, int, float, or string) and converts each type to a string.
	* @param arg the argument
	* @return returns a string representation of the given datatype.
	*/
	
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
	
	/**
	* Returns the data type of the arguments in the optional argument list. 
	* @param arg the argument.
	* @return returns the data type.
	*/
	
	public Argument.dataType getDataType(String arg){
		int index = getIndex(arg);
		return optionalArgumentsList.get(index).getDataType();
	}
	
	/**
	* Returns the number of positonal arguments 
	* @return returns number of positional arguments
	*/
	
	public int getNumOfPositionalArgs() {
		return argumentList.size();
	}
	
	/**
	* Returns the number of optional arguments
	* @return returns number of optional arguments
	*/
	
	public int getNumOfNamedArgs() {
		return optionalArgumentsList.size();
	}
	
	/**
	* Returns a postional argument argument object
	* @return returns argument object
	*/
	
	public Argument getPositionalArg(int index) {
		return argumentList.get(index);
	}
	
	/**
	* Returns a optional argument argument object
	* @return returns argument object
	*/
	
	public Argument getNamedArg(int index) {
		return optionalArgumentsList.get(index);
	}
	
}
	