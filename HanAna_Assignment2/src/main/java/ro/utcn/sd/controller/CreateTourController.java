package ro.utcn.sd.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import ro.utcn.sd.business.TournamentHandler;

public class CreateTourController {
	
	private static Alert alertSuccess;
	private static Alert alertFail;

	public CreateTourController() {
		alertFail = new Alert(AlertType.ERROR);
		alertSuccess = new Alert(AlertType.INFORMATION);
	}

	public void handleCreateButton(TextField name, TextField location, TextField entryFee, TextField dateStart,
			TextField dateFinish) {
		String nameString = name.getText();
		String entryFeeString = entryFee.getText();
		String dateStartsString = dateStart.getText();
		String dateFinishString = dateFinish.getText();
		String placeString = location.getText();
		
		if (!placeString.trim().isEmpty() && !nameString.trim().isEmpty() && !entryFeeString.trim().isEmpty() && !dateStartsString.trim().isEmpty()
				&& !dateFinishString.trim().isEmpty()) {
			TournamentHandler tournamentHandler = new TournamentHandler();
			if (tournamentHandler.createTournament(nameString, entryFeeString, dateStartsString, dateFinishString, placeString)) {
				alertSuccess.setContentText("Tournament successfully created");
				alertSuccess.showAndWait();
			} else {
				alertFail.setContentText("Tournament couldn't be created");
				alertFail.showAndWait();
			}
		}
	}

}
