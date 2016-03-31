package database.plugin.refilling;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.text.BadLocationException;
import database.main.date.Date;
import database.plugin.Command;
import database.plugin.InstancePlugin;
import database.plugin.expense.ExpensePlugin;
import database.plugin.storage.Storage;

public class RefillingPlugin extends InstancePlugin<Refilling> {
	private ExpensePlugin expensePlugin;

	public RefillingPlugin(ExpensePlugin expensePlugin, Storage storage) {
		super("refilling", storage, new RefillingOutputFormatter());
		this.expensePlugin = expensePlugin;
	}

	@Override public Refilling create(Map<String, String> parameter) {
		return new Refilling(	Double.valueOf(parameter.get("distance")), Double.valueOf(parameter.get("refuelAmount")), Double.valueOf(parameter.get("cost")),
								new Date(parameter.get("date")));
	}

	@Override public void createAndAdd(Map<String, String> refillingParameter) {
		super.createAndAdd(refillingParameter);
		Map<String, String> expenseParameter = new HashMap<String, String>();
		expenseParameter.put("name", "Auto - Tankstelle");
		expenseParameter.put("category", "Fahrtkosten");
		expenseParameter.put("value", refillingParameter.get("cost"));
		expenseParameter.put("date", refillingParameter.get("date"));
		expensePlugin.createAndAdd(expenseParameter);
	}

	@Command(tag = "new") public void createRequest() throws InterruptedException, BadLocationException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("refuelAmount", "[0-9]{1,13}(\\.[0-9]*)?");
		map.put("cost", "[0-9]{1,13}(\\.[0-9]*)?");
		map.put("distance", "[0-9]{1,13}(\\.[0-9]*)?");
		map.put("date", null);
		request(map);
		createAndAdd(map);
		update();
	}
}
