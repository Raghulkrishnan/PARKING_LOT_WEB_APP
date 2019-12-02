package controllers;

import java.io.IOException;

import Dao.DBConnect;
import application.DynamicTable;
import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.UserModel;
 
public class UserController extends DBConnect  {
	static int userId;
	static UserModel user;
	
	@FXML private TextField lot;
	@FXML private TextField level;
	@FXML private Label errorLabelField;
 
	public static void setUser(int id){
		UserController.userId = id;
	}
	
	public void ViewParking() {
		UserModel user_model = new UserModel();
		System.out.println(UserController.userId );
		DynamicTable dyn_Table = new DynamicTable();
		
		CreateParkingTable();
		
		//call method from DynamicTable class and pass some arbitrary query string
		dyn_Table.buildData("Select level,slot_A, slot_B, slot_C, slot_D from parking_lot",false);
	}
	
	public void BookParking() {
		try {
			ViewParking();
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("/views/BookSlot.fxml"));
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
			Main.stage.setTitle("Book a Slot");
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public void Logout() {
		//System.exit(0);
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