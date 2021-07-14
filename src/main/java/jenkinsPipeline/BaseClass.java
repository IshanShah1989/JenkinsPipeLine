package jenkinsPipeline;

import java.io.FileInputStream;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass 
{
	public static WebDriver driver;
	Properties prop;
	
	public BaseClass()
	{
		if(prop==null) 
		{			
			try 
			{
				prop= new Properties();
				FileInputStream fs = new FileInputStream(System.getProperty("user.dir")+"\\project.properties");
				prop.load(fs);
			} catch (Exception e)
			{
				e.printStackTrace();
				// report
			}
		}
	}
	
	public void getDriver(String str)
	{
		if(str.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\drivers\\chrome\\chromedriver.exe");
			System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
			driver = new ChromeDriver();	
			driver.manage().window().maximize();
		}
	}
	
	public void launch(String url)
	{
		driver.navigate().to(prop.getProperty(url));
	}
	
	public void click(String objectKey) 
	{
		getObject(objectKey).click();
	}
	
	public void type(String objectKey,String data)
	{
		getObject(objectKey).sendKeys(data);
	}

	public String getTextValue(String objectKey)
	{
		String text = getObject(objectKey).getText();
		return text;
	}	
	
	public String custom_xpath(String xpath_part1, String customValue, String xpath_part2)
	{
		String xpath = xpath_part1+customValue+xpath_part2;
		return xpath;
	}
	
	public void select(String objectKey,String data)
	{
		Select s= new Select(getObject(objectKey));
		s.selectByVisibleText(data);
	}
	
	public void clear(String objectKey)
	{
		getObject(objectKey).clear();
	}
	
	// central function to extract objects
	public WebElement getObject(String objectKey)
	{
		WebElement e = null;
		WebDriverWait wait  =  new WebDriverWait(driver, 10);

		try
		{
			if(objectKey.endsWith("_xpath")) 
			{
				e = driver.findElement(By.xpath(prop.getProperty(objectKey)));// present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(prop.getProperty(objectKey))));
			}
			else if(objectKey.endsWith("_id"))
			{
					e = driver.findElement(By.id(prop.getProperty(objectKey)));// present
					wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(prop.getProperty(objectKey))));
			}
			else if(objectKey.endsWith("_name:"))
			{
				e = driver.findElement(By.name(prop.getProperty(objectKey)));// present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(prop.getProperty(objectKey))));
			}
			else if(objectKey.endsWith("_css"))
			{
				e = driver.findElement(By.cssSelector(objectKey));// present
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(prop.getProperty(objectKey))));
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return e;
	}
	
}
