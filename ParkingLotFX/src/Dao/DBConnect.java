package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {	 
	Statement stmt = null;
	private static String url = "jdbc:mysql://www.papademas.net:3307/510fp";
	private static String username = "fp510";
	private static String password = "510";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

//public class DBConnect  {
//
//	protected Connection connection;
//	public Connection getConnection() {
//		return connection;
//	}
//
//	private static String url = "jdbc:mysql://www.papademas.net:3307/510fp";
//	private static String username = "fp510";
//	private static String password = "510";
//
//	public DBConnect() {
//		try {
//			connection = DriverManager.getConnection(url, username, password);
//		} 
//		catch (SQLException e) {
//			System.out.println("Error creating connection to database: " + e);
//			System.exit(-1);
//		}
//	}
	
	public void toggleUpdate(String level,String lot) {
		String query = "update rag_shr_parking_lot  set "+level+"="+"1-"+level+" WHERE level ="+lot;
		
		System.out.println(query);
		try(PreparedStatement stmt = connect().prepareStatement(query)) {
			int rs = stmt.executeUpdate();	   
		}
		catch (SQLException e) {
	   		System.out.println("exception");
		}
	}

	public void CreateTable() {
		try {
			System.out.println("Creating a table in the database...");
			
			stmt = connect().createStatement();
			
			String sql = "CREATE TABLE rag_shr_parking_users " +  "(pid INTEGER not NULL AUTO_INCREMENT," +
			 "username VARCHAR(30)," + " password VARCHAR(30)," +  " firstName VARCHAR(30)," + 
			 " lastName VARCHAR(30)," + "PRIMARY KEY (pid))";
			
			stmt.executeUpdate(sql);
			System.out.println("Table successfully created in the database!!!!!!!");
			
			connect().close();
		}
		catch(SQLException e) {
			System.out.println("Not working");
		}
	}
	public boolean InsertUserData(String username,String password,String fname,String lname) throws SQLException{
		
//		First Creating Table
//		CreateTable();
		
	    //insert statement
	    String query = "insert into rag_shr_parking_users (username, password, firstName, lastName)"
	      + " values (?, ?, ?, ?)";
	    
	    // MySQL insert prepared statement
	    PreparedStatement preparedStmt;
		try {
			preparedStmt = connect().prepareStatement(query);
			preparedStmt.setString (1, username);
		    preparedStmt.setString (2, password);
		    preparedStmt.setString   (3, fname);
		    preparedStmt.setString(4, lname);
		    
		    // execute the prepared statement
		    preparedStmt.execute();
		    return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}