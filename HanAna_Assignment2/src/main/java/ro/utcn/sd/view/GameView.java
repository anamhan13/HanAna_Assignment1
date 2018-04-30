package ro.utcn.sd.view;

import java.io.IOException;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ro.utcn.sd.controller.GameController;
import ro.utcn.sd.model.Game;
import ro.utcn.sd.model.Match;

public class GameView {

	private Scene scene;
	private Stage primaryStage;

	@FXML
	private Button updateScoreButton;

	@FXML
	private Button viewGamesButton;

	@FXML
	private TableView<Game> gamesTable;

	private Match match;
	private String userName;

	public GameView(Match match, String userName) {
		this.primaryStage = new Stage();
		this.match = match;
		this.userName = userName;
	}

	@SuppressWarnings("unchecked")
	private void initializeObjects() {
		this.gamesTable = (TableView<Game>) scene.lookup("#gamesTable");
		this.updateScoreButton = (Button) scene.lookup("#updateScoreButton");
		this.viewGamesButton = (Button) scene.lookup("#viewGamesButton");
	}

	public TableView<Game> getGamesTable() {
		return gamesTable;
	}

	public void setGamesTable(TableView<Game> gamesTable) {
		this.gamesTable = gamesTable;
	}

	public void display() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/GameFXML.fxml"));
			scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();

			initializeObjects();

			GameController gameController = new GameController(userName);

			setGamesTable(gameController.createGameTable(gamesTable));

			
			gamesTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Game>() {
				public void onChanged(ListChangeListener.Change<? extends Game> c) {
					for (final Game game : c.getList()) {
						updateScoreButton.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								gameController.handleUpdateScoreButton(match, game);
								gameController.handleViewGamesButton(gamesTable, match);
							}
						});
					}
				}
			});

			viewGamesButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					gameController.handleViewGamesButton(gamesTable, match);
				}
			});

			gameController.display("Update game window");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
