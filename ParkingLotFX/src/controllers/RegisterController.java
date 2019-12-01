package controllers;

import java.sql.SQLException;

import Dao.DBConnect;
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
	private TextField userid;
	@FXML
	private Label regErrorLabel;

	private UserModel model;

	public RegisterController() {
		model = new UserModel();
	}
	
	public void Register() throws SQLException {
		String firstName = this.fname.getText();
		String lastName = this.lname.getText();
		String pwd = this.password.getText();
		String userId = this.userid.getText();
		LoginModel model = new LoginModel();
		
		int user_id= model.GetCredentials(userId, pwd);
		
		Boolean isValid = user_id > 0 ? true : false;
		
		if (isValid) {
			regErrorLabel.setText("User alredy exists!");
			return;
		}
		
		boolean success = InsertUserData(userId, pwd, firstName, lastName);
		int uid = model.GetCredentials(userId, pwd);
		
		if(success) {
			try {
				AnchorPane root;
				root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/UserView.fxml"));
			
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

}