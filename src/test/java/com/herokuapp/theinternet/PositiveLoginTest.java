package com.herokuapp.theinternet;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class PositiveLoginTest extends AbstractSeleniumTest {
	String url = "http://the-internet.herokuapp.com/login";

	@Test
	@Parameters({ "username", "password" })
	public void testLogin(String username, String password) {
		// open test page
		driver.get(url);

		// Login page
		WebElement usernameField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//form[@id='login']//button[@class='radius']"));

		// fill fields and submit
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
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
