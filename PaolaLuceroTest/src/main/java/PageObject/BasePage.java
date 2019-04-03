package PageObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BasePage {
	
	protected static WebDriver driver=null;
	
	public void setup() throws IOException{
		
		Properties p= new Properties();
		FileInputStream fi = new FileInputStream("global.properties");
        p.load(fi);
        
        if (p.getProperty("browser").contains("chrome"))
        {
        	System.setProperty("webdriver.chrome.driver",p.getProperty("pathChrome"));
        	driver= new ChromeDriver();
        }
        else if  (p.getProperty("browser").contains("firefox"))
        {
        	System.setProperty("webdriver.gecko.driver", p.getProperty("pathFirefox"));
        	driver= new FirefoxDriver();
        }
        
        driver.get(p.getProperty("url"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
	}
	
	public void finish() {
		
		driver.close();
		driver.quit();
	}

}
