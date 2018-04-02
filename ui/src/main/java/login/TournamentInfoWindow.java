package login;

import handlers.TournamentHandler;
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

public class TournamentInfoWindow {

	private Stage window;

	private Button closeButton;
	private Button okButton;

	private TextField statusField;
	private TextField placeField;

	private Alert alertSuccess;
	private Alert alertFail;

	private int idTournament;

	public TournamentInfoWindow(int id) {
		this.idTournament = id;
	}

	public void display(String title) {
		window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		Label statusLabel = new Label("Change Status");
		Label placeLabel = new Label("Change Location");

		statusField = new TextField();
		placeField = new TextField();

		okButton = new Button("OK");
		closeButton = new Button("Close window");

		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		GridPane.setConstraints(statusLabel, 0, 0);
		GridPane.setConstraints(statusField, 1, 0);
		
		GridPane.setConstraints(placeLabel, 0, 1);
		GridPane.setConstraints(placeField, 1, 1);
		
		GridPane.setConstraints(okButton, 0, 2);
		GridPane.setConstraints(closeButton, 1, 2);

		alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle("Information Dialog");
		alertSuccess.setHeaderText(null);

		alertFail = new Alert(AlertType.ERROR);
		alertFail.setTitle("Information Dialog");
		alertFail.setHeaderText(null);

		gridPane.getChildren().addAll(statusLabel, statusField, placeLabel, placeField, okButton, closeButton);

		okButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				String statusString = statusField.getText();
				String placeString = placeField.getText();

				if (!statusString.trim().isEmpty()) {
					TournamentHandler tournamentHandler = new TournamentHandler();
					if (tournamentHandler.changeStatus(statusString, idTournament)) {
						alertSuccess.setContentText("Status successfully changed");
						alertSuccess.showAndWait();
					} else {
						alertFail.setContentText("Status couldn't be changed");
						alertFail.showAndWait();
					}
				}

				if (!placeString.trim().isEmpty()) {
					TournamentHandler tournamentHandler = new TournamentHandler();
					if (tournamentHandler.changePlace(placeString, idTournament)) {
						alertSuccess.setContentText("Location successfully changed");
						alertSuccess.showAndWait();
					} else {
						alertFail.setContentText("Location couldn't be changed");
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
