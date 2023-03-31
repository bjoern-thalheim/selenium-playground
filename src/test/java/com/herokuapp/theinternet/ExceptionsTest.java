package com.herokuapp.theinternet;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

/**
 * I resolved all error situations, except for the StaleElementReferenceException, so this test method is disabled.
 * @author thalheim
 *
 */
public class ExceptionsTest extends AbstractSeleniumTest {

	@Test
	public void testNoSuchElementException() throws InterruptedException {
		// given
		driver.navigate().to("https://practicetestautomation.com/practice-test-exceptions/");
		// when
		WebElement button = driver.findElement(By.xpath("/html//button[@id='add_btn']"));
		button.click();
		// then with fixed delay issue
		WebElement row2InputField = waitForElementToBeClickable("//div[@id='row2']", 5);
		assertThat(row2InputField).isNotNull();
	}

	@Test
	public void testElementNotInteractableException() {
		// given
		driver.navigate().to("https://practicetestautomation.com/practice-test-exceptions/");
		// when
		WebElement button = driver.findElement(By.xpath("//button[@id='add_btn']"));
		button.click();
		WebElement row2InputField = waitForElementToBeClickable("//div[@id='row2']/input[@type='text']", 5);
		assertThat(row2InputField.isEnabled()).isTrue();
		row2InputField.sendKeys("Spaghetti");
		WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']//button[@id='save_btn']"));
		saveButton.click();
		// then verify text was saved
		// field needs to be inactive
		assertThat(row2InputField.isEnabled()).isFalse();
		// flash message needs to be there
		WebElement confirmationFlashMessage = driver.findElement(By.id("confirmation"));
		assertThat(confirmationFlashMessage.getText()).isEqualTo("Row 2 was saved");
	}

	/**
	 * Test case 3: InvalidElementStateException
	 * <ol>
	 * <li>Open page</li>
	 * <li>Clear input field</li>
	 * <li>Type text into the input field</li>
	 * <li>Verify text changed</li>
	 * </ol>
	 * The input field is disabled. Trying to clear the disabled field will throw
	 * InvalidElementStateException. We need to enable editing of the input field
	 * first by clicking the Edit button.
	 * 
	 * If we try to type text into the disabled input field, we will get
	 * ElementNotInteractableException, as in Test case 2.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testInvalidElementStateException() throws InterruptedException {
		// given
		driver.navigate().to("https://practicetestautomation.com/practice-test-exceptions/");
		WebElement row1InputField = driver.findElement(By.xpath("//div[@id='row1']/input[@type='text']"));
		// when
		WebElement editButton = driver.findElement(By.xpath("//div[@id='row1']/button[@id='edit_btn']"));
		editButton.click();
		row1InputField.clear();
		row1InputField.sendKeys("Spaghetti");
		// then
		assertThat(row1InputField.getAttribute("value")).isEqualTo("Spaghetti");
	}

	/**
	 * 
	 * Test case 4: StaleElementReferenceException
	 * <ol>
	 * <li>Open page</li>
	 * <li>Find the instructions text element</li>
	 * <li>Push add button</li>
	 * <li>Verify instruction text element is no longer displayed</li>
	 * </ol>
	 * The instructions element is removed from the page when the second row is
	 * added. That’s why we can no longer interact with it. Otherwise, we will see
	 * StaleElementReferenceException.
	 */
	@Test(enabled = false)
	public void testStaleElementReferenceException() {
		// given
		driver.navigate().to("https://practicetestautomation.com/practice-test-exceptions/");
		WebElement instructions = driver.findElement(By.xpath("//p[@id='instructions']"));
		assertThat(instructions.isDisplayed()).isTrue();
		// when
		WebElement button = driver.findElement(By.xpath("/html//button[@id='add_btn']"));
		button.click();
		// then
		assertThat(instructions.isDisplayed()).isFalse();
	}

	/**
	 * Test case 5: TimeoutException
	 * <ol>
	 * <li>Open page</li>
	 * <li>Click Add button</li>
	 * <li>Wait for 3 seconds for the second input field to be displayed</li>
	 * <li>Verify second input field is displayed</li>
	 * </ol>
	 * The second row shows up after about 5 seconds, so a 3-second timeout is not
	 * enough. That’s why we will get TimeoutException while executing steps in the
	 * above test case.
	 */
	@Test
	public void testTimeoutException() {
		// given
		driver.navigate().to("https://practicetestautomation.com/practice-test-exceptions/");
		// when
		WebElement button = driver.findElement(By.xpath("/html//button[@id='add_btn']"));
		button.click();
		WebElement row2InputField = waitForElementToBeClickable("//div[@id='row2']", 5);
		// then
		assertThat(row2InputField).isNotNull();
	}

	private WebElement waitForElementToBeClickable(String xpath, int timeoutInSeconds) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		ExpectedCondition<WebElement> elementToBeClickable = ExpectedConditions.elementToBeClickable(By.xpath(xpath));
		return webDriverWait.until(elementToBeClickable);
	}

}
