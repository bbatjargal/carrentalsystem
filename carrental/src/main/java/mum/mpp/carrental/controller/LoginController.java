package mum.mpp.carrental.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import mum.mpp.carrental.Login;
import mum.mpp.carrental.Main;
import mum.mpp.carrental.factory.BaseFactory;

public class LoginController {

	@FXML
	private TextField txtUserName;
	
	@FXML
	private PasswordField txtPassword;
	
	@FXML
	private RadioButton rbAdmin;

	@FXML
	private RadioButton rbCustomer;
	
	@FXML
	private Text txtMessage;
		
	@FXML
	protected void handleLogin() {
		Main main = new Main();
		try {
			Main.currentUser = BaseFactory.login(txtUserName.getText().trim(), txtPassword.getText().trim(), 
					rbAdmin.isSelected() ? "admin":"customer");
						
			main.start(Main.primaryStage);
			Login.primaryStage.close();			
		} catch (Exception e) {
			e.printStackTrace();
			txtMessage.setText(e.getMessage());
		}
	}
	
}
