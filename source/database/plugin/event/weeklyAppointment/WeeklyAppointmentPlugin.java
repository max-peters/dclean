package database.plugin.event.weeklyAppointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.text.BadLocationException;
import org.w3c.dom.NamedNodeMap;
import database.main.userInterface.Terminal;
import database.plugin.Backup;
import database.plugin.Storage;
import database.plugin.event.EventPluginExtension;

public class WeeklyAppointmentPlugin extends EventPluginExtension<WeeklyAppointment> {
	public WeeklyAppointmentPlugin(Storage storage, Backup backup) {
		super("weeklyappointment", storage, backup);
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

	@Override public WeeklyAppointment create(NamedNodeMap nodeMap) {
		return new WeeklyAppointment(	nodeMap.getNamedItem("name").getNodeValue(),
										LocalDate.parse(nodeMap.getNamedItem("date").getNodeValue(), DateTimeFormatter.ofPattern("dd.MM.uuuu")),
										nodeMap.getNamedItem("begin").getNodeValue().isEmpty()	? null
																								: LocalTime.parse(	nodeMap.getNamedItem("begin").getNodeValue(),
																													DateTimeFormatter.ofPattern("HH:mm")),
										nodeMap	.getNamedItem("end").getNodeValue()
												.isEmpty() ? null : LocalTime.parse(nodeMap.getNamedItem("end").getNodeValue(), DateTimeFormatter.ofPattern("HH:mm")));
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