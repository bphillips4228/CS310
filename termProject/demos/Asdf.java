import edu.jsu.mcis.*;

public class Asdf{
	
	Parser p;
	
	public Asdf(){
		p = new Parser();
	}
	
	public static void main(String[] args){
		Asdf asdf = new Asdf();
		asdf.p.addArgument("length", Argument.dataType.FLOAT);
		asdf.p.addArgument("width", Argument.dataType.FLOAT);
		asdf.p.addArgument("height", Argument.dataType.FLOAT);
		String[] help = {"help", "false"};
		asdf.p.addOptionalArgument(help);
		System.out.println(asdf.p.getOptionalValue("help"));
		asdf.p.setShortForm("help", "h");
		System.out.println(asdf.p.shortForm("help"));
		asdf.p.setOptionalArgumentType("help", Argument.dataType.BOOLEAN);
		String[] digits = {"digits", "4"};
		asdf.p.addOptionalArgument(digits);
		System.out.println(asdf.p.getOptionalValue("digits"));
		asdf.p.parseValues(args);
		System.out.println(asdf.p.getOptionalValue("help"));
		float volume;
		float h = Float.parseFloat(asdf.p.getValue("height"));
		float w = Float.parseFloat(asdf.p.getValue("width"));
		float l = Float.parseFloat(asdf.p.getValue("length"));
		volume = l*w*h;
		System.out.println(volume);
	}

}