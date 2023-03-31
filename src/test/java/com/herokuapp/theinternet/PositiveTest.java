package com.herokuapp.theinternet;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class PositiveTest {

	@Test
	public void testLogin() {
		System.out.println("Starting loginTest");

		// Create driver
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// maximize browser window
		driver.manage().window().maximize();

		// open test page
		String url = "http://the-internet.herokuapp.com/login";
		driver.get(url);
		System.out.println("Page is opened.");

		// Login page
		WebElement username = driver.findElement(By.id("username"));
		WebElement password = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//form[@id='login']//button[@class='radius']"));

		// fill fields and submit
		username.sendKeys("tomsmith");
		password.sendKeys("SuperSecretPassword!");
		loginButton.click();

		// success page
		assertThat(driver.getCurrentUrl()).endsWith("/secure");
		// contains "Secure Area"
		WebElement successHeader = driver.findElement(By.xpath("//div[@id='content']/div[@class='example']/h2"));
		assertThat(successHeader.getText()).contains("Secure Area");
		// Flash success Message
		WebElement flashsSuccessMessage = driver.findElement(By.className("flash").className("success"));
		assertThat(flashsSuccessMessage.getText()).contains("You logged into a secure area!");
		// contains "Welcome to the Secure Area. When you are done click logout below."
		WebElement successSubHeader = driver
				.findElement(By.xpath("//div[@id='content']/div[@class='example']/h4[@class='subheader']"));
		assertThat(successSubHeader.getText())
				.isEqualTo("Welcome to the Secure Area. When you are done click logout below.");
		// Logout Button is visible
		WebElement logoutButton = driver.findElement(By.xpath("//a[@href='/logout']"));
		assertThat(logoutButton.isDisplayed()).isTrue();

		// Close browser
		driver.quit();
	}
}
