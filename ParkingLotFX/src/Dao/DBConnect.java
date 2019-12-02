package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public void AddLevel() {
		String id = GetMaxId();
		int idInt = Integer.parseInt(id);
		System.out.println(id);
		if (!id.equals("error")) {
			String query = "INSERT INTO parking_lot(level, slot_A, slot_B, slot_C, slot_D) VALUES (" + (idInt + 1)
					+ ",'0','0','0','0')";
			System.out.println(query);
			try (PreparedStatement stmt = connect().prepareStatement(query)) {
				int rs = stmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println("exception");
			}
		}
	}

	public String GetMaxId() {
		String query1 = "SELECT MAX(level) from parking_lot";
		System.out.println(query1);
		try (PreparedStatement stmt = connect().prepareStatement(query1)) {
			System.out.println("connected");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maxid = rs.getString(1);
				return maxid;
			}
		} catch (SQLException e) {
			System.out.println("Error" + e.getMessage());
		}
		return "error";
	}
	
	public int DeletionAllowed() {
		String id = GetMaxId();
		int idInt = Integer.parseInt(id);
		String query = "SELECT * from parking_lot  where level =" + idInt;
		System.out.println(query);
		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int ide = rs.getInt(2);
				int a = rs.getInt(3);
				int b = rs.getInt(4);
				int c = rs.getInt(5);
				int d = rs.getInt(6);
				System.out.print("Lvl" + ide + " A" + a + " B" + b + " C" + c + " D" + d );
				if (a + b + c + d  == 0)
					return 1;
				else
					return 0;
			}
		} catch (SQLException e) {
			System.out.println("exception");
		}
		return -1;
	}
	
	public void RemoveLastLevel() {
		String id = GetMaxId();
		int idInt = Integer.parseInt(id);
		System.out.println(id);
		if (!id.equals("error")) {
			String query = "Delete from parking_lot where level =" + idInt;
			System.out.println(query);
			try (PreparedStatement stmt = connect().prepareStatement(query)) {

				int rs = stmt.executeUpdate();

			} catch (SQLException e) {
				System.out.println("exception");
			}
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