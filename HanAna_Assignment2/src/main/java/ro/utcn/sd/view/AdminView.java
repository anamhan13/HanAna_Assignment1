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
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.utcn.sd.controller.AdminController;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.model.Tournament;

public class AdminView {

	private Stage stage;
	private Scene scene;

	@FXML
	private TableView<Player> playersTable;

	@FXML
	private TableView<Tournament> tournamentsTable;

	@FXML
	private SplitMenuButton searchTypeButton;

	@FXML
	private Button deleteTourButton;

	@FXML
	private Button depositButton;

	@FXML
	private Button withdrawButton;

	@FXML
	private Button deletePlayerButton;

	@FXML
	private Button createTourButton;

	@FXML
	private Button updateTourButton;

	@FXML
	private TextField moneyField;

	@FXML
	private Button refreshButton;

	public AdminView() {
		stage = new Stage();
	}

	@SuppressWarnings("unchecked")
	private void initializeObjects() {
		this.refreshButton = (Button) scene.lookup("#refreshButton");

		this.deleteTourButton = (Button) scene.lookup("#deleteTourButton");
		this.createTourButton = (Button) scene.lookup("#createTourButton");
		this.updateTourButton = (Button) scene.lookup("#updateTourButton");

		this.deletePlayerButton = (Button) scene.lookup("#deletePlayerButton");

		this.moneyField = (TextField) scene.lookup("#moneyField");

		this.depositButton = (Button) scene.lookup("#depositButton");
		this.withdrawButton = (Button) scene.lookup("#withdrawButton");

		this.searchTypeButton = (SplitMenuButton) scene.lookup("#searchTypeButton");

		this.playersTable = (TableView<Player>) scene.lookup("#playersTable");
		this.tournamentsTable = (TableView<Tournament>) scene.lookup("#tournamentsTable");
	}

	public TableView<Player> getPlayersTable() {
		return playersTable;
	}

	public void setPlayersTable(TableView<Player> playersTable) {
		this.playersTable = playersTable;
	}

	public TableView<Tournament> getTournamentsTable() {
		return tournamentsTable;
	}

	public void setTournamentsTable(TableView<Tournament> tournamentsTable) {
		this.tournamentsTable = tournamentsTable;
	}

	public void display() {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("/AdminFXML.fxml"));

			scene = new Scene(root);
			stage.setScene(scene);

			initializeObjects();

			AdminController adminController = new AdminController();

			setPlayersTable(adminController.createMatchTable(playersTable));
			setTournamentsTable(adminController.createTournamentTable(tournamentsTable));

			refreshButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					adminController.handleRefreshButton(playersTable, tournamentsTable);
				}
			});

			playersTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Player>() {
				public void onChanged(ListChangeListener.Change<? extends Player> c) {
					for (final Player player : c.getList()) {
						deletePlayerButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								adminController.handleDeletePlayerButton(player);
								adminController.handleRefreshButton(playersTable, tournamentsTable);
							}
						});
					}
				}
			});

			tournamentsTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Tournament>() {
				public void onChanged(ListChangeListener.Change<? extends Tournament> c) {
					for (final Tournament tournament : c.getList()) {
						deleteTourButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								adminController.handleDeleteTourButton(tournament);
								adminController.handleRefreshButton(playersTable, tournamentsTable);
							}
						});

					}
				}
			});

			tournamentsTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Tournament>() {
				public void onChanged(ListChangeListener.Change<? extends Tournament> c) {
					for (final Tournament tournament : c.getList()) {

						updateTourButton.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								adminController.handleUpdateTourButton(tournament);
								adminController.handleRefreshButton(playersTable, tournamentsTable);
							}
						});

					}
				}
			});

			createTourButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					adminController.handleCreateTourButton();
					adminController.handleRefreshButton(playersTable, tournamentsTable);
				}
			});

			playersTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Player>() {
				public void onChanged(ListChangeListener.Change<? extends Player> c) {
					for (final Player player : c.getList()) {
						withdrawButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								adminController.handleWithdrawButton(player, moneyField);
								adminController.handleRefreshButton(playersTable, tournamentsTable);
							}
						});
					}
				}
			});

			playersTable.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<Player>() {
				public void onChanged(ListChangeListener.Change<? extends Player> c) {
					for (final Player player : c.getList()) {
						depositButton.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								adminController.handleDepositButton(player, moneyField);
								adminController.handleRefreshButton(playersTable, tournamentsTable);
							}
						});
					}
				}
			});

			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
