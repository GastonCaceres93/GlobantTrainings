package gaston_caceres.training.globant.com.bookings.flight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gaston_caceres.training.globant.com.bookings.flight.misc.FlightInfo;
import gaston_caceres.training.globant.com.utils.ElementToValidate;
import gaston_caceres.training.globant.com.utils.ValidationType;

public class FlightReview {

	private WebDriver webDriver;

	private String reviewPageHandle;

	public FlightReview(WebDriver webDriver) {
		this.webDriver = webDriver;
		changeWindowHandle();
	}

	public WhoIsTraveling continueBooking() {
		new WebDriverWait(webDriver, 3).until(ExpectedConditions.presenceOfElementLocated(By.id("FlightUDPBookNowButton1"))).click();
		return new WhoIsTraveling();
	}

	public Set<ElementToValidate> getElementsToValidateFlightReviewPage(FlightInfo flightInfo) {
		Set<ElementToValidate> elements = new HashSet<ElementToValidate>();

		elements.add(new ElementToValidate(By.id("departure-date-0"), null,
				getDepartureDateFormated(flightInfo.getDepartureDate()), ValidationType.IS_ELEMENT_PRESENT,
				ValidationType.COMPLETE_TEXT));

		elements.add(new ElementToValidate(By.id("totalPriceForPassenger0"), null,
				String.valueOf(flightInfo.getPriceAsText()), ValidationType.PARTIAL_TEXT));

		elements.add(new ElementToValidate(By.id("arrival-airportcode-automation-label-0"), null,
				flightInfo.getDestinationAirport().toUpperCase(), ValidationType.COMPLETE_TEXT));

		elements.add(new ElementToValidate(By.id("departure-airport-automation-label-0"), null,
				flightInfo.getDepartureAirport().toUpperCase(), ValidationType.COMPLETE_TEXT));

		elements.add(
				new ElementToValidate(null, null, "https://www.travelocity.com/Details?", ValidationType.PARTIAL_URL));

		return elements;
	}

	public FlightReview goToReviewTripWindow() {
		try {
			webDriver.switchTo().window(reviewPageHandle);
		} catch (Exception e) {
			System.out.println("window not found...");
		}
		return this;
	}

	private void changeWindowHandle() {
		for (String handle : webDriver.getWindowHandles()) {
			webDriver.switchTo().window(handle);
			try {
				new WebDriverWait(webDriver, 3)
						.until(ExpectedConditions.presenceOfElementLocated(By.id("FlightUDPBookNowButton1")));
				this.reviewPageHandle = handle;
				break;
			} catch (Exception e) {
				continue;
			}
		}
	}

	private String getDepartureDateFormated(DateTime date) {
		String dateFormatted = null;
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		try {

			DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ENGLISH);
			String formattedDate = df.format(date.toDate());

			SimpleDateFormat fs = new SimpleDateFormat("MM/dd/yy");
			dateFormatted = format.format(fs.parse(formattedDate));

		} catch (Exception e) {
		}
		return dateFormatted;
	}

}
