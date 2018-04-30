package ro.utcn.sd.controller;

import java.util.Date;

import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.utcn.sd.business.PlayerHandler;
import ro.utcn.sd.business.TournamentHandler;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.model.Tournament;
import ro.utcn.sd.view.CreateTourView;
import ro.utcn.sd.view.UpdateTourView;

public class AdminController {
	
	private Stage window;
	private PlayerHandler playerHandler;
	private TournamentHandler tournamentHandler;
	private static Alert alertSuccess;
	private static Alert alertFail;

	public AdminController() {
		this.playerHandler = new PlayerHandler();
		this.tournamentHandler = new TournamentHandler();
		alertFail = new Alert(AlertType.ERROR);
		alertSuccess = new Alert(AlertType.INFORMATION);
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	public TableView<Player> createMatchTable(TableView<Player> playerTable) {

		TableColumn<Player, Long> idPlayer = new TableColumn<Player, Long>("Id");
		idPlayer.setMinWidth(128);
		idPlayer.setCellValueFactory(new PropertyValueFactory<Player, Long>("Id"));

		TableColumn<Player, String> name = new TableColumn<Player, String>("Name");
		name.setMinWidth(128);
		name.setCellValueFactory(new PropertyValueFactory<Player, String>("Name"));

		TableColumn<Player, String> mail = new TableColumn<Player, String>("Mail");
		mail.setMinWidth(128);
		mail.setCellValueFactory(new PropertyValueFactory<Player, String>("Mail"));

		TableColumn<Player, Double> balance = new TableColumn<Player, Double>("Balance");
		balance.setMinWidth(128);
		balance.setCellValueFactory(new PropertyValueFactory<Player, Double>("Balance"));

		boolean addAll = playerTable.getColumns().addAll(idPlayer, name, mail, balance);
		return playerTable;
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
	
	public void handleRefreshButton(TableView<Player> playerTable, TableView<Tournament> tournamentTable) {
		tournamentTable.setItems(tournamentHandler.getTournamentsList());
		tournamentTable.refresh();
		
		playerTable.setItems(playerHandler.getPlayersList());
		playerTable.refresh();
	} 
	
	public void handleDeletePlayerButton(Player player) {
		if (playerHandler.deletePlayer(player)) {
			alertSuccess.setContentText("Successfully deleted");
			alertSuccess.showAndWait();
		} else {
			alertFail.setContentText("Player couldn't be deleted");
			alertFail.showAndWait();
		}	
	} 
	
	public void handleDeleteTourButton(Tournament tournament) {
		if (tournamentHandler.deleteTournament(tournament)) {
			alertSuccess.setContentText("Successfully deleted");
			alertSuccess.showAndWait();
		} else {
			alertFail.setContentText("Tournament couldn't be deleted");
			alertFail.showAndWait();
		}
	} 
	
	public void handleUpdateTourButton(Tournament tournament) {
		UpdateTourView updateTourView = new UpdateTourView(tournament);
		updateTourView.display("Update tournament window");
	} 

	public void handleWithdrawButton(Player player, TextField money) {
		double value = Double.parseDouble(money.getText());
		playerHandler.updateBalance(player.getMail(), value, -1);
	} 
	
	public void handleDepositButton(Player player, TextField money) {
		double value = Double.parseDouble(money.getText());
		playerHandler.updateBalance(player.getMail(), value, 1);
	}
	
	public void handleCreateTourButton() {
		CreateTourView createTourView = new CreateTourView();
		createTourView.display();
	} 
	
	public void display(String title) {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
	} 
}
