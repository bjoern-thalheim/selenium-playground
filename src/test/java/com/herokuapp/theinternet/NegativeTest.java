package com.herokuapp.theinternet;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NegativeTest {

	WebDriver driver;

	@BeforeClass
	public void setup() {        
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
		options.addArguments("-headless");
		System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
		driver = new FirefoxDriver(options);
		
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void usernameIsInvalid() {
		abstractIncorrectLogin("bjoernthalheim", "SuperSecretPassword!", "username is invalid");
	}

	@Test
	public void passwordIsInvalid() {
		abstractIncorrectLogin("tomsmith", "SuperWrongPassword!", "password is invalid");
	}

	private void abstractIncorrectLogin(String uname, String pwd, String flashMessagePart) {
		// given
		String url = "http://the-internet.herokuapp.com/login";
		driver.navigate().to(url);
		driver.manage().window().maximize();
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//form[@id='login']//button[@class='radius']"));
		// when
		username.sendKeys(uname);
		password.sendKeys(pwd);
		loginButton.click();
		// then
		assertThat(driver.getCurrentUrl()).isEqualTo(url);
		WebElement flashMessage = driver.findElement(By.id("flash"));
		assertThat(flashMessage.getText()).contains(flashMessagePart);
	}
}
