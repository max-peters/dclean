package database.services.settings;

import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import com.google.gson.Gson;

import database.plugin.expense.Currency;
import database.services.ServiceRegistry;
import database.services.writerReader.IWriterReader;

public class SettingsProvider implements ISettingsProvider {
	private InternalParameters internalParameters;

	public SettingsProvider() {
		internalParameters = new InternalParameters();
	}

	@Override
	public InternalParameters getInternalParameters() {
		return internalParameters;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(Node node) throws ParserConfigurationException, DOMException {
		switch (node.getNodeName()) {
			case "displayLogger":
				internalParameters.displayLogger = Boolean.valueOf(node.getTextContent());
				break;
			case "eventDisplayRange":
				internalParameters.eventDisplayRange = Integer.valueOf(node.getTextContent());
				break;
			case "revertStackSize":
				internalParameters.revertStackSize = Integer.valueOf(node.getTextContent());
				break;
			case "calendarElementPriority":
				internalParameters.calendarElementPriorityList = new Gson().fromJson(node.getTextContent(),
						new LinkedList<>().getClass());
				break;
			case "defaultExchangeRates":
				internalParameters.defaultExchangeRates = new Gson().fromJson(node.getTextContent(),
						new HashMap<String, Double>().getClass());
				break;
			case "currentCurrency":
				internalParameters.currentCurrency = Currency.valueOf(node.getTextContent());
				break;
		}
	}

	@Override
	public void write() {
		IWriterReader writerReader = ServiceRegistry.Instance().get(IWriterReader.class);
		writerReader.add("settings", "displayLogger", String.valueOf(internalParameters.displayLogger));
		writerReader.add("settings", "eventDisplayRange", String.valueOf(internalParameters.eventDisplayRange));
		writerReader.add("settings", "revertStackSize", String.valueOf(internalParameters.revertStackSize));
		writerReader.add("settings", "calendarElementPriority",
				new Gson().toJson(internalParameters.calendarElementPriorityList));
		writerReader.add("settings", "defaultExchangeRates",
				new Gson().toJson(internalParameters.defaultExchangeRates));
		writerReader.add("settings", "currentCurrency", internalParameters.currentCurrency.toString());
	}
}
