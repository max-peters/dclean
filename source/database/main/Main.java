package database.main;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JOptionPane;
import database.main.userInterface.GraphicalUserInterface;
import database.main.userInterface.Terminal;
import database.plugin.Backup;
import database.plugin.event.EventPlugin;
import database.plugin.expense.ExpensePlugin;
import database.plugin.monthlyExpense.MonthlyExpensePlugin;
import database.plugin.refilling.RefillingPlugin;
import database.plugin.storage.Storage;
import database.plugin.subject.SubjectPlugin;
import database.plugin.task.TaskPlugin;
import database.plugin.utility.UtilityPlugin;

public class Main {
	public static void main(String[] args) {
		try {
			PluginContainer pluginContainer = new PluginContainer();
			GraphicalUserInterface graphicalUserInterface = new GraphicalUserInterface();
			Backup backup = new Backup();
			new Terminal(graphicalUserInterface, pluginContainer);
			WriterReader writerReader = new WriterReader(pluginContainer);
			Administration administration = new Administration(pluginContainer, writerReader, backup);
			Storage storage = new Storage();
			SubjectPlugin subjectPlugin = new SubjectPlugin(storage, backup);
			ExpensePlugin expensePlugin = new ExpensePlugin(storage, backup);
			MonthlyExpensePlugin monthlyExpensePlugin = new MonthlyExpensePlugin(expensePlugin, storage, backup);
			RefillingPlugin refillingPlugin = new RefillingPlugin(expensePlugin, storage, backup);
			TaskPlugin taskPlugin = new TaskPlugin(storage, backup);
			EventPlugin eventPlugin = new EventPlugin(storage, backup);
			UtilityPlugin utilityPlugin = new UtilityPlugin(writerReader, pluginContainer, storage);
			pluginContainer.addPlugin(utilityPlugin);
			pluginContainer.addPlugin(subjectPlugin);
			pluginContainer.addPlugin(expensePlugin);
			pluginContainer.addPlugin(monthlyExpensePlugin);
			pluginContainer.addPlugin(refillingPlugin);
			pluginContainer.addPlugin(taskPlugin);
			pluginContainer.addPlugin(eventPlugin);
			pluginContainer.addPlugin(storage);
			writerReader.read();
			eventPlugin.updateHolidays();
			graphicalUserInterface.setVisible(true);
			Terminal.initialOutput();
			Terminal.printCollectedLines();
			administration.request();
		}
		catch (Throwable e) {
			String stackTrace = "";
			if (e.getClass().equals(InvocationTargetException.class)) {
				e = e.getCause();
			}
			for (StackTraceElement element : e.getStackTrace()) {
				stackTrace = stackTrace + System.getProperty("line.separator") + element;
			}
			JOptionPane.showMessageDialog(null, stackTrace, e.getClass().getName(), JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(0);
		}
	}
}