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
import javafx.stage.Stage;
import ro.utcn.sd.controller.CreateTourController;

public class CreateTourView {

	@FXML
	private Button createButton;

	@FXML
	private TextField name;

	@FXML
	private TextField location;

	@FXML
	private TextField entryFee;

	@FXML
	private TextField dateStart;

	@FXML
	private TextField dateFinish;

	private Stage stage;
	private Scene scene;

	public CreateTourView() {
		stage = new Stage();
	}

	public void initializeObjects() {
		this.name = (TextField) scene.lookup("#name");
		this.location = (TextField) scene.lookup("#location");
		this.entryFee = (TextField) scene.lookup("#fee");
		this.dateStart = (TextField) scene.lookup("#dateStart");
		this.dateFinish = (TextField) scene.lookup("#dateFinish");

		this.createButton = (Button) scene.lookup("#createButton");
	}

	public void display() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/CreateTourFXML.fxml"));
			scene = new Scene(root);
			stage.setScene(scene);

			initializeObjects();
			CreateTourController createTourController = new CreateTourController();

			createButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					createTourController.handleCreateButton(name, location, entryFee, dateStart, dateFinish);
				}
			});

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
