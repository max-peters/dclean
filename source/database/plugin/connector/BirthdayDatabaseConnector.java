package database.plugin.connector;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.plugin.element.Birthday;
import database.services.ServiceRegistry;
import database.services.database.IDatabase;
import database.services.database.SQLStatements;

public class BirthdayDatabaseConnector extends EventDatabaseConnector<Birthday> {
	@Override public Birthday create(ResultSet resultSet) throws SQLException {
		return new Birthday(resultSet.getString("name"), resultSet.getDate("date").toLocalDate());
	}

	@Override public String selectQuery() throws SQLException {
		return SQLStatements.BIRTHDAY_SELECT;
	}

	@Override protected PreparedStatement prepareStatement(Birthday birthday, String sql) throws SQLException {
		PreparedStatement preparedStatement = ServiceRegistry.Instance().get(IDatabase.class).prepareStatement(sql);
		preparedStatement.setString(1, birthday.getName());
		preparedStatement.setDate(2, Date.valueOf(birthday.getDate()));
		preparedStatement.setString(3, "birthday");
		return preparedStatement;
	}
}
