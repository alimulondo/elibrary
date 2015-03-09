package pl.library.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IQuery {
	void disconnect();
	PreparedStatement select(String col, int id) throws SQLException, ClassNotFoundException;
	Object[] loginCheck(String user, String pass);
	void insert(Object[] obj, String table);
	void update(Object[] obj, String table);
	void delete(Object[] obj, String table);
	void deleteAll(String col);
}
