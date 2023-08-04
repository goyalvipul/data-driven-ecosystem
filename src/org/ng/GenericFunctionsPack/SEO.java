package org.ng.GenericFunctionsPack;

import org.openqa.selenium.By;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class SEO {
	public WebDriver driver;

	public SEO(WebDriver driver){
		this.driver=driver;
	}



	/***********************************************************************************************
	 * Function Description : Fetches Page title
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/


	public String GetPageTitle(){
		return driver.getTitle();
	}

	/***********************************************************************************************
	 * Function Description : Fetches Page Description[SEO]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/

	public String GetPageDescription(){
		if(isPresent("//meta[@name='Description']")){
			return driver.findElement(By.xpath("//meta[@name='Description']")).getAttribute("content");
		}
		if(isPresent("//meta[@name='description']")){
			return driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
		}
		if(isPresent("//META[@name='Description']")){
			return driver.findElement(By.xpath("//meta[@name='Description']")).getAttribute("content");
		}
		if(isPresent("//META[@name='description']")){
			return driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
		}else{return "No Description";}			

	}



	/***********************************************************************************************
	 * Function Description : Fetches Page Keywords[SEO]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/
	public String GetPageKeywords(){
		if(isPresent("//meta[@name='Keywords']")){
			return driver.findElement(By.xpath("//meta[@name='Keywords']")).getAttribute("content");
		}

		if(isPresent("//META[@name='Keywords']")){
			return driver.findElement(By.xpath("//META[@name='Keywords']")).getAttribute("content");
		}


		if(isPresent("//meta[@name='keywords']")){
			return driver.findElement(By.xpath("//meta[@name='keywords']")).getAttribute("content");
		}


		if(isPresent("//META[@name='keywords']")){
			return driver.findElement(By.xpath("//META[@name='keywords']")).getAttribute("content");
		}else{return "No Keywords";}

	}

	/***********************************************************************************************
	 * Function Description : Fetches Current Page URL]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/

	public String GetPageURL(){
		return driver.getCurrentUrl();
	}

	/***********************************************************************************************
	 * Function Description : Fetches Canonical tag of the page[SEO]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/
	public String GetCanonicalTag(){


		if(isPresent("//link[@rel='canonical']")){
			return driver.findElement(By.xpath("//link[@rel='canonical']")).getAttribute("href");
		}

		if(isPresent("//link[@rel='Canonical']")){
			return driver.findElement(By.xpath("//link[@rel='Canonical']")).getAttribute("href");
		}else{return "No CanonicalTag";}
	}

	/***********************************************************************************************
	 * Function Description : Fetches Classification tag of the page[SEO]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/
	public String GetClassificationTag(){


		if(isPresent("//link[@rel='classification']")){
			return driver.findElement(By.xpath("//link[@rel='classification']")).getAttribute("href");
		}

		if(isPresent("//link[@rel='Classification']")){
			return driver.findElement(By.xpath("//link[@rel='Classification']")).getAttribute("href");
		}else{return "No ClassificationTag";}
	}

	/***********************************************************************************************
	 * Function Description : Fetches H1 tag of the page[SEO]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/
	public String GetH1Tag(){
		if(driver.findElements(By.tagName("h1")).size()==1){
			return driver.findElement(By.tagName("h1")).getText();
		}

		if(driver.findElements(By.tagName("H1")).size()==1){
			return driver.findElement(By.tagName("H1")).getText();
		}else{return "No H1tag found";}
	}

	/***********************************************************************************************
	 * Function Description : Fetches H2 tag of the page[SEO]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/

	public String GetH2Tag(){
		if(driver.findElements(By.tagName("h2")).size()==1){
			return driver.findElement(By.tagName("h2")).getText();
		}


		if(driver.findElements(By.tagName("H2")).size()==1){
			return driver.findElement(By.tagName("H2")).getText();
		}else{return "No H2tag found";}
	}

	/***********************************************************************************************
	 * Function Description : Fetches H3 tag of the page[SEO]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/

	public String GetH3Tag(){
		if(driver.findElements(By.tagName("h3")).size()==1){
			return driver.findElement(By.tagName("h3")).getText();
		}

		if(driver.findElements(By.tagName("H3")).size()==1){
			return driver.findElement(By.tagName("H3")).getText();
		}else{return "No H3tag found";}
	}

	/***********************************************************************************************
	 * Function Description : Fetches H4 tag of the page[SEO]
	 * author: Omkar, date: 13-Jun-2013
	 * *********************************************************************************************/

	public String GetH4Tag(){
		if(driver.findElements(By.tagName("h4")).size()==1){
			return driver.findElement(By.tagName("h4")).getText();
		}

		if(driver.findElements(By.tagName("H4")).size()==1){
			return driver.findElement(By.tagName("H4")).getText();
		}else{return "No H4tag found";}
	}

	/***********************************************************************************************
	 * Function Description : It Checks The Presence of a element on page of given path
	 * author: Ankit Choudhary, date: 11-Apr-2013
	 * *********************************************************************************************/

	public Boolean isPresent(String elementXpath)
	{


		// SetImplicitWaitInMilliSeconds(500);
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		if(driver.findElements(By.xpath(elementXpath)).size()!=0)
		{
			return true;
		}
		else
		{
			return false;
		}


	}
}
