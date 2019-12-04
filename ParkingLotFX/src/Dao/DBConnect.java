package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public void ToggleUpdate(String level,String slot) {
		String query = "update parking_slots set " + level + "=" + "1-" + level + " WHERE level =" + slot;

		try(PreparedStatement stmt = connect().prepareStatement(query)) {
			int rs = stmt.executeUpdate();	   
		}
		catch (SQLException e) {
	   		System.out.println("Exception while updating using ToggleUpdate function");
		}
	}
	
	public boolean CheckLevelExists(String level) {
		String lastLevel = GetLastLevel();
		
		int levelNumber = Integer.parseInt(level);
		int lastLevelNumber = Integer.parseInt(lastLevel);
		
		if(levelNumber <= lastLevelNumber)
			return true;
		else
			return false;
		
	}	

	public boolean OpenToBook(String level, String slot) {
		String returnVal = "";
		String query = "SELECT " + level + " FROM parking_slots WHERE level = " + slot;

		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				returnVal = rs.getString(1);
			}

		} 
		catch (SQLException e) {
			System.out.println("Exception while executing function:- " + e.getMessage());
		}
		
		if (Integer.parseInt(returnVal) == 0)
			return true;
		else
			return false;
		
	}

	public int OneBookingPerUserCheck(int userId) {
		int count = 0;
		String query = "SELECT COUNT(*) FROM parking_slot_bookings WHERE userId = " + userId;
		
		System.out.println(query);
		
		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				count = rs.getInt(1);
			}

		} 
		catch (SQLException e) {
			System.out.println("Exception while checking if the user has already booked a slot or not.");
		}
		return count;
	}
	
	public boolean BookSlot(String lvl, String slot, int userId) {
		
		ToggleUpdate(lvl, slot);
		String query = "INSERT into parking_slot_bookings(userId,level,slot) values(" + userId + "," + slot + ",'" + lvl + "')";

		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			int rs = stmt.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
			System.out.println("Exception while booking a slot: " + e.getMessage());
		}
		return false;
	}

	public String GetBookedSlot(int id) {
		
		String query = "Select level,slot from parking_slot_bookings where userId =" + id;

		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String level = rs.getString(1);
				String lot = rs.getString(2);
				
//				return level and lot as a string
				return (level + lot);
			}
			
		} 
		catch (SQLException e) {
			System.out.println("Exception while retrieving the slot booked: " + e.getMessage());
		}
		
		return "No records";
	}

	public boolean ConfirmBookingCancellation(int user_id) {
		String query = "DELETE from parking_slot_bookings where userId =" + user_id;

		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			int rs = stmt.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
			System.out.println("Exception while cancelling the booking: " + e.getMessage());
		}
		
		return false;
	}
	
	//Admin can change the status of booking from booked to not booked and vice versa if required
	public boolean RevertUserBooking(String level, String slot) {

		String query = "DELETE from parking_slot_bookings where slot = ?  AND level ="+slot;

		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			stmt.setString(1,level);
			int rs = stmt.executeUpdate();
			return true;
		} 
		catch (SQLException e) {
			System.out.println("Exception while reverting booking status: " + e.getMessage());
		}
		return false;
	}
	
	public void AddNewLevel() {
		
		String id = GetLastLevel();
		int levelNumber = Integer.parseInt(id);
		
		if (!id.equals("error")) {
			String query = "INSERT INTO parking_slots(level, A, B, C, D) VALUES (" + (levelNumber + 1)
					+ ",'0','0','0','0')";
			

			try (PreparedStatement stmt = connect().prepareStatement(query)) {
				int rs = stmt.executeUpdate();
			} 
			catch (SQLException e) {
				System.out.println("Exception occured while adding a level to the parking lot table.");
			}
		}
	}
	
	public String GetLastLevel() {
		String query = "SELECT MAX(level) from parking_slots";

		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String maxid = rs.getString(1);
				return maxid;
			}
			
		} 
		catch (SQLException e) {
			System.out.println("Exception occured: " + e.getMessage());
		}
		return "error";
	}
	
	public int DeletionAllowed() {
		String id = GetLastLevel();
		int levelNumber = Integer.parseInt(id);
		
		String query = "SELECT * from parking_slots  where level =" + levelNumber;

		try (PreparedStatement stmt = connect().prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int lvl = rs.getInt(2);
				int a = rs.getInt(3), b = rs.getInt(4), c = rs.getInt(5), d = rs.getInt(6);
				
				System.out.print("Level " + lvl + " A" + a + " B" + b + " C" + c + " D" + d );
				
//				checking if none of the slots are filled
				if (a + b + c + d  == 0)
					return 1;
				else
					return 0;
			}
		} 
		catch (SQLException e) {
			System.out.println("Exception while checking if deletion is possible");
		}
		return -1;
	}
	
	public void RemoveLastLevel() {
		String id = GetLastLevel();
		int level = Integer.parseInt(id);

		if (!id.equals("error")) {
			String query = "Delete from parking_slots where level =" + level;

			try (PreparedStatement stmt = connect().prepareStatement(query)) {
				int rs = stmt.executeUpdate();
			} 
			catch (SQLException e) {
				System.out.println("Exception occured while removing a level from the parking lot table");
			}
		}
	}
	
}