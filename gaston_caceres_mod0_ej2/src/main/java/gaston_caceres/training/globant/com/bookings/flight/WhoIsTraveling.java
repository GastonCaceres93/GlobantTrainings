package gaston_caceres.training.globant.com.bookings.flight;

import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.By;

import gaston_caceres.training.globant.com.utils.ElementToValidate;
import gaston_caceres.training.globant.com.utils.ValidationType;

public class WhoIsTraveling {

	public Set<ElementToValidate> getElementsToValidateFlightReviewPage() {
		Set<ElementToValidate> elements = new HashSet<ElementToValidate>();

		elements.add(new ElementToValidate(By.xpath(".//*[@id='preferences']/form/fieldset/h2"),null, "Who's traveling?", ValidationType.COMPLETE_TEXT));

		elements.add(new ElementToValidate(By.id("cko-form"),null, null, ValidationType.IS_ELEMENT_PRESENT));

		elements.add(new ElementToValidate(By.id("trip-summary"),null, null, ValidationType.IS_ELEMENT_PRESENT));

		elements.add(new ElementToValidate(By.id("login-module-toggle"),null, null, ValidationType.IS_ELEMENT_PRESENT));
		
		elements.add(new ElementToValidate(null,null, "https://www.travelocity.com/FlightCheckout", ValidationType.PARTIAL_URL));

		return elements;
	}

}
