package login;

import handlers.GameHandler;
import handlers.MatchHandler;
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
import model.Game;
import model.Match;
import model.Winner;
import validation.GameEndValidation;

public class GameDisplayWindow {

	private static Stage window;

	private Button viewGamesButton;
	private Button updateScoreButton;
	private Button closeButton;

	private TableView<Game> tableGames;

	private GameHandler gameHandler;
	private MatchHandler matchHandler;
	private TournamentHandler tournamentHandler;

	private UpdateInfoWindow infoWindow;

	private Match userMatch;
	private String mail;

	private Alert alertSuccess;
	private Alert alertFail;

	public GameDisplayWindow(Match userMatch, String mail) {
		gameHandler = new GameHandler();
		matchHandler = new MatchHandler();
		tournamentHandler = new TournamentHandler();
		this.userMatch = userMatch;
		this.mail = mail;
	}

	private void createGameTable() {
		tableGames = new TableView<Game>();

		TableColumn<Game, Integer> idGame = new TableColumn<Game, Integer>("IdGame");
		idGame.setMinWidth(100);
		idGame.setCellValueFactory(new PropertyValueFactory<Game, Integer>("IdGame"));

		TableColumn<Game, Integer> score1 = new TableColumn<Game, Integer>("Score1");
		score1.setMinWidth(100);
		score1.setCellValueFactory(new PropertyValueFactory<Game, Integer>("Score1"));

		TableColumn<Game, Integer> score2 = new TableColumn<Game, Integer>("Score2");
		score2.setMinWidth(100);
		score2.setCellValueFactory(new PropertyValueFactory<Game, Integer>("Score2"));

		TableColumn<Game, Integer> idMatch = new TableColumn<Game, Integer>("IdMatch");
		idMatch.setMinWidth(100);
		idMatch.setCellValueFactory(new PropertyValueFactory<Game, Integer>("IdMatch"));

		boolean addAll = tableGames.getColumns().addAll(idGame, score1, score2, idMatch);
	}

	public void display(String title) {
		window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(10);

		viewGamesButton = new Button("View games");
		updateScoreButton = new Button("Update score");
		closeButton = new Button("Close window");

		createGameTable();

		alertSuccess = new Alert(AlertType.INFORMATION);
		alertSuccess.setTitle("Information Dialog");
		alertSuccess.setHeaderText(null);

		alertFail = new Alert(AlertType.ERROR);
		alertFail.setTitle("Information Dialog");
		alertFail.setHeaderText(null);

		GridPane.setConstraints(tableGames, 0, 0);
		GridPane.setConstraints(viewGamesButton, 1, 0);
		GridPane.setConstraints(updateScoreButton, 1, 2);
		GridPane.setConstraints(closeButton, 0, 2);

		viewGamesButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tableGames.setItems(gameHandler.getGamesList(userMatch.getIdMatch()));
				tableGames.refresh();
			}
		});

		//AICI MAI TREBE SA ITI CREEZI MECIURI IN CAZ CA MAI TREBUIE JUCATE, DUPA CE SE AFAL SCORUL UNUIA
		tableGames.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Game>() {
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends Game> c) {
				for (final Game game : c.getList()) {
					updateScoreButton.setOnAction(new EventHandler<ActionEvent>() {
						public void handle(ActionEvent event) {
							if (mail.equals(userMatch.getMail1()) || mail.equals(userMatch.getMail2())) {

								if (GameEndValidation.detectGameEnd(game.getScore1(), game.getScore2()) == -1) {
									if (gameHandler.updateScore(userMatch, mail, game)) {
										alertSuccess.setContentText("Successful score update");
										alertSuccess.showAndWait();
									} else {
										alertFail.setContentText("Cannot update score");
										alertFail.showAndWait();
									}
								} else {
									if (GameEndValidation.detectGameEnd(game.getScore1(), game.getScore2())==1) {
										gameHandler.setWinner(game, Winner.PLAYER1);
										
										String name = gameHandler.findWinner(game).get(0).getName();
										alertSuccess.setContentText(name + " won the game!");
										alertSuccess.showAndWait();
									}
									else if (GameEndValidation.detectGameEnd(game.getScore1(), game.getScore2())==2) {
										gameHandler.setWinner(game,Winner.PLAYER2);
										
										String name = gameHandler.findWinner(game).get(1).getName();
										alertSuccess.setContentText(name + " won the game!");
										alertSuccess.showAndWait();
									}
									else if (GameEndValidation.needRematch(game.getScore1(), game.getScore2())) {
										gameHandler.setWinner(game,Winner.TIE);
									
										alertSuccess.setContentText("It's a tie!");
										alertSuccess.showAndWait();
									}
								}
							} else {
								alertFail.setContentText("You are not allowed to update this score");
								alertFail.showAndWait();
							}
						}
					});
				}
			}
		});

		closeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				window.close();
			}
		});

		gridPane.getChildren().addAll(closeButton, tableGames, updateScoreButton, viewGamesButton);

		Scene scene = new Scene(gridPane, 600, 200);
		window.setScene(scene);
		window.showAndWait();

	}

}
