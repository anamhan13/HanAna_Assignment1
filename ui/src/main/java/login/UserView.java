package login;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dao.connection.ConnectionFactory;

import handlers.MatchHandler;
import handlers.PlayerHandler;
import handlers.TournamentHandler;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Match;
import model.Player;
import model.Tournament;
import model.Winner;
import repository.PlayerRepository;

public class UserView {

	private Stage window;

	private Button closeButton;
	private Button viewMatchesButton;
	private Button viewToursButton;
	private Button updateInfoButton;
	private Button viewGamesButton;
	private Button startTourButton;
	//private Button registerTourButton;

	private TableView<Match> tableMatches;
	private TableView<Tournament> tableTournaments;

	private MatchHandler matchHandler;
	private TournamentHandler tournamentHandler;

	private UpdateInfoWindow infoWindow;
	
	private String username;
	private Connection connection;
	
	public UserView(String username) {
		connection = ConnectionFactory.getConnection();
		matchHandler = new MatchHandler();
		tournamentHandler = new TournamentHandler();
		this.username = username;
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

	
	
	public void display(String title) {
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(1, 1, 1, 1));
		gridPane.setVgap(4);
		gridPane.setHgap(5);

		viewMatchesButton = new Button("View matches");
		viewToursButton = new Button("View tournaments");
		updateInfoButton = new Button("Update info");
		viewGamesButton = new Button("View games");
		startTourButton = new Button("Start tournament");
		//registerTourButton = new Button("Register in tournament");
		closeButton = new Button("Close window");

		createMatchTable();
		createTournamentTable();

		GridPane.setConstraints(viewMatchesButton, 1, 0);
		GridPane.setConstraints(viewToursButton, 1, 1);
		GridPane.setConstraints(updateInfoButton, 1, 2);
		GridPane.setConstraints(viewGamesButton, 2, 0);
		GridPane.setConstraints(startTourButton, 0, 3);
		//GridPane.setConstraints(registerTourButton, 2, 1);
		GridPane.setConstraints(closeButton, 0, 2);
		GridPane.setConstraints(tableTournaments, 0, 1);

		infoWindow = new UpdateInfoWindow(this.username);

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

		updateInfoButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				infoWindow.display("Change account info");
			}
		});

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				window.close();
			}
		});


		tableMatches.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Match>() {
            public void onChanged(ListChangeListener.Change<? extends Match> c) {
            	for (final Match match : c.getList()) {
            		viewGamesButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							GameDisplayWindow gameDisplayWindow = new GameDisplayWindow(match,username);
							gameDisplayWindow.display("Display games");
						}
					});
            	}
            }
        });
		
		tableTournaments.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Tournament>() {
            public void onChanged(ListChangeListener.Change<? extends Tournament> c) {
            	for (final Tournament tournament : c.getList()) {
            		startTourButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent arg0) {
							
							PlayerHandler playerHandler = new PlayerHandler();
							List<Player> players = playerHandler.findPlayers();
							
							tournamentHandler = new TournamentHandler();
							Winner winner = tournamentHandler.startTournament(tournament, players);
							System.out.println(winner.toString());
						}
					});
            		
            		
            	}
            }
        });
		
		
		
		gridPane.getChildren().addAll(viewMatchesButton, viewToursButton, closeButton, tableMatches, tableTournaments,
				updateInfoButton, viewGamesButton, startTourButton);

		Scene scene = new Scene(gridPane, 900, 300);
		window.setScene(scene);
		window.showAndWait();

	}

}
