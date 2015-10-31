package database.plugin.subject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import database.main.PluginContainer;
import database.main.Terminal;
import database.plugin.Command;
import database.plugin.InstancePlugin;

public class SubjectPlugin extends InstancePlugin {
	public SubjectPlugin(PluginContainer pluginContainer) {
		super(pluginContainer, "subject", new SubjectList());
	}

	@Command(tag = "new") public void createRequest() throws InterruptedException, IOException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "[A-ZÖÄÜ].*");
		map.put("tag", "[a-zöäüß]*");
		request(map);
		create(map);
		update();
	}

	@Command(tag = "show") public void showRequest() throws InterruptedException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("show", "(average|" + ((SubjectList) instanceList).getTagsAsRegex() + ")");
		request(map);
		Terminal.printLine(instanceList.output(map));
		Terminal.waitForInput();
	}

	@Command(tag = "add") public void addRequest() throws InterruptedException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("add", ((SubjectList) instanceList).getTagsAsRegex());
		map.put("score", "[0-9]{1,13}(\\.[0-9]*)?");
		map.put("maximum points", "[0-9]{1,13}(\\.[0-9]*)?");
		request(map);
		Subject toChange = ((SubjectList) instanceList).getSubject(map.get("add"));
		toChange.setGrade(Double.parseDouble(map.get("score")), Double.parseDouble(map.get("maximum points")));
		toChange.calcPercent();
		update();
	}
}
