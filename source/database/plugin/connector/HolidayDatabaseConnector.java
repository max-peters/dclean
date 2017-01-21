package database.plugin.connector;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.plugin.element.Holiday;
import database.services.ServiceRegistry;
import database.services.database.IDatabase;
import database.services.database.SQLStatements;

public class HolidayDatabaseConnector extends EventDatabaseConnector<Holiday> {
	@Override public Holiday create(ResultSet resultSet) throws SQLException {
		return new Holiday(resultSet.getString("name"), resultSet.getDate("date").toLocalDate());
	}

	@Override public String selectQuery() throws SQLException {
		return SQLStatements.HOLIDAY_SELECT;
	}

	@Override protected PreparedStatement prepareStatement(Holiday holiday, String sql) throws SQLException {
		PreparedStatement preparedStatement = ServiceRegistry.Instance().get(IDatabase.class).prepareStatement(sql);
		preparedStatement.setString(1, holiday.getName());
		preparedStatement.setDate(2, Date.valueOf(holiday.getDate()));
		preparedStatement.setString(3, "holiday");
		return preparedStatement;
	}
}