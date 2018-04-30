package ro.utcn.sd.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.utcn.sd.controller.UpdateTourController;
import ro.utcn.sd.model.Tournament;

public class UpdateTourView {

	private Scene scene;
	private Stage primaryStage;

	@FXML
	private Button saveFeeButton;

	@FXML
	private Button saveLocationButton;

	@FXML
	private Button saveStatusButton;

	@FXML
	private MenuButton selectStatusButton;

	@FXML
	private TextField moneyField;

	@FXML
	private TextField locationField;

	private MenuItem upcoming;
	private MenuItem ongoing;
	private MenuItem finished;
	private MenuItem cancelled;
	private MenuItem nope;

	private Tournament tournament;

	public UpdateTourView(Tournament tournament) {
		this.primaryStage = new Stage();
		this.tournament = tournament;
	}

	private void initializeObjects() {
		moneyField = (TextField) scene.lookup("#moneyField");
		locationField = (TextField) scene.lookup("#locationField");

		saveFeeButton = (Button) scene.lookup("#saveFeeButton");
		saveLocationButton = (Button) scene.lookup("#saveLocationButton");
		saveStatusButton = (Button) scene.lookup("#saveStatusButton");

		selectStatusButton = (MenuButton) scene.lookup("#statusButton");

		this.upcoming = new MenuItem("Upcoming");
		this.ongoing = new MenuItem("Ongoing");
		this.finished = new MenuItem("Finished");
		this.cancelled = new MenuItem("Cancelled");
		this.nope = new MenuItem("No change");

		selectStatusButton.getItems().addAll(nope, upcoming, ongoing, finished, cancelled);

	}

	public void display(String title) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/UpdateTourFXML.fxml"));
			scene = new Scene(root);
			primaryStage.setScene(scene);

			initializeObjects();

			UpdateTourController updateTourController = new UpdateTourController();

			nope.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					updateTourController.handleSaveStatusButton(tournament, "Nope");
				}
			});

			upcoming.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					updateTourController.handleSaveStatusButton(tournament, "Upcoming");
				}
			});

			ongoing.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					updateTourController.handleSaveStatusButton(tournament, "Ongoing");
				}
			});

			finished.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					updateTourController.handleSaveStatusButton(tournament, "Finished");
				}
			});

			cancelled.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					updateTourController.handleSaveStatusButton(tournament, "Cancelled");
				}
			});

			saveFeeButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (!moneyField.getText().trim().isEmpty())
						updateTourController.handleSaveFeeButton(tournament, moneyField);
				}
			});

			saveLocationButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (!locationField.getText().trim().isEmpty())
						updateTourController.handleSaveLocationButton(tournament, locationField);
				}
			});

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
