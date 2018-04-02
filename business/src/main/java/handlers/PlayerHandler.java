package handlers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.dao.connection.ConnectionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Match;
import model.Player;
import repository.MatchRepository;
import repository.PlayerRepository;
import security.Authentication;
import validation.UserLoginValidation;

public class PlayerHandler {

	private Connection connection;
	private PlayerRepository playerRepository;

	public PlayerHandler() {
		connection = ConnectionFactory.getConnection();
		playerRepository = new PlayerRepository(connection);
	}

	public ObservableList<Player> getPlayersList() {

		List<Player> playersList = new ArrayList<Player>();
		playersList = playerRepository.findAll();

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
		PlayerRepository playerRepository = new PlayerRepository(connection);
		Player admin = playerRepository.find("mail", "anahan@gmail.com");

		if (admin != null)
			if (admin.getMail().equals(mail))
				return true;
		return false;
	}

	public boolean changePassword(String mail, String password) {
		PlayerRepository playerRepository = new PlayerRepository(connection);
		Player player = playerRepository.find("mail", mail);

		Authentication authentication = new Authentication();
		if (authentication.isPasswordStrong(password)) {
			player.setPassword(password);
			int updated = playerRepository.update(player, mail);

			if (updated > 0)
				return true;
			return false;
		}
		return false;
	}

	public boolean changeAddress(String mail, String address) {
		PlayerRepository playerRepository = new PlayerRepository(connection);
		Player player = playerRepository.find("mail", mail);

		player.setAddress(address);
		int updated = playerRepository.update(player, mail);

		if (updated > 0)
			return true;
		return false;
	}

	public boolean deletePlayer(Player player) {
		MatchRepository matchRepository = new MatchRepository(connection);
		List<Match> matches = matchRepository.findByPlayer(player.getMail());

		int deleted = -1;
		if (matches == null) {
			deleted = playerRepository.delete(player.getMail());
			if (deleted == -1)
				return false;
			else
				return true;
		}
		return false;
	}

	public boolean createAccount(String name, String mail, String password, String address) {

		UserLoginValidation userLoginValidation = new UserLoginValidation(mail, password, connection);
		Authentication authentication = new Authentication();
	
		Player player = new Player(name, mail, password, address, false);
		
		player.setNewPlayer(true);
		if (!authentication.isPasswordStrong(password) && !userLoginValidation.isMailValid())
			return false;

		int inserted = playerRepository.insert(player);
		System.out.println(inserted);
		if (inserted == -1)
			return false;

		return true;
	}
	
	public List<Player> findPlayers() {
		List<Player> players = new ArrayList<>();
		
		Player player = new Player(false);
		
		for (int i=0; i<8;i++) {
			player = playerRepository.findById(i+2);
			players.add(player);
		}
		return players;
	}
}
