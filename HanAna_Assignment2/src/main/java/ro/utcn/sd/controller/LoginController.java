package ro.utcn.sd.controller;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import ro.utcn.sd.business.PlayerHandler;
import ro.utcn.sd.validators.UserLoginValidator;
import ro.utcn.sd.view.AdminView;
import ro.utcn.sd.view.LoginView;
import ro.utcn.sd.view.UserView;

public class LoginController {

	private LoginView loginView;

	private Parent root;
	private Scene scene;

	private UserView userView;
	private AdminView adminView;

	private static Alert alertSuccess;
	private static Alert alertFail;

	public LoginController(LoginView loginView) {
		this.loginView = loginView;
	}

	public LoginView getLoginView() {
		return loginView;
	}

	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}

	public void showLoginView() {
		this.loginView.showMainView();

	}

	public void handleLoginButtonAction(TextField user, TextField pass) {

		String userNameString = user.getText();
		String passwordString = pass.getText();

		alertFail = new Alert(AlertType.ERROR);
		alertSuccess = new Alert(AlertType.INFORMATION);

		if (!userNameString.trim().isEmpty()) {
			if (!passwordString.trim().isEmpty()) {

				PlayerHandler playerHandler = new PlayerHandler();
				UserLoginValidator userLoginValidation = new UserLoginValidator(userNameString, passwordString);

				if (userLoginValidation.validateCredentials()) {
					alertSuccess.setContentText("Successful login");
					alertSuccess.showAndWait();

					if (!playerHandler.checkIfAdmin(userNameString)) {
						userView = new UserView(userNameString);
						userView.display();
					} else {
						 adminView = new AdminView();
						 adminView.display();
					}
				} else {
					alertFail.setContentText("Incorrect username and/or password");
					alertFail.showAndWait();
				}
			} else {
				alertFail.setContentText("No field can be empty");
				alertFail.showAndWait();
			}
		} else {
			alertFail.setContentText("No field can be empty");
			alertFail.showAndWait();
		}
	}

}
