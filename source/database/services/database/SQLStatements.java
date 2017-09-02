package database.services.database;

public class SQLStatements {
	public static final String APPOINTMENT_DELETE = "DELETE FROM appointment WHERE name=? AND beginDate=? AND beginTime=? AND endDate=? AND endTime=? AND daysTilRepeat=? AND spezification=?;";
	public static final String APPOINTMENT_INSERT = "INSERT INTO appointment VALUES (?,?,?,?,?,?,?);";
	public static final String APPOINTMENT_SELECT = "SELECT * FROM appointment;";
	public static final String BIRTHDAY_SELECT = "SELECT * FROM event where type='birthday';";
	public static final String DAY_SELECT = "SELECT * FROM event where type='day';";
	public static final String EVENT_DELETE = "DELETE FROM event WHERE name=? AND date=? AND type=?;";
	public static final String EVENT_INSERT = "INSERT INTO event VALUES (?,?,?);";
	public static final String EXPENSE_DELETE = "DELETE FROM expense WHERE name=? AND category=? AND date=? AND value=? AND currency=? AND exchangerate=?;";
	public static final String EXPENSE_INSERT = "INSERT INTO expense VALUES (?,?,?,?,?,?);";
	public static final String EXPENSE_SELECT = "SELECT * FROM expense ORDER BY date;";
	public static final String EXPENSE_SELECT_ALL = "SELECT * FROM expenseall";
	public static final String EXPENSE_SELECT_ALL_LENGTH = "SELECT MAX(LENGTH(name)) AS namelength, MAX(LENGTH(CAST(sum AS DECIMAL (18 , 2 )))) AS sumlength FROM expenseall;";
	public static final String EXPENSE_SELECT_AVERAGE = "SELECT SUM(value / exchangerate) as sum FROM expense WHERE ((MONTH(date) != MONTH(NOW())) OR (YEAR(date) != YEAR(NOW())))";
	public static final String EXPENSE_SELECT_CATEGORY = "SELECT * FROM expensecategories";
	public static final String EXPENSE_SELECT_CATEGORY_LENGTH = "SELECT MAX(LENGTH(category)) AS categorylength, MAX(LENGTH(CAST(sum AS DECIMAL (18 , 2 )))) AS sumlength, MAX(LENGTH(CAST(percentage AS DECIMAL (18 , 2 )))) AS percentagelength FROM expensecategories;";
	public static final String EXPENSE_SELECT_CONTAINS = "SELECT * FROM expense WHERE name=? AND category=? AND date=? AND value=? AND currency=? AND exchangerate=?;";
	public static final String EXPENSE_SELECT_MONTH = "SELECT DATE_FORMAT(date,'%d/%m') AS date, name, (value / exchangerate) AS value FROM expense WHERE (MONTH(date) = MONTH(?)) AND (YEAR(date) = YEAR(?)) order by expense.date;";
	public static final String EXPENSE_SELECT_MONTHS = "SELECT * FROM expensemonths;";
	public static final String EXPENSE_SELECT_STRINGCOMPLETE_CATEGORY = "SELECT category AS first, name AS second, COUNT(category) AS count FROM expense GROUP BY name, category;";
	public static final String EXPENSE_SELECT_STRINGCOMPLETE_NAME = "SELECT name AS first, '' AS second, COUNT(name) AS count FROM expense GROUP BY name;";
	public static final String EXPENSE_SELECT_TOTAL = "SELECT SUM(value / exchangerate) AS total FROM expense";
	public static final String FREQUENTNAMES_INSERT = "INSERT INTO frequentnames VALUES (?, ?, 0) ON DUPLICATE KEY UPDATE count = count + 1";
	public static final String FREQUENTNAMES_SELECT_STRINGCOMPLETE = "SELECT name AS first, '' AS second, count FROM frequentnames WHERE type=?";
	public static final String HOLIDAY_SELECT = "SELECT * FROM event where type='holiday';";
	public static final String REFILLING_DELETE = "DELETE FROM refilling WHERE cost=? AND date=? AND distance=? AND refuelAmount=?;";
	public static final String REFILLING_INSERT = "INSERT INTO refilling VALUES (?,?,?,?);";
	public static final String REFILLING_SELECT = "SELECT * from refilling";
	public static final String REFILLING_SELECT_ALL = "SELECT * from refillingall";
	public static final String REFILLING_SELECT_INITIAL_OUTPUT = "SELECT COUNT(*) as count, SUM(distance) as distance, SUM(refuelAmount) as refuelAmount, AVG(average) as average from refillingall;";
	public static final String REPETITITVEEXPENSE_DELETE = "DELETE FROM repetitiveexpense WHERE name=? AND category=? AND value=? AND date=? AND executionDay=? AND repeatInterval=?;";
	public static final String REPETITITVEEXPENSE_INSERT = "INSERT INTO repetitiveexpense VALUES (?,?,?,?,?,?);";
	public static final String REPETITITVEEXPENSE_SELECT = "SELECT * FROM repetitiveexpense;";
	public static final String SUBJECT_DELETE = "DELETE FROM subject WHERE name=? AND score=? AND maxPoints=? AND counter=?;";
	public static final String SUBJECT_INSERT = "INSERT INTO subject VALUES (?,?,?,?);";
	public static final String SUBJECT_SELECT = "SELECT * FROM subject;";
	public static final String SUBJECT_SELECT_ALL = "SELECT *, score/maxPoints * 100 AS percent FROM subject order by counter;";
	public static final String SUBJECT_SELECT_AVERAGE = "SELECT * FROM subjectaverage;";
	public static final String TASK_DELETE = "DELETE FROM task WHERE name=?;";
	public static final String TASK_INSERT = "INSERT INTO task VALUES (?);";
	public static final String TASK_SELECT = "SELECT * FROM task;";
}
