package testing.Ecosystem;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;



public class TakeScreenshot extends TestListenerAdapter{
	static int sheetCount = 2;
	testing.Ecosystem.Xls_Reader xls;
		
	@Override
	public void onTestFailure(ITestResult tr) {
		try {
			testing.Ecosystem.TestData.AllExecutedCases.add(tr.getTestName());
			String[] paths = null;
			if(testing.Ecosystem.TestData.xlsPath.contains(","))
				paths = testing.Ecosystem.TestData.xlsPath.split(",");
			for(int j=0;j<paths.length;j++){
				if(!paths[j].equals("")){
					testing.Ecosystem.Xls_Reader xls = new testing.Ecosystem.Xls_Reader(paths[j]);
					for(int i=2;i<=xls.getRowCount("TestCaseSheet");i++){
						if(testing.Ecosystem.TestData.TestCaseId.equalsIgnoreCase(xls.getCellData("TestCaseSheet", "TestCaseID", i))){
							xls.setCellData("TestCaseSheet", "Result", i, "Fail");
							String error = tr.getThrowable().toString();
							if(error.startsWith("java.lang.AssertionError")){
								InsertTmpData(tr.getInstanceName(),tr.getName(),"Fail",xls.getCellData("TestCaseSheet", "Browser", i));
							}
							else{
								tr.setStatus(ITestResult.SKIP);
								InsertTmpData(tr.getInstanceName(),tr.getName(),"Skip",xls.getCellData("TestCaseSheet", "Browser", i));
							}
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		try {
			xUpdateTestDetails("FAIL");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			xScreenShot();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			addDescription();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		try {
			testing.Ecosystem.TestData.AllExecutedCases.add(tr.getTestName());
			String[] paths = null;
			if(testing.Ecosystem.TestData.xlsPath.contains(","))
				paths = testing.Ecosystem.TestData.xlsPath.split(",");
			for(int j=0;j<paths.length;j++){
				if(!paths[j].equals("")){
					testing.Ecosystem.Xls_Reader xls = new testing.Ecosystem.Xls_Reader(paths[j]);
					for(int i=2;i<=xls.getRowCount("TestCaseSheet");i++){
						if(testing.Ecosystem.TestData.TestCaseId.equalsIgnoreCase(xls.getCellData("TestCaseSheet", "TestCaseID", i))){
							xls.setCellData("TestCaseSheet", "Result", i, "Skipped");
							InsertTmpData(tr.getInstanceName(),tr.getName(),"Skip",xls.getCellData("TestCaseSheet", "Browser", i));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			xUpdateTestDetails("SKIPPED");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			addDescription();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		try {
			testing.Ecosystem.TestData.AllExecutedCases.add(tr.getTestName());
			String[] paths = null;
			if(testing.Ecosystem.TestData.xlsPath.contains(","))
				paths = testing.Ecosystem.TestData.xlsPath.split(",");
			for(int j=0;j<paths.length;j++){
				if(!paths[j].equals("")){
					testing.Ecosystem.Xls_Reader xls = new testing.Ecosystem.Xls_Reader(paths[j]);
					for(int i=2;i<=xls.getRowCount("TestCaseSheet");i++){
						if(testing.Ecosystem.TestData.TestCaseId.equalsIgnoreCase(xls.getCellData("TestCaseSheet", "TestCaseID", i))){
							xls.setCellData("TestCaseSheet", "Result", i, "Pass");
							InsertTmpData(tr.getInstanceName(),tr.getName(),"Pass",xls.getCellData("TestCaseSheet", "Browser ", i));
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}

		try {
			xUpdateTestDetails("PASS");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			addDescription();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}


	public void addDescription(){
		String[] paths = null;
		if(testing.Ecosystem.TestData.xlsPath.contains(","))
			paths = testing.Ecosystem.TestData.xlsPath.split(",");

		for(int j=0;j<paths.length;j++){
			if(!paths[j].equals(""))
				addDescriptionLoop(paths[j]);
		}
	}


	private void addDescriptionLoop(String path){
		testing.Ecosystem.Xls_Reader xls = new testing.Ecosystem.Xls_Reader(path);
		for(int i=2;i<=xls.getRowCount("TestCaseSheet");i++){
			if(testing.Ecosystem.TestData.TestCaseId.equalsIgnoreCase(xls.getCellData("TestCaseSheet", "TestCaseID", i))){
				if(!xls.getCellData("TestCaseSheet", "TestCase", i).equals(""))
					Reporter.log("[BriefDesc]: "+xls.getCellData("TestCaseSheet", "TestCase", i));
				else
					Reporter.log("[BriefDesc]: Description is not provided");
				if(!xls.getCellData("TestCaseSheet", "TestSteps", i).equals(""))
					Reporter.log("[Steps]: "+xls.getCellData("TestCaseSheet", "TestSteps", i));
				else
					Reporter.log("[Steps]: Test Steps are not provided");
				if(!xls.getCellData("TestCaseSheet", "ExpectedResults", i).equals("")){
					Reporter.log("[Expected]: "+xls.getCellData("TestCaseSheet", "ExpectedResults", i));
				}
				else
					Reporter.log("[Expected]: Expected Result is not provided");
				break;
			}
		}
	}

	
	public void InsertTmpData(String classname, String testcase, String status, String browser){
		xls = new testing.Ecosystem.Xls_Reader(testing.Ecosystem.TestData.tmpFolder+"/"+classname+".xls");
		int rowcount = xls.getRowCount("Sheet1");
		xls.setCellData("Sheet1", "testcase", rowcount+1, testcase);
		xls.setCellData("Sheet1", "status", rowcount+1, status);
		xls.setCellData("Sheet1", "browser", rowcount+1, browser);
	}


	public void xScreenShot() throws InterruptedException {
		try {

			String NewFileNamePath;
			String NewFilePathName;

			java.awt.Dimension resolution = Toolkit.getDefaultToolkit()
					.getScreenSize();
			Rectangle rectangle = new Rectangle(resolution);

			// Get the dir path
			File directory = new File(".");

			DateFormat dateFormat = new SimpleDateFormat(
					"MMM_dd_yyyy_hh_mm_ssaa");
			Date date = new Date();

			// Check for the IP Address of the system, to check from which system the scripts are been executed...
			InetAddress ownIP = InetAddress.getLocalHost();
			String dateName = dateFormat.format(date);
			NewFileNamePath = directory.getCanonicalPath() + "\\resources\\screenshots\\"+ testing.Ecosystem.TestData.TestCaseId + "___" + dateName + "_"+ ".png";
			NewFilePathName = "\\resources\\screenshots\\"+ testing.Ecosystem.TestData.TestCaseId + "___" + dateName + "_"+ ".png";
			// Capture the screen shot of the area of the screen defined by the rectangle

			Robot robot = new Robot();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(rectangle));
			ImageIO.write(bi, "png", new File(NewFileNamePath));

			NewFileNamePath = "<a href=.." + NewFilePathName + " target='_blank'>Click Here</a>";
			Print("[Snapshot]:"+NewFileNamePath);				
			//NewFileNamePath = directory.getCanonicalPath() + "\\resources\\screenshots\\"+ xslt.TestData.TestCaseId + "___" + dateFormat.format(date) + "_"+ ".png";
			//Print(NewFileNamePath);


		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Print(String Text) {
		System.out.println(Text);
		Reporter.log(Text);
		String Temp = Text;
		testing.Ecosystem.TestData.sMessages = testing.Ecosystem.TestData.sMessages + Temp.replaceAll(" ", "_") + "#";
	}


	public void xUpdateTestDetails(String Status) throws Exception   {
		File directory = new File(".");
		if (testing.Ecosystem.TestData.TestCaseId == ""){
			testing.Ecosystem.TestData.TestCaseId = "IdNotDefined";
		}
		String Temp = "[Status]:"+testing.Ecosystem.TestData.TestCaseId + "_" + Status;
		Temp = Temp.replaceAll(" ", "_");
		Print (Temp);
		Print("[End]:" + xGetDateTimeIP());
		//Print (xslt.TestData.sMessages);

	}	

	public java.lang.String xGetDateTimeIP() throws Exception {
		// get current date time with Date() to create unique file name
		DateFormat dateFormat = new SimpleDateFormat("hh_mm_ssaa_dd_MMM_yyyy");
		// get current date time with Date()
		Date date = new Date();
		// To identify the system
		InetAddress ownIP = InetAddress.getLocalHost();
		return (dateFormat.format(date) + "_IP" + ownIP.getHostAddress());
	}


}
