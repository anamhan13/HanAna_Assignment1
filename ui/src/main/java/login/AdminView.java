package login;

import java.util.Date;

import handlers.MatchHandler;
import handlers.PlayerHandler;
import handlers.TournamentHandler;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Match;
import model.Player;
import model.Tournament;

public class AdminView {

	private static Stage window;
	private static Button closeButton;

	private Button viewMatchesButton;
	private Button viewToursButton;
	private Button viewPlayersButton;
	private Button updateTourButton;
	private Button deleteTourButton;
	private Button deletePlayerButton;
	private Button createTourButton;
	
	private TableView<Match> tableMatches;
	private TableView<Tournament> tableTournaments;
	private TableView<Player> tablePlayers;

	private MatchHandler matchHandler;
	private TournamentHandler tournamentHandler;
	private PlayerHandler playerHandler;

	private TournamentInfoWindow infoWindow;
	
	private Alert alertSuccess;
	private Alert alertFail;

	public AdminView() {
		matchHandler = new MatchHandler();
		tournamentHandler = new TournamentHandler();
		playerHandler = new PlayerHandler();
	}

	private void createMatchTable() {
		tableMatches = new TableView<Match>();

		TableColumn<Match, Integer> idMatch = new TableColumn<Match, Integer>("IdMatch");
		idMatch.setMinWidth(150);
		idMatch.setCellValueFactory(new PropertyValueFactory<Match, Integer>("IdMatch"));

		TableColumn<Match, String> player1 = new TableColumn<Match, String>("Mail1");
		player1.setMinWidth(150);
		player1.setCellValueFactory(new PropertyValueFactory<Match, String>("Mail1"));

		TableColumn<Match, String> player2 = new TableColumn<Match, String>("Mail2");
		player2.setMinWidth(150);
		player2.setCellValueFactory(new PropertyValueFactory<Match, String>("Mail2"));

		TableColumn<Match, Integer> idTournament = new TableColumn<Match, Integer>("IdTournament");
		idTournament.setMinWidth(150);
		idTournament.setCellValueFactory(new PropertyValueFactory<Match, Integer>("IdTournament"));

		boolean addAll = tableMatches.getColumns().addAll(idMatch, player1, player2, idTournament);
	}

	private void createTournamentTable() {
		tableTournaments = new TableView<Tournament>();

		TableColumn<Tournament, Integer> idTournament = new TableColumn<Tournament, Integer>("IdTournament");
		idTournament.setMinWidth(100);
		idTournament.setCellValueFactory(new PropertyValueFactory<Tournament, Integer>("IdTournament"));

		TableColumn<Tournament, String> name = new TableColumn<Tournament, String>("Name");
		name.setMinWidth(100);
		name.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Name"));

		TableColumn<Tournament, String> status = new TableColumn<Tournament, String>("Status");
		status.setMinWidth(100);
		status.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Status"));

		TableColumn<Tournament, Date> dateStart = new TableColumn<Tournament, Date>("DateStart");
		dateStart.setMinWidth(100);
		dateStart.setCellValueFactory(new PropertyValueFactory<Tournament, Date>("DateStart"));

		TableColumn<Tournament, Date> dateFinish = new TableColumn<Tournament, Date>("DateFinish");
		dateFinish.setMinWidth(100);
		dateFinish.setCellValueFactory(new PropertyValueFactory<Tournament, Date>("DateFinish"));

		TableColumn<Tournament, String> place = new TableColumn<Tournament, String>("Place");
		place.setMinWidth(100);
		place.setCellValueFactory(new PropertyValueFactory<Tournament, String>("Place"));

		boolean addAll = tableTournaments.getColumns().addAll(idTournament, name, status, dateStart, dateFinish, place);
	}

	private void createPlayersTable() {
		tablePlayers = new TableView<Player>();

		TableColumn<Player, Integer> idPlayer = new TableColumn<Player, Integer>("IdPlayer");
		idPlayer.setMinWidth(100);
		idPlayer.setCellValueFactory(new PropertyValueFactory<Player, Integer>("IdPlayer"));

		TableColumn<Player, String> name = new TableColumn<Player, String>("Name");
		name.setMinWidth(100);
		name.setCellValueFactory(new PropertyValueFactory<Player, String>("Name"));

		TableColumn<Player, String> mail = new TableColumn<Player, String>("Mail");
		mail.setMinWidth(100);
		mail.setCellValueFactory(new PropertyValueFactory<Player, String>("Mail"));

		TableColumn<Player, String> password = new TableColumn<Player, String>("Password");
		password.setMinWidth(100);
		password.setCellValueFactory(new PropertyValueFactory<Player, String>("Password"));

		TableColumn<Player, String> address = new TableColumn<Player, String>("Address");
		address.setMinWidth(100);
		address.setCellValueFactory(new PropertyValueFactory<Player, String>("Address"));

		boolean addAll = tablePlayers.getColumns().addAll(idPlayer, name, mail, password, address);
	}
	
	public void display(String title) {
		window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(1, 1, 1, 1));
		gridPane.setVgap(4);
		gridPane.setHgap(5);

		createTourButton = new Button("Create tournament");
		viewMatchesButton = new Button("View matches");
		viewToursButton = new Button("View tournaments");
		viewPlayersButton = new Button("View players");
		updateTourButton = new Button("Update tournament info");
		deleteTourButton = new Button("Remove tournament");
		deletePlayerButton = new Button("Remove player");
		closeButton = new Button("Close window");

		
		createMatchTable();
		createTournamentTable();
		createPlayersTable();

		GridPane.setConstraints(viewMatchesButton, 1, 0);
		GridPane.setConstraints(viewToursButton, 1, 1);
		GridPane.setConstraints(updateTourButton, 2, 1);
		GridPane.setConstraints(viewPlayersButton, 1, 2);
		GridPane.setConstraints(deleteTourButton, 3, 1);
		GridPane.setConstraints(deletePlayerButton, 3, 2);
		
		GridPane.setConstraints(createTourButton, 2, 5);
		GridPane.setConstraints(closeButton, 1, 5);
		GridPane.setConstraints(tableTournaments, 0, 1);
		GridPane.setConstraints(tablePlayers, 0, 2);
		
		alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle("Information Dialog");
		alertSuccess.setHeaderText(null);

		alertFail = new Alert(AlertType.ERROR);
		alertFail.setTitle("Information Dialog");
		alertFail.setHeaderText(null);

		viewMatchesButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tableMatches.setItems(matchHandler.getMatchesList());
				tableMatches.refresh();
			}
		});

		viewToursButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tableTournaments.setItems(tournamentHandler.getTournamentsList());
				tableTournaments.refresh();
			}
		});
		
		viewPlayersButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tablePlayers.setItems(playerHandler.getPlayersList());
				tableTournaments.refresh();
			}
		});

		tableTournaments.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Tournament>() {
			public void onChanged(ListChangeListener.Change<? extends Tournament> c) {
				for (final Tournament tournament : c.getList()) {
					updateTourButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent arg0) {
							infoWindow = new TournamentInfoWindow(tournament.getIdTournament());
							infoWindow.display("Change tournament info");
						}
					});

				}
			}
		});
		
		tablePlayers.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Player>() {
			public void onChanged(ListChangeListener.Change<? extends Player> c) {
				for (final Player player : c.getList()) {
					deletePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent arg0) {
							if (playerHandler.deletePlayer(player)) {
								alertSuccess.setContentText("Player successfully deleted");
								alertSuccess.showAndWait();
							} else {
								alertFail.setContentText("Player couldn't be deleted");
								alertFail.showAndWait();
							}
						}
					});

				}
			}
		});
		
		tableTournaments.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Tournament>() {
			public void onChanged(ListChangeListener.Change<? extends Tournament> c) {
				for (final Tournament tournament : c.getList()) {
					deleteTourButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent arg0) {
							if (tournamentHandler.deleteTournament(tournament)) {
								alertSuccess.setContentText("Tournament successfully deleted");
								alertSuccess.showAndWait();
								tableTournaments.refresh();
							} else {
								alertFail.setContentText("Tournament couldn't be deleted");
								alertFail.showAndWait();
							}
						}
					});

				}
			}
		});

		
		createTourButton.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				CreateTourWindow createTourWindow = new CreateTourWindow();
				createTourWindow.display("Create tournament");
			}
		});

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				window.close();
			}
		});

		gridPane.getChildren().addAll(tablePlayers, tableMatches, tableTournaments, closeButton, updateTourButton, viewMatchesButton,
				viewToursButton, viewPlayersButton, deletePlayerButton, deleteTourButton, createTourButton);

		Scene scene = new Scene(gridPane, 1100, 700);
		window.setScene(scene);
		window.showAndWait();

	}
}
