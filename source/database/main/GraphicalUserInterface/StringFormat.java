package database.main.GraphicalUserInterface;

import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public enum StringFormat {
	STANDARD, BOLD, ITALIC;
	public String toString() {
		String format = null;
		switch (this) {
			case STANDARD:
				format = "standard";
				break;
			case BOLD:
				format = "bold";
				break;
			case ITALIC:
				format = "italic";
				break;
			default:
				break;
		}
		return format;
	}

	public void initialise(StyledDocument styledDocument) {
		switch (this) {
			case STANDARD:
				break;
			case BOLD:
				StyleConstants.setBold(styledDocument.addStyle(toString(), StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE)), true);
				break;
			case ITALIC:
				StyleConstants.setItalic(styledDocument.addStyle(toString(), StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE)), true);
				break;
			default:
				break;
		}
	}
}