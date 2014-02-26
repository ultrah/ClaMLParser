package de.ebertp.dai.claml;

import java.io.File;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;

import de.ebertp.dai.claml.model.ClaMLRoot;

public class ClaMLParserMain {

	public static void main(String[] args) {
		File input;
		File output;
		File logFile;

		//if no parameters are give, use default location
		if (args.length >= 2) {
			input = new File(args[0]);
			output = new File(args[1]);
		} else {
			input = new File("clamlfile.xml");
			output = new File("xmloutput.xml");
		}

		System.out.println(System.getProperty("user.dir"));
		
		//if no parameters are give, use default log location
		if (args.length == 3) {
			logFile = new File(args[2]);
		} else {
			logFile = new File("log.txt");
		}

		System.out.println("ClaMLParser: Running....");

		ClaMLRoot claMLRoot = null;
		try {
			claMLRoot = new ClaMLParser().parseClaMLFromXML(input);
		} catch (DocumentException e1) {
			System.out.println("ClaMLParser: Error parsing ClaML File, exiting!");
			e1.printStackTrace();
		}

		if (claMLRoot != null) {

			Document document = new CLaMLXmlCreator(true).toDocument(claMLRoot);

			try {
				ParserUtils.writeXmlToFile(output, document, true);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("ClaMLParser: Error writing Output File");
			}
			try {
				ParserUtils.writeLog(claMLRoot, logFile, true);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("ClaMLParser: Error writing Log");
			}

			System.out.println("ClaMLParser: Done");
		}
	}

}
