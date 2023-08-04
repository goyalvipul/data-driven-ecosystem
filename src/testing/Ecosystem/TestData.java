package testing.Ecosystem;

import java.util.ArrayList;


public class TestData {
	
	// testing crendentials
	public static String TestCaseId = "";
	public static String sMessages="";
	public static ArrayList<String> AllExecutedCases = new ArrayList<String>();
	public static String tmpFolder = System.getProperty("java.io.tmpdir");
	public static testing.Ecosystem.Xls_Reader globalXLS= new testing.Ecosystem.Xls_Reader(".\\TestCaseCreation\\excelSheetRef.xls");
	public static String xlsPath= xlsPath();
	public static String linkUsed = "test";
	
	public static String baseDomain = "testng3";
	public static String baseurl = "http://"+baseDomain+".infoedge.com";
	public static String baseurl_ar = "http://arabic."+baseDomain+".infoedge.com";
	public static String baseLogin = "https://login."+baseDomain+".infoedge.com";
	public static String baseurl_mobile = "http://m."+baseDomain+".infoedge.com";
	
	
	/*public static String baseDomain = "naukrigulf";
	public static String baseurl = "http://www."+baseDomain+".com";
	public static String baseurl_ar = "http://arabic."+baseDomain+".com";
	public static String baseLogin = "https://login."+baseDomain+".com";
	public static String baseurl_mobile = "http://m."+baseDomain+".com";
	 */
	
private static String xlsPath(){
	String path="";
	for(int i=2;i<=globalXLS.getRowCount("Sheet1");i++){
		if(globalXLS.getCellData("Sheet1", "Refer_Sheet", i).equalsIgnoreCase("Y")){
			path= testing.Ecosystem.TestData.globalXLS.getCellData("Sheet1", "Reference_Sheet_Path", i)+","+path;
		}
	}
	return path;
}
}