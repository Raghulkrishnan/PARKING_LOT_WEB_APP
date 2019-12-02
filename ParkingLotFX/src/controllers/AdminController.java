package controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import Dao.DBConnect;
import application.DynamicTable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
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
	
	public AdminController() {}
	
	public void ViewBookings() {
		DynamicTable d = new DynamicTable();
		//call method from DynamicTable class and pass some arbitrary query string
		d.buildData("Select bid,uid from parking_lot_bookings",false);
	}
	
	public void ViewParkingLot() {
		DynamicTable d = new DynamicTable();
		//call method from DynamicTable class and pass some arbitrary query string
		d.buildData("Select level,slot_A,slot_B,slot_C,slot_D from parking_lot",false);	
	}
	
	public void AddParking() {
	    pane1.setVisible(false);
	    pane2.setVisible(false);
	    pane3.setVisible(true);
	}
	
	public void UpdateParking() {
		pane1.setVisible(true);
		pane2.setVisible(false);
		pane3.setVisible(false);
	}
	
	public void DeleteParking() {
	    pane1.setVisible(false);
	    pane2.setVisible(true);
	    pane3.setVisible(false);
	}	
	
	public void SubmitAdd() {
		AddLevel();
		System.out.println("Add Submit button pressed");
	}	
	
	public void SubmitDelete() {
		RemoveLevel();
		System.out.println("Delete Submit button pressed");
	}	
	
	public void SubmitUpdate() {
		String level = this.txtField_Level.getText();
		String slot = this.txtField_Slot.getText();
		ToggleUpdate(level,slot);
		System.out.println(level + " " + slot);
		System.out.println("Update Submit button pressed");
	}	
	
}