package controllers;

import java.sql.SQLException;
import java.util.Base64;

import Dao.DBConnect;
import Dao.DaoModel;
import application.Main;
import models.LoginModel;
import models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class RegisterController extends DBConnect{
	@FXML
	private TextField fname;
	@FXML
	private PasswordField password;
	@FXML
	private TextField lname;
	@FXML
	private TextField username;
	@FXML
	private Label regErrorLabel;

	private UserModel model;

	DaoModel dm = new DaoModel();
	
	public RegisterController() {
		model = new UserModel();
	}
	
	public void Register() throws SQLException {
		String firstName = this.fname.getText(), lastName = this.lname.getText(), pwd = this.password.getText(), userId = this.username.getText();
		LoginModel model = new LoginModel();
		
		// Validations
		if (firstName == null || firstName.trim().equals("")) {
			regErrorLabel.setText("Firstname cannot be empty or spaces");
			System.out.println(regErrorLabel);
			return;

		}
		if (lastName == null || lastName.trim().equals("")) {
			regErrorLabel.setText("Lastname cannot be empty or spaces");
			System.out.println(regErrorLabel);
			return;
		}
		if (userId == null || userId.trim().equals("")) {
			regErrorLabel.setText("Username cannot be empty or spaces");
			System.out.println(regErrorLabel);
			return;
		}
		if (pwd == null || pwd.trim().equals("")) {
			regErrorLabel.setText("Password cannot be empty or spaces");
			System.out.println(regErrorLabel);
			return;
		}
		if ((userId == null || userId.trim().equals(""))
				&& (pwd == null || pwd.trim().equals(""))
				&& (lastName == null || lastName.trim().equals(""))
				&& (firstName == null || firstName.trim().equals(""))) {
			
			regErrorLabel.setText("Fields cannot be empty or spaces");
			System.out.println(regErrorLabel);
			return;
		}
		
		int user_id= model.GetCredentials(userId, pwd);
		
		Boolean isValid = user_id > 0 ? true : false;
		
		if (isValid) {
			regErrorLabel.setText("User alredy exists!");
			return;
		}

		//Hashcoding password
		String hashedPwd = Base64.getEncoder().encodeToString(pwd.getBytes());
		
		boolean success = dm.InsertUserData(userId, pwd, firstName, lastName);
		int uid = model.GetCredentials(userId, pwd);

		if(success) {
			try {
				AnchorPane root;
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			
				Main.stage.setTitle("User View");
				Scene scene = new Scene(root);
				Main.stage.setScene(scene);

			} 
			catch (Exception e) {
				System.out.println("Error occured while inflating view: " + e);
			}
		}
		else
			regErrorLabel.setText("Unable to create user");
		
		return;
	}
	
	public void CancelRegistration() {
		try {
			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/LoginView.fxml"));
			Scene scene = new Scene(root);
			Main.stage.setScene(scene);
			Main.stage.setTitle("Login");
		} catch (Exception e) {
			System.out.println("Error occured while inflating view: " + e);
		}
	}

}