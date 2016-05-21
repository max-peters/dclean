package database.plugin.event;

import java.time.LocalDate;
import database.plugin.Instance;

public abstract class Event extends Instance {
	public LocalDate	date;
	public String		name;

	public Event(String name, LocalDate date) {
		this.name = name;
		this.date = date;
	}

	public LocalDate updateYear() {
		LocalDate currentDate = LocalDate.now();
		if (currentDate.getMonthValue() > date.getMonthValue() || currentDate.getMonthValue() == date.getMonthValue() && currentDate.getDayOfMonth() > date.getDayOfMonth()) {
			return date.withYear(currentDate.getYear() + 1);
		}
		else {
			return date.withYear(currentDate.getYear());
		}
	}

	protected abstract String getAdditionToOutput();
}
