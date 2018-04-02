package login;

import java.sql.Connection;

import org.dao.connection.ConnectionFactory;

import handlers.PlayerHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import validation.UserLoginValidation;

public class Login extends Application {

	private Button loginButton;
	private Button signupButton;
	private Button backButton;

	private Scene scene;

	private TextField userNameField;
	private TextField passwordField;

	private static Connection connection;

	private Alert alertSuccess;
	private Alert alertFail;

	private UserView userView;
	private AdminView adminView;

	public Login() {
		connection = ConnectionFactory.getConnection();
	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		primaryStage.setTitle("Login window");

		loginButton = new Button();
		loginButton.setText("Log in");

		signupButton = new Button();
		signupButton.setText("Sign up");

		backButton = new Button();
		backButton.setText("Back");

		Label userNameLabel = new Label("Username");
		Label passwordLabel = new Label("Password");

		userNameField = new TextField();
		passwordField = new TextField();

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		GridPane.setConstraints(userNameLabel, 0, 0);
		userNameField.setPromptText("user@example.com");
		GridPane.setConstraints(userNameField, 1, 0);

		GridPane.setConstraints(passwordLabel, 0, 1);
		passwordField.setPromptText("password");
		GridPane.setConstraints(passwordField, 1, 1);

		GridPane.setConstraints(loginButton, 0, 2);
		GridPane.setConstraints(signupButton, 1, 2);

		gridPane.getChildren().addAll(userNameField, userNameLabel, passwordField, passwordLabel, loginButton,
				signupButton);

		alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle("Information Dialog");
		alertSuccess.setHeaderText(null);

		alertFail = new Alert(AlertType.ERROR);
		alertFail.setTitle("Information Dialog");
		alertFail.setHeaderText(null);

		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {

				String userNameString = userNameField.getText();
				String passwordString = passwordField.getText();

				if (!userNameString.trim().isEmpty()) {
					if (!passwordString.trim().isEmpty()) {

						PlayerHandler playerHandler = new PlayerHandler();
						UserLoginValidation userLoginValidation = new UserLoginValidation(userNameString,
								passwordString, connection);
						if (userLoginValidation.validateCredentials()) {
							alertSuccess.setContentText("Successful login");
							alertSuccess.showAndWait();

							if (!playerHandler.checkIfAdmin(userNameString)) {
								userView = new UserView(userNameString);
								userView.display("User interface");
							} else {
								adminView = new AdminView();
								adminView.display("Admin interface");
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

				VBox layout1 = new VBox(20);
				layout1.getChildren().addAll(backButton);

				backButton.setOnAction(new EventHandler<ActionEvent>() {
					public void handle(ActionEvent arg0) {
						primaryStage.setScene(scene);
					}
				});
			}
		});

		signupButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				SignUpWindow signUpWindow = new SignUpWindow();
				signUpWindow.display("Sign up");
			}
		});

		scene = new Scene(gridPane, 300, 150);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		Login.connection = connection;
	}

}
