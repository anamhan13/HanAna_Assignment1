package login;

import handlers.TournamentHandler;
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

public class CreateTourWindow {

	private Stage window;

	private Button closeButton;
	private Button okButton;

	private TextField nameField;
	private TextField statusField;
	private TextField dateStartField;
	private TextField dateFinishField;
	private TextField placeField;

	private Alert alertSuccess;
	private Alert alertFail;

	public CreateTourWindow() {
	}

	public void display(String title) {
		window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		Label nameLabel = new Label("Insert Name");
		Label statusLabel = new Label("Insert Status");
		Label dateStartLabel = new Label("Insert Start Date");
		Label dateFinishLabel = new Label("Insert Finish Date");
		Label placeLabel = new Label("Insert Location");

		nameField = new TextField();
		statusField = new TextField();
		dateStartField = new TextField();
		dateFinishField = new TextField();
		placeField = new TextField();

		okButton = new Button("OK");
		closeButton = new Button("Close window");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		GridPane.setConstraints(nameLabel, 0, 0);
		GridPane.setConstraints(nameField, 1, 0);

		GridPane.setConstraints(statusLabel, 0, 1);
		GridPane.setConstraints(statusField, 1, 1);

		GridPane.setConstraints(dateStartLabel, 0, 2);
		GridPane.setConstraints(dateStartField, 1, 2);

		GridPane.setConstraints(dateFinishLabel, 0, 3);
		GridPane.setConstraints(dateFinishField, 1, 3);

		GridPane.setConstraints(placeLabel, 0, 4);
		GridPane.setConstraints(placeField, 1, 4);

		GridPane.setConstraints(okButton, 0, 5);
		GridPane.setConstraints(closeButton, 1, 5);

		alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle("Information Dialog");
		alertSuccess.setHeaderText(null);

		alertFail = new Alert(AlertType.ERROR);
		alertFail.setTitle("Information Dialog");
		alertFail.setHeaderText(null);

		gridPane.getChildren().addAll(nameField, nameLabel, statusField, statusLabel, dateStartLabel, dateStartField,
				dateFinishLabel, dateFinishField, placeLabel, placeField, okButton, closeButton);

		okButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				String nameString = nameField.getText();
				String statString = statusField.getText();
				String dateStartsString = dateStartField.getText();
				String dateFinishString = dateFinishField.getText();
				String placeString = placeField.getText();

				if (!placeString.trim().isEmpty() && !nameString.trim().isEmpty() && !statString.trim().isEmpty() && !dateStartsString.trim().isEmpty()
						&& !dateFinishString.trim().isEmpty()) {
					TournamentHandler tournamentHandler = new TournamentHandler();
					if (tournamentHandler.createTournament(nameString, statString, dateStartsString, dateFinishString, placeString)) {
						alertSuccess.setContentText("Tournament successfully created");
						alertSuccess.showAndWait();
					} else {
						alertFail.setContentText("Tournament couldn't be created");
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

		Scene scene = new Scene(gridPane, 400, 250);
		window.setScene(scene);
		window.showAndWait();

	}
}
