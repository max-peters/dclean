package database.plugin;

import java.util.ArrayList;

public interface Writeable {
	public ArrayList<String> write();

	public void read(String line);
}