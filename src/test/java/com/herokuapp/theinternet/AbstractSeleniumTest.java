package com.herokuapp.theinternet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class AbstractSeleniumTest {

	WebDriver driver;

	@BeforeClass
	@Parameters("browser")
	public void setup(@Optional("firefox") String browser) {
		switch (browser) {
		case "firefox": {
			FirefoxOptions options = new FirefoxOptions();
			options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
			options.addArguments("-headless");
			System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
			driver = new FirefoxDriver(options);
			break;
		}
		case "chrome": {
			System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
			driver = new ChromeDriver();
			break;
		}
		case "edge": {
			System.setProperty("webdriver.edge.driver", "src/main/resources/msedgedriver.exe");
			driver = new EdgeDriver();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + browser);
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

}
