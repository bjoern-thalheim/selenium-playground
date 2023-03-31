package com.herokuapp.theinternet;

import static org.assertj.core.api.Assertions.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeLoginTest extends AbstractSeleniumTest {
	
	String url = "http://the-internet.herokuapp.com/login";

	@Test
	@Parameters({"username", "password", "flashMessagePart"})
	public void executeIncorrecLogin(String username, String password, String flashMessagePart) {
		// given
		driver.navigate().to(url);
		WebElement usernameField = driver.findElement(By.id("username"));
		WebElement passwordField = driver.findElement(By.id("password"));
		WebElement loginButton = driver.findElement(By.xpath("//form[@id='login']//button[@class='radius']"));
		// when
		usernameField.sendKeys(username);
		passwordField.sendKeys(password);
		loginButton.click();
		// then
		assertThat(driver.getCurrentUrl()).isEqualTo(url);
		WebElement flashMessage = driver.findElement(By.id("flash"));
		assertThat(flashMessage.getText()).contains(flashMessagePart);
	}
}
