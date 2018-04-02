package handlers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.dao.connection.ConnectionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Match;
import repository.MatchRepository;

public class MatchHandler {

	private Connection connection;
	private MatchRepository matchRepository;

	public MatchHandler() {
		connection = ConnectionFactory.getConnection();
		matchRepository = new MatchRepository(connection);
	}

	public ObservableList<Match> getMatchesList() {

		List<Match> matchesList = new ArrayList<Match>();
		matchesList = matchRepository.findAll();

		if (matchesList.size() != 0) {

			ObservableList<Match> observableList = FXCollections.observableArrayList();
			for (Match match : matchesList) {
				observableList.add(match);
			}

			return observableList;
		}

		return null;
	}

}
