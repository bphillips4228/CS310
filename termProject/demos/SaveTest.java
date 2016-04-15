import edu.jsu.mcis.*;

public class SaveTest {
	
	public SaveTest() {
		
	}
	
	public static void main(String[] args){
		Parser p = new Parser();
		XmlTool x = new XmlTool();
		p.addArgument("length", Argument.dataType.FLOAT);
		p.addArgument("width", Argument.dataType.FLOAT);
		p.addArgument("height", Argument.dataType.FLOAT);
		p.addOptionalArgument("type", "false", Argument.dataType.BOOLEAN, "t");
		p.addOptionalArgument("digits", "4");
		p.parseValues(args);
		x.saveToXML("saveFile.xml", p);
		x.saveToXML("saveFile.xml", p);
	}
}