package database.plugin;

import java.util.Map;

public abstract class Instance {
	// public boolean equals(Map<String, String> map) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
	// NoSuchMethodException, SecurityException {
	// if (this.equals(this.getClass().getConstructor(Map.class).newInstance(map))) {
	// return true;
	// }
	// else {
	// return false;
	// }
	// }
	@Override public abstract boolean equals(Object object);

	public abstract Map<String, String> getParameter();
}