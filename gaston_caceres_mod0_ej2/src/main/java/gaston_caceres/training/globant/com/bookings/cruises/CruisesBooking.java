package gaston_caceres.training.globant.com.bookings.cruises;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import gaston_caceres.training.globant.com.bookings.cruises.misc.CruiseInfo;
import gaston_caceres.training.globant.com.bookings.cruises.misc.Destinations;

public class CruisesBooking {

	private WebDriver webDriver;
	private CruiseInfo cruise;
	private CruisesSelection cruiseSelection;

	public CruisesBooking(WebDriver webDriver) {
		this.webDriver = webDriver;
		cruise = new CruiseInfo();
		PageFactory.initElements(webDriver, this);
	}

	public CruisesBooking selectDestination(Destinations destination) {
		By locator = By.id("cruise-destination");
		Select destinationSelect = new Select(webDriver.findElement(locator));
		new WebDriverWait(webDriver, 1).until(ExpectedConditions.elementToBeClickable(locator));
		destinationSelect.selectByValue(destination.value());
		cruise.setDestination(destination);
		return this;
	}

	/**
	 * @param monthYear
	 *            with format <b>MM yyyy</b>
	 */
	public CruisesBooking selectDepartureMonth(String monthYear) {
		By locator = By.id("cruise-departure-month");

		Select departureMonth = new Select(
				(new WebDriverWait(webDriver, 10)).until(ExpectedConditions.presenceOfElementLocated(locator)));

		new WebDriverWait(webDriver, 1).until(ExpectedConditions.elementToBeClickable(locator));

		departureMonth.selectByValue(getDepartureValue(monthYear));
		return this;
	}

	private String getDepartureValue(String monthYear) {
		String departureValue = null;
		try {
			DateTime departureDate = new DateTime(new SimpleDateFormat("MM yyyy").parse(monthYear));
			cruise.setDeparture(departureDate.toDate());
			departureValue = new SimpleDateFormat("yyyy-MM-dd").format(departureDate.toDate());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return departureValue;
	}

	public CruisesBooking search() {
		webDriver.findElement(By.id("search-button")).click();
		return this;
	}

	public CruisesSelection cruiseSelection() {
		if (cruiseSelection == null) {
			cruiseSelection = new CruisesSelection(webDriver);
		}
		return cruiseSelection;
	}

	public boolean validCruiseSearch() {
		boolean valid = false;
		String destinationFilter = null;
		String dateFilter =null;
		Date dateFiltered = null;
		if(cruiseSelection().isSimpleFilter()){
			destinationFilter = new Select(webDriver.findElement(By.xpath(".//*[@Id='menu-content-destination']"))).getFirstSelectedOption().getAttribute("value");
			dateFilter = new Select(webDriver.findElement(By.xpath(".//*[@Id='menu-content-departure-month']"))).getFirstSelectedOption().getAttribute("value");
		}else{
			destinationFilter = webDriver.findElement(By.xpath(".//*[@Id='menu-selection-destination']/div")).getAttribute("data-value");
			dateFilter = webDriver.findElement(By.xpath(".//*[@id='menu-selection-departure-month']/div")).getAttribute("data-value");
		}
		dateFiltered = parseDate(dateFilter, "yyyy-MM-dd");
		if (destinationFilter.equals(cruise.getDestination().value())
				&& cruise.getDepartureDate().equals(dateFiltered)) {
			valid = true;
		}
		return valid;
	}

	private Date parseDate(String dateToParse, String pattern) {
		Date date = null;
		try {
			date = new SimpleDateFormat(pattern).parse(dateToParse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

}
