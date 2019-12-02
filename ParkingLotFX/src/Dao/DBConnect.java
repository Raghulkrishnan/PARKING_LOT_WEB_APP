package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {	 
	Statement stmt = null;
	int i =0;
	private static String url = "jdbc:mysql://www.papademas.net:3307/510fp";
	private static String username = "fp510";
	private static String password = "510";

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	public void ToggleUpdate(String level,String slot) {
		System.out.println(level + " " + slot);
		String query = "update parking_lot SET slot_"+ level + "=" + "1 WHERE level ="+slot;
		
		System.out.println(query);
		try(PreparedStatement stmt = connect().prepareStatement(query)) {
			int rs = stmt.executeUpdate();	   
		}
		catch (SQLException e) {
	   		System.out.println("exception");
		}
	}
	
	public void RemoveLevel() {
//		String query = "DELETE FROM parking_lot ORDER BY level DESC LIMIT 1";
		try {
			stmt = connect().createStatement();
			
			String sql = "DELETE FROM parking_lot ORDER BY level DESC LIMIT 1";
			
			stmt.executeUpdate(sql);
			System.out.println("Table successfully cleared in the database!!!!!!!");
			
			connect().close();
		}
		catch(SQLException e) {
			System.out.println("Unable to remove!");
		}
	}
	
	public void AddLevel() {
		int rowCount = 0;
		try {
			stmt = connect().createStatement();
			
			String sql = "COUNT(*)";
			
			stmt.executeUpdate(sql);
			
			connect().close();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public void CreateUserTable() {
		try {
			System.out.println("Creating a table in the database...");
			
			stmt = connect().createStatement();
			
			String sql = "CREATE TABLE parking_lot_users " +  "(pid INTEGER not NULL AUTO_INCREMENT," +
			 "username VARCHAR(30)," + " password VARCHAR(30)," +  " firstName VARCHAR(30)," + 
			 " lastName VARCHAR(30)," + " isAdmin TINYINT(1)," +  "PRIMARY KEY (pid))";
			
			stmt.executeUpdate(sql);
			System.out.println("Table successfully created in the database!!!!!!!");
			
			connect().close();
		}
		catch(SQLException e) {
			System.out.println("Table already exists");
		}
	}
	
	public void CreateParkingTable() {
		try {
			System.out.println("Creating a parking table in the database...");
			
			stmt = connect().createStatement();
			
			String sql = "CREATE TABLE parking_lot" +  "(pid INTEGER not NULL AUTO_INCREMENT," +
					 "level INT(10)," + " slot_A INT(2)," +  " slot_B INT(2)," + 
					 " slot_C INT(2)," + "slot_D INT(2)," + "PRIMARY KEY (pid))";
			
			stmt.executeUpdate(sql);
			System.out.println("Parking table successfully created in the database!!!!!!!");
			
			connect().close();
		}
		catch(SQLException e) {
//			while(i <= 5) {
//				boolean slot1 = InsertEmptySlots();
//				i++;
//			}	
			System.out.println("Parking table already exists");
		}
	}
	
	public boolean InsertEmptySlots() {
		String query = "insert into parking_lot (level, slot_A, slot_B, slot_C, slot_D)"
			      + " values (?, ?, ?, ?, ?)";
			    
	    // MySQL insert prepared statement
	    PreparedStatement preparedStmt;
		try {
			preparedStmt = connect().prepareStatement(query);
			preparedStmt.setInt(1, i+1);
		    preparedStmt.setInt(2, 0);
		    preparedStmt.setInt(3, 0);
		    preparedStmt.setInt(4, 0);
		    preparedStmt.setInt(5, 0);
		    // execute the prepared statement
		    preparedStmt.execute();
		    
		    return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean InsertUserData(String username,String password,String fname,String lname) throws SQLException{
		
//		First Creating Table
		CreateUserTable();
		
	    //insert statement
	    String query = "insert into parking_lot_users (username, password, firstName, lastName, isAdmin)"
	      + " values (?, ?, ?, ?, ?)";
	    
	    // MySQL insert prepared statement
	    PreparedStatement preparedStmt;
		try {
			preparedStmt = connect().prepareStatement(query);
			preparedStmt.setString(1, username);
		    preparedStmt.setString(2, password);
		    preparedStmt.setString(3, fname);
		    preparedStmt.setString(4, lname);
		    preparedStmt.setInt(5, 0);
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