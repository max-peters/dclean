package database.plugin.event.appointment;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Element;
import database.main.date.Date;
import database.main.date.Time;
import database.plugin.event.Event;

public class Appointment extends Event {
	public Time	begin;
	public Time	end;

	public Appointment(String name, Date date, Time begin, Time end) {
		super(name, date);
		this.begin = begin;
		this.end = end;
	}

	@Override public Map<String, String> getParameter() {
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.putAll(super.getParameter());
		parameter.put("begin", begin.toString());
		parameter.put("end", end.toString());
		return parameter;
	}

	@Override public void insertParameter(Element element) {
		super.insertParameter(element);
		element.setAttribute("begin", begin.toString());
		element.setAttribute("end", end == null ? "" : end.toString());
	}

	@Override public Date updateYear() {
		return date;
	}

	@Override protected String appendToOutput() {
		String string = "";
		if (begin != null) {
			string = "[" + begin + " UHR";
			if (end != null) {
				string += " to " + end + " UHR]";
			}
			else {
				string += "]";
			}
		}
		return string;
	}
}
