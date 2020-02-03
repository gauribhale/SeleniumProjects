package Base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import Utilities.ExcelReader;
import Utilities.MonitoringMail;

public class BaseTest {

	/*
	 * Excel logs properties testng javamail reportng database webdriver explicit
	 * and implicit keywords screenshots maven jenkins
	 */

	public static WebDriver driver;
	public static Properties OR = new Properties();
	public static Properties Config = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger(BaseTest.class);
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\AddCustomerData.xlsx");
	public static MonitoringMail mail = new MonitoringMail();
	public static WebDriverWait wait;
	public static WebElement dropdown;

	@BeforeSuite // launching browsers and OR and Config files
	public void setUp() throws IOException {

		if (driver == null) {

			PropertyConfigurator
					.configure(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\log4j.properties");
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();

			}
			try {
				Config.load(fis);
				log.info("config file loaded!");
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				e.printStackTrace();

			}
			try {
				OR.load(fis);
				log.info("OR file loaded!");
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (Config.getProperty("browser").equalsIgnoreCase("firefox")) {
				System.setProperty(Config.getProperty("geckodriverkey"),
						System.getProperty("user.dir") + Config.getProperty("geckodriverpath"));
				driver = new FirefoxDriver();
				log.info("firefox launched");
			} else if (Config.getProperty("browser").equalsIgnoreCase("chrome")) {
				System.setProperty(Config.getProperty("chromedriverkey"),
						System.getProperty("user.dir") + Config.getProperty("chromedriverpath"));
				driver = new ChromeDriver();
				log.info("Chrome launched");
			} else if (Config.getProperty("browser").equalsIgnoreCase("ie")) {
				System.setProperty(Config.getProperty("iedriverkey"),
						System.getProperty("user.dir") + Config.getProperty("iedriverpath"));
				driver = new InternetExplorerDriver();
				log.info("ie launched");
			}
			driver.get(Config.getProperty("testsiteurl"));
			log.info("navigated to site: " + Config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);

			// DB CONNECTION

			wait = new WebDriverWait(driver, Integer.parseInt(Config.getProperty("explicit.wait")));

		}
	}

	public static boolean isElementPresent(String key) {
		try {
			if (key.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(key)));
			} else if (key.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(key)));
			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(key)));
			}
			log.info("element found: " + key);
			return true;
		} catch (Throwable t) {
			log.error("error in finding element: " + key);
			return false;
		
		}
	}

	public static void click(String key) {

		try {
			if (key.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(key))).click();
			} else if (key.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(key))).click();
			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(key))).click();
			}

			log.info("clicked on element: " + key);
		}

		catch (Throwable t) {
			System.out.println(t.getMessage());
			log.info("error while clicking on: " + key);

			log.error("error message:", t);
		}

	}

	public static void type(String key, String value) {

		try {
			if (key.endsWith("_XPATH")) {
				driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(value);
			} else if (key.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(key))).sendKeys(value);
			}

			else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(key))).sendKeys(value);
			}

			log.info("typed in an element: " + key + " -->value: " + value);
		}

		catch (Throwable t) {
			System.out.println(t.getMessage());
			log.info("error while typing in: " + key);
			// log.info("error message:", t);
			log.error("error: ", t);
		}

	}

	public static void select(String key, String value) {
		if (key.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(key)));
		} else if (key.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(key)));
		} else if (key.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(key)));
		}
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		log.info("Typing in element: " + key + " -->value is: " + value);
	}

	public static void acceptAlert() throws InterruptedException {
		try {
			Alert alert = driver.switchTo().alert();
			Thread.sleep(2000);
			log.info(alert.getText());
			alert.accept();
			
		} catch (Throwable t) {
			log.info("No alert present");
		}
	}
	
	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			log.info("alert is present");
			return true;
		} catch (Exception e) {
			log.info("No alert is present");
			return false;
		}
		
	}

	public static void clearField(String key) {
		driver.findElement(By.cssSelector(OR.getProperty(key))).clear();
	}

	@AfterSuite
	public void tearDown() {

		log.info("test execution completed!");
	}

}
