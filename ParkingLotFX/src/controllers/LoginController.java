package controllers;
import java.util.Base64;
import application.Main;
import models.LoginModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController {
	@FXML
	private TextField userNameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorLabelField;

	private LoginModel model;

	public LoginController() {
		model = new LoginModel();
	}

	public void Login() {
		String username = this.userNameField.getText();
		String password = this.passwordField.getText();
		
		//Hashcoding password
		String hashedPswd = Base64.getEncoder().encodeToString(password.getBytes());

		// Validations
		if (username == null || username.trim().equals("")) {
			errorLabelField.setText("Username cannot be empty or just spaces.");
			return;
		}
		
		if (password == null || password.trim().equals("")) {
			errorLabelField.setText("Password cannot be empty or just spaces.");
			return;
		}
		
		if (username == null || username.trim().equals("") && 
		   (password == null || password.trim().equals(""))) {
			errorLabelField.setText("Username or password cannot be empty or just spaces.");
			return;
		}
		// authentication check
		VerifyCredentials(username, password);
	}

	public void VerifyCredentials(String username, String password) {
		
		int  user_id = model.GetCredentials(username, password);
		
		Boolean isValid =user_id > 0 ? true : false;

		if (!isValid) {
			errorLabelField.setText("User does not exist!");
			return;
		}
		
		try {
			AnchorPane root;
			if (model.isAdmin() && isValid) {
				// If user is admin, inflate admin view
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/AdminView.fxml"));
				System.out.println("Admin logged in succesfully!!");
				Main.stage.setTitle("Admin View");
			} 
			else {
				// If user is customer, inflate customer view
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/UserView.fxml"));
 				UserController.setUser(user_id);
 				System.out.println("User logged in succesfully!!");
				Main.stage.setTitle("User View");
			}

			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
		} 
		catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}
	
	public void RegisterUser() {
		try {
			AnchorPane root;

			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/RegisterView.fxml"));
			Main.stage.setTitle("Register");
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);

		} 
		catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}

	}
}