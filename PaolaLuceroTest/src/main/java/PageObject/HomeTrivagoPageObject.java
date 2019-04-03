package PageObject;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import PageObject.helpers.SmartWaits;
import java.util.concurrent.TimeUnit;

public class HomeTrivagoPageObject {

	WebDriver driver;

	public HomeTrivagoPageObject(WebDriver driver) {
		this.driver = driver;
	}

	/* select location Elements */
	By location_id = By.id("horus-querytext");
	By byNameLocator_span = By.tagName("span");
	By focus = By.className("btn-horus__value");

	/* selectElementDropDownList Elements */
	By containerResult = By.xpath("//*[@id='js-fullscreen-hero']/div/div[2]/form/div/div/div[1]/div[2]/div/div");
	By byNameLocator_ul = By.tagName("ul");
	By byNameLocator_li = By.tagName("li");

	/* selectDateDeparting Elements */
	By byNameLocator_td = By.tagName("td");

	/* selectDateReturning Elements */
	By datepickerTable = By.className("cal-month");
	By buttonNext = By.className("cal-btn-next");

	/* selectRoomSize Elements */
	// By menuRoom = By.className("btn-horus__content-wrap");
	By containerMenuRoom = By.xpath("//*[@id='js-fullscreen-hero']/div/div[2]/form/div[2]");

	By size = By.className("roomtype-btn__label");

	/* searchButton */
	By searchButton = By.xpath("//*[@id='js-fullscreen-hero']/div/div[2]/form/div/div/div[3]/button");

	SmartWaits w = new SmartWaits(driver);

	/***
	 * This method enters a value in 'location' textfile. 
	 * Parameters received from XML file.
	 */
	public void imputLocation(String location) throws InterruptedException {

		driver.findElement(location_id).click();
		driver.findElement(location_id).sendKeys(location);
		selectElementDropDownList(location);
	}

	/***
	 * This method selects one element from the dropdown list that matches with one value
	 */
	public void selectElementDropDownList(String location) {
		List<WebElement> childrenElementsbySpan = null;
		String temp = null;
		WebElement childrenContainer = driver.findElement(containerResult).findElement(byNameLocator_ul);
		List<WebElement> childrenElements = childrenContainer.findElements(byNameLocator_li);
		for (WebElement child : childrenElements) {
			childrenElementsbySpan = child.findElements(byNameLocator_span);
			for (int i = 0; i <= childrenElementsbySpan.size(); i++) {
				temp = childrenElementsbySpan.get(i).getText();
				if (temp.contains(String.valueOf(location))) {
					child.click();
					break;
				}
			}
			break;
		}
	}

	/***
	 * This method selects the checkIn date. Handles datepicker object.
	 */
	public void selectCheckInDate() {

		// This is from date picker table
		WebElement dateWidgetFrom = driver.findElement(datepickerTable);

		// These are the rows of the 'From date' picker table
		// List<WebElement> rows = dateWidgetFrom.findElements(By.tagName("tr"));
		// This are the columns of the from date picker table
		List<WebElement> columns = dateWidgetFrom.findElements(byNameLocator_td);

		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		for (WebElement cell : columns) {
			// Select Today's Date
			//System.out.println(cell.getText() + " , " + getCurrentDay());
			if (cell.getText().contains(getCurrentDay())) {
				cell.click();
				break;
			}
		}
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	/***
	 * This method gets the CurrentDay.
	 * If the time of the searching is after 6 pm
	 * the method returns the next day.
	 */
	private String getCurrentDay() {

		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		String todayStr = null;
		int currentlyTime = calendar.get(Calendar.HOUR_OF_DAY);
		if (currentlyTime > 18) {
			todayStr = getCheckOutDay(1);
			return todayStr;
		} else {
			// Get Current Day as a number
			int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
			// Integer to String Conversion
			todayStr = Integer.toString(todayInt);
		}
		return todayStr;
	}

	/***
	 * This method selects checkOut date. Handles datepicker object
	 * 
	 * @param: numberOfMonths : is a parameter that is entered in the XML file. It was
	 *         added because if a search is for 3 months from today’s date, for time to
	 *         time a result is not returned. 
	 *         Basically this parameter is the number of the months that
	 *         you want to select from your current date.
	 */
	public void selectCheckOutDate(int numberOfMonths) {

		// This is from date picker table
		WebElement dateWidgetFrom = driver.findElement(datepickerTable);
		// This are the rows of the from date picker table
		// This are the columns of the from date picker table
		List<WebElement> columns = dateWidgetFrom.findElements(byNameLocator_td);
		//
		int tempVar = 1;
		try {
			while ((driver.findElement(buttonNext).isDisplayed()) && (numberOfMonths >= tempVar)) {
				driver.findElement(buttonNext).click();
				tempVar++;
			}
			// DatePicker is a table. Thus we can navigate to each cell
			// and if a cell matches with the current date then we will click it.
			for (WebElement cell : columns) {
				// Select Today's Date
				//System.out.println(cell.getText());
				if (cell.getText().contains(getCurrentDay())) {
					cell.click();
					break;
				}
			}
		} catch (Exception e) {
			// DatePicker is a table. Thus we can navigate to each cell
			// and if a cell matches with the current date then we will click it.
			for (WebElement cell : columns) {
				// Select Today's Date
				if (cell.getText().contains(getCurrentDay())) {
					cell.click();
					break;
				}
			}
		}
	}

	/***
	 * This method gets the checkOut day. Calculates the number of days between
	 * checkIn date and checkOut date.
	 * @param day
	 * @return
	 */
	public String getCheckOutDay(int months) {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// Number of days to add
		calendar.add(Calendar.DAY_OF_MONTH, months);
		// Return the Date object with the new days value added
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		return todayStr;
	}

	/***
	 * This method selects the room size.
	 * @param: sizeDescription: is the size of the room that you want. This
	 *         parameter is entered from XML file.
	 */
	public void selectRoomSize(String sizeDescription) {

		WebElement childrenContainer = driver.findElement(containerMenuRoom);
		List<WebElement> childrenElements = childrenContainer.findElements(byNameLocator_li);
		for (WebElement cell : childrenElements) {
			//System.out.println(cell.getText());
			if (cell.getText().equals(sizeDescription)) {
				cell.click();
				break;
			}
		}
	}

	/***
	 * This method is called when the Search button is clicked.
	 * return boolean
	 */
	public Boolean searchButton() {
		boolean result = false;
        if (driver.findElement(searchButton).isDisplayed() ) {
		driver.findElement(searchButton).click();
		result = true;
        }
        return result;
	}
}
