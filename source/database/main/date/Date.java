package database.main.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class Date implements Comparable<Date> {
	public Day		day;
	public Month	month;
	public Year		year;

	public Date(String date) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		String currentDate = format.format(calendar.getTime());
		String dateSplitResult[] = currentDate.split(Pattern.quote("."));
		String inputSplitResult[] = date.split(Pattern.quote("."));
		int counter = 0;
		for (int i = 0; i < date.length(); i++) {
			if (String.valueOf(date.charAt(i)).matches(Pattern.quote("."))) {
				counter++;
			}
		}
		if (date.length() == 0) {
			year = new Year(Integer.parseInt(dateSplitResult[2]));
			month = year.months[Integer.parseInt(dateSplitResult[1]) - 1];
			day = month.days[Integer.parseInt(dateSplitResult[0]) - 1];
		}
		else if (counter == 2) {
			year = new Year(Integer.parseInt(inputSplitResult[2]));
			month = year.months[Integer.parseInt(inputSplitResult[1]) - 1];
			day = month.days[Integer.parseInt(inputSplitResult[0]) - 1];
		}
		else if (counter == 1) {
			year = new Year(Integer.parseInt(dateSplitResult[2]));
			month = year.months[Integer.parseInt(inputSplitResult[1]) - 1];
			day = month.days[Integer.parseInt(inputSplitResult[0]) - 1];
		}
		else if (counter == 0) {
			year = new Year(Integer.parseInt(dateSplitResult[2]));
			month = year.months[Integer.parseInt(dateSplitResult[1]) - 1];
			day = month.days[Integer.parseInt(inputSplitResult[0]) - 1];
		}
	}

	public String toString() {
		return String.format("%2s", day.counter).replace(' ', '0') + "." + String.format("%2s", month.counter).replace(' ', '0') + "." + String.format("%4s", year.counter).replace(' ', '0');
	}

	public int compareTo(Date date) {
		return calculateDaySum(this) - calculateDaySum(date);
	}

	private int calculateDaySum(Date date) {
		int sum = 0;
		for (int i = 0; i < date.month.counter - 1; i++) {
			for (int j = 0; j < date.year.months[i].days.length; j++) {
				sum++;
			}
		}
		for (int j = 0; j < date.day.counter; j++) {
			sum++;
		}
		for (int i = 1900; i < date.year.counter; i++) {
			Year currentYear = new Year(i);
			sum = currentYear.dayCount + sum;
		}
		return sum;
	}

	public static Date getDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		return new Date(format.format(calendar.getTime()));
	}

	public static String getDateAsString() {
		Calendar calendar = Calendar.getInstance();
		calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		return format.format(calendar.getTime());
	}

	public static boolean testDateString(String dateInformation) {
		try {
			new Date(dateInformation);
		}
		catch (Throwable e) {
			return false;
		}
		return true;
	}
}