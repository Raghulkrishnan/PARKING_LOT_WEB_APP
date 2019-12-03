package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Dao.DBConnect;

public class LoginModel extends DBConnect {
	
	private Boolean admin;

	public Boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(Boolean isUserAdmin) {
		admin = isUserAdmin;
	}
	
	public int GetCredentials(String username, String password){
	   int user_id = -1;
	   int u_admin;
	   
	   String query = "SELECT * FROM parking_slot_users WHERE username = ? and password = ?;";
	   
	   try(PreparedStatement stmt = connect().prepareStatement(query)) {
		   
		   stmt.setString(1, username);
		   stmt.setString(2, password);
		   
		   ResultSet rs = stmt.executeQuery();
		   
		   if(rs.next()) { //using rs.next() because usually there will be no more rows. it will return false. If true, user_id is set  
				user_id = rs.getInt("id");
				u_admin = rs.getInt("isAdmin");
				
				if(u_admin == 1)
					setAdmin(true);
				else
					setAdmin(false);
				
				return user_id;
		   }
	   }
	   catch (SQLException e) {
		   e.printStackTrace();   
	   }
	   return user_id;
    }
}