import edu.jsu.mcis.*;

import java.util.*;
import java.lang.*;

public class VolumeCalc{

	private static Parser p;
	private static XmlTool x;
	
	public static void main(String[] args){
		float l, w, h, r;
		double volume = 0;
		x = new XmlTool();
		p = x.load("../src/test/java/edu/jsu/mcis/loadFile.xml");
		p.parseValues(args);
		
		String type = p.getOptionalValue("type");
		int digits = Integer.parseInt(p.getOptionalValue("digits"));
		
		switch(type){
			case "box":
					l = Float.parseFloat(p.getValue("length"));
					w = Float.parseFloat(p.getValue("width"));
					h = Float.parseFloat(p.getValue("height"));
					volume = l*w*h;
					break;
			case "cylinder":
					h = Float.parseFloat(p.getValue("height"));
					r = Float.parseFloat(p.getValue("width"))/2;
					volume = Math.PI*Math.pow(r, 2)*h;
					break;
			case "sphere":
					r = Float.parseFloat(p.getValue("width"))/2;
					volume = (4.0/3.0)*Math.PI*Math.pow(r, 2);
					break;
		}
		System.out.println("Digits is: " + String.valueOf(digits));
		System.out.println("The volume of the " + type + " is: " + String.format("%." + Integer.toString(digits) + "f", volume));
	}
}