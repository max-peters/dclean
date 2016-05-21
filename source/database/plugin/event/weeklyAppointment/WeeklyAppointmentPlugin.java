package database.plugin.event.weeklyAppointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.text.BadLocationException;
import database.main.userInterface.Terminal;
import database.plugin.Backup;
import database.plugin.Storage;
import database.plugin.event.EventPluginExtension;

public class WeeklyAppointmentPlugin extends EventPluginExtension<WeeklyAppointment> {
	public WeeklyAppointmentPlugin(Storage storage, Backup backup) {
		super("weeklyappointment", storage, backup, WeeklyAppointment.class);
	}

	@Override public void add(WeeklyAppointment weeklyAppointment) {
		while (!(!weeklyAppointment.date.isBefore(LocalDate.now()))&& !weeklyAppointment.date.isEqual(LocalDate.now()) | weeklyAppointment.date.isEqual(LocalDate.now())
				&& weeklyAppointment.begin == null
				|| weeklyAppointment.date.isEqual(LocalDate.now())&& weeklyAppointment.begin != null && weeklyAppointment.end == null
					&& !weeklyAppointment.begin.isAfter(LocalTime.now())
				|| weeklyAppointment.date.isEqual(LocalDate.now())&& weeklyAppointment.begin != null && weeklyAppointment.end != null
					&& !weeklyAppointment.end.isAfter(LocalTime.now())) {
			weeklyAppointment.date = weeklyAppointment.date.plusDays(7);
		}
		super.add(weeklyAppointment);
	}

	@Override public void createRequest() throws InterruptedException, BadLocationException {
		String name;
		String temp = "";
		LocalTime begin;
		LocalDate date;
		name = Terminal.request("name", ".+");
		date = LocalDate.parse(Terminal.request("date", "DATE"), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
		temp = Terminal.request("begin", "(TIME)");
		begin = temp.isEmpty() ? null : LocalTime.parse(temp, DateTimeFormatter.ofPattern("HH:mm"));
		if (begin != null) {
			temp = Terminal.request("end", "(TIME)");
			while (!temp.isEmpty() && begin.isBefore(LocalTime.parse(temp, DateTimeFormatter.ofPattern("HH:mm")))) {
				Terminal.errorMessage();
				temp = Terminal.request("end", "(TIME)");
			}
		}
		Terminal.blockInput();
		add(new WeeklyAppointment(name, date, begin, temp.isEmpty() ? null : LocalTime.parse(temp, DateTimeFormatter.ofPattern("HH:mm"))));
	}
}
