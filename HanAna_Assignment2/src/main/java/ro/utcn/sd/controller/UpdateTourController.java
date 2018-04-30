
package ro.utcn.sd.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.utcn.sd.business.TournamentHandler;
import ro.utcn.sd.model.Tournament;

public class UpdateTourController {

	private Stage window;
	private TournamentHandler tournamentHandler;
	private static Alert alertSuccess;
	private static Alert alertFail;

	public UpdateTourController() {
		this.tournamentHandler = new TournamentHandler();
		alertFail = new Alert(AlertType.ERROR);
		alertSuccess = new Alert(AlertType.INFORMATION);
	}

	public void handleSaveFeeButton(Tournament tournament, TextField moneyField) {
		String moneyString = moneyField.getText();

		System.out.println("CF MEEEI BAN " + moneyString);
		if (tournamentHandler.changeEntryFee(Double.parseDouble(moneyString), tournament.getIdTournament())) {
			alertSuccess.setContentText("Entry fee successfully changed");
			alertSuccess.showAndWait();
		} else {
			alertFail.setContentText("Entry fee couldn't be changed");
			alertFail.showAndWait();
		}

	}

	public void handleSaveLocationButton(Tournament tournament, TextField locationField) {

		String locationString = locationField.getText();
		System.out.println("CF MEEEI LOC " + locationString);
		if (tournamentHandler.changePlace(locationString, tournament.getIdTournament())) {
			alertSuccess.setContentText("Location successfully changed");
			alertSuccess.showAndWait();
		} else {
			alertFail.setContentText("Location couldn't be changed");
			alertFail.showAndWait();
		}

	}

	public void handleSaveStatusButton(Tournament tournament, String status) {
		System.out.println("CF MEEEI STAT");

		if (!status.equals("Nope")) {
			if (tournamentHandler.changeStatus(status, tournament.getIdTournament())) {
				alertSuccess.setContentText("Status successfully changed");
				alertSuccess.showAndWait();
			} else {
				alertFail.setContentText("Status couldn't be changed");
				alertFail.showAndWait();
			}
		}
	}

	public void display(String title) {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
	}
}
