package ro.utcn.sd.controller;

import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.utcn.sd.business.MatchHandler;
import ro.utcn.sd.business.PlayerHandler;
import ro.utcn.sd.business.TournamentHandler;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.model.Tournament;
import ro.utcn.sd.view.GameView;

public class UserController {

	private Stage window;
	private String userName;
	private PlayerHandler playerHandler;
	private TournamentHandler tournamentHandler;
	private static Alert alertSuccess;
	private static Alert alertFail;

	public UserController(String username) {
		this.userName = username;
		this.playerHandler = new PlayerHandler();
		this.tournamentHandler = new TournamentHandler();
		alertFail = new Alert(AlertType.ERROR);
		alertSuccess = new Alert(AlertType.INFORMATION);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public TableView<Match> createMatchTable(TableView<Match> matchTable) {

		TableColumn<Match, Long> idMatch = new TableColumn<Match, Long>("IdMatch");
		idMatch.setMinWidth(128);
		idMatch.setCellValueFactory(new PropertyValueFactory<Match, Long>("IdMatch"));

		TableColumn<Match, Long> player1 = new TableColumn<Match, Long>("IdPlayer1");
		player1.setMinWidth(128);
		player1.setCellValueFactory(new PropertyValueFactory<Match, Long>("IdPlayer1"));

		TableColumn<Match, Long> player2 = new TableColumn<Match, Long>("IdPlayer2");
		player2.setMinWidth(128);
		player2.setCellValueFactory(new PropertyValueFactory<Match, Long>("IdPlayer2"));

		TableColumn<Match, Long> idTournament = new TableColumn<Match, Long>("IdTournament");
		idTournament.setMinWidth(128);
		idTournament.setCellValueFactory(new PropertyValueFactory<Match, Long>("IdTournament"));

		TableColumn<Match, Integer> winner = new TableColumn<Match, Integer>("Winner");
		winner.setMinWidth(128);
		winner.setCellValueFactory(new PropertyValueFactory<Match, Integer>("Winner"));

		boolean addAll = matchTable.getColumns().addAll(idMatch, player1, player2, idTournament, winner);
		return matchTable;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public TableView<Tournament> createTournamentTable(TableView<Tournament> tournamentTable) {

		TableColumn<Tournament, Long> idTournament = new TableColumn<Tournament, Long>("IdTournament");
		idTournament.setMinWidth(100);
		idTournament.setCellValueFactory(new PropertyValueFactory<Tournament, Long>("IdTournament"));

		TableColumn<Tournament, String> name = new TableColumn<Tournament, String>("Name");
		name.setMinWidth(80);
		name.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Name"));

		TableColumn<Tournament, String> status = new TableColumn<Tournament, String>("Status");
		status.setMinWidth(80);
		status.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Status"));

		TableColumn<Tournament, Date> dateStart = new TableColumn<Tournament, Date>("DateStart");
		dateStart.setMinWidth(80);
		dateStart.setCellValueFactory(new PropertyValueFactory<Tournament, Date>("DateStart"));

		TableColumn<Tournament, Date> dateFinish = new TableColumn<Tournament, Date>("DateFinish");
		dateFinish.setMinWidth(80);
		dateFinish.setCellValueFactory(new PropertyValueFactory<Tournament, Date>("DateFinish"));

		TableColumn<Tournament, String> place = new TableColumn<Tournament, String>("Place");
		place.setMinWidth(60);
		place.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Place"));

		TableColumn<Tournament, Double> prize = new TableColumn<Tournament, Double>("Prize");
		prize.setMinWidth(60);
		prize.setCellValueFactory(new PropertyValueFactory<Tournament, Double>("Prize"));

		TableColumn<Tournament, Double> entryFee = new TableColumn<Tournament, Double>("EntryFee");
		entryFee.setMinWidth(80);
		entryFee.setCellValueFactory(new PropertyValueFactory<Tournament, Double>("EntryFee"));

		TableColumn<Tournament, Boolean> paid = new TableColumn<Tournament, Boolean>("Paid");
		paid.setMinWidth(80);
		paid.setCellValueFactory(new PropertyValueFactory<Tournament, Boolean>("Paid"));

		boolean addAll = tournamentTable.getColumns().addAll(idTournament, name, status, dateStart, dateFinish, place,
				entryFee, prize);

		return tournamentTable;
	}

	public void handleViewMatchesButton(TableView<Match> matchTable) {
		MatchHandler matchHandler = new MatchHandler();
		matchTable.setItems(matchHandler.getMatchesList());
		matchTable.refresh();
	}

	public void handleViewToursButton(TableView<Tournament> tournamentTable) {
		TournamentHandler tournamentHandler = new TournamentHandler();
		tournamentTable.setItems(tournamentHandler.getTournamentsList());
		tournamentTable.refresh();
	}

	public void handleDepositButton(TextField moneyField, TextArea moneyArea) {
		double value = Double.parseDouble(moneyField.getText());
		moneyArea.setText(Double.toString(playerHandler.updateBalance(userName, value, 1)));
	}

	public void handleEnrollButton(Tournament tournament, TextArea moneyArea, TableView<Tournament> tournamentTable) {
		if (!tournament.getStatus().equals("Upcoming")) {
			alertFail.setContentText("Registration period has passed");
			alertFail.showAndWait();
			return;
		}
		
		if (tournamentHandler.isPlayerEnrolled(tournament, userName)) {
			alertFail.setContentText("You are already enrolled in this tournament");
			alertFail.showAndWait();
			return;
		}
		
		if (tournamentHandler.enoughPlayers(tournament)) {
			alertFail.setContentText("There are enough players already");
			alertFail.showAndWait();
			return;
		}
		
		if (!tournamentHandler.canPlayerPay(tournament, userName)) {
			alertFail.setContentText("Not enough money in your account");
			alertFail.showAndWait();
			return;
		}
		moneyArea.setText(Double.toString(playerHandler.updateBalance(userName, tournament.getEntryFee(), -1)));
		tournamentHandler.enrollPlayer(tournament, userName);
		handleViewToursButton(tournamentTable);
		alertSuccess.setContentText("Successful enrollment");
		alertSuccess.showAndWait();
	}

	public void handleUpdateScoreButton(Match match) {
		GameView gameView = new GameView(match, userName);
		gameView.display();
	}

	public void handleSearchNameButton(TableView<Tournament> tournamentTable, TextField nameField) {
		tournamentTable.getItems().clear();
		Tournament tournament = tournamentHandler.findByName(nameField.getText());
		ObservableList<Tournament> toursList = FXCollections.observableArrayList();
		toursList.add(tournament);
		tournamentTable.setItems(toursList);
		tournamentTable.refresh();
	}

	public void handleSearchTypeButton(TableView<Tournament> tournamentTable, String type) {
		tournamentTable.getItems().clear();
		tournamentTable.setItems(tournamentHandler.convertToObservableList(tournamentHandler.findByType(type)));
		tournamentTable.refresh();
	}

	public void handleSearchStatusButton(TableView<Tournament> tournamentTable, String status) {
		tournamentTable.getItems().clear();
		tournamentTable.setItems(tournamentHandler.convertToObservableList(tournamentHandler.findByStatus(status)));
		tournamentTable.refresh();
	}

	public String getAccountBalance() {
		Player player = playerHandler.findPlayer(userName);
		return Double.toString(player.getBalance());
	}

	public void display(String title) {

		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

	}

}
