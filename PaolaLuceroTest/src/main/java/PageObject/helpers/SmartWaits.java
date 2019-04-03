package PageObject.helpers;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SmartWaits {
	protected WebDriver driver;
	private WebElement element;

	public SmartWaits(WebDriver driver) {
		this.driver = driver;
	}

	public void fluentWait(final By locator) {

		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 5 seconds.
		@SuppressWarnings("deprecation")
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(40, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		element = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
	}

	/***
	 * The following are the Expected Conditions that can be used in Explicit Wait
	 * 
	 * alertIsPresent() elementSelectionStateToBe() elementToBeClickable()
	 * elementToBeSelected() frameToBeAvaliableAndSwitchToIt()
	 * invisibilityOfTheElementLocated() invisibilityOfElementWithText()
	 * presenceOfAllElementsLocatedBy() presenceOfElementLocated()
	 * textToBePresentInElement() textToBePresentInElementLocated()
	 * textToBePresentInElementValue() titleIs() titleContains() visibilityOf()
	 * visibilityOfAllElements() visibilityOfAllElementsLocatedBy()
	 * visibilityOfElementLocated()
	 */

	public void waitForElement(By element, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.presenceOfElementLocated(element));
		} catch (Exception e) {

			System.out.println("....");
		}
	}

	public void waitForElement(WebElement element, int timeout) {
	try {
		new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
	} catch (Exception e) {

		System.out.println("....");
	}
	}

}
