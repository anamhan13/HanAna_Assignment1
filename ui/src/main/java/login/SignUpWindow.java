package login;

import handlers.PlayerHandler;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SignUpWindow {

	private Stage window;

	private Button closeButton;
	private Button okButton;

	private TextField addressField;
	private TextField passwordField;
	private TextField nameField;
	private TextField mailField;

	private Alert alertSuccess;
	private Alert alertFail;

	public SignUpWindow() {
	}

	public void display(String title) {
		window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		Label addressLabel = new Label("Insert Address");
		Label passwordLabel = new Label("Insert Password");
		Label namelLabel = new Label("Insert Name");
		Label maiLabel = new Label("Insert Mail");

		addressField = new TextField();
		passwordField = new TextField();
		nameField = new TextField();
		mailField = new TextField();

		okButton = new Button("OK");
		closeButton = new Button("Close window");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		GridPane.setConstraints(namelLabel, 0, 0);
		GridPane.setConstraints(nameField, 1, 0);

		GridPane.setConstraints(maiLabel, 0, 1);
		passwordField.setPromptText("user@example.com");
		GridPane.setConstraints(mailField, 1, 1);

		GridPane.setConstraints(passwordLabel, 0, 2);
		passwordField.setPromptText("password");
		GridPane.setConstraints(passwordField, 1, 2);

		GridPane.setConstraints(addressLabel, 0, 3);
		GridPane.setConstraints(addressField, 1, 3);

		GridPane.setConstraints(okButton, 0, 4);
		GridPane.setConstraints(closeButton, 1, 4);

		alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle("Information Dialog");
		alertSuccess.setHeaderText(null);

		alertFail = new Alert(AlertType.ERROR);
		alertFail.setTitle("Information Dialog");
		alertFail.setHeaderText(null);

		gridPane.getChildren().addAll(namelLabel, nameField, maiLabel, mailField, addressField, addressLabel,
				passwordField, passwordLabel, okButton, closeButton);

		okButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				String addressString = addressField.getText();
				String passwordString = passwordField.getText();
				String nameString = nameField.getText();
				String mailsString = mailField.getText();

				if (!mailsString.trim().isEmpty() && !passwordString.trim().isEmpty() && !nameString.trim().isEmpty() && !addressString.trim().isEmpty()) {
					PlayerHandler playerHandler = new PlayerHandler();
					if (playerHandler.createAccount(nameString, mailsString, passwordString, addressString)) {
						alertSuccess.setContentText("Account successfully created");
						alertSuccess.showAndWait();
					} else {
						alertFail.setContentText("Account couldn't be created");
						alertFail.showAndWait();
					}
				}

				window.close();
			}

		});

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				window.close();
			}
		});

		Scene scene = new Scene(gridPane, 300, 200);
		window.setScene(scene);
		window.showAndWait();

	}
}
