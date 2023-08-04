package testing.Ecosystem;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
	File file;
	DocumentBuilderFactory document;
	DocumentBuilder builder;
	Document doc;
	NodeList nodelist;
	Node node;
	
	public XMLParser(String filepath){
		try{
			file = new File(filepath);
			document = DocumentBuilderFactory.newInstance();
			builder = document.newDocumentBuilder();
			doc = builder.parse(file);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getXMLNodeValue(String tagname, int tagindex){
		nodelist = doc.getElementsByTagName(tagname);
		node = nodelist.item(tagindex);
		String value = node.getTextContent();
		return value;
	}
	
	public int getXMLNodeCount(String tagname){
		nodelist = doc.getElementsByTagName(tagname);
		return nodelist.getLength();
	}
	
}
