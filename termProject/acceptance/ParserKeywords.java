import edu.jsu.mcis.*;
import java.util.*;

public class ParserKeywords{
	
	private Parser p;
	private String output;
	
	public void startProgramWithArguments(String[] args){
		p = new Parser();
		p.addArgument("length", "float");
		p.addArgument("width", "float");
		p.addArgument("height", "float");
		try{
			p.parseValues(args);
		}
		catch(WrongTypeException ex){
			output = "usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: argument width: invalid float value: " + ex.getWrongTypeArg();
		}
		catch(HelpException ex){
			output = "usage: java VolumeCalculator length width height\nCalcuate the volume of a box.\npositional arguments:\nlength the length of the box (float)\nwidth the width of the box(float)\nheight the height of the box(float)";
		}
	}
	
	
	public void startVolumeCacluatorWithArguments(String[] args){
		p = new Parser();
		String[] asdf = {"length", "width", "height"};
		p.addArguments(asdf);
		try {
			p.parseValues(args);
			float volume;
			float h = Float.parseFloat(p.getValue("height"));
			float w = Float.parseFloat(p.getValue("width"));
			float l = Float.parseFloat(p.getValue("length"));
			volume = l*w*h;
			output = Float.toString(volume);
		}
		catch(TooManyArgsException ex) {
			output = "usage: java VolumeCalculator length width height\nVolumeCalcultor.java: error: unrecognized arguments: " + ex.getExtraArgs();
		}
		
	}
	
	public void startAbsurdProgramWithArguments(String[] args){
		p = new Parser();
		p.addArgument("pet");
		p.addArgument("number");
		p.addArgument("rainy");
		p.addArgument("bathrooms");
		p.parseValues(args);
	} 
	
	public String getLength(){
		return p.getValue("length");
	}
	
	public String getWidth(){
		return p.getValue("width");
	}
	
	public String getHeight(){
		return p.getValue("height");
	}
	
	public String getRainy(){
		return p.getValue("rainy");
	}
	
	public String getPet(){
		return p.getValue("pet");
	}
	
	public String getBathrooms(){
		return p.getValue("bathrooms");
	}
	
	public String getNumber(){
		return p.getValue("number");
	}
	
	public String getProgramOutput(){
		return output;
	}
	
	
}