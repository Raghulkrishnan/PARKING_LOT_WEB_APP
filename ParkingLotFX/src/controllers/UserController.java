package controllers;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicLong;

import com.sun.javafx.geom.Rectangle;
import com.sun.prism.paint.Color;


import Dao.DBConnect;
import Dao.DaoModel;
import application.DynamicTable;
import application.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import models.UserModel;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ChoiceDialog;

public class UserController extends DBConnect  {
	static int userId;
	static UserModel user;
	
	@FXML private TextField slot;
	@FXML private TextField level;
	@FXML private Label errorLabelField;
	
	@FXML
	private Label status;
	@FXML
	private TableView<UserModel> tblAccounts;
	@FXML
	private TableColumn<UserModel, String> tid;
	@FXML
	private TableColumn<UserModel, String> balance;

	DaoModel dm = new DaoModel();
	
	public UserController() {}
	
	public static void setUser(int id){
		UserController.userId = id;
	}
	
	public void ViewParking() {
		UserModel user_model = new UserModel();
		
		DynamicTable dyn_Table = new DynamicTable();
		
		dm.CreateParkingTable();
		
		dyn_Table.buildData("Select level,A, B, C, D from parking_slots",false);
	}
	
	public void BookParking() {
		try {
			ViewParking();
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/BookSlot.fxml"));
			Parent root1 = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("Book a Slot");
			stage.setScene(new Scene(root1));
			stage.show();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Function that will make the actual booking
		public void ConfirmBooking() {
			String level = this.level.getText();
			String lot = this.slot.getText();

			// Validations
			if ((level == null || level.trim().equals("")) || (lot == null || lot.trim().equals(""))) {
				errorLabelField.setText("Level or slot, either cannot be empty or spaces");
				return;
			}
			if (!level.matches("[A-E]{1}")) {
				errorLabelField.setText("Incorrect slot input\na)Ranges only from A to D(Capital letters) \nb)Single character only");
				return;
			}
			if (!lot.matches("[0-9]+$")) {
				errorLabelField.setText("Only numbers are accepted for levels");
				return;
			}
			
			if (CheckLevelExists(lot)) {
				
				if (OpenToBook(level, lot)) {
					
					if (OneBookingPerUserCheck(userId) == 0) {
						BookSlot(level, lot, userId);
						errorLabelField.setText("Slot Booked");
					} 
					else {
						errorLabelField.setText("Slot cannot be booked.\nYou already have a booking.\nOnly one booking allowed!!");
					}
				} 
				else {
					errorLabelField.setText("Slot cannot be booked\nAlready occupied!!");
				}
			} 
			else {
				errorLabelField.setText("Level input is more than existing levels.\n Please check !!");
			}	
		}

		// Function to cancel the booking
		public void CancelBooking() {
			// Get the booked slot by user
			String slotBooked = GetBookedSlot(userId);
			
			//Get the individual string values
			String level=String.valueOf(slotBooked.charAt(0));
			String lot =String.valueOf(slotBooked.charAt(1));
			
			// Inform if no records exist
			if (slotBooked.equals("No records")) {
				status.setText("You do not have any bookings");
			}
			// Perform a deletion by confirmation through a dialog box
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Delete your booking?\n" + slotBooked);
				alert.showAndWait();
				
				// If given yes to delete, then perform delete
				if (alert.getResult() == ButtonType.OK) {
					System.out.println("Alert given as yes");
					
					if (ConfirmBookingCancellation(userId)) {
						ToggleUpdate(lot,level);
						status.setText("Your booking has been cancelled");
					} 
					else {
						status.setText("Cannot cancel the booking.\nThere was an error");
					}
				} 
				else {
					System.out.println("if alert escaped");
				}
			}
		}
	
	
	public void LogoutUser() {
		try {
		    AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
			Main.stage.setTitle("Login");
		} 
		catch(Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}
}