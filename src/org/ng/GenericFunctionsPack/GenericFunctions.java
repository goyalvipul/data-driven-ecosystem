package org.ng.GenericFunctionsPack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import testing.Ecosystem.Xls_Reader;


public class GenericFunctions {
	public WebDriver driver;
	public String browserType="";
	public String winHandle;

	public String openWebURL = "http://testsrv1.infoedge.com";

	protected static ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	public static WebDriver getDriver(){
		return webDriver.get();
	}
	public static void setDriver(WebDriver driver){
		webDriver.set(driver);
	}
	
	
	/***********************************************************************************************
	 * Function Description : Sets implicit Wait by accepting timeout in seconds
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public GenericFunctions(){

	}

	public GenericFunctions(WebDriver driver){
		this.driver = driver;
	}

	public String SetImplicitWaitInSeconds(int timeOut){
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		return "Timeout set to "+timeOut+" seconds.";
	}


	/***********************************************************************************************
	 * Function Description : Sets implicit Wait by accepting timeout in milliseconds
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public String SetImplicitWaitInMilliSeconds(int timeOut){
		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.MILLISECONDS);
		return "Timeout set to "+timeOut+" milli seconds.";
	}

	/***********************************************************************************************
	 * Function Description : Initiates the driver. Set the browserType variable before calling this function 
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/

	public WebDriver StartDriver(String browserType){


		if(browserType.trim().equalsIgnoreCase("")){
			System.out.println("Kindly set the 'browserType' variable before calling this function");
			return driver;
		}

		if(browserType.equalsIgnoreCase("FF")){
			driver = new FirefoxDriver();
		}

		else if(browserType.startsWith("FF")){
			String BrowserVersion =browserType.split("FF")[1].trim();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			
			profile.setPreference("browser.download.dir", "C:\\workspace\\NaukriGulf\\temp"); 
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/vnd.ms-excel,text/csv,application/ms-excel,image/jpeg,application/zip,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/pdf,application/msword,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,image/png,application/vnd.ms-powerpoint");
            profile.setPreference("browser.download.manager.showWhenStarting", false);
 
            /*File file = new File("C:\\workspace\\MozillaVersions\\Mozilla Firefox17\\extensions\\firebug-1.10.0.xpi");
            try {
                profile.addExtension(file);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
			driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\workspace\\MozillaVersions\\Mozilla Firefox"+BrowserVersion+"\\firefox.exe")), profile);
		}


		if(browserType.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:/workspace/jars/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browserType.startsWith("Chrome")){
			String BrowserVersion =browserType.split("Chrome")[1].trim();
			System.setProperty("webdriver.chrome.driver", "C:/workspace/jars/chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.binary", "C:/workspace/ChromeVersions/Chrome"+BrowserVersion+"/chrome.exe");
			driver = new ChromeDriver(capabilities);
		}

		if(browserType.equalsIgnoreCase("IE32")){
			System.setProperty("webdriver.ie.driver", "C:/workspace/jars/IEDriverServer32.exe");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.out.println(capabilities);
			driver = new InternetExplorerDriver(capabilities);
		}

		if(browserType.equalsIgnoreCase("IE64")){
			System.setProperty("webdriver.ie.driver", "C:/workspace/jars/IEDriverServer64.exe");
			driver = new InternetExplorerDriver();
		}

		if(browserType.equalsIgnoreCase("MOBILEFF")){
			FirefoxProfile profile = new FirefoxProfile(); 
			try {
				profile.addExtension(new File("c:\\workspace\\user_agent_switcher-0.7.3-fx+sm.xpi"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			// profile.setPreference("general.useragent.override", "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16"); 
			profile.setPreference("general.useragent.override", "Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"); 
			driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\workspace\\MozillaVersions\\Mozilla Firefox18\\firefox.exe")), profile);
		}

		SetImplicitWaitInSeconds(15);

		return driver;

	}

	/***********************************************************************************************
	 * Function Description : Stops the driver
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public String StopDriver(){
		driver.quit();
		return("browser closed");
	}

	public WebDriver StartDriverInstance(String browserType){
		if(browserType.trim().equalsIgnoreCase("")){
				System.out.println("Kindly set the 'browserType' variable before calling this function");
				return driver;
		}
		if(browserType.equalsIgnoreCase("FF")){
			driver = new FirefoxDriver();
			GenericFunctions.setDriver(driver);
            driver = GenericFunctions.getDriver();
		}
		else if(browserType.startsWith("FF")){
			String BrowserVersion =browserType.split("FF")[1].trim();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setAcceptUntrustedCertificates(true);
			profile.setAssumeUntrustedCertificateIssuer(true);
			
			profile.setPreference("browser.download.dir", "C:\\workspace\\NaukriGulf\\temp"); 
            profile.setPreference("browser.download.folderList", 2);
            profile.setPreference("browser.helperApps.neverAsk.saveToDisk","application/vnd.ms-excel,text/csv,application/ms-excel,image/jpeg,application/zip,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/pdf,application/msword,application/vnd.ms-powerpoint,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,image/png,application/vnd.ms-powerpoint");
            profile.setPreference("browser.download.manager.showWhenStarting", false);
 
            /*File file = new File("C:\\workspace\\MozillaVersions\\Mozilla Firefox17\\extensions\\firebug-1.10.0.xpi");
            try {
                profile.addExtension(file);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
			
            driver = new FirefoxDriver(new FirefoxBinary(new File("C:\\workspace\\MozillaVersions\\Mozilla Firefox"+BrowserVersion+"\\firefox.exe")), profile);
            GenericFunctions.setDriver(driver);
            driver = GenericFunctions.getDriver();
		}
		if(browserType.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:/workspace/jars/chromedriver.exe");
			driver = new ChromeDriver();
			GenericFunctions.setDriver(driver);
            driver = GenericFunctions.getDriver();
		}
		else if(browserType.startsWith("Chrome")){
			   String BrowserVersion =browserType.split("Chrome")[1].trim();
			   System.setProperty("webdriver.chrome.driver", "C:/workspace/jars/chromedriver.exe");
			   DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			   capabilities.setCapability("chrome.binary", "C:/workspace/ChromeVersions/Chrome"+BrowserVersion+"/chrome.exe");
			   driver = new ChromeDriver(capabilities);
			   GenericFunctions.setDriver(driver);
			   driver = GenericFunctions.getDriver();
			  }
			
		if(browserType.equalsIgnoreCase("IE32")){
			System.setProperty("webdriver.ie.driver", "C:/workspace/jars/IEDriverServer32.exe");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			System.out.println(capabilities);
			driver = new InternetExplorerDriver(capabilities);
			GenericFunctions.setDriver(driver);
            driver = GenericFunctions.getDriver();
		}
		
		if(browserType.equalsIgnoreCase("IE64")){
			System.setProperty("webdriver.ie.driver", "C:/workspace/jars/IEDriverServer64.exe");
			driver = new InternetExplorerDriver();
			GenericFunctions.setDriver(driver);
            driver = GenericFunctions.getDriver();
		}
		SetImplicitWaitInSeconds(15);
		return driver;
	}
	
	public String StopDriverInstance(){
		driver = GenericFunctions.getDriver();
		System.out.println("THREADDDDDDD CLOSED:"+Thread.currentThread().getId());
		if (driver != null) {
            driver.quit();
        }
		return "closed";
	}
	

	/***********************************************************************************************
	 * Function Description : Takes ScreenShot and returns the screenshot name
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public String TakeScreenshot(){
		String directory = System.getProperty("user.dir");
		directory = directory.replace("\\", "\\\\");

		String SaveName = Calendar.getInstance().getTime().toString().replace(":", "").replace(" ", "").trim();
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {

			FileUtils.copyFile(scrFile, new File(directory+"\\screenshots\\"+SaveName+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return SaveName+".png";
	}


	/***********************************************************************************************
	 * Function Description : Accepts the alert box message
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public String AlertBox_Accept(){
		try{
		// Get a handle to the open alert, prompt or confirmation
		Alert alert = driver.switchTo().alert();
		// And acknowledge the alert (equivalent to clicking "OK")
		alert.accept();
		return("Alert '"+alert.getText()+"' accepted");
		}catch(Exception e){
			return "";
		}
		
	}

	/***********************************************************************************************
	 * Function Description : Dismisses the alert box message
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public String AlertBox_Dismiss(){
		// Get a handle to the open alert, prompt or confirmation
		Alert alert = driver.switchTo().alert();
		// And acknowledge the alert (equivalent to clicking "cancel")
		alert.dismiss();
		return("Alert '"+alert.getText()+"' dismissed");
	}


	/***********************************************************************************************
	 * Function Description : gets the handle for the current window
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public void GetWindowHandle(){
		winHandle = driver.getWindowHandle();
	}

	/***********************************************************************************************
	 * Function Description : Switches to the most recent window opened
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public void SwitchtoNewWindow(){
		for(String windowsHandle : driver.getWindowHandles()){
			driver.switchTo().window(windowsHandle);
		}
	}

	/***********************************************************************************************
	 * Function Description : Closes the window
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public void CloseNewWindow(){
		driver.close();
	}

	/***********************************************************************************************
	 * Function Description : Switches back to original window
	 * author: Tarun Narula, date: 25-Feb-2013
	 * *********************************************************************************************/
	public void SwitchtoOriginalWindow(){
		driver.switchTo().window(winHandle);
	}


	public String selectDropDownByValue(String ddName, String value){
		String result="";
		List<WebElement> element = driver.findElement(By.xpath("//*[@id='"+ddName+"']")).findElements(By.tagName("option"));
		for(WebElement elem:element){
			String fieldValue = elem.getAttribute("value");
			if(fieldValue.startsWith(value)){
				new Select(driver.findElement(By.xpath("//*[@id='"+ddName+"']"))).selectByValue(fieldValue);
				result="Pass";
				break;
			}
		}
		if(result.equals(""))
			result = "Fail";
		return result;
	}

	public void ClickElementInADivHavingButton(String parent_Xpath, String inputdata){

		if(inputdata.equalsIgnoreCase("Reset"))
			driver.findElement(By.xpath(parent_Xpath)).findElement(By.tagName("button")).click();

		else{
			driver.findElement(By.xpath(parent_Xpath)).findElement(By.linkText(inputdata.trim())).click();
		}


	}

	public void GoToSleep(int TimeInMillis)
	{
		try{Thread.sleep(TimeInMillis);} catch(Exception e){}
	}

	public void ClickElementInADiv(String parent_Xpath, String inputdata){
		driver.findElement(By.xpath(parent_Xpath)).findElement(By.linkText(inputdata.trim())).click();
	}


	public void ClickElementInA_MutliSelectDiv(String parent_Xpath, String inputdata){
		String[] Str=inputdata.split(";");
		for(int j=0;j<Str.length;j++){
			driver.findElement(By.xpath(parent_Xpath)).findElement(By.linkText(Str[j].trim())).click();
		}
	}



	/***********************************************************************************************
	 * Function Description : It Checks The Presence and Visibility of a element on page of given path
	 * author: Ankit Choudhary, date: 11-Apr-2013
	 * *********************************************************************************************/


	public Boolean isVisible(String elementXpath)
	{
		SetImplicitWaitInMilliSeconds(500);
		if(driver.findElements(By.xpath(elementXpath)).size()!=0 && driver.findElement(By.xpath(elementXpath)).isDisplayed())
		{SetImplicitWaitInMilliSeconds(10000);
		return true;
		}
		else
		{SetImplicitWaitInMilliSeconds(10000);
		return false;
		}


	}


	/***********************************************************************************************
	 * Function Description : Compares two provided xl columns and set the status
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/
	public void CompareTwoXLColumns(Xls_Reader datatable, String sheetname, String colname1, String colname2, String statuscol){
		for(int i=2; i<= datatable.getRowCount(sheetname);i++)
		{
			if(!datatable.getCellData(sheetname, colname1, i).equals(""))
			{
				if(datatable.getCellData(sheetname, colname1, i).equals(datatable.getCellData(sheetname, colname2, i))){

					datatable.setCellData(sheetname, statuscol, i, "pass");
				}else{
					datatable.setCellData(sheetname, statuscol, i, "fail");
				}
			}	
		}
	}
	
	/***********************************************************************************************
	 * Function Description : Function to compare two comma separated strings
	 * author: Aarti, date: 31-Dec-2013
	 * *********************************************************************************************/
	public String compareStrings (String a, String b) {
		String results = "";
		String resultset = "";
	    String arr1[] = a.split(",");
	    String arr2[] = b.split(",");
	    for (int i = 0; i < arr1.length; i++) {
	        for (int j = 0; j < arr2.length; j++) {
	            if (arr1[i].trim().equals(arr2[j].trim())) 
	            {
	            	results = "Pass";
	               break;
	            }
	            else
	            {
	            	results="Fail";
	            }
	        }
	        
	        resultset = resultset + ","+ results;
	    }
	    System.out.println(resultset);
	    return resultset;
	   
	}
	
	
	/***********************************************************************************************
	 * Function Description : Set/Write  String List at given excel path sheetname and column.
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/
	public void SetStringListInXLColumn(Xls_Reader datatable, String sheetname, String colname, int j, List<String> list){

		for(int i=0; i<list.size(); i++){
			datatable.setCellData(sheetname, colname, j, list.get(i));
			j=j+1;
		}

	}

	/***********************************************************************************************
	 * Function Description : Will go to provided div/span/xpath and fetch the attribute of provided tagname
	 * Example: Fetching links text from a provided div: input required div xpath, tagname as "a" and attribute as"text"
	 * Example: Fetching links url from a provided div: input required div xpath, tagname as "a" and attribute as"href"
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/
	public List<String> FetchFromParent(String parent_Xpath, String tagname, String attribute){

		ArrayList<String> list = new ArrayList<String>();
		List<WebElement> elements =driver.findElement(By.xpath(parent_Xpath)).findElements(By.tagName(tagname));
		for(int i=0; i< elements.size(); i++){
			if(attribute.equalsIgnoreCase("text"))
				list.add(elements.get(i).getText());
			else
				list.add(elements.get(i).getAttribute(attribute));	
		}
		return list;
	}



}





