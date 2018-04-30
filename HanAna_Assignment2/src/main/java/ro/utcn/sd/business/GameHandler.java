package ro.utcn.sd.business;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ro.utcn.sd.dao.GameDao;
import ro.utcn.sd.dao.MatchDao;
import ro.utcn.sd.dao.PlayerDao;
import ro.utcn.sd.dao.factory.DaoFactory;
import ro.utcn.sd.model.Game;
import ro.utcn.sd.model.Match;
import ro.utcn.sd.model.Player;

public class GameHandler {

	private GameDao gameDao;
	private MatchDao matchDao;

	public GameHandler() {
		this.gameDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getGameDao();
		this.matchDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getMatchDao();
	}

	public ObservableList<Game> getGamesList(long idMatch) {

		List<Game> gamesList = new ArrayList<Game>();
		gamesList = gameDao.findAll();
		
		for (Game game : gamesList) {
			if (game.getIdMatch() != idMatch)
				gamesList.remove(game);
		}

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

		Match match = matchDao.find(usermMatch.getIdMatch());
		int updated = 1;

		if (mail.equals(match.getPlayer1().getMail())) {

			game.setScore1(game.getScore1() + 1);
			gameDao.update(game);
		} else if (mail.equals(match.getPlayer2().getMail())) {
			game.setScore2(game.getScore2() + 1);
			gameDao.update(game);
		}

		if (updated == -1)
			return false;
		return true;
	}

	public void setWinner(Game game, int winner) {
		game.setWinner(winner);
		gameDao.update(game);
	}
	
	public List<Player> findWinner(Game game){
		Match match = new Match();
		match = matchDao.find(game.getMatch().getIdMatch());
		
		List<Player> players = new ArrayList<>();
		PlayerDao playerDao = DaoFactory.getInstance(DaoFactory.Type.HIBERNATE).getPlayerDao();
		Player player = new Player(false);
		
		player = playerDao.find(match.getIdPlayer1());
		players.add(player);
		player = playerDao.find(match.getIdPlayer2());
		
		return players;
		
	}
}

