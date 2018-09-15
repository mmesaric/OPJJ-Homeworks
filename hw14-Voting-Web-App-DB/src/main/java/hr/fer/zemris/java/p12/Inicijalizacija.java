package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * This class represents the implementation of a Servlet Context Listener. Its
 * job is to setup initial connection with database after reading properties
 * from WEB-INF/dbsettings.properties file, create connection pool and check if
 * initial tables "Polls" and "PollOptions" are set up. If not, creates them and
 * fills them with rows defined in this class.
 * 
 * @author Marko Mesarić
 *
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

	/**
	 * Query used for creating table "polls".
	 */
	private final String CREATE_POLLS_QUERY = "CREATE TABLE Polls"
			+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + " title VARCHAR(150) NOT NULL,"
			+ " message CLOB(2048) NOT NULL" + ")";

	/**
	 * Query used for creating table "polloptions".
	 */
	private final String CREATE_POLL_OPTIONS_QUERY = "CREATE TABLE PollOptions"
			+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," + " optionTitle VARCHAR(100) NOT NULL,"
			+ " optionLink VARCHAR(150) NOT NULL," + " pollID BIGINT," + " votesCount BIGINT,"
			+ " FOREIGN KEY (pollID) REFERENCES Polls(id)" + " )";

	/**
	 * Queries used for inserting rows containing 2 different polls.
	 */
	private final String INSERT_INTO_POLLS = "INSERT INTO Polls (title, message) VALUES"
			+ " ('Glasanje za omiljeni bend', 'Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako"
			+ " biste glasali!') \n " + "INSERT INTO Polls (title, message) VALUES"
			+ " ('Glasanje za omiljeno piće', 'Od sljedećih pića, koje Vam je najdraže? Kliknite na link kako biste glasali!')";

	/**
	 * Queries used for populating pollOptions table with bands poll options rows.
	 */
	private final String BANDS_POLL_OPTIONS = "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('The Beatles', 'https://www.youtube.com/watch?v=z9ypq6_5bsg',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('The Platters', 'https://www.youtube.com/watch?v=H2di83WAOhU',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('The Beach Boys', 'https://www.youtube.com/watch?v=2s4slliAtQU',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('The Four Seasons', 'https://www.youtube.com/watch?v=y8yvnqHmFds',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('The Marcels', 'https://www.youtube.com/watch?v=qoi3TH59ZEs',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('The Everly Brothers', 'https://www.youtube.com/watch?v=tbU3zdAgiX8',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('The Mamas And The Papas', 'https://www.youtube.com/watch?v=N-aK6JnyFmk',?, 0)\n";

	/**
	 * Queries used for populating pollOptions table with drinks poll options rows.
	 */
	private final String DRINKS_POLL_OPTIONS = "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('Coca-Cola', 'http://www.coca-cola.com/global/',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('Fanta', 'https://www.coca-cola.hr/fanta/hr/home/',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('Sprite', 'https://www.sprite.com/',?, 0)\n"
			+ "INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES"
			+ "		('Schweppes', 'http://www.schweppes.com/',?, 0)";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties")));
		} catch (IOException e) {
			throw new RuntimeException("Exception when trying to read from properties file.", e);
		}

		String connectionURL;
		try {
			connectionURL = loadProperties(sce, properties);
		} catch (NullPointerException e) {
			throw new RuntimeException("Invalid DB parameters given in dbsettings.properties file.", e);
		}

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("Exception when trying to initialiaze pool.", e1);
		}
		cpds.setJdbcUrl(connectionURL);

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

		try {
			Connection connection = cpds.getConnection();

			setupTables("POLLS", CREATE_POLLS_QUERY, connection);
			setupTables("POLLOPTIONS", CREATE_POLL_OPTIONS_QUERY, connection);

			checkTableSize(INSERT_INTO_POLLS, connection);

		} catch (SQLException e) {
			throw new RuntimeException("Exception when retrieving connection and setting up tables.", e);
		}

	}

	/**
	 * Auxiliary method used for checking the current number of rows in table. If
	 * there is less than 2 rows currently stored in table Polls, inserts two new
	 * rows with corresponding pollOptions rows data used in voting web application.
	 * 
	 * @param query
	 *            query to be executed
	 * @param connection
	 *            database connection
	 * @throws SQLException
	 *             in case of sql exception
	 */
	private void checkTableSize(String query, Connection connection) throws SQLException {

		int counter = 0;
		PreparedStatement statement = connection.prepareStatement("select id, title, message from Polls");
		ResultSet rs = statement.executeQuery();

		while (rs != null && rs.next()) {
			counter++;
		}

		if (counter < 2) {
			String[] queries = query.split("\n");

			String singleQuery1 = queries[0];
			Statement insertStatement1 = connection.createStatement();
			insertStatement1.executeUpdate(singleQuery1, Statement.RETURN_GENERATED_KEYS);

			ResultSet keys = insertStatement1.getGeneratedKeys();
			keys.next();
			long returnedID1 = (long) keys.getInt(1);

			insertPollOptions(returnedID1, BANDS_POLL_OPTIONS, connection);

			String singleQuery2 = queries[1];
			Statement insertStatement2 = connection.createStatement();
			insertStatement2.executeUpdate(singleQuery2, Statement.RETURN_GENERATED_KEYS);

			ResultSet keys2 = insertStatement2.getGeneratedKeys();
			keys2.next();
			long returnedID2 = (long) keys2.getInt(1);

			insertPollOptions(returnedID2, DRINKS_POLL_OPTIONS, connection);
		}
	}

	/**
	 * Auxiliary method which processes insert row queries and executes them one by
	 * one
	 * 
	 * @param returnedID
	 *            foreign pollID key under which poll options are to be stored
	 * @param insertQuery
	 *            query to be processed
	 * @param connection
	 *            connection to database
	 * @throws SQLException
	 *             in case of sql exception
	 */
	private void insertPollOptions(long returnedID, String insertQuery, Connection connection) throws SQLException {
		PreparedStatement insertOptionStatement = null;
		String[] pollOptionsInserts = insertQuery.split("\n");
		for (String insert : pollOptionsInserts) {
			insertOptionStatement = connection.prepareStatement(insert);
			insertOptionStatement.setLong(1, returnedID);
			insertOptionStatement.executeUpdate();
		}

	}

	/**
	 * Auxiliary method which checks if there is a table with given name in current
	 * database. Creates it if missing.
	 * 
	 * @param tableName
	 *            table name to be checked
	 * @param query
	 *            query to be executed
	 * @param connection
	 *            connection to database
	 * @throws SQLException
	 *             in case of sql exception
	 */
	private void setupTables(String tableName, String query, Connection connection) throws SQLException {

		DatabaseMetaData databaseMetaData = connection.getMetaData();
		ResultSet tables = databaseMetaData.getTables(null, null, tableName, null);

		if (!tables.next()) {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
	}

	/**
	 * Auxiliary method used for loading properties defined in dbsettings.properties
	 * file
	 * 
	 * @param sce
	 *            servlet context event for this listener
	 * @param properties
	 *            properties file
	 * @return connection url string
	 */
	private String loadProperties(ServletContextEvent sce, Properties properties) {

		String host = properties.getProperty("host");
		String port = properties.getProperty("port");
		String dbName = properties.getProperty("name");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");

		Objects.requireNonNull(host, "Host property can't be null");
		Objects.requireNonNull(port, "Port property can't be null");
		Objects.requireNonNull(dbName, "DB name property can't be null");
		Objects.requireNonNull(user, "User name property can't be null");
		Objects.requireNonNull(password, "Password property can't be null");

		String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password="
				+ password;

		return connectionURL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
