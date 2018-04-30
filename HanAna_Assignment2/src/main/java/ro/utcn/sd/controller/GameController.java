package ro.utcn.sd.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ro.utcn.sd.business.GameHandler;
import ro.utcn.sd.model.Game;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.validators.GameEndValidator;

public class GameController {

	private Stage window;

	private String userName;
	//private PlayerHandler playerHandler;
	//private TournamentHandler tournamentHandler;
	private static Alert alertSuccess;
	private static Alert alertFail;

	public GameController(String username) {
		this.userName = username;
		//this.playerHandler = new PlayerHandler();
		//this.tournamentHandler = new TournamentHandler();
		alertFail = new Alert(AlertType.ERROR);
		alertSuccess = new Alert(AlertType.INFORMATION);
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public TableView<Game> createGameTable(TableView<Game> gameTable) {

		TableColumn<Game, Long> idGame = new TableColumn<Game, Long>("IdGame");
		idGame.setMinWidth(110);
		idGame.setCellValueFactory(new PropertyValueFactory<Game, Long>("IdGame"));

		TableColumn<Game, Integer> player1 = new TableColumn<Game, Integer>("Score1");
		player1.setMinWidth(110);
		player1.setCellValueFactory(new PropertyValueFactory<Game, Integer>("Score1"));

		TableColumn<Game, Integer> player2 = new TableColumn<Game, Integer>("Score2");
		player2.setMinWidth(110);
		player2.setCellValueFactory(new PropertyValueFactory<Game, Integer>("Score2"));

		TableColumn<Game, Long> idMatch = new TableColumn<Game, Long>("IdMatch");
		idMatch.setMinWidth(110);
		idMatch.setCellValueFactory(new PropertyValueFactory<Game, Long>("IdMatch"));

		TableColumn<Game, Integer> winner = new TableColumn<Game, Integer>("Winner");
		winner.setMinWidth(110);
		winner.setCellValueFactory(new PropertyValueFactory<Game, Integer>("Winner"));

		boolean addAll = gameTable.getColumns().addAll(idMatch, idGame, player1, player2, winner);
		return gameTable;
	}

	public void handleUpdateScoreButton(Match match, Game game) {
		GameHandler gameHandler = new GameHandler();

		if (userName.equals(match.getPlayer1().getMail()) || userName.equals(match.getPlayer2().getMail())) {

			if (GameEndValidator.detectGameEnd(game.getScore1(), game.getScore2()) == -1) {
				if (gameHandler.updateScore(match, userName, game)) {

					if (GameEndValidator.detectGameEnd(game.getScore1(), game.getScore2()) == 1) {
						gameHandler.setWinner(game, 1);
						
						String name = gameHandler.findWinner(game).get(0).getName();
						alertSuccess.setContentText(name + " won the game!");
						alertSuccess.showAndWait();
					} else if (GameEndValidator.detectGameEnd(game.getScore1(), game.getScore2()) == 2) {
						gameHandler.setWinner(game, 2);

						String name = gameHandler.findWinner(game).get(1).getName();
						alertSuccess.setContentText(name + " won the game!");
						alertSuccess.showAndWait();
					} else if (GameEndValidator.needRematch(game.getScore1(), game.getScore2())) {
						gameHandler.setWinner(game, -1);

						alertSuccess.setContentText("It's a tie!");
						alertSuccess.showAndWait();
					}
				} else {
					alertFail.setContentText("Cannot update score");
					alertFail.showAndWait();
				}
			} else {
				alertFail.setContentText("The game has already finished");
				alertFail.showAndWait();
			}
		} else {
			alertFail.setContentText("You are not allowed to update this score");
			alertFail.showAndWait();
		}
	}

	public void handleViewGamesButton(TableView<Game> gamesTable, Match match) {
		GameHandler gameHandler = new GameHandler();
		gamesTable.setItems(gameHandler.getGamesList(match.getIdMatch()));
		gamesTable.refresh();
	}

	public void display(String title) {

		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

	}

}
