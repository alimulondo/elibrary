package pl.library.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * MysqlQueryModel Class
 * Queries for mysql database
 */
public class MysqlQueryModel implements IQuery {
	//database connection object
	private DatabaseModel _dbModel;
	private Connection _conn = null;
	
	/*
	 * Non-argument constructor
	 * Get instance of database class
	 */
	public MysqlQueryModel() {
		_dbModel = DatabaseModel.getInstance();
	}
	
	/*
	 * Create connect with database
	 * 
	 * @throws ClassNotFoundException, SQLException
	 */
	private void connect() throws ClassNotFoundException, SQLException {
		_conn = _dbModel.getConnection();
	}
	
	/*
	 * Close connection with database
	 */
	public void disconnect() {
		try {
			if(_conn != null)
	            _conn.close();
	    } catch(SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/*
	 * Check if user & password exists in database
	 * 
	 * @param String user	the string with login
	 * @param String pass 	the string with password
	 * @return Object[] 	return array of objects with
	 * 						id number, login & password
	 */
	public Object[] loginCheck(String user, String pass) {
		PreparedStatement stmt = null;
		ResultSet result = null;
		Object[] userParam = new Object[3];
		
		try {
			connect();
		
			stmt = _conn.prepareStatement("select * from users");
			result = stmt.executeQuery();
			while(result.next()) {
				if(result.getString(2).equals(user) && result.getString(3).equals(pass)) {
					userParam[0] = result.getInt(1);
					userParam[1] = result.getString(2);
					userParam[2] = result.getString(3);	
				}
			}
			
			result.close();
		    stmt.close();
		    _conn.close();				
		} catch(SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e);
            e.printStackTrace();
		} catch(ClassNotFoundException e) {
            System.err.println("Brak sterownika: "+e);
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
            try {
                if(_conn != null)
                    _conn.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
		
		//if user & pass don't exists in database
		if(userParam[0] == null) {
			userParam[0] = 0;
			userParam[1] = "none";
			userParam[2] = "none";
		}
		return userParam;
	}
	
	/*
	 * Select statement for .jsp view
	 * 
	 * @param String col	string with column name 
	 * @param int id		user id number
	 * @return PreparedStatement	statement ready to execute
	 */
	public PreparedStatement select(String col, int id) throws SQLException, ClassNotFoundException {
		PreparedStatement stmt = null;
		//prepare statement query 
		String query = null;
		if(col != null) 
			query = "select * from books where books_userid="+id+" order by "+col+";";
		else
			query = "select * from books where books_userid="+id+";";
		
		try {
			connect();
			stmt = _conn.prepareStatement(query);
		    stmt.execute("SET NAMES 'utf8'");			
		} catch(SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e);
            e.printStackTrace();
		} catch(ClassNotFoundException e) {
            System.err.println("Brak sterownika: "+e);
            e.printStackTrace();
        } 
		return stmt;
	}
	
	/*
	 * Insert Method for MySQL database
	 *
	 * @param Object[] obj array of objects
	 * @param String col column name in database
	 */
	public void insert(Object[] obj, String table) {
		PreparedStatement stmt = null;
		//array of strings with data types of each array element
		String[] dataTypes = getFieldsType(obj);
		//string builder with questions marks
		StringBuilder marks = prepMarks(obj);
		//prepare query 
		String query = null;
		if(table.equals("books")) 
			query = "INSERT INTO "+table+" VALUES(default,"+marks+",now());";
		else if(table.equals("users"))
			query = "INSERT INTO "+table+" VALUES(default,"+marks+");";
		
		try {
			connect();
			stmt = _conn.prepareStatement(query);
			stmt = this.setArgs(stmt, obj, dataTypes);
		    stmt.execute("SET NAMES 'utf8'");
		    stmt.executeUpdate();
		    
		    stmt.close();
		    _conn.close();				
		} catch(SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e);
            e.printStackTrace();
		} catch(ClassNotFoundException e) {
            System.err.println("Brak sterownika: "+e);
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
            try {
                if(_conn != null)
                    _conn.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void update(Object[] obj, String table) {
		
	}
	
	public void delete(Object[] obj, String table) {
		PreparedStatement stmt = null;
		//array of strings with data types of each array element
		String[] dataTypes = getFieldsType(obj);
		//prepare query 
		String[] query = new String[2];
		if(table.equals("books")) {
			query[0] = "DELETE FROM "+table+" WHERE bookid=? and books_userid=?;";
			query[1] = null;
		} else if(table.equals("users")) {
			//first delete books associated with user then the user itself
			query[0] = "DELETE FROM books WHERE books_userid=?;";
			query[1] = "DELETE FROM users WHERE userid=?;";
		}
		
		try {
			connect();
			if(query[1] == null) {
				stmt = _conn.prepareStatement(query[0]);	
				stmt = this.setArgs(stmt, obj, dataTypes);
			    stmt.execute("SET NAMES 'utf8'");
			    stmt.executeUpdate();
			} else {
				for(String q: query) {
					stmt = _conn.prepareStatement(q);	
					stmt = this.setArgs(stmt, obj, dataTypes);
				    stmt.execute("SET NAMES 'utf8'");
				    stmt.executeUpdate();
				}
			}
		    
		    stmt.close();
		    _conn.close();				
		} catch(SQLException e) {
            System.err.println("Błąd połączenia z bazą danych: " + e);
            e.printStackTrace();
		} catch(ClassNotFoundException e) {
            System.err.println("Brak sterownika: "+e);
            e.printStackTrace();
        } finally {
            try {
                if(stmt != null)
                    stmt.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
            try {
                if(_conn != null)
                    _conn.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	public void deleteAll(String table) {
		
	}
	
	/*
	 * Take array of objects as argument and return
	 * array of strings where every string represent
	 * the class name of corresponding entry object
	 * 
	 * @param Object[] obj 
	 * @return String[] array of strings with class names
	 */
	private String[] getFieldsType(Object[] obj) {
		String[] types = new String[obj.length];
		for(int i=0; i<obj.length; i++)
			types[i] = obj[i].getClass().getName();
		
		return types;
	}
	
	/*
	 * Check the size of array of objects, create and 
	 * return StringBuilder with N questions marks
	 * 
	 * @param Object[] obj
	 * @return StringBuilder stringbuilder with questions
	 * 						 marks 
	 */
	private StringBuilder prepMarks(Object[] obj) {
		StringBuilder marks = new StringBuilder();		
		int size = obj.length;
		while(size > 1) {
			marks.append("?,");
			size--;
		}
		marks.append("?");
		
		return marks;
	}
	
	/*
	 * Set arguments for prepared statement depend on the length 
	 * of input array of objects and the data type of each object
	 */
	private PreparedStatement setArgs(PreparedStatement stmt,Object[] obj, 
										String[] dataTypes) throws SQLException {
		
		for(int i=0; i<obj.length; i++) {
			switch(dataTypes[i]) {
				case "java.lang.String":
					stmt.setString(i+1, (String) obj[i]);
					break;
				case "java.lang.Integer":
					stmt.setInt(i+1, (Integer) obj[i]);
					break;
				case "java.lang.Double":
					stmt.setDouble(i+1, (Double) obj[i]);
					break;
			}
		}	
		
		return stmt;
	}
	
	/*
	 * ********* TEST *************************
	 */
	public static void main(String[] args) {
		MysqlQueryModel mqm = new MysqlQueryModel();
		Object[] obj = new Object[6];
		obj[0] = 1;
		obj[1] = "moj";
		obj[2] = "moj";
		obj[3] = "moj";
		obj[4] = 44.00;
		obj[5] = "moj";
		mqm.insert(obj, "books");
	}
	
}
