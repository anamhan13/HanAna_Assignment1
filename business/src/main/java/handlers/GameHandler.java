package handlers;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.dao.connection.ConnectionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Game;
import model.Match;
import model.Player;
import model.Winner;
import repository.GameRepository;
import repository.MatchRepository;
import repository.PlayerRepository;

public class GameHandler {

	private Connection connection;
	private GameRepository gameRepository;
	private MatchRepository matchRepository;

	public GameHandler() {
		connection = ConnectionFactory.getConnection();
		gameRepository = new GameRepository(connection);
		matchRepository = new MatchRepository(connection);
	}

	public ObservableList<Game> getGamesList(int idMatch) {

		List<Game> gamesList = new ArrayList<Game>();
		gamesList = gameRepository.findByMatch(idMatch);

		if (gamesList.size() != 0) {

			ObservableList<Game> observableList = FXCollections.observableArrayList();
			for (Game game : gamesList) {
				observableList.add(game);
			}
			return observableList;
		}
		return null;
	}

	public boolean updateScore(Match usermMatch, String mail, Game game) {
		
		Match match = matchRepository.find("idmatch", usermMatch.getIdMatch());
		int updated = -1;
		
		if (mail.equals(match.getMail1())) {
			
			game.setScore1(game.getScore1() + 1);
			updated = gameRepository.update(game, game.getIdGame());
		} else if (mail.equals(match.getMail2())) {
			game.setScore2(game.getScore2() + 1);
			updated = gameRepository.update(game, game.getIdGame());
		}
		
		if (updated == -1)
			return false;
		return true;
	}
	
	public void setWinner(Game game, Winner winner) { 
		game.setWinner(winner);
		
	}
	
	public List<Player> findWinner(Game game){
		Match match = new Match();
		match = matchRepository.find("idmatch", game.getIdMatch());
		
		List<Player> players = new ArrayList<>();
		PlayerRepository playerRepository = new PlayerRepository(connection);
		Player player = new Player(false);
		
		player = playerRepository.find("mail", match.getMail1());
		players.add(player);
		player = playerRepository.find("mail", match.getMail2());
		
		return players;
		
	}

}
