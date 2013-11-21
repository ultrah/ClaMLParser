package de.ebertp.dai.claml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import de.ebertp.dai.claml.model.ClaMLClass;
import de.ebertp.dai.claml.model.ClaMLModifierClass;
import de.ebertp.dai.claml.model.ClaMLRoot;

public class ParserUtils {
	
	public static Document readXmlFromFile(File file) throws DocumentException {
		System.out.println("ParserUtil: Reading XML from "+file.toString());
		
        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        
        System.out.println("ParserUtil: Read XML from "+file.toString());
        return document;
	}
	
	public static Document readXmlFromFile(InputStream is) throws DocumentException {
		System.out.println("ParserUtil: Reading XML from "+is.toString());
		
        SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        
        System.out.println("ParserUtil: Read XML from "+is.toString());
        return document;
	}
	
	
	public static boolean writeXmlToFile(File file, Document document, boolean deleteFirst) {
		System.out.println("ParserUtil: Writing output to "+file.toString());
		if(deleteFirst){
			file.delete();
		}
		
		try {
			XMLWriter writer = new XMLWriter(new FileWriter(file), OutputFormat.createPrettyPrint());
			writer.write(document);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println("ParserUtil: Output written to "+file.toString());
		return true;
	}
	
	
	public static void writeLog(ClaMLRoot claMLRoot, File file, boolean deleteFirst){
		if(deleteFirst){
			file.delete();
		}
		
		System.out.println("ClaMLParser: Writing log to "+file.toString());
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			printClassTree(out, claMLRoot);
			out.newLine();
			out.write("###################################################################");
			out.newLine();
			printModifierTree(out, claMLRoot);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("ClaMLParser: Log written to "+file.toString());
	}
	
	public static void printClassTree(BufferedWriter out, ClaMLClass root) throws IOException{
		out.write(root.toString());
		out.newLine();
		for(ClaMLClass claMLClass : root.getChildren().values()){
			printClassTree(out, claMLClass);
		}
	}
	
	public  static void printModifierTree(BufferedWriter out, ClaMLRoot root) throws IOException {
		out.write(root.toString());
		out.newLine();
		for(ClaMLModifierClass claMLModifier : root.getModifiers().values()){
			out.write(claMLModifier.toString());
			out.newLine();
			for(ClaMLModifierClass claMLModifierChild : claMLModifier.getChildren().values()){
				claMLModifierChild.toString();
			}
		}
	}

}
