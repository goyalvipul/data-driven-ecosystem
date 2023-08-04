package testing.Ecosystem;

import java.io.FileOutputStream;
import java.util.*;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class DataManipulation{
	testing.Ecosystem.Xls_Reader xls;
	static ArrayList<String> allsheetnames = new ArrayList<String>();
	
	
	public static void main(String args[]){
		DataManipulation data = new DataManipulation();
		if(args[0].equals("CreateExcel"))
			data.CreateTempExcel();
		else if(args[0].equals("modifyxml"))
			data.modifyXML();
		else if(args[0].equals("CreateXMLs"))
			data.CreateExtraXML(args[1]);
		else if(args[0].equals("MergePreviousExecution"))
			data.MergePreviousExecution();
		else if(args[0].equals("setup"))
			data.setup();
	}

	public void CreateTempExcel(){
		try{
			String filename = testing.Ecosystem.TestData.tmpFolder+"/allsheetnames.xls" ;
			HSSFWorkbook workbook=new HSSFWorkbook();
			HSSFSheet sheet =  workbook.createSheet("Sheet1");  
			HSSFRow rowhead=   sheet.createRow(0);
			String[] columnNames = {"names"};
			for(int col=0;col<columnNames.length;col++){
				rowhead.createCell(col).setCellValue(columnNames[col]);
			}
			FileOutputStream fileOut =  new FileOutputStream(filename);
			workbook.write(fileOut);
			fileOut.close();
			testing.Ecosystem.Xls_Reader read = new testing.Ecosystem.Xls_Reader(filename);
			int sheetrows = 2;
			
			for(int i=2;i<=testing.Ecosystem.TestData.globalXLS.getRowCount("Sheet1");i++){
				if(testing.Ecosystem.TestData.globalXLS.getCellData("Sheet1", "Refer_Sheet", i).equalsIgnoreCase("Y")){
					testing.Ecosystem.TestData.xlsPath= testing.Ecosystem.TestData.globalXLS.getCellData("Sheet1", "Reference_Sheet_Path", i);
					xls = new testing.Ecosystem.Xls_Reader(testing.Ecosystem.TestData.xlsPath);
				}
				else
					continue;
				int packageCounter = xls.getRowCount("PackageSheet");
				for(int j=2;j<=packageCounter;j++){
					String sheetname = xls.getCellData("PackageSheet", "PackageName", j)+"."+xls.getCellData("PackageSheet", "ClassFileName", j);
					read.setCellData("Sheet1", "names", sheetrows, sheetname);sheetrows++;
					String file = testing.Ecosystem.TestData.tmpFolder+"/"+sheetname+".xls" ;
					HSSFWorkbook workbook1=new HSSFWorkbook();
					HSSFSheet sheet1 =  workbook1.createSheet("Sheet1");  
					HSSFRow rowhead1=   sheet1.createRow(0);
					String[] columnNames1 = {"testcase","status","browser"};
					for(int col=0;col<columnNames1.length;col++){
						rowhead1.createCell(col).setCellValue(columnNames1[col]);
					}
					FileOutputStream fileOut1 =  new FileOutputStream(file);
					workbook1.write(fileOut1);
					fileOut1.close();
				}
			}
		} catch ( Exception ex ) {
			System.out.println(ex);
		}
		System.out.println("Data Created.");
	}

	public void setup(){
		try{
			Xls_Reader xls = new Xls_Reader();
			String[] columNames = {"Reference_Sheet_Path","Refer_Sheet"};
			xls.createXlsFile("excelSheetRef", ".\\TestCaseCreation", columNames);
			String[] testcaseSheet = {"Module","Result","TestCaseID","RunAutomation","PackageName","ClassFileName","Browser","TestCase",
					"TestSteps","ExpectedResults"};
			xls.createXlsFile("excelSheetRef", ".\\TestCaseCreation\\TestCases", testcaseSheet);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void modifyXML(){
		try{
			String filepath = ".\\test-output\\testng-results.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
			NodeList testmethods = doc.getElementsByTagName("test-method");
			for(int j=0; j<testmethods.getLength();j++){
				NodeList list1 =testmethods.item(j).getChildNodes();
				for (int i = 0; i < list1.getLength(); i++) {
					Node node = list1.item(i);
					if ("exception".equals(node.getNodeName())) {

						NamedNodeMap exception_attr =  node.getAttributes();
						Node exception_node_attr = exception_attr.getNamedItem("class");
						if("java.lang.AssertionError".equals(exception_node_attr.getNodeValue())){}else{
							NamedNodeMap attr = testmethods.item(j).getAttributes();
							Node nodeAttr = attr.getNamedItem("status");
							nodeAttr.setTextContent("SKIP");

							Element ro = doc.createElement("reporter-output");
							String act ="%3c![CDATA[[Status]:TestFour_SKIPPED_RUNTIME]]%3e";
							String result = java.net.URLDecoder.decode(act, "UTF8");

							ro.appendChild(doc.createTextNode(result));
							testmethods.item(j).appendChild(ro);
							System.out.println("Modified and::"+exception_node_attr.getNodeValue());
							break;
						}
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			System.out.println("XML Transformed");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}
	
	public void MergePreviousExecution(){
		try{
			String NewResultXML = ".\\test-output\\testng-results.xml";
			String MainResultXML = testing.Ecosystem.TestData.tmpFolder+"\\testng-results\\testng-results.xml";
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document docMain = docBuilder.parse(MainResultXML);
			Document docNew = docBuilder.parse(NewResultXML);
			NodeList testmethodsMain = docMain.getElementsByTagName("test-method");
			NodeList testmethodsNew = docNew.getElementsByTagName("test-method");
			
			for(int i=0; i<testmethodsNew.getLength();i++){
				NamedNodeMap newAttributes = testmethodsNew.item(i).getAttributes();
				Node newMethodName = newAttributes.getNamedItem("name");
				String newName = newMethodName.getNodeValue();
				Node newMethodStatus = newAttributes.getNamedItem("status");
				String newStatus = newMethodStatus.getNodeValue();
				for(int j=0;j<testmethodsMain.getLength();j++){
					NamedNodeMap mainAttributes = testmethodsMain.item(j).getAttributes();
					Node mainMethodName = mainAttributes.getNamedItem("name");
					String mainName = mainMethodName.getNodeValue();
					Node mainMethodStatus = mainAttributes.getNamedItem("status");
					String mainStatus = mainMethodStatus.getNodeValue();
					if(newName.equals(mainName)){
						if(!(newStatus.equals(mainStatus))){
							Node copyTo = testmethodsMain.item(j);
							for (Node child; (child = copyTo.getFirstChild()) != null; copyTo.removeChild(child));
							NodeList childNodesNew = testmethodsNew.item(i).getChildNodes();
							for(int cnode=0;cnode<childNodesNew.getLength();cnode++){
								Node attachNode = childNodesNew.item(cnode);
								Node copyFrom = docMain.importNode(attachNode, true);
								copyTo.appendChild(copyFrom);
							}
							mainMethodStatus.setTextContent(newStatus);
						}
					}
				}
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(docMain);
			StreamResult result = new StreamResult(new File(MainResultXML));
			transformer.transform(source, result);
			System.out.println("XML Transformed");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} 
	}

		
	public void CreateExtraXML(String grouping){
		WriteXMLFile xml = new WriteXMLFile();
		xml.CreateExtraXML(grouping,"fail");
		xml.CreateExtraXML(grouping,"skip");
	}

	
	/*public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
		List<IMethodInstance> result = new ArrayList<IMethodInstance>();
	    for (IMethodInstance m : methods) {
	      Method javaMethod = m.getMethod().getConstructorOrMethod().getMethod();
	      Set<String> groups = new HashSet<String>();
	      if (javaMethod.isAnnotationPresent(BeforeMethod.class)) {
	    	  BeforeMethod test = javaMethod.getAnnotation(BeforeMethod.class);
	        for (String group : test.groups()) {
	          groups.add(group);
	        }
	      }
	      String[] arr = new String[groups.size()];
	      groups.toArray(arr);
	      System.out.println("Before Method Name:"+javaMethod.getName());
	      if(javaMethod.getName().equals("TC_Resman_3")){
	    	  System.out.println("Method Name:"+javaMethod.getName());
	    	  for(int i=0;i<arr.length;i++)
	    		  System.out.println("Groups Defined:"+arr[i]);
	      }
	      if (groups.contains("smoke")) {
	        result.add(m);
	      }
	    }
	    return result;
	  }*/
	
	
	
}

