package server.mappers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public abstract class Mapper {
	// JDBC driver name and database URL
	protected static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	protected static final String DB_URL = "jdbc:mysql://localhost/assignment3";

	// Database credentials
	protected static final String USER = "root";
	protected static final String PASS = "milkshake11";

	protected Connection conn = null;
	protected Statement stmt = null;

	protected Mapper() throws DataSourceException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
		} catch (Exception e) {
			throw new DataSourceException(e);
		}

	}

	@Override
	public void finalize() throws Throwable {
		stmt.close();
		conn.close();
		super.finalize();
	}

}
