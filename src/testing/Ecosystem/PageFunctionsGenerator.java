package testing.Ecosystem;
//change package name here.
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import org.openqa.selenium.WebDriver;


public class PageFunctionsGenerator {
	WebDriver driver;
	static String TextFilePath="C:\\code_generated.txt";


	public static void main(String[] args) throws Exception{
		Object classname = Class.forName(args[0]).newInstance();
		String destination = TextFilePath;
		if(args.length>1){
			if(args[1]!="")
				destination = args[1];
		}
		gen(classname, destination);
	}

	public static void gen(Object classname, String destination) throws Exception{
		for (Field field : classname.getClass().getDeclaredFields()){
			System.out.println(field.getName());
			String xpathdet = field.getName();
			int len= xpathdet.split("_").length;
			System.out.println(xpathdet.split("_")[len-1]);

			String label_name;
			//System.out.println(label_name);
			BufferedWriter out;

			out= null;
			try {
				if(xpathdet.endsWith("_Txt")){
					label_name = xpathdet.substring(0, xpathdet.length()-4);
					out = new BufferedWriter(new FileWriter(destination, true));

					//********Fill**************************
					out.newLine();
					out.write("public  void commonFill_"+label_name+"_Txt(String inputdata){");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).clear();");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).sendKeys(inputdata);");
					out.newLine();
					out.write("}");
					out.newLine();

					//********Clear**************************
					out.newLine();
					out.write("public  void commonClear_"+label_name+"_Txt(){");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).clear();");
					out.newLine();						
					out.write("}");
					out.newLine();

					//********Click**************************
					out.newLine();
					out.write("public  void commonClick_"+label_name+"_Txt(){");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).click();");
					out.newLine();						
					out.write("}");
					out.newLine();

					//********GetText**************************
					out.newLine();
					out.write("public String commonGetText_"+label_name+"_Txt(){");
					out.newLine();
					out.write("return driver.findElement(By.xpath("+xpathdet);
					out.write(")).getAttribute("+"\"value\""+");");
					out.newLine();						
					out.write("}");
					out.newLine();
					out.close();
				}

				if(xpathdet.endsWith("_DD")){
					//****************************Select From DropDown****************************
					label_name = xpathdet.substring(0, xpathdet.length()-3);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out.newLine();
					out.write("public void commonSelect_"+label_name+"_DD(String inputdata){");
					out.newLine();						
					out.write("new Select (driver.findElement(By.xpath("+xpathdet+"))).selectByVisibleText(inputdata);");						
					out.newLine();						
					out.write("}");
					out.newLine();

					//*******************************UnSelect DropDown*********************
					out.newLine();
					out.write("public void commonDeSelect_"+label_name+"_DD(String inputdata){");
					out.newLine();						
					out.write("new Select (driver.findElement(By.xpath("+xpathdet+"))).deselectByVisibleText(inputdata);");						
					out.newLine();						
					out.write("}");
					out.newLine();

					//*******************************GetSelected DropDown*********************
					out.newLine();
					out.write("public String commonGetSelectedText_"+label_name+"_DD(){");
					out.newLine();						
					out.write("return new Select (driver.findElement(By.xpath("+xpathdet+"))).getFirstSelectedOption().getText();");						
					out.newLine();						
					out.write("}");
					out.newLine();

					//*******************************GetSelected ID DropDown*********************
					out.newLine();
					out.write("public String commonGetSelectedID_"+label_name+"_DD(){");
					out.newLine();						
					out.write("return new Select (driver.findElement(By.xpath("+xpathdet+"))).getFirstSelectedOption().getAttribute(\"value\");");						
					out.newLine();						
					out.write("}");
					out.newLine();

					out.close();
				}

				//******************Select Checkbox
				if(xpathdet.endsWith("_Chk")){
					label_name = xpathdet.substring(0, xpathdet.length()-4);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out.newLine();
					out.write("public void commonSelect_"+label_name+"_Chk(){");
					out.newLine();	
					out.write("if(driver.findElement(By.xpath("+xpathdet+")).isSelected()){}else{");
					out.write("driver.findElement(By.xpath("+xpathdet+")).click();");						
					out.newLine();						
					out.write("}");							
					out.newLine();
					out.write("}");
					out.newLine();

					//****************************DeSelect Checkbox
					out.newLine();
					out.write("public void commonDeSelect_"+label_name+"_Chk(){");
					out.newLine();	
					out.write("if(driver.findElement(By.xpath("+xpathdet+")).isSelected()){");
					out.write("driver.findElement(By.xpath("+xpathdet+")).click();");						
					out.newLine();						
					out.write("}");							
					out.newLine();
					out.write("}");
					out.newLine();

					//************IsSelected Checkbox
					out.newLine();
					out.write("public boolean commonIsChecked_"+label_name+"_Chk(){");
					out.newLine();
					out.write("return driver.findElement(By.xpath("+xpathdet);
					out.write(")).isSelected();");
					out.newLine();						
					out.write("}");
					out.newLine();
					out.close();
				}


				//********************************Radio button
				//******************Select 
				if(xpathdet.endsWith("_Rd")){
					label_name = xpathdet.substring(0, xpathdet.length()-3);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out.newLine();
					out.write("public void commonSelect_"+label_name+"_Rd(){");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).click();");						
					out.newLine();	
					out.write("}");
					out.newLine();


					//************IsSelected 
					out.newLine();
					out.write("public boolean commonIsChecked_"+label_name+"_Rd(){");
					out.newLine();
					out.write("return driver.findElement(By.xpath("+xpathdet);
					out.write(")).isSelected();");
					out.newLine();						
					out.write("}");
					out.newLine();
					out.close();
				}


				//********************************Link
				//******************Click 
				if(xpathdet.endsWith("_Lnk")){
					label_name = xpathdet.substring(0, xpathdet.length()-4);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out.newLine();
					out.write("public void commonClick_"+label_name+"_Lnk(){");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).click();");						
					out.newLine();	
					out.write("}");
					out.newLine();


					//************GetText 
					out.newLine();
					out.write("public String commonGetLinkText_"+label_name+"_Lnk(){");
					out.newLine();
					out.write("return driver.findElement(By.xpath("+xpathdet);
					out.write(")).getText();");
					out.newLine();						
					out.write("}");
					out.newLine();
					out.close();
				}


				//********************************Link
				//******************Click 
				if(xpathdet.endsWith("_Btn")){
					label_name = xpathdet.substring(0, xpathdet.length()-4);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out.newLine();
					out.write("public void commonClick_"+label_name+"_Btn(){");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).click();");						
					out.newLine();	
					out.write("}");
					out.newLine();
					out.close();
				}


				//********************************Label
				//******************Click 
				if(xpathdet.endsWith("_Lbl")){

					label_name = xpathdet.substring(0, xpathdet.length()-4);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));
					out.newLine();
					out.write("public void commonClick_"+label_name+"_Lbl(){");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).click();");						
					out.newLine();	
					out.write("}");
					out.newLine();


					//************GetText 
					out.newLine();
					out.write("public String commonGetLabelText_"+label_name+"_Lbl(){");
					out.newLine();
					out.write("return driver.findElement(By.xpath("+xpathdet);
					out.write(")).getText();");
					out.newLine();						
					out.write("}");
					out.newLine();
					out.close();
				}


				//********************************Label
				//******************Click 
				if(xpathdet.endsWith("_WE")){

					label_name = xpathdet.substring(0, xpathdet.length()-3);
					out = new BufferedWriter(new FileWriter(TextFilePath, true));

					out.newLine();
					out.write("public void commonClick_"+label_name+"_WE(){");
					out.newLine();
					out.write("driver.findElement(By.xpath("+xpathdet+")).click();");						
					out.newLine();	
					out.write("}");
					out.newLine();


					//************GetText 
					out.newLine();
					out.write("public String commonGetElementText_"+label_name+"_WE(){");
					out.newLine();
					out.write("return driver.findElement(By.xpath("+xpathdet);
					out.write(")).getText();");
					out.newLine();						
					out.write("}");
					out.newLine();
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
