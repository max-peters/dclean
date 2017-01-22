package database.plugin.plugin;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.text.BadLocationException;
import database.main.UserCancelException;
import database.main.userInterface.ITerminal;
import database.main.userInterface.RequestRegexPattern;
import database.plugin.Command;
import database.plugin.Plugin;
import database.plugin.connector.ExpenseDatabaseConnector;
import database.plugin.element.Expense;
import database.plugin.outputHandler.ExpenseOutputHandler;
import database.services.ServiceRegistry;
import database.services.database.IConnectorRegistry;
import database.services.undoRedo.CommandHandler;
import database.services.undoRedo.command.InsertCommand;

public class ExpensePlugin extends Plugin {
	public ExpensePlugin() throws SQLException {
		super("expense", new ExpenseOutputHandler());
	}

	@Command(tag = "new") public void createRequest() throws InterruptedException, BadLocationException, UserCancelException, SQLException {
		ITerminal terminal = ServiceRegistry.Instance().get(ITerminal.class);
		IConnectorRegistry registry = ServiceRegistry.Instance().get(IConnectorRegistry.class);
		ExpenseDatabaseConnector connector = (ExpenseDatabaseConnector) registry.get(Expense.class);
		String requestResult;
		String name;
		String category;
		Double value;
		LocalDate date;
		requestResult = terminal.request("name", RequestRegexPattern.NAME, connector.nameStringComplete);
		name = connector.nameStringComplete.getCorrespondingString(requestResult);
		requestResult = terminal.request("category", RequestRegexPattern.NAME, connector.categoryStringComplete.getMostUsedString("", name),
			connector.categoryStringComplete);
		category = connector.categoryStringComplete.getCorrespondingString(requestResult);
		value = Double.valueOf(terminal.request("value", RequestRegexPattern.DOUBLE));
		requestResult = terminal.request("date", "DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
		date = requestResult.isEmpty() ? LocalDate.now() : LocalDate.parse(requestResult, DateTimeFormatter.ofPattern("dd.MM.uuuu"));
		CommandHandler.Instance().executeCommand(new InsertCommand(new Expense(name, category, value, date)));
		connector.refreshStringComplete();
	}

	@Override public ExpenseOutputHandler getOutputHandler() {
		return (ExpenseOutputHandler) super.getOutputHandler();
	}
}
