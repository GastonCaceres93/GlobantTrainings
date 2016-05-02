package gaston_caceres.training.globant.com.utils;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValidatePage {
	
	private WebDriver webDriver;
	
	public ValidatePage(WebDriver webDriver){
		this.webDriver = webDriver;
	}

	public boolean validElements(Set<ElementToValidate> elementsToValidate) {
		boolean validPage = true;
		for (ElementToValidate element : elementsToValidate) {
			if (!validateElement(element)) {
				validPage = false;
				break;
			}
		}
		return validPage;
	}

	private boolean validateElement(ElementToValidate element) {
		boolean valid = true;
		for (ValidationType validation : element.validations()) {
			switch (validation) {
			case COMPLETE_TEXT:
				valid = validateCompleteText(element);
				break;
			case COMPLETE_URL:
				valid = validateCompleteURL(element);
				break;
			case IS_ELEMENT_PRESENT:
				valid = isElementPresent(element.locator());
				break;
			case PARTIAL_TEXT:
				valid = validatePartialText(element);
				break;
			case PARTIAL_URL:
				valid = validatePartialURL(element);
				break;
			default:
				break;
			}

			if (!valid) {
				break;
			}
		}

		return valid;
	}

	private boolean validateCompleteURL(ElementToValidate element) {
		return webDriver.getCurrentUrl().equals(element.value());
	}

	private boolean validatePartialURL(ElementToValidate element) {
		return webDriver.getCurrentUrl().contains(element.value());
	}

	private boolean validateCompleteText(ElementToValidate element) {
		WebElement webEl = (new WebDriverWait(webDriver, 3)
				.until(ExpectedConditions.presenceOfElementLocated(element.locator())));
		return element.value() != null && element.value().equals(webEl.getText());
	}

	private boolean validatePartialText(ElementToValidate element) {
		WebElement webEl = (new WebDriverWait(webDriver, 3)
				.until(ExpectedConditions.presenceOfElementLocated(element.locator())));
		return element.value() != null && webEl.getText().contains(element.value());
	}

	private boolean isElementPresent(By locator) {
		try {
			(new WebDriverWait(webDriver, 3)).until(ExpectedConditions.presenceOfElementLocated(locator));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}catch (TimeoutException e) {
			return false;
		}
	}

}
