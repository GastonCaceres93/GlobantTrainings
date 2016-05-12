package gaston_caceres.training.globant.com.bookings.cruises;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import gaston_caceres.training.globant.com.bookings.cruises.misc.CruiseFilter;
import gaston_caceres.training.globant.com.bookings.cruises.misc.CruiseLenght;

public class CruisesSelection {

	private WebDriver webDriver;

	private CruiseFilter filter;

	private List<WebElement> cruises;
	private WebElement cruise;

	public CruisesSelection(WebDriver driver) {
		webDriver = driver;
		filter = new CruiseFilter(webDriver);
	}

	public CruisesSelection filterByLenght(CruiseLenght lenght) {
		filter.filterByCruiseLenght(lenght);
		return this;
	}

	private void loadCruises() {
		try {
			//there has to be a better way...
			new WebDriverWait(webDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.className("asdasfafdf")));
		} catch (Exception e) {
			//nothing to do here....
		}finally {
			cruises = webDriver.findElements(By.className("cruise-listing-wrapper"));
		}
	}

	public CruisesSelection selectCruise(int position){
		loadCruises();
		cruise = cruises.get(position - 1);
		cruise.findElement(By.cssSelector(".btn-secondary.btn-sub-action")).click();
		return this;
	}
	
	public boolean isItineraryVisible(){
		try {
			new WebDriverWait(webDriver,10).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".box.collapsible-box.loader-box.loaded.open")));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isSimpleFilter(){
		return filter.isSimpleFilter();
	}
	
}
