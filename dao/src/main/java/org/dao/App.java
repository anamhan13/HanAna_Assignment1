package org.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.dao.connection.ConnectionFactory;

import model.Match;
import model.Tournament;
import repository.MatchRepository;
import repository.TournamentRepository;

/**
 * Hello world!
 *
 */

public class App {

	public static void main(String[] args) {

		Connection connection = ConnectionFactory.getConnection();

		MatchRepository matchRepository = new MatchRepository(connection);
		List<Match> matchesList = new ArrayList<Match>();
		matchesList = matchRepository.findByTournament(2);

		if (matchesList.size() != 0)
			for (Match match : matchesList) {
				System.out.println(match.getIdMatch());
			}
		
		TournamentRepository tournamentRepository = new TournamentRepository(connection);
		Tournament tournament =  tournamentRepository.find("idtournament", 2);
		System.out.println(tournament.getStatus());
	}
}
