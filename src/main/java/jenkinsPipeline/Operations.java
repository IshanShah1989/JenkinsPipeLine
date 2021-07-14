package jenkinsPipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jenkinsPipeline.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Operations extends BaseClass
{

	ArrayList<Integer> al= new ArrayList<Integer>();
	
	public String getTemp()
	{
		String temp = driver.findElement(By.xpath("//span[@id='temperature']")).getText();
		System.out.println(temp);
//		temp = temp.substring(0,2);
		temp=temp.replaceAll("\\W","");
		temp=temp.replaceAll("[a-zA-Z]","");
//		System.out.println(temp.length());
		temp.trim();
		temp=temp.replaceAll("\\s", "");
		//System.out.println(temp.substring(0, temp.length() - 2));
		return temp;
	}
	
	public void getPrice(String element)
	{
		al.clear();
		//System.out.println(driver.findElement(By.xpath("//p[contains(.,'Aloe')]")).getAttribute("price"));
		List<WebElement> el = driver.findElements(By.xpath("//p[contains(.,'"+element+"')]/following-sibling::p"));
		for(int i=0;i<el.size();i++)
		{
			String str1 = el.get(i).getText();
			str1=str1.replaceAll("\\W","");
			str1=str1.replaceAll("[a-zA-Z]","");
			al.add(Integer.parseInt(str1));			
		}
		Collections.sort(al);
		System.out.println("//p[contains(.,'"+al.get(0)+"')]");
		driver.findElement(By.xpath("//p[contains(.,'"+al.get(0)+"')]//following-sibling::button")).click();
	}

	public void checkSum()
	{
		String p1 = driver.findElement(By.xpath("//tr[1]/td[2]")).getText();
		String p2 = driver.findElement(By.xpath("//tr[2]/td[2]")).getText();
		int sum = Integer.parseInt(p1)+Integer.parseInt(p2);
		String actualSum = driver.findElement(By.xpath("//p[@id='total']")).getText();
		actualSum=actualSum.replaceAll("\\W","");
		actualSum=actualSum.replaceAll("[a-zA-Z]","");
		Assert.assertEquals(actualSum, String.valueOf(sum));
		
	}
	
}
