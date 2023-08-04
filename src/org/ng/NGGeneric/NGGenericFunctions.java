package org.ng.NGGeneric;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


public class NGGenericFunctions {
	WebDriver driver;
	testing.Ecosystem.Xls_Reader xls;
	org.ng.GenericFunctionsPack.GenericFunctions generic;
	org.ng.GenericFunctionsPack.DBFunctions db;

	public NGGenericFunctions(WebDriver driver){
		this.driver = driver;
		generic = new org.ng.GenericFunctionsPack.GenericFunctions(driver);
		db = new org.ng.GenericFunctionsPack.DBFunctions();
	}


	/**********************************************************************************
	 * @Function: This function returns the date in the format specified.
	 * @Parameters: This function takes, Date Format as an argument in which it return the DATE.
	 * @ExampleArguments: "yyMMddhhmmss"
	 **************************************************************************************/
	public String getCurrentDate(String dateFormat){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		return formater.format(cal.getTime());
	}

	public String getFormatedDay(String DATE){
		int date = Integer.parseInt(DATE);
		//Calendar cal = Calendar.getInstance();
		String[] suffixes =
				  //    0     1     2     3     4     5     6     7     8     9
				     { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
				  //    10    11    12    13    14    15    16    17    18    19
				       "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
				  //    20    21    22    23    24    25    26    27    28    29
				       "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
				  //    30    31
				       "th", "st" };
				
		return DATE + suffixes[date];
	}
	
	
	@SuppressWarnings("static-access")
	public String getOldDate(String dateFormat, int NumberofDaysOldDateNeeded){
		int amount = NumberofDaysOldDateNeeded;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		cal.setTime(cal.getTime());
		cal.add(cal.DAY_OF_YEAR, -amount);
		return formater.format(cal.getTime());
	}

	public String getDate_SundayNextWeek(String dateFormat){
		Calendar cal=Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		while (cal.get( Calendar.DAY_OF_WEEK ) != Calendar.SUNDAY)
		    cal.add(Calendar.DAY_OF_WEEK, 1 );
		return formater.format(cal.getTime());
	}

	
	@SuppressWarnings("static-access")
	public String getDate_NextMonthFirstDay(String dateFormat){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1);
		cal.set(cal.DATE, 1);
		return formater.format(cal.getTime());
	}
	

	public void performMoveMouse(){
		Dimension s1= driver.manage().window().getSize();
		driver.manage().window().setSize(s1);
	}


	public boolean createFolder(String path, String folderName){
		File file = new File(path+"\\"+folderName);
		return file.mkdir();
	}

	public String getFilePresence(String path, String fileName){
		for(int i=1;i<=30;i++){
			File file = new File(path+"\\"+fileName);
			if(file.exists())
				return "Yes";
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Fileeeeeeeee:"+i);
		}
		return "No";
	}

	
	public void RenameDownloadedFile(String NewName, String DownloadPath) {
		File DownloadedFile = new File(DownloadPath);
		String a[] = DownloadedFile.list();
		for (int i = 0; i < a.length; i++) {
			File OriginalFile = new File(DownloadPath+"\\"+a[i]);
			File RenameFile = new File(DownloadPath+"\\"+NewName);
			OriginalFile.renameTo(RenameFile);
		}
	}

	public void MoveFile(String OriginalFilePath, String DestinationFolderPath) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		File OriginalFile = new File(OriginalFilePath);
		OriginalFile.renameTo(new File(DestinationFolderPath+"\\"+OriginalFile.getName()));
	}

	
	public void DeleteFiles(String path){
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		try{
			File file = new File(path);
			File[] filelist = file.listFiles();
			for(int n=0;n<filelist.length;n++){
				if (filelist[n].isFile()){
					System.gc();
					Thread.sleep(2000);
					filelist[n].delete();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/************************************************************************************
	 * Function used to access old format excel files.. 
	 *************************************************************************************/
	
	public int getRowNumber(String path,String sheetname, String rowName){
		try{
			FileInputStream fis = new FileInputStream(path);
			Workbook wk = Workbook.getWorkbook(fis);
			WorkbookSettings wbSettings = new WorkbookSettings(); 
			wbSettings.setSuppressWarnings(true);
			Sheet s = wk.getSheet(sheetname);
			fis.close();
			return s.findCell(rowName).getRow();

		}catch(IOException e){
			e.printStackTrace();
			return 0;
		} catch (BiffException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	public int getColumnNumber(String path,String sheetname, String colName){
		try{
			FileInputStream fis = new FileInputStream(path);
			Workbook wk = Workbook.getWorkbook(fis);
			WorkbookSettings wbSettings = new WorkbookSettings(); 
			wbSettings.setSuppressWarnings(true);
			Sheet s = wk.getSheet(sheetname);
			fis.close();
			return s.findCell(colName).getColumn();

		}catch(IOException e){
			e.printStackTrace();
			return 0;
		} catch (BiffException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	public int getRowCount(String path,String sheetname){
		try{
			FileInputStream fis = new FileInputStream(path);
			Workbook wk = Workbook.getWorkbook(fis);
			WorkbookSettings wbSettings = new WorkbookSettings(); 
			wbSettings.setSuppressWarnings(true);
			Sheet s = wk.getSheet(sheetname);
			fis.close();
			return s.getRows();

		}catch(IOException e){
			e.printStackTrace();
			return 0;
		} catch (BiffException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	
	public String getCellData_hrefLinks(String path,String sheetname, int rowNum, String colName){
		try{
			FileInputStream fis = new FileInputStream(path);
			Workbook wk = Workbook.getWorkbook(fis);
			WorkbookSettings wbSettings = new WorkbookSettings(); 
			wbSettings.setSuppressWarnings(true);
			Sheet s = wk.getSheet(sheetname);
			fis.close();
			int colNumber = s.findCell(colName).getColumn();
			return s.getCell(colNumber,rowNum).getContents();

		}catch(IOException e){
			e.printStackTrace();
			return "Empty";
		} catch (BiffException e) {
			e.printStackTrace();
			return "Empty";
		}
	}
	
	
	public String getCellData(String path,String sheetname, int rowNum, String colName){
		try{
			FileInputStream fis = new FileInputStream(path);
			Workbook wk = Workbook.getWorkbook(fis);
			WorkbookSettings wbSettings = new WorkbookSettings(); 
			wbSettings.setSuppressWarnings(true);
			Sheet s = wk.getSheet(sheetname);
			fis.close();
			int colNumber = s.findCell(colName).getColumn();
			return String.valueOf(s.getCell(colNumber,rowNum).getContents());

		}catch(IOException e){
			e.printStackTrace();
			return "Empty";
		} catch (BiffException e) {
			e.printStackTrace();
			return "Empty";
		}
	}
	
	
	
	public String getCellData(String path,String sheetname, int rowNum, int colNumber){
		try{
			FileInputStream fis = new FileInputStream(path);
			Workbook wk = Workbook.getWorkbook(fis);
			WorkbookSettings wbSettings = new WorkbookSettings(); 
			wbSettings.setSuppressWarnings(true);
			Sheet s = wk.getSheet(sheetname);
			fis.close();
			return s.getCell(colNumber,rowNum).getContents();

		}catch(IOException e){
			e.printStackTrace();
			return "Empty";
		} catch (BiffException e) {
			e.printStackTrace();
			return "Empty";
		}
	}
	
	
	/****************************************************************************************************/
	
	
	public void waitForJSLoad(){
		WebDriverWait wait= new WebDriverWait(driver, 30);
        final JavascriptExecutor executor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver driver){
                return (Boolean)executor.executeScript("return Ajax.activeRequestCount");
            }
        };
        System.out.println(pageLoadCondition);
        wait.until(pageLoadCondition);
    }
	
	public void waitForAjax(long timeoutInSeconds) {
		 System.out.println("Checking active ajax calls by calling jquery.active");
		 try {
		  if (driver instanceof JavascriptExecutor) {
		   JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		 
		   for (int i = 0; i < timeoutInSeconds; i++) {
		    Object numberOfAjaxConnections = jsDriver
		      .executeScript("return jQuery.active");
		    // return should be a number
		    if (numberOfAjaxConnections instanceof Long) {
		     Long n = (Long) numberOfAjaxConnections;
		     System.out.println("Number of active jquery ajax calls: " + n);
		     if (n.longValue() == 0L)
		      break;
		    }
		    Thread.sleep(1000);// 1 second sleep
		   }
		  } else {
		   System.out.println("Web driver: " + driver
		     + " cannot execute javascript");
		  }
		 } catch (Exception e) {
		  System.out.println("Failed to wait for ajax response: " + e);
		 }
		}

	
	
}
