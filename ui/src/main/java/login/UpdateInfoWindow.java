package login;

import handlers.PlayerHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UpdateInfoWindow {

	private Stage window;

	private Button closeButton;
	private Button okButton;

	private TextField addressField;
	private TextField passwordField;

	private Alert alertSuccess;
	private Alert alertFail;

	private final String userMail;

	public UpdateInfoWindow(String mail) {
		this.userMail = mail;
	}

	public void display(String title) {
		window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		Label addressLabel = new Label("Change Address");
		Label passwordLabel = new Label("Change Password");

		addressField = new TextField();
		passwordField = new TextField();

		okButton = new Button("OK");
		closeButton = new Button("Close window");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		GridPane.setConstraints(addressLabel, 0, 0);
		GridPane.setConstraints(addressField, 1, 0);

		GridPane.setConstraints(passwordLabel, 0, 1);
		passwordField.setPromptText("password");
		GridPane.setConstraints(passwordField, 1, 1);

		GridPane.setConstraints(okButton, 0, 2);
		GridPane.setConstraints(closeButton, 1, 2);

		alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle("Information Dialog");
		alertSuccess.setHeaderText(null);

		alertFail = new Alert(AlertType.ERROR);
		alertFail.setTitle("Information Dialog");
		alertFail.setHeaderText(null);

		gridPane.getChildren().addAll(addressField, addressLabel, passwordField, passwordLabel, okButton, closeButton);

		okButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				String addressString = addressField.getText();
				String passwordString = passwordField.getText();

				if (!addressString.trim().isEmpty()) {
					PlayerHandler playerHandler = new PlayerHandler();
					if (playerHandler.changeAddress(userMail, addressString)) {
						alertSuccess.setContentText("Address successfully changed");
						alertSuccess.showAndWait();
					} else {
						alertFail.setContentText("Address couldn't be changed");
						alertFail.showAndWait();
					}
				}

				if (!passwordString.trim().isEmpty()) {
					PlayerHandler playerHandler = new PlayerHandler();
					if (playerHandler.changePassword(userMail, passwordString)) {
						alertSuccess.setContentText("Password successfully changed");
						alertSuccess.showAndWait();
					} else {
						alertFail.setContentText("Password couldn't be changed");
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

		Scene scene = new Scene(gridPane, 300, 150);
		window.setScene(scene);
		window.showAndWait();

	}

}
