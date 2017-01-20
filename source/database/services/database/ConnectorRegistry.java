package database.services.database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ConnectorRegistry {
	private Map<Class<?>, IDatabaseConnector<?>> map;

	public ConnectorRegistry() {
		map = new HashMap<>();
	}

	public <I> IDatabaseConnector<I> get(Class<I> type) throws SQLException {
		return (IDatabaseConnector<I>) map.get(type);
	}

	public <I> void register(Class<I> type, IDatabaseConnector<I> connector) {
		map.put(type, connector);
	}
}
