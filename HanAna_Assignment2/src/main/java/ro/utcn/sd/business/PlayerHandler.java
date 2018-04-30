package ro.utcn.sd.business;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.dao.PlayerDao;
import ro.utcn.sd.dao.factory.DaoFactory;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.validators.Authentication;
import ro.utcn.sd.validators.UserLoginValidator;

public class PlayerHandler {

	private PlayerDao playerDao;
	private final String ADMIN = "anahan@gmail.com";

	public PlayerHandler() {
		this.playerDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getPlayerDao();
		;
	}

	public ObservableList<Player> getPlayersList() {

		List<Player> playersList = new ArrayList<Player>();
		playersList = playerDao.findAll();

		if (playersList.size() != 0) {

			ObservableList<Player> observableList = FXCollections.observableArrayList();
			for (Player player : playersList) {
				observableList.add(player);
			}
			return observableList;
		}
		return null;
	}

	public boolean checkIfAdmin(String mail) {
		Player admin = playerDao.findByMail(ADMIN);

		if (admin != null)
			if (admin.getMail().equals(mail))
				return true;
		return false;
	}

	public boolean changePassword(String mail, String password) {
		Player player = playerDao.findByMail(mail);
		int updated = 1;
		Authentication authentication = new Authentication();
		if (authentication.isPasswordStrong(password)) {
			player.setPassword(password);
			playerDao.update(player);

			if (updated > 0)
				return true;
			return false;
		}
		return false;
	}

	public double updateBalance(String mail, double value, int deposit) {
		Player player = findPlayer(mail);
		switch (deposit) {
		case 1:
			player.setBalance(player.getBalance() + value);
			break;
		case 0:
			player.setBalance(value);
			break;
		case -1:
			System.out.println(value);
			System.out.println(player.getBalance() - value);
			player.setBalance(player.getBalance() - value);
			break;
		}

		playerDao.update(player);
		return player.getBalance();
	}

	public boolean deletePlayer(Player player) {
		MatchDao matchDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getMatchDao();

		List<Match> matches = matchDao.findByPlayer(player);

		if (matches.size() == 0) {
			playerDao.delete(player);
			return true;
		}
		return false;
	}

	public boolean createAccount(String name, String mail, String password, String address, double balance) {

		UserLoginValidator userLoginValidation = new UserLoginValidator(mail, password);
		Authentication authentication = new Authentication();

		Player player = new Player(name, mail, password, address, false, balance);

		if (!authentication.isPasswordStrong(password) && !userLoginValidation.isMailValid())
			return false;

		int inserted = 1;
		playerDao.insert(player);
		System.out.println(inserted);
		if (inserted == -1)
			return false;

		return true;
	}

	public Player findPlayer(String userName) {
		return playerDao.findByMail(userName);
	}

	public List<Player> findPlayers() {
		List<Player> players = new ArrayList<>();

		Player player = new Player(false);

		for (int i = 0; i < 8; i++) {
			player = playerDao.find(i + 2);
			players.add(player);
		}
		return players;
	}
}
