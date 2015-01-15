package pl.library.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataModel {
	private static DataModel _instance = null;
	private Connection _conn;
	private static final String CONNECTOR="com.mysql.jdbc.Driver";
	private static final String DATABASE = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASS = "mysql";
	
	private DataModel() {}
	
	public void createConnection() throws ClassNotFoundException, SQLException {
		Class.forName(CONNECTOR);
		_conn = DriverManager.getConnection(DATABASE,USER,PASS);
	}
	
	public static DataModel getInstance() {
		if(_instance == null) 
			_instance = new DataModel();		

		return _instance;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		createConnection();
		return _conn;
	}
	
	public static void main(String[] args) {
		DataModel dm = DataModel.getInstance();
		Connection conn = null;
		
		try {
			  conn = dm.getConnection();

		      Statement stmt = conn.createStatement();
		      
		      String sql = "INSERT INTO books VALUES (default, 'Zara', 'Ali', 18, 'good', now());";
		      stmt.executeUpdate(sql);
		      
		      conn.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		try {
		    conn = dm.getConnection();
			PreparedStatement stmt = conn.prepareStatement("select * from books");
			ResultSet result = stmt.executeQuery();
			while(result.next()) {
				System.out.println(result.getInt(1) + " " +
									result.getString(3) + " "+
									result.getString(3) + " "+
									result.getDouble(4) + " "+
									result.getString(5) + " "+
									result.getDate(6));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}

