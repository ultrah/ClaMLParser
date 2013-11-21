package de.ebertp.dai.claml;

import java.io.File;

import org.dom4j.Document;

import de.ebertp.dai.claml.model.ClaMLRoot;

public class ClaMLParserMain {
	
	public static void main(String[] args){
		File input;
		File output;
		File logFile;

		if (args.length >= 2) {
			input = new File(args[0]);
			output = new File(args[1]);
		}
		else {
			input = new File("C:/clamlfile.xml");
			output = new File("C:/xmloutput.xml");
		}
		
		if (args.length == 3) {
			logFile = new File(args[2]);
		}
		else {
			logFile = new File("C:/log.txt");
		}
		
		System.out.println("ClaMLParser: Running....");
		
		ClaMLRoot claMLRoot = new ClaMLParser().parseClaMLFromXML(input);
		Document document = new CLaMLXmlCreator(true).toDocument(claMLRoot);
		
		ParserUtils.writeXmlToFile(output, document, true);
		ParserUtils.writeLog(claMLRoot, logFile, true);
		
		System.out.println("ClaMLParser: Done");
	}
	

}
