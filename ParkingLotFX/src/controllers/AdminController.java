package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Dao.DBConnect;
import application.DynamicTable;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.UserModel;

public class AdminController extends DBConnect  {

	@FXML
	private TextField txtField_Level;
	@FXML
	private TextField txtField_Slot;
	@FXML
	private Pane pane1;
	@FXML
	private Pane pane2;
	@FXML
	private Pane pane3;
	@FXML
	private Label lblError;
	@FXML
	private Label msg;
	
	public AdminController() {}
	
	public void SubmitUpdate() {
		String level = this.txtField_Level.getText();
		String slot = this.txtField_Slot.getText();

		if ((level == null || level.trim().equals("")) || (slot == null || slot.trim().equals(""))) {
			lblError.setText("Fields cannot be empty or spaces");
			return;
		}
		if (!level.matches("[A-E]{1}")) {
			lblError.setText("Incorrect slot entered.\na)Ranges only from A to D(Upper case)\nb)Single character is only accepted");
			return;
		}
		if (!slot.matches("[0-9]+")) {
			lblError.setText("Only numbers are accepted for levels");
			return;
		}
		
		if(CheckLevelExists(slot)) {
			
			ToggleUpdate(level, slot);
			
			if(RevertUserBooking(level,slot))
				lblError.setText("User booking has been updated!");
		}
		else {
			lblError.setText("Incorrect level. Select an existing level");
		}
		
		System.out.println("Update Submit button pressed");

	}
	
	public void ViewBookings() {
		DynamicTable d = new DynamicTable();
		
		CreateBookingsTable();
		
		d.buildData("Select * from parking_slot_bookings",false);
		
		pane1.setVisible(false);
		pane2.setVisible(false);
	}
	
	public void ViewParkingLot() {
		DynamicTable d = new DynamicTable();
		d.buildData("Select level,A,B,C,D from parking_slots",false);	

		pane1.setVisible(false);
		pane2.setVisible(false);
	}

//	add or remove parking button
	public void EditOptions() {
		pane1.setVisible(false);
		pane2.setVisible(true);
	}
	
	public void UpdateParking() {
		pane1.setVisible(true);
		pane2.setVisible(false);
	}
	
	public void ToggleParking() {
		pane1.setVisible(true);
		pane2.setVisible(false);
	}
	
	public void AddLevel() {
		System.out.println("Add a level called");
		AddNewLevel();
		msg.setText("A new level has been added successfully !");
	}

	public void DeleteLevel() {
		// Check if none of the slots in the level is occupied before removing
		if (DeletionAllowed() > 0) {
			RemoveLastLevel();
			msg.setText("A level has been deleted successfully!");
		}
		else {
			msg.setText("The level is occupied and cannot be deleted!");
		}
	}

	// Function for logout
	public void LogoutAdmin() {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);

			Main.stage.setTitle("Login");
		} 
		catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

}