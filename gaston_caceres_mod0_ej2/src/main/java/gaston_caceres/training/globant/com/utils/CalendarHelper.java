package gaston_caceres.training.globant.com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarHelper {

	private WebDriver webDriver;

	public static final String DATE_FORMAT = "dd/MM/yyyy";
	private static final String CALENDAR_TITLE_FORMAT = "MMM yyyy";

	private WebElement calendarOne;
	private WebElement calendarTwo;

	public CalendarHelper(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public CalendarHelper nextMonth() {
		webDriver.findElement(By.cssSelector(".btn-paging.btn-secondary.next")).click();;
		initCalendars();
		return this;
	}

	public CalendarHelper prevMonth() {
		webDriver.findElement(By.cssSelector(".btn-paging.btn-secondary.prev")).click();;
		initCalendars();
		return this;
	}

	public CalendarHelper selectDate(DateTime date) {
		String dateFormated = getDateFormated(date, CALENDAR_TITLE_FORMAT);
		initCalendars();
		while (!monthIsVisible(dateFormated)) {
			if(shouldGoBack(date)){
				prevMonth();
			}else{
				nextMonth();
			}
		}
		getCalendar(dateFormated).findElement(By.linkText(String.valueOf(date.getDayOfMonth()))).click();
		return this;
	}

	private WebElement getCalendar(String date) {
		String calendarOneTitle = this.calendarOne.findElement(By.xpath("header/h2")).getText();
		String calendarTwoTitle = this.calendarTwo.findElement(By.xpath("header/h2")).getText();
		
		if (date.equalsIgnoreCase(calendarOneTitle)) {
			return this.calendarOne;
		} else if (date.equalsIgnoreCase(calendarTwoTitle)) {
			return this.calendarTwo;
		}

		return null;
	}

	private boolean monthIsVisible(String date) {
		return getCalendar(date) != null;
	}

	private String getDateFormated(DateTime date, String format) {
		return  new SimpleDateFormat(format,Locale.ENGLISH).format(date.toDate());
	}

	private void initCalendars() {
		WebElement calendarsDivContainer = findCalendarContainer();
		calendarOne = calendarsDivContainer.findElement(By.xpath("section[1]"));
		calendarTwo = calendarsDivContainer.findElement(By.xpath("section[2]"));
	}
	
	private WebElement findCalendarContainer(){
		try {
			//aparentement siempre lo encuentra con .cal ....
			return (new WebDriverWait(webDriver, 2)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cal")));
		} catch (Exception e) {
			return (new WebDriverWait(webDriver, 2)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cal.show-second-month")));
		}
	}
	private boolean shouldGoBack(DateTime date){
		SimpleDateFormat format = new SimpleDateFormat("MMM yyyy",Locale.ENGLISH);
		DateTime firstCalendar = null;
		try {
			firstCalendar = new DateTime( format.parse(this.calendarOne.findElement(By.xpath("header/h2")).getText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getMonthOfYear()<firstCalendar.getMonthOfYear(); 
	}

}
