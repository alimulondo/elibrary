package pl.library.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * DataModel Class
 * Use Singleton pattern
 * Create connection with database
 * Field DBTYPE store the type of database
 * 
 * @author Piotr Skurski
 * @version 1.1
 */
class DatabaseModel {
	//instance of DatabaseModel
	private static DatabaseModel _instance = null;
	//type of database
	private static final String DBTYPE = "mysql";
	private static final String CONNECTOR="com.mysql.jdbc.Driver";
	private static final String DATABASE = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASS = "mysql";
	private Connection _conn = null;
	
	/*
	 * Private non-argument constructor
	 */
	private DatabaseModel() {}
	
	/*
	 * Return the instance of the DataModel class
	 * 
	 * @return DatabaseModel
	 */
	static DatabaseModel getInstance() {
		if(_instance == null) 
			_instance = new DatabaseModel();		

		return _instance;
	}
	
	/*
	 * Create connection with database
	 * 
	 * @throws ClassNotFoundException, SQLException
	 */
	void createConnection() throws ClassNotFoundException, SQLException {
		Class.forName(CONNECTOR);
		_conn = DriverManager.getConnection(DATABASE,USER,PASS);
	}
	
	/*
	 * Return connection object
	 * 
	 * @return Connection
	 * @throws ClassNotFoundException, SQLException
	 */
	Connection getConnection() throws ClassNotFoundException, SQLException {
		createConnection();
		return _conn;
	}
	
	/*
	 * Return the type of database: mysql, postgre etc
	 * 
	 * @return String the string with database type
	 */
	String getDbType() {
		return DBTYPE;
	}
	
}

