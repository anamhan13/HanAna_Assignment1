package repository;

import java.util.List;

import model.Match;

public interface MatchOperations {

	public Match find(String col, int id);
	
	public List<Match> findByPlayer(String mail);
	
	public List<Match> findByTournament(int id);
	
	public List<Match> findAll();

	public int insert(Match game);

	public int update(Match game, int id);

	public int delete(int id);

}
