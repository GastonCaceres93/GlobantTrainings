package gaston_caceres.training.globant.com.utils;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ValidatePage {

	private WebDriver webDriver;

	public ValidatePage(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public boolean validElements(Set<ElementToValidate> elementsToValidate) {
		boolean validPage = true;
		for (ElementToValidate element : elementsToValidate) {
			if (!validateElement(element)) {
				System.out.println(element.locator());
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
			case ATTRIBUTE:
				valid = validateAttribute(element);
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
		try {
			WebElement webEl = (new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(element.locator())));
			return element.value() != null && element.value().equalsIgnoreCase(webEl.getText());
		} catch (Exception e) {
			System.out.println("validateCompleteText failed for");
			return false;
		}
	}

	private boolean validatePartialText(ElementToValidate element) {
		try {
			WebElement webEl = (new WebDriverWait(webDriver, 10).until(ExpectedConditions.presenceOfElementLocated(element.locator())));
			return element.value() != null && webEl.getText().contains(element.value());
		} catch (Exception e) {
			System.out.println("validatePartialText failed for");
			e.printStackTrace();
			return false;
		}
	}
	

	@SuppressWarnings("unused")
	private boolean isElementPresent(By locator) {
		try {
			WebElement element = (new WebDriverWait(webDriver, 20).until(ExpectedConditions.presenceOfElementLocated(locator)));
			return true;
		}catch (Exception e) {
			System.out.println("isElementPresent failed for");
			return false;
		}
	}

	private boolean validateAttribute(ElementToValidate element) {
		boolean valid = true;
		if (isElementPresent(element.locator())) {
			WebElement webEl = (new WebDriverWait(webDriver, 3).until(ExpectedConditions.presenceOfElementLocated(element.locator())));
			valid = webEl.getAttribute(element.getAttribute()).equals(element.value());
		} else {
			valid = false;
		}
		return valid;
	}

}
