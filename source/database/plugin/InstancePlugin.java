package database.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Map;
import javax.swing.text.BadLocationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import database.main.userInterface.StringFormat;
import database.main.userInterface.StringType;
import database.main.userInterface.Terminal;
import database.plugin.storage.Storage;

public abstract class InstancePlugin<T extends Instance> extends Plugin {
	protected OutputFormatter<T>	formatter;
	protected LinkedList<T>			list;
	private Storage					storage;

	public InstancePlugin(String identity, Storage storage, OutputFormatter<T> formatter) {
		super(identity);
		this.list = new LinkedList<T>();
		this.storage = storage;
		this.formatter = formatter;
	}

	public void add(T instance) {
		list.add(instance);
	}

	public void clearList() {
		list.clear();
	}

	public abstract T create(Map<String, String> parameter);

	public void createAndAdd(Map<String, String> parameter) {
		add(create(parameter));
	}

	public Iterable<T> getIterable() {
		return list;
	}

	@Override public void initialOutput() throws BadLocationException {
		String initialOutput = formatter.getInitialOutput(list);
		if (!initialOutput.isEmpty()) {
			Terminal.printLine(getIdentity() + ":", StringType.MAIN, StringFormat.BOLD);
			Terminal.printLine(initialOutput, StringType.MAIN, StringFormat.STANDARD);
		}
	}

	@Override public void print(Document document, Element element) {
		for (T instance : list) {
			Element entryElement = document.createElement("entry");
			instance.insertParameter(entryElement);
			element.appendChild(entryElement);
		}
		Element entryElement = document.createElement("display");
		entryElement.setAttribute("boolean", String.valueOf(getDisplay()));
		element.appendChild(entryElement);
	}

	@Override public void read(String nodeName, Map<String, String> parameter) {
		if (nodeName.equals("entry")) {
			createAndAdd(parameter);
		}
		else if (nodeName.equals("display")) {
			setDisplay(Boolean.valueOf(parameter.get("boolean")));
		}
	}

	public void remove(Instance toRemove) throws BadLocationException {
		list.remove(toRemove);
		update();
	}

	@Command(tag = "show") public void show() throws InterruptedException, BadLocationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String command = Terminal.request("show", getCommandTags(formatter.getClass()));
		for (Method method : formatter.getClass().getMethods()) {
			if (method.isAnnotationPresent(Command.class) && method.getAnnotation(Command.class).tag().equals(command)) {
				Object output = method.invoke(formatter, list);
				Terminal.getLineOfCharacters('-');
				Terminal.printLine(output, StringType.SOLUTION, StringFormat.STANDARD);
				Terminal.waitForInput();
			}
		}
	}

	@Command(tag = "store") public void store() throws BadLocationException, InterruptedException {
		storage.store(this);
	}
}
