package testing.Ecosystem;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.testng.ITestNGMethod;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class WriteXMLFile extends TestData{

	testing.Ecosystem.Xls_Reader xls=null;
	private static String parallelExecution="";
	private static String threadcount="2";
	private static String specificExecution = "NO";
	private List<String> groups = new ArrayList<String>();
	private static String brwsr = "FF17";


	public static void main(String[] args){
		if(!args[0].equals("${parallelType}")){
			parallelExecution = args[0];
			if(!args[1].equals("${count}")){
				Pattern number = Pattern.compile("^[1-5]{1}$");
				Matcher m = number.matcher(args[1]);
				if(m.find())
					threadcount = args[1];
			}
		}
		if(!(args[3].equals("${browser}"))){
			brwsr = args[3];
		}

		WriteXMLFile f = new WriteXMLFile();

		if(args[2].equalsIgnoreCase("yes") || args[2].equalsIgnoreCase("y")){
			specificExecution = "YES";
			if(args[0].equals("methods")){
				parallelExecution = "tests";
				System.out.println("When grouping is selected, parallel execution will be done Class Wise..");
			}
		}
		f.CreateXMLFile();
	}


	public void CreateXMLFile(){
		try{
			//datatable.Xls_Reader xls1 = new datatable.Xls_Reader(mainSheet);
			for(int i=2;i<=testing.Ecosystem.TestData.globalXLS.getRowCount("Sheet1");i++){
				if(testing.Ecosystem.TestData.globalXLS.getCellData("Sheet1", "Refer_Sheet", i).equalsIgnoreCase("Y")){
					testing.Ecosystem.TestData.xlsPath= testing.Ecosystem.TestData.globalXLS.getCellData("Sheet1", "Reference_Sheet_Path", i);
					xls = new testing.Ecosystem.Xls_Reader(testing.Ecosystem.TestData.globalXLS.getCellData("Sheet1", "Reference_Sheet_Path", i));
					CreateXML(0,null,"testng");
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void CreateXML(int counter,String[] allnames,String xmlFileName) {
		if(xls==null && counter==0)
			System.out.println("PLEASE ASSIGN A TEST CASE SHEET IN '''excelSheetRef.xls''' FILE");

		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("suite");
			doc.appendChild(rootElement);
			
			File f = new File("");
			String name =f.getAbsolutePath();
			String mainName = name.substring(name.lastIndexOf("\\")+1, name.length());

			CreateAttribute("name",mainName,rootElement,doc);

			if(!WriteXMLFile.parallelExecution.equals("")){
				CreateAttribute("parallel",parallelExecution,rootElement,doc);
				CreateAttribute("thread-count",threadcount,rootElement,doc);
			}
			Element listeners = CreateElement("listeners",rootElement,doc);
			Element listener1 = CreateElement("listener",listeners,doc);
			Element listener2 = CreateElement("listener",listeners,doc);

			CreateAttribute("class-name","ngframework.TakeScreenshot",listener1,doc);
			CreateAttribute("class-name","ngframework.TestNGError",listener2,doc);

			String xmlType = xmlFileName.substring(xmlFileName.indexOf("_")+1, xmlFileName.length());
			/*****************************************/
			CreateMainXML(doc,rootElement);
			/*****************************************/

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(".\\"+xmlFileName+".xml"));

			transformer.transform(source, result);
			System.out.println("XML File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}


	private String CreateExtraXML_grouping_TRUE(Document document,String xmlType){
		String excelformation="";
		String xmlcreation="";
		testing.Ecosystem.Xls_Reader excel;
		NodeList testmethods = document.getElementsByTagName("test");
		for(int i=0;i<testmethods.getLength();i++){
			NodeList childnodes =testmethods.item(i).getChildNodes();
			for(int j=0;j<childnodes.getLength();j++){
				Node classesnode = childnodes.item(j);
				if("classes".equals(classesnode.getNodeName())){
					Node classnode = classesnode.getFirstChild();
					NamedNodeMap node = classnode.getAttributes();
					Node className = node.getNamedItem("name");
					String classnme = className.getNodeValue();
					excel = new testing.Ecosystem.Xls_Reader(testing.Ecosystem.TestData.tmpFolder+classnme+".xls");
					Element methods = document.createElement("methods");
					classnode.appendChild(methods);
					excelformation = Create_IncludeExclude_ForExtraXMLs(excel,methods,xmlType,document);
					if(excelformation.equals("yes"))
						xmlcreation="yes";
				}
			}
		}
		return xmlcreation;
	}

	private String CreateExtraXML_grouping_FALSE(Document document, String xmlType){
		String excelformation="";
		testing.Ecosystem.Xls_Reader excel;
		NodeList testmethods = document.getElementsByTagName("test");
		for(int i=0;i<testmethods.getLength();i++){
			NodeList childnodes =testmethods.item(i).getChildNodes();
			for(int j=0;j<childnodes.getLength();j++){
				Node classesnode = childnodes.item(j);
				if("classes".equals(classesnode.getNodeName())){
					Node classnode = classesnode.getFirstChild();
					NamedNodeMap node = classnode.getAttributes();
					Node className = node.getNamedItem("name");
					String classnme = className.getNodeValue();
					excel = new testing.Ecosystem.Xls_Reader(testing.Ecosystem.TestData.tmpFolder+classnme+".xls");
					Node methodnode = classnode.getFirstChild();
					NodeList include_exclude_list = methodnode.getChildNodes();
					for(int k=0;k<include_exclude_list.getLength();k++){
						Node elem = include_exclude_list.item(k);
						String element = elem.getNodeName();
						NamedNodeMap attribute = elem.getAttributes();
						Node attrName = attribute.getNamedItem("name");
						String name = attrName.getNodeValue();
						if(!(element.equals("exclude"))){
							for(int excelcount=2;excelcount<excel.getRowCount("Sheet1");excelcount++){
								int rownum=excel.getCellRowNum("Sheet1", "testcase", name);
								String status = excel.getCellData("Sheet1", "status", rownum);
								if(xmlType.equals("fail")){
									if(status.equalsIgnoreCase("pass"))
										document.renameNode(elem, null, "exclude");
									else if(status.equalsIgnoreCase("skip")){
										document.renameNode(elem, null, "exclude");
									}else if(status.equalsIgnoreCase("fail"))
										excelformation="yes";
								}else if(xmlType.equals("skip")){
									if(status.equalsIgnoreCase("pass"))
										document.renameNode(elem, null, "exclude");
									else if(status.equalsIgnoreCase("fail"))
										document.renameNode(elem, null, "exclude");
									else if(status.equalsIgnoreCase("skip"))
										excelformation="yes";
								}
							}
						}
					}
				}
			}
		}
		return excelformation;
	}
	
	public void CreateExtraXML(String grouping,String xmlType){
		String xmlcreationstatus="";
		try {
			String file = ".\\testng.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document document = docBuilder.parse(file);
			
			if(grouping.equalsIgnoreCase("yes"))
				xmlcreationstatus = CreateExtraXML_grouping_TRUE(document,xmlType);
			else
				xmlcreationstatus= CreateExtraXML_grouping_FALSE(document,xmlType);
			
			if(xmlcreationstatus.equals("yes")){
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(document);
				StreamResult result = new StreamResult(new File(".\\testng_"+xmlType+".xml"));
				transformer.transform(source, result);
				
				System.out.println("XML File saved!");
			}else{
				File f = new File(".\\testng_"+xmlType+".xml");
				if(f.exists())
					f.delete();
				System.out.println("There are no "+xmlType+"ed cases..");
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}


	private void CreateMainXML(Document doc,Element rootElement){
		for(int l=2;l<=testing.Ecosystem.TestData.globalXLS.getRowCount("Sheet1");l++){
			if(testing.Ecosystem.TestData.globalXLS.getCellData("Sheet1", "Refer_Sheet", l).equalsIgnoreCase("Y")){
				xls = new Xls_Reader(testing.Ecosystem.TestData.globalXLS.getCellData("Sheet1", "Reference_Sheet_Path", l));
			}
			else{
				continue;
			}
			int packageCounter = xls.getRowCount("PackageSheet");
			int testCaseCounter = xls.getRowCount("TestCaseSheet");

			for (int i=2;i<=packageCounter;i++){
				String moduleName = xls.getCellData("PackageSheet", "ClassFileName", i);

				Element test = doc.createElement("test");
				rootElement.appendChild(test);
				Attr attr1 = doc.createAttribute("name");
				attr1.setValue(moduleName);
				test.setAttributeNode(attr1);
				Attr attr2 = doc.createAttribute("preserve-order");
				attr2.setValue("true");
				test.setAttributeNode(attr2);
				
				Element classes = doc.createElement("classes");
				test.appendChild(classes);

				Element class1 = doc.createElement("class");
				classes.appendChild(class1);
				String className = xls.getCellData("PackageSheet", "PackageName", i)+"."+xls.getCellData("PackageSheet", "ClassFileName", i);
				Attr attrr = doc.createAttribute("name");
				attrr.setValue(className);
				class1.setAttributeNode(attrr);

				if(specificExecution.equalsIgnoreCase("no"))
					Create_IncludeExclude_TestMethod_UsingExcel(testCaseCounter, doc, className, class1);
				else if(specificExecution.equalsIgnoreCase("yes"))
					CreateGroupTestMethod(test, doc, className, class1);
			}
		}
	}

	private Element CreateElement(String ElementName, Element ParentElement, Document doc){
		Element element = doc.createElement(ElementName);
		ParentElement.appendChild(element);
		return element;
	}

	private void CreateAttribute(String AttributeName,String AttributeValue, Element ParentElement, Document doc){
		Attr attr = doc.createAttribute(AttributeName);
		attr.setValue(AttributeValue);
		ParentElement.setAttributeNode(attr);
	}

	private void CreateGroupTestMethod(Element test, Document doc, String className, Element methods){
		List<String> names = getGroupNames("Enter the GROUP Names for Class:-"+className+"-, (Enter '#' to END):");
		Element grp = CreateElement("groups",test, doc);
		Element param =CreateElement("parameter",grp, doc);
		CreateAttribute("name","browserType",param,doc);
		CreateAttribute("value",brwsr,param,doc);
		Element run =CreateElement("run",grp, doc);

		for(int counter=0; counter<names.size(); counter++){
			Element include =CreateElement("include",run, doc);
			CreateAttribute("name",names.get(counter),include,doc);
		}
	}

	public String Create_IncludeExclude_ForExtraXMLs(testing.Ecosystem.Xls_Reader excel, Element methods, String xmlCreationtype, Document doc){
		String excelformation="";
		int testCaseCounter = excel.getRowCount("Sheet1");
		for (int j=2;j<=testCaseCounter;j++){
			String status = excel.getCellData("Sheet1", "status", j);
			String testcase = excel.getCellData("Sheet1", "testcase", j);
			String browser = excel.getCellData("Sheet1", "browser", j);
			if(xmlCreationtype.equalsIgnoreCase("fail")){
				if(status.equals("Pass")){
					Element exclude = CreateElement("exclude",methods,doc);
					CreateAttribute("name",testcase,exclude,doc);
				}else if(status.equals("Fail")){
					excelformation="yes";
					Element include = CreateElement("include",methods,doc);
					CreateAttribute("name",testcase,include,doc);
					/*Element parameter = CreateElement("parameter",include,doc);
					if(browser.equals(""))
						browser="FF";
					CreateAttribute("value",browser,parameter,doc);
					CreateAttribute("name","browserType",parameter,doc);*/
				}else if(status.equals("Skip")){
					Element exclude = CreateElement("exclude",methods,doc);
					CreateAttribute("name",testcase,exclude,doc);
				}
			}
			else if(xmlCreationtype.equalsIgnoreCase("skip")){
				if(status.equals("Pass")){
					Element exclude = CreateElement("exclude",methods,doc);
					CreateAttribute("name",testcase,exclude,doc);
				}else if(status.equals("Fail")){
					Element exclude = CreateElement("exclude",methods,doc);
					CreateAttribute("name",testcase,exclude,doc);
				}else if(status.equals("Skip")){
					excelformation="yes";
					Element include = CreateElement("include",methods,doc);
					CreateAttribute("name",testcase,include,doc);
					/*Element parameter = CreateElement("parameter",include,doc);
					if(browser.equals(""))
						browser="FF";
					CreateAttribute("value",browser,parameter,doc);
					CreateAttribute("name","browserType",parameter,doc);*/
				}
			}
		}
		return excelformation;
	}

	private void Create_IncludeExclude_TestMethod_UsingExcel(int testCaseCounter, Document doc, String className, Element class1){
		Element methods = doc.createElement("methods");
		class1.appendChild(methods);
		for (int j=2;j<=testCaseCounter;j++){
			String classNameWithinTags = xls.getCellData("TestCaseSheet", "PackageName", j)+"."+xls.getCellData("TestCaseSheet", "ClassFileName", j);
			if(className.equalsIgnoreCase(classNameWithinTags)){
				if(xls.getCellData("TestCaseSheet", "RunAutomation", j).equalsIgnoreCase("Yes") || xls.getCellData("TestCaseSheet", "RunAutomation", j).equalsIgnoreCase("Y")){
					Element include = CreateElement("include",methods,doc);
					CreateAttribute("name",xls.getCellData("TestCaseSheet", "TestCaseID", j),include,doc);

					String value="";
					Element parameter = CreateElement("parameter",include,doc);
					if(!xls.getCellData("TestCaseSheet", "Browser", j).equals("")){
						value = xls.getCellData("TestCaseSheet", "Browser", j);
					}else{
						value = "FF";
					}
					CreateAttribute("value",value,parameter,doc);
					CreateAttribute("name","browserType",parameter,doc);
				}
				else{
					Element exclude = CreateElement("exclude",methods,doc);
					CreateAttribute("name",xls.getCellData("TestCaseSheet", "TestCaseID", j),exclude,doc);
				}
			}
		}
	}


	private List<String> getGroupNames(String message){
		Scanner scan = new Scanner(System.in);
		groups.clear();
		System.out.println(message);
		while(scan.hasNextLine()) {
			String name = scan.nextLine();
			if(name.equals("#"))
				break;
			System.out.println("Group Name:"+name);
			groups.add(name);
		}
		String names="";
		for(int count=0;count<groups.size();count++){
			names= names+","+groups.get(count);
		}
		System.out.println("Selected Group Names Are: '"+names.substring(1, names.length())+"'");
		System.out.println("Confirm (Y/N):");
		String ans = scan.nextLine();
		if(!ans.equalsIgnoreCase("y")){
			scan.reset();
			getGroupNames(message);
		}
		if(groups.size()==0){
			System.out.println("You have not mentioned any Group that need to be executed. Please specify atleast one.");
			scan.reset();
			getGroupNames(message);
		}
		return groups;
	}

	public static ITestNGMethod createITestNGMethod (ITestNGMethod meth, Method method){
		System.out.println("ITestMethod Me AA Geya");
		String[] a =meth.getGroups();
		for(int i=0;i<a.length;i++)
			System.out.println("Group Name:"+a[i]);
		return meth;
	}

}
