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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.utcn.sd.controller.UserController;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.model.Tournament;

public class UserView {

	private String username;
	private Scene scene;
	private Stage primaryStage;

	@FXML
	private TableView<Match> matchTable;
	@FXML
	private TableView<Tournament> tournamentTable;

	@FXML
	private Button viewMatchesButton;

	@FXML
	private Button viewToursButton;

	@FXML
	private MenuButton searchStatusButton;

	@FXML
	private MenuButton searchTypeButton;

	@FXML
	private Button enrollButton;

	@FXML
	private Button depositButton;

	@FXML
	private Button updateScoreButton;

	@FXML
	private Button searchNameButton;

	@FXML
	private TextArea moneyArea;

	@FXML
	private TextField nameField;

	@FXML
	private TextField moneyField;

	private MenuItem upcoming;
	private MenuItem ongoing;
	private MenuItem finished;
	private MenuItem cancelled;

	private MenuItem paid;
	private MenuItem free;

	public UserView(String username) {
		this.username = username;
		this.primaryStage = new Stage();
	}

	public TableView<Match> getMatchTable() {
		return matchTable;
	}

	public TableView<Tournament> getTournamentTable() {
		return tournamentTable;
	}

	public void setMatchTable(TableView<Match> matchTable) {
		this.matchTable = matchTable;
	}

	public void setTournamentTable(TableView<Tournament> tournamentTable) {
		this.tournamentTable = tournamentTable;
	}

	@SuppressWarnings("unchecked")
	private void initializeObjects() {
		this.viewMatchesButton = (Button) scene.lookup("#viewMatchesButton");
		this.viewToursButton = (Button) scene.lookup("#viewToursButton");

		this.enrollButton = (Button) scene.lookup("#enrollButton");
		this.updateScoreButton = (Button) scene.lookup("#updateScoreButton");

		this.searchNameButton = (Button) scene.lookup("#searchNameButton");
		this.depositButton = (Button) scene.lookup("#depositButton");

		this.matchTable = (TableView<Match>) scene.lookup("#matchTable");
		this.tournamentTable = (TableView<Tournament>) scene.lookup("#tournamentTable");

		this.searchTypeButton = (MenuButton) scene.lookup("#searchTypeButton");
		this.searchStatusButton = (MenuButton) scene.lookup("#searchStatusButton");

		this.moneyField = (TextField) scene.lookup("#moneyField");
		this.nameField = (TextField) scene.lookup("#nameField");

		this.moneyArea = (TextArea) scene.lookup("#moneyArea");

		this.upcoming = new MenuItem("Upcoming");
		this.ongoing = new MenuItem("Ongoing");
		this.finished = new MenuItem("Finished");
		this.cancelled = new MenuItem("Cancelled");

		searchStatusButton.getItems().addAll(upcoming, ongoing, finished, cancelled);

		this.free = new MenuItem("Free");
		this.paid = new MenuItem("Paid");

		searchTypeButton.getItems().addAll(free, paid);

	}

	public void display() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/UserFXML.fxml"));
			scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();

			UserController userController = new UserController(username);

			initializeObjects();

			setMatchTable(userController.createMatchTable(this.getMatchTable()));
			setTournamentTable(userController.createTournamentTable(this.getTournamentTable()));

			viewMatchesButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userController.handleViewMatchesButton(matchTable);
				}
			});

			viewToursButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userController.handleViewToursButton(tournamentTable);
				}
			});

			moneyArea.setText(userController.getAccountBalance());

			depositButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userController.handleDepositButton(moneyField, moneyArea);
				}
			});

			searchNameButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (!nameField.getText().trim().isEmpty())
						userController.handleSearchNameButton(tournamentTable, nameField);
				}
			});

			upcoming.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userController.handleSearchStatusButton(tournamentTable, "Upcoming");
				}
			});

			ongoing.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userController.handleSearchStatusButton(tournamentTable, "Upcoming");
				}
			});

			finished.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userController.handleSearchStatusButton(tournamentTable, "Finished");
				}
			});

			cancelled.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					userController.handleSearchStatusButton(tournamentTable, "Cancelled");
				}
			});

			free.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					userController.handleSearchTypeButton(tournamentTable, "Free");
				}
			});

			paid.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					userController.handleSearchTypeButton(tournamentTable, "Paid");
				}
			});

			matchTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Match>() {
				public void onChanged(ListChangeListener.Change<? extends Match> c) {
					for (final Match match : c.getList()) {
						updateScoreButton.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								userController.handleUpdateScoreButton(match);
							}
						});
					}
				}
			});

			tournamentTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Tournament>() {
				public void onChanged(ListChangeListener.Change<? extends Tournament> c) {
					for (final Tournament tour : c.getList()) {
						enrollButton.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								userController.handleEnrollButton(tour, moneyArea, tournamentTable);
							}
						});
					}
				}
			});

			userController.display("User window");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
