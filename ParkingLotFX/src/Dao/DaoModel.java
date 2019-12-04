package Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import Dao.DBConnect;

public class DaoModel {
	//Declare DB objects 
	DBConnect conn = null;
	Statement stmt = null;
	int i = 0;
	
	PreparedStatement p_stmt = null;

	// constructor
	public DaoModel() { 
		//create db object instance
		conn = new DBConnect();
	}
	

	public void CreateUserTable() {
		try {
			System.out.println("Creating a table in the database...");
			
			stmt = conn.connect().createStatement();
			
			String sql = "CREATE TABLE parking_slot_users " +  "(id INTEGER not NULL AUTO_INCREMENT," +
			 "username VARCHAR(30)," + " password VARCHAR(30)," +  " firstName VARCHAR(30)," + 
			 " lastName VARCHAR(30)," + " isAdmin TINYINT(1)," +  "PRIMARY KEY (id))";
			
			stmt.executeUpdate(sql);
			System.out.println("Table successfully created in the database!!!!!!!");
			
			conn.connect().close();
		}
		catch(SQLException e) {
			System.out.println("Table already exists");
		}
	}
	
	public void CreateParkingTable() {
		try {
			System.out.println("Creating a parking table in the database...");
			
			stmt = conn.connect().createStatement();
			
			String sql = "CREATE TABLE parking_slots" +  "(pid INTEGER not NULL AUTO_INCREMENT," +
					 "level INT(10)," + "A INT(2)," +  "B INT(2)," + 
					 "C INT(2)," + "D INT(2)," + "PRIMARY KEY (pid))";
			
			stmt.executeUpdate(sql);
			System.out.println("Parking table successfully created in the database!!!!!!!");

			conn.connect().close();
		}
		catch(SQLException e) {
//			while(i <= 5) {
//				InsertEmptySlots();
//				i++;
//			}
			System.out.println("Parking table already exists");
		}
	}
	
	public void CreateBookingsTable() {
		try {
			System.out.println("Creating a booking table in the database...");
			
			stmt = conn.connect().createStatement();
			
			String sql = "CREATE TABLE parking_slot_bookings" +  "(bookingid INTEGER not NULL AUTO_INCREMENT," +
					 "userId INT(10)," + "level INT(2)," +  "slot VARCHAR(2)," + "PRIMARY KEY (bookingid))";
			
			stmt.executeUpdate(sql);
			System.out.println("booking table successfully created in the database!!!!!!!");

			conn.connect().close();
		}
		catch(SQLException e) {
			System.out.println("Booking table already exists");
		}
	}
	
	public boolean InsertEmptySlots() {
		String query = "insert into parking_slots (level, A, B, C, D)"
			      + " values (?, ?, ?, ?, ?)";
			    
	    // MySQL insert prepared statement
	    PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.connect().prepareStatement(query);
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
		
		System.out.println("inserting.............");
		
//		First Creating Table
		CreateUserTable();
		
	    //insert statement
	    String query = "insert into parking_slot_users (username, password, firstName, lastName, isAdmin)"
	      + " values (?, ?, ?, ?, ?)";
	    
	    // MySQL insert prepared statement
	    PreparedStatement preparedStmt;
		try {
			preparedStmt = conn.connect().prepareStatement(query);
			preparedStmt.setString(1, username);
		    preparedStmt.setString(2, password);
		    preparedStmt.setString(3, fname);
		    preparedStmt.setString(4, lname);
		    preparedStmt.setInt(5, 0);//   0 is user. 1 is admin
		    
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