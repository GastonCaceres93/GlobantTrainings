package gaston_caceres.training.globant.com.bookings.hotel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelSearch {

	private WebDriver webDriver;

	private List<WebElement> hotels;

	private String hotelSearched;

	public HotelSearch(WebDriver driver, String hotelSearch) {
		webDriver = driver;
		hotelSearched = hotelSearch;
	}

	public boolean hotelFound() {
		boolean found = false;
		loadHotels();
		for (WebElement hotel : hotels) {
			if (isHotel(hotel)) {
				found = true;
				break;
			}
		}
		return found;
	}

	private boolean isHotel(WebElement webElement) {
		String hotelName = webElement.findElement(By.xpath("div/div/article/h3")).getText();
		return hotelName.contains(hotelSearched);
	}

	private void loadHotels() {
		new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("resultsContainer")));
		hotels = webDriver.findElements(By.cssSelector(".hotelWrapper"));
	}

}
