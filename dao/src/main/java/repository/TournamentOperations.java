package repository;

import java.util.List;

import model.Tournament;

public interface TournamentOperations {

	public Tournament find(String col, int id);
	
	public List<Tournament> findAll();

	public int insert(Tournament game);

	public int update(Tournament game, int id);

	public int delete(int id);

}
