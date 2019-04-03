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



public class TrivagoTest_SpaFilter extends BasePage {

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
	 * This test shows the result of the all of hotels searched without applying any
	 * filters. 
	 * This method is dependent of the 'findLocation' test.
	 * 
	 */

	@Test(dependsOnMethods = { "findLocation" })
	public void showResultsWithOutFilters() {
		System.out.println("TC: Start showResultsWithOutFilters() test-------------------------");
		HotelsResult r = new HotelsResult(driver);
		r.showResult();
		System.out.println("End showResultsWithOutFilters() test----------------------------");
		System.out.println("           ");
	}
	
	/***
	 * This test verifies if a filter is applied.
	 * This method is dependent of the 'showResultsWithOutFilters' test.
	 * Receives the parameter from XML file:
	 * @param nameFilter
	 */
	@Parameters({ "nameFilter" })
	@Test(dependsOnMethods = { "showResultsWithOutFilters" })
	public void applyFilters(String nameFilter) {
		System.out.println("TC: Start applyFilters() test-------------------------------------");
		HotelsResult r = new HotelsResult(driver);
		System.out.println("...... before filter--------------------------------------");
		r.showResult();
		assertTrue(r.applyFilter(nameFilter));
		System.out.println("....... after filter--------------------------------------");
		r.showResult();
		System.out.println("End applyFilters() test----------------------------------------");
		System.out.println("           ");
	}


	/***
	 * This test shows the results before a filter is applied. 
	 * This test uses the XML file
	 * This method is dependent of the 'applyFilters' test.  
	 * Receives parameter from XML file:
	 *  
	 * @param nameHotel2Spa
	 * @param nameFilter
	 */
	@Parameters({  "nameHotel2Spa", "nameFilter" })
	@Test(dependsOnMethods = { "applyFilters" })
	public void showResultsWithFiltersFromXMLFileFalse( String nameHotel2Spa, String nameFilter) {
		System.out.println("TC: Start showResultsWithFiltersFromXMLFileFalse() test--------------------------------------");
		HotelsResult r = new HotelsResult(driver);
		assertFalse(r.IsNameHotelList(nameHotel2Spa, nameFilter));
		System.out.println("End showResultsWithFiltersFromXMLFileFalse() test--------------------------------------");
		System.out.println("           ");
	}
	
	/***
	 * This test shows the results before a filter is applied. 
	 * This test uses the XML file
	 * This method is dependent of the 'applyFilters' test.  
	 * Receives parameter from XML file:
	 *  
	 * @param nameHotel1Spa
	 * @param nameFilter
	 */
	@Parameters({ "nameHotel1Spa", "nameFilter" })
	@Test(dependsOnMethods = { "applyFilters" })
	public void showResultsWithFiltersFromXMLFileTrue(String nameHotel1Spa,  String nameFilter) {
		System.out.println("TC: Start showResultsWithFiltersFromXMLFileTrue() test--------------------------------------");
		HotelsResult r = new HotelsResult(driver);
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}

		assertTrue(r.IsNameHotelList(nameHotel1Spa, nameFilter));
		System.out.println("End showResultsWithFiltersFromXMLFileTrue() test-----------------------------------------");
		System.out.println("           ");
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
