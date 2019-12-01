package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Dao.DBConnect;

public class UserModel extends DBConnect implements User<ParkingLot>{

	private String firstName;
	private String lastName;
	private int id;
	private int cid;
    private ParkingLot pt;
    DBConnect conn = null;
    
	public UserModel() {}
 
	public UserModel(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		
		conn = new DBConnect();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
 	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public List<UserModel> getBookings(int cid) {
		
		List<UserModel> accounts  = new ArrayList<>();
		
		String query = "SELECT lot,level FROM bookings join users WHERE uid = ?;";
		
		try(PreparedStatement statement = conn.connect().prepareStatement(query)){
            statement.setInt(1, cid);
            ResultSet resultSet = statement.executeQuery();
            
            while(resultSet.next()) {
            	UserModel account = new UserModel();
                //grab record data by table field name into UserModel account object
            }
        } 
		catch(SQLException e){
            System.out.println("Error fetching Accounts: " + e);
        }
		return accounts; //return array list
	}

	@Override
	public ParkingLot getCustomerInfo() {
		// TODO Auto-generated method stub
		return pt;
	}
}