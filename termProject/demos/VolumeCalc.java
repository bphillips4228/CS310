import java.util.*;

public class VolumeCalc{
	
	private String length, width, height;
	private int numberOfArgs;
	
	public VolumeCalc(){
		numberOfArgs = 3;
	}
	
	public String calculateVolume(){
		float volume;
		float h = Float.parseFloat(height);
		float w = Float.parseFloat(width);
		float l = Float.parseFloat(length);
		volume = l*w*h;
		return Float.toString(volume);
	}
	
	public void setDimensions(String l, String w, String h){
		height = h;
		width = w;
		length = l;
		
	}
	
	public static void main(String[] args){
		VolumeCalc VC = new VolumeCalc();
		float a;
		
		for(int i = 0; i < args.length; i++){
			try{
					a = Float.parseFloat(args[i]);	
			}catch(NumberFormatException nfe){
				System.err.print("VolumeCalculator.java: error: unrecognized arguments:");
				System.err.print(" " + args[i]);
			}
		}
		
		try{
			//VC.setDimensions(args[0], args[1], args[2]);
			if(args.length < 3)
				throw new IndexOutOfBoundsException();
		
			else if(args.length > 3){
				throw new TooManyArgsException();
			}
		}catch(TooManyArgsException ex){
			System.err.print("VolumeCalculator.java: error: unrecognized arguments:");
			for(int j = VC.numberOfArgs; j < args.length; j++)
				System.out.print(" " + args[j]);
			
		}catch(IndexOutOfBoundsException ex){
			System.err.println("VolumeCalculator.java: error: the following arguments are required:");
			if(args.length == 2)
				System.out.print(" height");
			if(args.length == 1)
				System.out.print(" width, height");
			else
				System.out.print(" length, width, height");
			
		}
		
		System.out.println("The volume is: " + VC.calculateVolume());
	}
	
	
}

class TooManyArgsException extends Exception{
	
	public TooManyArgsException(){}
	
	public TooManyArgsException(String message){
		super(message);
	}
	
}

/*if(args.length < 3)
				System.err.println("VolumeCalculator.java: error: the following arguments are required: height");
		
			else if(args.length > 3){
				System.err.print("VolumeCalculator.java: error: unrecognized arguments:");
				for(int i = VC.numberOfArgs; i < args.length; i++)
				System.out.print(" " + args[i]);
			}*/