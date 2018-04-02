package handlers;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.dao.connection.ConnectionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import model.Game;
import model.Match;
import model.Player;
import model.Tournament;
import model.Winner;
import repository.GameRepository;
import repository.MatchRepository;
import repository.PlayerRepository;
import repository.TournamentRepository;
import validation.TournamentValidation;

public class TournamentHandler {

	private Connection connection;
	private TournamentRepository tournamentRepository;

	public TournamentHandler() {
		connection = ConnectionFactory.getConnection();
		tournamentRepository = new TournamentRepository(connection);
	}

	public ObservableList<Tournament> getTournamentsList() {
		List<Tournament> toursList = new ArrayList<Tournament>();
		toursList = tournamentRepository.findAll();

		if (toursList.size() != 0) {
			ObservableList<Tournament> observableList = FXCollections.observableArrayList();
			for (Tournament match : toursList)
				observableList.add(match);

			return observableList;
		}
		return null;
	}

	public boolean registerInTournament(Tournament tournament, String user) {
		MatchRepository matchRepository = new MatchRepository(connection);
		List<Match> matchesList = matchRepository.findByPlayer(user);

		PlayerRepository playerRepository = new PlayerRepository(connection);
		Player player = playerRepository.find("mail", user);

		int updated = -1;

		if (TournamentValidation.canPlayerRegister(tournament)) {
			if (matchesList.size() != 0) {
				for (Match match : matchesList) {
					if (match.getIdTournament() == tournament.getIdTournament()) {
						return false;
					}
				}

				tournament.getPlayersList().add(player);
				if (tournament.getPlayersList().size() == 8) {
					tournament.setStatus("closed registration");
					updated = tournamentRepository.update(tournament, tournament.getIdTournament());
					if (updated == -1)
						return false;
				}
			}
		}

		List<Pair<Integer, Integer>> pairs = generateMatches();
		List<Match> establishedMatches = new ArrayList<Match>();

		int inserted = -1;

		for (Pair<Integer, Integer> pair : pairs) {
			Match match = new Match(matchesList.get(pair.getKey()).getMail1(),
					matchesList.get(pair.getValue()).getMail2(), tournament.getIdTournament());

			inserted = matchRepository.insert(match);
			if (inserted != -1)
				establishedMatches.add(match);
		}

		if (establishedMatches.size() != 4)
			return false;

		return true;
	}

	public boolean openRegistration(Tournament tournament) {
		if (!tournament.getStatus().equals("canceled")) {
			if (tournament.getStatus().equals("not yet")) {
				tournament.setStatus("open registration");
				return true;
			}
		}
		return false;
	}

	public boolean closeRegistration(Tournament tournament) {
		if (!tournament.getStatus().equals("canceled")) {
			if (tournament.getStatus().equals("open registration")) {
				if (tournament.getPlayersList().size() == 8) {
					tournament.setStatus("closed registration");
					return true;
				}
			}
		}
		return false;
	}

	private List<Pair<Integer, Integer>> generateMatches() {
		List<Pair<Integer, Integer>> matchesList = new ArrayList<Pair<Integer, Integer>>();

		List<Integer> randoms = Arrays.asList(4, 5, 6, 7);

		List<Integer> clonedList = new ArrayList<Integer>();
		clonedList.addAll(randoms);
		Collections.shuffle(clonedList);

		for (int i = 0; i < 4; i++) {
			Pair<Integer, Integer> pair = new Pair<Integer, Integer>(new Integer(i), clonedList.get(i));
			matchesList.add(i, pair);
		}

		return matchesList;
	}

	public boolean changeStatus(String status, int id) {

		Tournament tournament = tournamentRepository.find("idtournament", id);
		tournament.setStatus(status);

		int updated = tournamentRepository.update(tournament, id);

		if (updated > 0)
			return true;
		return false;
	}

	public boolean changePlace(String place, int id) {

		Tournament tournament = tournamentRepository.find("idtournament", id);
		tournament.setPlace(place);

		int updated = tournamentRepository.update(tournament, id);

		if (updated > 0)
			return true;
		return false;
	}

	public boolean deleteTournament(Tournament tournament) {
		MatchRepository matchRepository = new MatchRepository(connection);
		List<Match> matches = matchRepository.findByTournament(tournament.getIdTournament());

		int deleted = -1;
		if (matches == null) {
			deleted = tournamentRepository.delete(tournament.getIdTournament());
			if (deleted == -1)
				return false;
			else
				return true;
		}
		return false;
	}

	public boolean createTournament(String name, String status, String dateStart, String dateFinish, String place) {

		Tournament tournament;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tournament = new Tournament(name, status, formatter.parse(dateStart), formatter.parse(dateFinish), place);
			if (!TournamentValidation.areDatesCronological(tournament))
				return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		int inserted = tournamentRepository.insert(tournament);
		if (inserted == -1)
			return false;
		return true;
	}

	private Winner processMatch(Tournament tournament, Match match) {
		MatchRepository matchRepository = new MatchRepository(connection);
		GameRepository gameRepository = new GameRepository(connection);

		Game game;
		int inserted = -1;
		for (int i = 0; i < 3; i++) {
			game = new Game(match.getIdMatch(), 0, 0);
			inserted = gameRepository.insert(game);
			if (inserted != -1) {
				
			}
		}
		return null;
	}

	public Winner startTournament(Tournament tournament, List<Player> players) {

		if (tournament.getStatus().equals("closed registration")) {
			if (players.size() == 8) {
				tournament.setStatus("quarter finals");
				int updated = tournamentRepository.update(tournament, tournament.getIdTournament());

				List<Pair<Integer, Integer>> pairs;
				MatchRepository matchRepository = new MatchRepository(connection);
				Match match;
				if (updated != -1) {
					pairs = generateMatches();

					List<Match> establishedMatches = new ArrayList<Match>();

					int inserted = -1;

					for (Pair<Integer, Integer> pair : pairs) {
						match = new Match(players.get(pair.getKey()).getMail(), players.get(pair.getValue()).getMail(),
								tournament.getIdTournament());

						inserted = matchRepository.insert(match);
						if (inserted != -1)
							establishedMatches.add(match);
					}

					if (establishedMatches.size() == 4) {
						tournament.setMatchesList(establishedMatches);
					}

					for (Match match2 : establishedMatches) {
						System.out.println(match2.getMail1() + "   " + match2.getMail2());

					}

				}
			}
		}
		return Winner.PLAYER1;
	}

}
