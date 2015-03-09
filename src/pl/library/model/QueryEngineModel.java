package pl.library.model;

/*
 * QueryEngineModel Class
 * Factory which create concrete instance of
 * database type use in application, e.g.
 * if database type equals "mysql" than the 
 * MysqlQueryModel class is create
 * 
 * @author Piotr Skurski
 * @version 1.1
 */
public class QueryEngineModel {
	
	/*
	 * Create and return object with database type use in application
	 * 
	 * @return IQuery object of concrete database type e.g. mysql
	 */
	public IQuery getDbType() {
		IQuery dbType = null;
		DatabaseModel dbModel = DatabaseModel.getInstance();
		String type = dbModel.getDbType();
		
		if(type.equals("mysql")) {
			dbType = new MysqlQueryModel();
		}
		
		return dbType;
	}
}
