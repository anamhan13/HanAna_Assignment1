package ro.utcn.sd.dao;

import java.util.List;

import ro.utcn.sd.model.Match;
import ro.utcn.sd.model.Player;
import ro.utcn.sd.model.Tournament;

public interface MatchDao extends Dao<Match> {

	Match find(long id);

	List<Match> findAll();

	List<Match> findByPlayer(Player player);

	List<Match> findByTournament(Tournament tournament);

	void delete(Match objectToDelete);

	void update(Match objectToUpdate);

	void insert(Match objectToCreate);

	void deleteById(long id);

	void closeConnection();

}