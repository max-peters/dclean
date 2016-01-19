package database.main.userInterface;

import java.util.ArrayList;
import javax.swing.text.BadLocationException;

public class Terminal {
	private static GraphicalUserInterface graphicalUserInterface;

	public Terminal(GraphicalUserInterface graphicalUserInterface) {
		Terminal.graphicalUserInterface = graphicalUserInterface;
	}

	public static void blockInput() {
		graphicalUserInterface.blockInput();
	}

	public static int checkRequest(ArrayList<String> strings) throws InterruptedException, BadLocationException {
		return graphicalUserInterface.checkRequest(strings);
	}

	public static void collectLine(Object output, StringFormat stringFormat) {
		graphicalUserInterface.collectLine(output, stringFormat);
	}

	public static void errorMessage() throws BadLocationException, InterruptedException {
		graphicalUserInterface.errorMessage();
	}

	public static void getLineOfCharacters(char character) throws BadLocationException {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < graphicalUserInterface.getMaximumAmountOfCharactersPerLine(character); i++) {
			builder.append("-");
		}
		printLine(builder.toString(), StringType.REQUEST, StringFormat.STANDARD);
	}

	public static void initialOutput() throws BadLocationException {
		graphicalUserInterface.initialOutput();
	}

	public static void printCollectedLines() throws InterruptedException, BadLocationException {
		Terminal.getLineOfCharacters('-');
		graphicalUserInterface.printCollectedLines();
	}

	public static void printLine(Object object, StringType stringType, StringFormat stringFormat) throws BadLocationException {
		graphicalUserInterface.printLine(object, stringType, stringFormat);
	}

	public static String readLine() throws InterruptedException {
		return graphicalUserInterface.readLine();
	}

	public static void releaseInput() {
		graphicalUserInterface.releaseInput();
	}

	public static String request(String printOut, String regex) throws InterruptedException, BadLocationException {
		return graphicalUserInterface.request(printOut, regex);
	}

	public static void showMessageDialog(Throwable e) {
		graphicalUserInterface.showMessageDialog(e);
	}

	public static void update() throws BadLocationException {
		graphicalUserInterface.update();
	}

	public static void waitForInput() throws InterruptedException {
		graphicalUserInterface.waitForInput();
	}
}
