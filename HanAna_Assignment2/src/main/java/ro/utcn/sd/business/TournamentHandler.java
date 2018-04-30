package ro.utcn.sd.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.dao.PlayerDao;
import ro.utcn.sd.dao.TournamentDao;
import ro.utcn.sd.dao.factory.DaoFactory;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.model.Tournament;
import ro.utcn.sd.validators.TournamentValidator;

public class TournamentHandler {

	private TournamentDao tournamentDao;
	private PlayerHandler playerHandler;

	public TournamentHandler() {
		tournamentDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getTournamentDao();
		playerHandler = new PlayerHandler();
	}

	public ObservableList<Tournament> getTournamentsList() {
		List<Tournament> toursList = new ArrayList<Tournament>();
		toursList = tournamentDao.findAll();

		if (toursList.size() != 0) {
			ObservableList<Tournament> observableList = FXCollections.observableArrayList();
			for (Tournament tournament : toursList)
				observableList.add(tournament);

			return observableList;
		}
		return null;
	}

	public ObservableList<Tournament> convertToObservableList(List<Tournament> tournaments) {

		if (tournaments.size() != 0) {
			ObservableList<Tournament> observableList = FXCollections.observableArrayList();
			for (Tournament tournament : tournaments)
				observableList.add(tournament);

			return observableList;
		}
		return null;
	}

	public boolean registerInTournament(Tournament tournament, String user) {
		PlayerDao playerDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getPlayerDao();
		Player player = playerDao.findByMail(user);

		MatchDao matchDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getMatchDao();
		List<Match> matchesList = matchDao.findByPlayer(player);

		int updated = 1;

		if (TournamentValidator.canPlayerRegister(tournament)) {
			if (matchesList.size() != 0) {
				for (Match match : matchesList) {
					if (match.getTournament().getIdTournament() == tournament.getIdTournament()) {
						return false;
					}
				}

				tournament.getPlayersList().add(player);
				if (tournament.getPlayersList().size() == 8) {
					tournament.setStatus("closed registration");
					tournamentDao.update(tournament);
					if (updated == -1)
						return false;
				}
			}
		}

		List<Pair<Integer, Integer>> pairs = generateMatches();
		List<Match> establishedMatches = new ArrayList<Match>();

		int inserted = 1;

		for (Pair<Integer, Integer> pair : pairs) {
			// Match match = new
			// Match(matchesList.get(pair.getKey()).getPlayer1().getMail(),
			// matchesList.get(pair.getValue()).getPlayer2().getMail(),
			// tournament.getIdTournament());

			// NU-I GATA AICI
			Match match = new Match();
			matchDao.insert(match);
			if (inserted != -1)
				establishedMatches.add(match);
		}

		if (establishedMatches.size() != 4)
			return false;

		return true;
	}

	// public boolean openRegistration(Tournament tournament) {
	// if (!tournament.getStatus().equals("canceled")) {
	// if (tournament.getStatus().equals("not yet")) {
	// tournament.setStatus("open registration");
	// return true;
	// }
	// }
	// return false;
	// }
	//
	// public boolean closeRegistration(Tournament tournament) {
	// if (!tournament.getStatus().equals("canceled")) {
	// if (tournament.getStatus().equals("open registration")) {
	// if (tournament.getPlayersList().size() == 8) {
	// tournament.setStatus("closed registration");
	// return true;
	// }
	// }
	// }
	// return false;
	// }

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

	public boolean changeEntryFee(double value, long id) {
		Tournament tournament = tournamentDao.find(id);
		tournament.setEntryFee(value);

		tournamentDao.update(tournament);

		return true;
	}

	public boolean changeStatus(String status, long id) {

		Tournament tournament = tournamentDao.find(id);

		if (status.equals("Upcoming"))
			return false;
		if (status.equals("Ongoing") && tournament.getStatus().equals("Finished"))
			return false;
		if (status.equals("Finished") && tournament.getWinner() == -1)
			return false;
		tournament.setStatus(status);

		tournamentDao.update(tournament);
		return true;
	}

	public boolean changePlace(String place, long id) {

		Tournament tournament = tournamentDao.find(id);
		tournament.setPlace(place);
		
		tournamentDao.update(tournament);

		return true;
	}

	public boolean deleteTournament(Tournament tournament) {
		MatchDao matchDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getMatchDao();
		List<Match> matches = matchDao.findByTournament(tournament);

		if (matches.size() == 0) {
			tournamentDao.delete(tournament);
			return true;
		}
		return false;
	}

	public boolean createTournament(String name, String entryFee, String dateStart, String dateFinish, String place) {

		Tournament tournament = new Tournament();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		double value = Double.parseDouble(entryFee);
		try {
			
			tournament = new Tournament(name, "Upcoming", formatter.parse(dateStart), formatter.parse(dateFinish), place,
					false);
			if (value!=0) {
				tournament.setPaid(true);
				tournament.setEntryFee(value);
				tournament.setPrize(value);
			}
			if (!TournamentValidator.areDatesCronological(tournament))
				return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		int inserted = 1;
		tournamentDao.insert(tournament);
		if (inserted == -1)
			return false;
		return true;
	}

	public Tournament findByName(String name) {
		return tournamentDao.findByName(name);
	}

	public List<Tournament> findByType(String type) {
		return tournamentDao.findByType(type);
	}

	public List<Tournament> findByStatus(String status) {
		return tournamentDao.findByStatus(status);
	}

	@SuppressWarnings("unused")
	public boolean isPlayerEnrolled(Tournament tournament, String userName) {
		Player player = playerHandler.findPlayer(userName);
		for (Player p : tournament.getPlayersList())
			if (p.getMail().equals(userName))
				return true;
		return false;
	}

	public boolean canPlayerPay(Tournament tournament, String userName) {
		Player player = playerHandler.findPlayer(userName);
		if (!tournament.isPaid())
			return true;
		if (tournament.getEntryFee() > player.getBalance())
			return false;
		return true;
	}

	public boolean enoughPlayers(Tournament tournament) {
		if (tournament.getPlayersList().size() > 7)
			return true;
		return false;
	}

	public void enrollPlayer(Tournament tournament, String userName) {
		Player player = playerHandler.findPlayer(userName);
		tournament.getPlayersList().add(player);
		tournamentDao.update(tournament);
		player.getTournamentsList().add(tournament);
		if (tournament.isPaid()) {
			player.setBalance(player.getBalance() - tournament.getEntryFee());
			tournament.setPrize(tournament.getPrize() + tournament.getEntryFee());
			tournamentDao.update(tournament);
		}
		PlayerDao playerDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getPlayerDao();
		playerDao.update(player);
	}
}
