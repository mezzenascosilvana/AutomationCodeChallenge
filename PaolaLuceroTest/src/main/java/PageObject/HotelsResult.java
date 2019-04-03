package PageObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import PageObject.helpers.SmartWaits;

public class HotelsResult {

	WebDriver driver;

	public HotelsResult(WebDriver driver) {
		this.driver = driver;
	}

	/* Elements */

	By containerResult = By.xpath("//*[@id='main_content']/div[4]");
	By containerToolBar = By.className("filter-toolbar");
	By containerSuggestion = By
			.xpath("//*[@id='page_wrapper']/section/div/div/ul/li[5]/div/div/section/div/div[1]/div/div/div[2]/div");
	By containerFooter = By.className("refinement-row__actions");
	By byNameLocator_className = By.className("item__details");
	By byNameLocator_li = By.tagName("li");
	By byNameLocator_H3 = By.tagName("h3");
	By byNameLocator_span = By.tagName("span");
	By byNameLocator_id = By.id("undefined-input");
	By buttonDone = By.xpath("//*[@id='page_wrapper']/section/div/div/ul/li[5]/div/div/section/div/footer/button[2]");
	By buttonReset = By.xpath("//*[@id='page_wrapper']/section/div/div/ul/li[5]/div/div/section/div/footer/button[1]");
	By logoName = By.id("js_navigation");
	By lostFocus = By.xpath("//*[@id='js-fullscreen-hero']/div/div[2]/form/div/div/div[3]/button/span[2]/span");
	
	SmartWaits w = new SmartWaits(driver);

	/***
	 * This method is used to show all the results before a filter is applied.
	 * @return: nameHotelList: the list of hotels to be searched
	 */
	public ArrayList<String> showResult() {

		WebElement childrenElementsbyH3 = null;
		WebElement childrenContainer = driver.findElement(containerResult);
		ArrayList<String> nameHotelList = new ArrayList<String>();
		List<WebElement> childrenElements = childrenContainer.findElements(byNameLocator_className);
		String temp = null;
		System.out.println("************List of the Hotel************");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		for (WebElement cell : childrenElements) {
			childrenElementsbyH3 = cell.findElement(byNameLocator_H3);
			temp = childrenElementsbyH3.getText();
			System.out.println(temp);
			nameHotelList.add(temp);
		}
		return nameHotelList;
	}

	/**
	 * This method verifies if a particular hotel is showed after a filter is applied.
	 * @param nameHotel
	 * @param nameFilter
	 * @return: Boolean
	 */
	public Boolean IsNameHotelList(String nameHotel, String nameFilter) {
		ArrayList<String> list = new ArrayList<String>();
		boolean result = false;	
		list = showResult();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		for (String str : list) {
			if (str.contains(nameHotel)) {
				System.out.println("The name of the hotel searched is: " +"'"+ nameHotel +"'"+ "   and it IS in the list." + " Filter applied: " + nameFilter );
				result = true;
				break;			
				}
		}
		if( result == false) System.out.println("The name of the hotel searched is: " +"'"+ nameHotel +"'"+ "   and it is NOT in the list." + " Filter applied: " + nameFilter );
		return result;
	}

	/***
	 * This method is used to apply a filter.
	 * @param nameFilter
	 * @return Boolean
	 */
	public Boolean applyFilter(String nameFilter) {
		boolean result = false;
		boolean result2 = false;
		WebElement childrenContainer = driver.findElement(containerToolBar);
		List<WebElement> childrenElements = childrenContainer.findElements(byNameLocator_span);
		for (WebElement cell : childrenElements) {
			//System.out.println(cell.getText());
			if (cell.getText().equals("Select")) {
				if (cell.isDisplayed()) {
					cell.click();
					result2 = true;
				}
				break;
			}
		}
		driver.findElement(byNameLocator_id).sendKeys(nameFilter);
		result = findElementInContainer(containerSuggestion, byNameLocator_li, nameFilter, lostFocus);
		return result && result2;
	}

	/***
	 * This method is used to find an element in a container by using a Locator and String.
	 * @param nameContainer
	 * @param nameLocatorInContainer
	 * @param stringToFind
	 * @return Boolean
	 */
	public Boolean findElementInContainer(By nameContainer, By nameLocatorInContainer, String stringToFind,
			By buttonActions) {
		boolean result = false;
		WebElement childrenContainer = driver.findElement(nameContainer);
		List<WebElement> childrenElements = childrenContainer.findElements(nameLocatorInContainer);
		for (WebElement cell : childrenElements) {
			//System.out.println(cell.getText());
			if (cell.getText().equals(stringToFind)) {
				if (cell.isDisplayed()) {
					cell.click();
					if (driver.findElement(buttonActions).isDisplayed()) {
						driver.findElement(buttonActions).click();
						result = true;
						//driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
						w.waitForElement(driver.findElement(containerResult), 25);
					}
					break;
				}
			}
		}
		return result;
	}
	
   /***
    * This method verifies if a filter was applied.	
    * @return Boolean
    */
	public Boolean isFilterApplied() {
		boolean result = false;
		WebElement childrenContainer = driver.findElement(containerToolBar);
		List<WebElement> childrenElements = childrenContainer.findElements(byNameLocator_span);
		for (WebElement cell : childrenElements) {
			//System.out.println(cell.getText());
			if (cell.getText().equals("(1) Filter")) {
				result = true;
				System.out.println("Filter was applied");
				break;
			}
		}
		return result;
	}

	/***
	 * This method is used to clean a filter.
	 * @return Boolean
	 */
	public Boolean cleanFilter() {
		boolean result = false;
		if (isFilterApplied()) {
			findElementInContainer(containerToolBar, byNameLocator_span, "(1) Filter", buttonReset);
			 driver.findElement(lostFocus).click();
			result = true;
			System.out.println("Filter reseted");
		}
		return result;
	}
}
