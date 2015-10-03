package database.plugin.event.birthday;

import database.main.Administration;
import database.main.GraphicalUserInterface;
import database.main.PluginContainer;
import database.main.Terminal;
import database.plugin.event.EventPluginExtention;

public class BirthdayPlugin extends EventPluginExtention {
	public BirthdayPlugin(PluginContainer pluginContainer, Terminal terminal, GraphicalUserInterface graphicalUserInterface, Administration administration) {
		super(pluginContainer, terminal, graphicalUserInterface, administration, "birthday", new BirthdayList());
	}
}