package org.business;

import java.sql.Connection;

import org.dao.connection.ConnectionFactory;

import handlers.GameHandler;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import model.Game;
import model.Match;
import repository.GameRepository;
import repository.MatchRepository;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testApp() {
		assertTrue(true);
	}

	// public void testDeleteTournament()
	// {
	// TournamentHandler tHandler = new TournamentHandler();
	// Connection connection = ConnectionFactory.getConnection();
	// TournamentRepository tournamentRepository = new
	// TournamentRepository(connection);
	// Tournament tournament = tournamentRepository.find("idtournament", 4);
	// assertEquals(tHandler.deleteTournament(tournament), true);
	// }

	// public void testCreateTournament(){
	// TournamentHandler tHandler = new TournamentHandler();
	// assertEquals(tHandler.createTournament("pingpong", "ongoing",
	// "2018-07-11", "2018-08-01", "boston"), true);
	// }

	
	public void testUpdateScore() {
		GameHandler gameHandler = new GameHandler();
		Connection connection = ConnectionFactory.getConnection();
		
		MatchRepository matchRepository = new MatchRepository(connection);
		Match match = matchRepository.find("idmatch", 3);
		
		GameRepository gameRepository = new GameRepository(connection);
		Game game = gameRepository.find("idgame", 3);
		
		Assert.assertEquals(gameHandler.updateScore(match, "dana@hotmail.com", game), true);
		
		game = gameRepository.find("idgame", 3);
		Assert.assertEquals(game.getScore1(), 11);
	}

}
