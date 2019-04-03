package test;

import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import PageObject.BasePage;
import PageObject.HomeTrivagoPageObject;
import PageObject.HotelsResult;



public class TrivagoTest_WiFiFilter extends BasePage {

	/***
	 * This test receive the parameters from XML file and performs a search.
	 * 
	 * @param location
	 * @param sizeDescription
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Parameters({ "location", "sizeDescription", "numberOfMonths" })
	@Test
	public void findLocation(String location, String sizeDescription, int numberOfMonths)
			throws InterruptedException, IOException {
		System.out.println("//******************************************START OF THE TEST SUITE***********************************************************************// ");
		System.out.println("           ");
		System.out.println("TC: Start findLocation test--------------------------------------");
		setup();
		HomeTrivagoPageObject d = new HomeTrivagoPageObject(driver);
		d.imputLocation(location);
		d.selectCheckInDate();
		d.selectCheckOutDate(numberOfMonths);
		d.selectRoomSize(sizeDescription);
		// Then press 'Search' button
		assertTrue(d.searchButton());
		System.out.println("End findLocation test----------------------------------------");
		System.out.println("           ");
	}
	

	/***
	 * This test shows results before a filter is applied. 
	 * For this test the @DataProvider method was used.
	 * Receives parameter from @DataProvider method:
	 * @param nameHotel
	 * @param nameFilter
	 */
	@Parameters({ "nameFilter" })
	@Test( dataProvider = "getDataFalse")
	public void showResultsWithFiltersFromDataProviderFalse(String nameHotel, String nameFilter) {
		System.out.println("TC: showResultsWithFiltersFromDataProviderFalse() test--------------------------------------");
		HotelsResult r = new HotelsResult(driver);
		assertTrue(r.applyFilter(nameFilter));
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
		System.out.println("....... after filter--------------------------------------");
		assertFalse(r.IsNameHotelList(nameHotel, nameFilter));
		System.out.println("End showResultsWithFiltersFromDataProviderFalse() test--------------------------------------");
		System.out.println();
	}
	
	/***
	 * This test is used to reset the filter before filter is applied. 
	 * This method is dependent of the 'showResultsWithFiltersFromDataProviderTrue' test.
	 */
	@Test(dependsOnMethods = { "findLocation" })
	public void resetFilter() {
		System.out.println("Start cleanFilter() tests--------------------------------------");
		HotelsResult r = new HotelsResult(driver);
		assertTrue(r.cleanFilter());
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
		System.out.println("End cleanFilter() test--------------------------------------");
		System.out.println();
	}

	/***
	 * This test shows results before a filter is applied. 
	 * For this test the @DataProvider method was used.
	 * Receives parameter from @DataProvider method:
	 * @param nameHotel
	 * @param nameFilter
	 */
	@Parameters({ "nameFilter" })
	@Test(dependsOnMethods = { "resetFilter" }, dataProvider = "getDataTrue")
	public void showResultsWithFiltersFromDataProviderTrue(String nameHotel, String nameFilter) {
		System.out.println("TC: Start showResultsWithFiltersFromDataProviderTrue() test--------------------------------------");
		HotelsResult r = new HotelsResult(driver);
		assertTrue(r.applyFilter(nameFilter));
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
		System.out.println("....... after filter--------------------------------------");
		assertTrue(r.IsNameHotelList(nameHotel, nameFilter));
		System.out.println("End showResultsWithFiltersFromDataProviderTrue() test--------------------------------------");
	}
	
	/***
	 * This method is used to test different combinations of data. 
	 * object has two dimension [row][column]: 
	 * 	[2]= number of the combinations 
	 *  [2] = number of parameters
	 */
	@DataProvider
	public Object[][] getDataFalse() {

		Object[][] data = new Object[1][2];
		data[0][0] = "Jurys Inn Cork";
		data[0][1] = "Free WiFi";
		 
		return data;
	}
	
	/***
	 * This method is used to test different combinations of data. 
	 * object has two dimension [row][column]: 
	 * 	[2]= number of the combinations 
	 *  [2] = number of parameters
	 */
	@DataProvider
	public Object[][] getDataTrue() {

		Object[][] data = new Object[1][2];
		data[0][0] = "Cork International Hotel"; 
		data[0][1] = "Free WiFi";
		 
		return data;
	}

	/***
	 * Close all browsers and Selenium web drivers
	 */
	@AfterTest
	public void cleanUp() {

		try {
			System.out.println("//******************************************END OF THE TEST SUITE***********************************************************************//");

		} finally {

			finish();
		}
	}

}
