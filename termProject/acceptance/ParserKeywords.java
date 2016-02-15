import edu.jsu.mcis.*;
import java.util.*;

public class ParserKeywords{
	
	private Parser p;
	private String output;
	
	public void startVolumeCacluatorWithArguments(String[] args){
		System.out.println(Arrays.toString(args));
		p = new Parser();
		String[] asdf = {"length", "width", "height"};
		p.insertNames(asdf);
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
		p.insertName("pet");
		p.insertName("number");
		p.insertName("rainy");
		p.insertName("bathrooms");
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