package atendaservices.dao.util;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import atendaservices.exceptions.DataException;

public class ConnectionManager {
//	private static final Logger logger=Logger.getLogger(ConnectionManager.class.getName());
	private static final Logger logger=Logger.getAnonymousLogger();
	private static final ResourceBundle bundleConfig= ResourceBundle.getBundle("config");
	private static final String DB_USER=bundleConfig.getString("jdbc.user");
	private static final String DB_URL = bundleConfig.getString("jdbc.url");
	private static final String DB_PASSWORD = bundleConfig.getString("jdbc.password");
	
	private static final String JDBC_DRIVER = bundleConfig.getString("jdbc.driver.classname");
	
	
	static {
		
		// Register JDBC driver	
		try {		
			Class.forName(JDBC_DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, e.getMessage());
		}

	}

	
	public static final Connection getConnection() throws DataException{
		try {
			return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		}catch(SQLException e) {
			logger.info("no hay connection: " + e.getMessage());
		}
		return null;
	}
	
	public static final void closeConnection (Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			logger.info("la connection no cerro");
		}
	}
	
	public static final void closeResultSet (ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException e) {
			logger.info("el resultSet no cerro");
		}
	}
	
	public static final void closePreparedStatement (PreparedStatement preparedStatement) throws DataException{
		try {
			preparedStatement.close();
		} catch (SQLException e) {
			logger.info("la preparedStatement no cerro");
		}
	}
	
	public static void closeConnection(Connection connection, boolean commit) throws DataException {
	        try {
	            if (connection != null) {
	                if (commit) {
	                	connection.commit();
	                } else {
	                	connection.rollback();
	                }
	                connection.close();
	            }
	        } catch (SQLException e) {
	            logger.info("la conexion no cerro");
	        }
	}
}