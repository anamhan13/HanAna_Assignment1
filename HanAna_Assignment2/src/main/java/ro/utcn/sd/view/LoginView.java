package ro.utcn.sd.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.utcn.sd.controller.LoginController;

public class LoginView {

	private Stage primaryStage;
	private AnchorPane layout;

	@FXML
	private Button loginButton;

	@FXML
	private Button registerButton;

	@FXML
	private TextField userField;

	@FXML
	private TextField passField;
	
	private Scene scene;

	public LoginView(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public AnchorPane getLayout() {
		return layout;
	}

	public void setLayout(AnchorPane layout) {
		this.layout = layout;
	}

	public Button getLoginButton() {
		return loginButton;
	}

	public void setLoginButton() {
		this.loginButton = (Button) primaryStage.getScene().lookup("#loginButton");
	}

	public Button getRegisterButton() {
		return registerButton;
	}

	public void setRegisterButton() {
		this.registerButton = (Button) primaryStage.getScene().lookup("#registerButton");
	}

	public TextField getUserField() {
		return userField;
	}

	public void setUserField() {
		userField = (TextField) scene.lookup("#userField");
	}

	public TextField getPassField() {
		return passField;
	}

	public void setPassField() {
		passField = (TextField) scene.lookup("#passField");
	}

	public void showMainView() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/LoginFXML.fxml"));
			
			scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
			
			setLoginButton();
			setRegisterButton();
			setUserField();
			setPassField();
			
			loginButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					LoginController loginController = new LoginController(new LoginView(primaryStage));
					loginController.handleLoginButtonAction(getUserField(), getPassField());
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
