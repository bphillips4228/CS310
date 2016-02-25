package edu.jsu.mcis;

public class HelpException extends RuntimeException{
	
	public HelpException(){
		super("usage: java VolumeCalculator length width height\nCalcuate the volume of a box.\npositional arguments:\n   length the length of the box (float)\n   width the width of the box(float)\n   height the height of the box(float)");
	}
}