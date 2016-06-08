package database.plugin.event.multiDayAppointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import javax.swing.text.BadLocationException;
import database.main.userInterface.Terminal;
import database.plugin.Storage;
import database.plugin.backup.BackupService;
import database.plugin.event.Event;
import database.plugin.event.EventPluginExtension;
import database.plugin.event.appointment.Appointment;

public class MultiDayAppointmentPlugin extends EventPluginExtension<MultiDayAppointment> {
	public MultiDayAppointmentPlugin(Storage storage) {
		super("multidayappointment", storage, MultiDayAppointment.class);
	}

	@Override public void add(MultiDayAppointment multiDayAppointment) {
		if (!multiDayAppointment.date.isBefore(LocalDate.now()) || !multiDayAppointment.lastDay.isBefore(LocalDate.now())) {
			super.add(multiDayAppointment);
		}
	}

	@Override public void createRequest() throws InterruptedException, BadLocationException {
		String name;
		String temp = "";
		LocalTime begin;
		LocalTime end;
		LocalDate firstDay;
		LocalDate lastDay;
		name = Terminal.request("name", ".+");
		firstDay = LocalDate.parse(Terminal.request("first day", "DATE"), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
		temp = Terminal.request("begin", "(TIME)");
		begin = temp.isEmpty() ? null : LocalTime.parse(temp, DateTimeFormatter.ofPattern("HH:mm"));
		lastDay = LocalDate.parse(Terminal.request("last day", "DATE"), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
		while (!lastDay.isAfter(firstDay)) {
			Terminal.errorMessage();
			lastDay = LocalDate.parse(Terminal.request("last day", "DATE"), DateTimeFormatter.ofPattern("dd.MM.uuuu"));
		}
		temp = Terminal.request("end", "(TIME)");
		end = temp.isEmpty() ? null : LocalTime.parse(temp, DateTimeFormatter.ofPattern("HH:mm"));
		MultiDayAppointment multiDayAppointment = new MultiDayAppointment(name, firstDay, begin, lastDay, end);
		add(multiDayAppointment);
		BackupService.backupCreation(multiDayAppointment, this);
	}

	public List<Event> getEvents(LocalDate date) {
		List<Event> eventList = new LinkedList<Event>();
		for (MultiDayAppointment event : getIterable()) {
			if ((event.date.isBefore(date) || event.date.isEqual(date)) && (event.lastDay.isAfter(date) || event.lastDay.isEqual(date))) {
				eventList.add(new Appointment(event.name, date, null, null));
			}
		}
		return eventList;
	}
}
