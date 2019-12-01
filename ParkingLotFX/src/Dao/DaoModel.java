package Dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoModel {
	//Declare DB objects 
	DBConnect conn = null;
	Statement stmt = null;
	
	PreparedStatement p_stmt = null;

	// constructor
	public DaoModel() { 
		//create db object instance
		conn = new DBConnect();
	}

	public void CreateTable() {
		try {
//			Executing the Create Query
			System.out.println("Creating a table in the database");
			
			stmt = conn.connect().createStatement();
			
			String sql = "CREATE TABLE rag_shr_parking_users " +  "(pid INTEGER not NULL AUTO_INCREMENT," +
			"id VARCHAR(10)," + "income numeric(8,2)," + " pep VARCHAR(3)," + 
			"PRIMARY KEY (pid))";
			
			stmt.executeUpdate(sql);
			System.out.println("Table successfully created in the database");
			
//			closing DB Connection
			conn.connect().close();
		}
		catch(SQLException se) {
//			se.printStackTrace();
			System.out.println("Table already exists!!!");
		}
	}
}